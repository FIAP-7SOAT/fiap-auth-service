package br.com.fiap.auth.service.auth.controller

import br.com.fiap.auth.service.auth.model.AuthRequest
import br.com.fiap.auth.service.auth.service.AuthResponse
import br.com.fiap.auth.service.auth.service.AuthService
import br.com.fiap.auth.service.user.service.CustomUserDetails
import br.com.fiap.auth.service.user.service.CustomUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val authService: AuthService,
    private val customUserDetailsService: CustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(request.email)

        if (!passwordEncoder.matches(request.password, userDetails.password)) {
            throw BadCredentialsException("Senha inválida")
        }

        val authToken = UsernamePasswordAuthenticationToken(userDetails, request.password, userDetails.authorities)
        authenticationManager.authenticate(authToken)

        val userId = (userDetails as CustomUserDetails).getUserId()
        val roles = userDetails.authorities.map { it.authority } // Obtendo as roles do usuário

        val token = authService.generateToken(request.email, userId, roles)
        return ResponseEntity.ok(AuthResponse(token))
    }

}
