package com.mercadolivro.config

import com.mercadolivro.enums.Role
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
) : WebSecurityConfigurerAdapter() {

    /* Lista de urls sem autenticação */
    private val PUBLIC_MATCHERS = arrayOf<String>()

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customer"
    )

    private val ADMIN_MATCHERS = arrayOf("/admin/**")

    /* Passando a melhor forma de autenticação a partir do identificador recuperar o usuário
    * customer */
    override fun configure(auth: AuthenticationManagerBuilder) {
        /* Passando o Encoder para o spring decodificar ou codificar senha
        * Para ele conseguir de fato bater as senhas => bCryptPasswordEncoder*/
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        /* desativando cors */
        http.cors().and().csrf().disable()

        /* Todas as requisições que chegam devem estar autenticadas */
        http.authorizeRequests()
        /* Rotas sem autenticação obrigatória */
        .antMatchers(*PUBLIC_MATCHERS).permitAll()
        .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()

         /* Rotas exclusivas para administradores do sistema */
        .antMatchers(*ADMIN_MATCHERS).hasAnyAuthority(Role.ADMIN.description)
        .anyRequest().authenticated()

        /* Filtro de autenticaçã passando authenticationManager direto do spring e customerRepository via injeção de dependência linha 22*/
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))

        // requisições independentes as requisições podem ser de usuários diferentes.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
}

@Bean
fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
return BCryptPasswordEncoder()
}

}