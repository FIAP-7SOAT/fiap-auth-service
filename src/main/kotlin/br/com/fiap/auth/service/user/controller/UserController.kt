package br.com.fiap.auth.service.user.controller

import br.com.fiap.auth.service.notification.service.EmailNotificationService
import br.com.fiap.auth.service.user.model.UserRequest
import br.com.fiap.auth.service.user.model.UserResponse
import br.com.fiap.auth.service.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val emailNotificationService: EmailNotificationService


) {



    @GetMapping("/users/test-email")
    fun testEmail(): ResponseEntity<String> {

        emailNotificationService.sendWelcomeEmail(
            email = "morattojr@gmail.com",
            name = "Test User",
            password = "12345"
        )
        return ResponseEntity.ok("E-mail enviado com sucesso!")
    }


    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(request)
        return ResponseEntity.ok(UserResponse(user.id, user.name, user.email))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(UserResponse(user.id, user.name, user.email))
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.getAllUsers().map { user ->
            UserResponse(user.id, user.name, user.email)
        }
        return ResponseEntity.ok(users)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody request: UserRequest
    ): ResponseEntity<UserResponse> {
        val updatedUser = userService.updateUser(id, request)
        return ResponseEntity.ok(UserResponse(updatedUser.id, updatedUser.name, updatedUser.email))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}
