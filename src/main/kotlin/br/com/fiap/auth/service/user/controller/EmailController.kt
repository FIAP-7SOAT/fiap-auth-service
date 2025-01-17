package br.com.fiap.auth.service.user.controller

import br.com.fiap.auth.service.notification.service.EmailNotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/emails")
class EmailController(
    private val emailNotificationService: EmailNotificationService
) {

    @GetMapping("/test-email")
    fun testEmail(): ResponseEntity<String> {
        emailNotificationService.sendWelcomeEmail(
            email = "test@example.com",
            name = "Test User",
            password = "12345"
        )
        return ResponseEntity.ok("E-mail enviado com sucesso!")
    }
}
