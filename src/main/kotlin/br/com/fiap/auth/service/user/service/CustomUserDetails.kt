package br.com.fiap.auth.service.user.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val userId: String,
    private val email: String,
    private val password: String,
    authorities: Collection<GrantedAuthority>
) : User(email, password, authorities), UserDetails {

    // Método público para acessar o userId
    fun getUserId(): String {
        return userId
    }
}
