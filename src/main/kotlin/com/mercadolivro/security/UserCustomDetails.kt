package com.mercadolivro.security

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/* Criando customDetails onde ele recebe um customer model e valida algumas coisas.
* Pega senha, id, verifica se ascredentials estão expiradas e pega o status e compara com ativo  */
class UserCustomDetails(val customerModel: CustomerModel): UserDetails {
    // passando para o spring como verificar metodos.
    val id: Int = customerModel.id!!


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = customerModel.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    // Returna a senha do consumidor
    override fun getPassword(): String = customerModel.password

    // Neste caso estamos tratando o userName como id
    override fun getUsername(): String = customerModel.id.toString()

    //Não se aplica X
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    //Não se aplica X

    override fun isCredentialsNonExpired(): Boolean = true

    // O usuário esta ativo na base de dados
    override fun isEnabled(): Boolean = customerModel.status == CustomerStatus.ATIVO
}