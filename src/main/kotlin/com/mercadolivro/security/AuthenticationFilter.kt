package com.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mercadolivro.controller.request.LoginRequest
import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/* Criando AuthenticationFilter que extende do  UsernamePasswordAuthenticationFilter que faz parte do  spring security*/
class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    /* Quando chamar a url '/login' acontecerar uma tentativa de autenticação*/
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
         try {
             /* Pegar email  e senha a partir da referencia de um objeto de requeste: LoginRequest  */
             /* Pegaremos o que vier no body do login no nosso objeto de login   */
             val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java) // A partir daqui a gente vai receber o email e a senha



             /* Depois disso vamos pegar o findByEmail para a gente conseguirmos recuperar o id através desse email  */
             val id = customerRepository.findByEmail(loginRequest.email)?.id

             /* Uma vez que recuperamos o id criaremos o authToken passando o id e senha*/
             val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.password)

             /* retornamos o authenticate o próprio spring faz as autenticações e verificando se está tudo certo. */
             return authenticationManager.authenticate(authToken)

         } catch (ex: Exception) {
             /* */
             throw AuthenticationException("Falha ao autenticar", "409")
         }
    }

    /* Aqui recuperamos e geramos nosso token*/
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        // recuperando id
        val id = (authResult.principal as UserCustomDetails).id
        val token = jwtUtil.generateToken(id)
        response.addHeader("Authorization", "Bearer $token")
    }

}