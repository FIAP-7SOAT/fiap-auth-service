package br.com.fiap.auth.service.user.service

import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    @Lazy private val userService: UserService // 🔹 Adicionando @Lazy para evitar ciclo
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByEmail(username)
            ?: throw UsernameNotFoundException("Usuário não encontrado")

        // Retorna CustomUserDetails incluindo o userId
        return CustomUserDetails(
            user.id.toString(), // Supondo que o `user.id` seja do tipo Long ou UUID, converta para String
            user.email,
            user.password,
            listOf() // Sem roles, conforme mencionado
        )
    }

}
