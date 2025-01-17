package br.com.fiap.auth.service.auth.service

import br.com.fiap.auth.service.user.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder
) {

    fun authenticate(email: String, password: String): String {
        val user = userService.findByEmail(email) ?: throw IllegalArgumentException("Usuário não encontrado")
        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Senha inválida")
        }
        return tokenService.generateToken(user)
    }
}