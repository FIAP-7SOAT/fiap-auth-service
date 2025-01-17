package br.com.fiap.auth.service.user.service

import br.com.fiap.auth.service.exception.UserAlreadyExistsException
import br.com.fiap.auth.service.exception.UserNotFoundException
import br.com.fiap.auth.service.notification.service.EmailNotificationService
import br.com.fiap.auth.service.user.model.User
import br.com.fiap.auth.service.user.model.UserRequest
import br.com.fiap.auth.service.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailNotificationService: EmailNotificationService
) {

    fun createUser(request: UserRequest): User {
        if (userRepository.findByEmail(request.email) != null) {
            throw UserAlreadyExistsException("E-mail '${request.email}' já está em uso.")
        }

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )
        val savedUser = userRepository.save(user)

        // Enviar e-mail de boas-vindas
        emailNotificationService.sendWelcomeEmail(
            email = savedUser.email,
            name = savedUser.name,
            password = request.password
        )

        return savedUser
    }

    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw UserNotFoundException("Usuário com o e-mail '$email' não encontrado.")
    }

    fun getUserById(id: UUID): User {
        return userRepository.findById(id).orElseThrow {
            UserNotFoundException("Usuário com o ID '$id' não encontrado.")
        }
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun updateUser(id: UUID, request: UserRequest): User {
        val existingUser = userRepository.findById(id).orElseThrow {
            UserNotFoundException("Usuário com o ID '$id' não encontrado.")
        }

        val updatedUser = existingUser.copy(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )
        return userRepository.save(updatedUser)
    }

    fun deleteUser(id: UUID): Boolean {
        val user = userRepository.findById(id).orElseThrow {
            UserNotFoundException("Usuário com o ID '$id' não encontrado.")
        }
        userRepository.delete(user)
        return true
    }
}
