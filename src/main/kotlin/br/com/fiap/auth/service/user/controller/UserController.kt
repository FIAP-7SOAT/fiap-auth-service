package br.com.fiap.auth.service.user.controller

import UserResponse
import br.com.fiap.auth.service.notification.service.EmailNotificationService
import br.com.fiap.auth.service.user.dto.ErrorResponse
import br.com.fiap.auth.service.user.model.UserRequest
import br.com.fiap.auth.service.user.service.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
@Validated
class UserController(
    private val userService: UserService,
    private val emailNotificationService: EmailNotificationService
) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping
    fun createUser(@Valid @RequestBody request: UserRequest): ResponseEntity<Any> {
        return try {
            logger.info("Criando usuário: ${request.email}")
            val user = userService.createUser(request)
            ResponseEntity.ok(UserResponse.fromEntity(user))
        } catch (e: AccessDeniedException) {
            // Acesso negado
            ResponseEntity.status(403).body(ErrorResponse("Acesso proibido: Você não tem permissão para criar um usuário."))
        } catch (e: Exception) {
            // Outros erros
            ResponseEntity.status(500).body(ErrorResponse("Erro interno do servidor"))
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    fun getUserById(@PathVariable id: UUID, authentication: Authentication): ResponseEntity<Any> {
        return try {
            logger.info("Buscando usuário com ID: $id")
            val user = userService.getUserById(id)
            ResponseEntity.ok(UserResponse.fromEntity(user))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorResponse("Erro interno do servidor"))
        }
    }


    @GetMapping
    fun getAllUsers(): ResponseEntity<Any> {
        return try {
            logger.info("Buscando todos os usuários")
            val users = userService.getAllUsers().map(UserResponse::fromEntity)
            ResponseEntity.ok(users)
        } catch (e: AccessDeniedException) {
            ResponseEntity.status(403).body(ErrorResponse("Acesso proibido: Você não tem permissão para visualizar os usuários."))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorResponse("Erro interno do servidor"))
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    fun updateUser(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UserRequest
    ): ResponseEntity<Any> {
        return try {
            logger.info("Atualizando usuário com ID: $id")
            val updatedUser = userService.updateUser(id, request)
            ResponseEntity.ok(UserResponse.fromEntity(updatedUser))
        } catch (e: AccessDeniedException) {
            ResponseEntity.status(403).body(ErrorResponse("Acesso proibido: Você não tem permissão para atualizar este usuário."))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorResponse("Erro interno do servidor"))
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Any> {
        return try {
            logger.info("Excluindo usuário com ID: $id")
            userService.deleteUser(id)
            ResponseEntity.noContent().build()
        } catch (e: AccessDeniedException) {
            ResponseEntity.status(403).body(ErrorResponse("Acesso proibido: Você não tem permissão para excluir este usuário."))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorResponse("Erro interno do servidor"))
        }
    }
}

