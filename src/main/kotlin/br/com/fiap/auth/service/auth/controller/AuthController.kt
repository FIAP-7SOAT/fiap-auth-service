package br.com.fiap.auth.service.auth.controller

import br.com.fiap.auth.service.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {

    @GetMapping("/me")
    fun getAuthenticatedUser(@AuthenticationPrincipal user: User): ResponseEntity<Any> {
        val userInfo = userService.findByEmail(user.username)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(mapOf("email" to userInfo.email, "name" to userInfo.name))
    }
}
