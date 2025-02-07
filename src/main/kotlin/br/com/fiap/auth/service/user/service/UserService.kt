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
        val name = requireNotNull(request.name) { "O campo 'name' é obrigatório." }
        val email = requireNotNull(request.email) { "O campo 'email' é obrigatório." }
        val password = requireNotNull(request.password) { "O campo 'password' é obrigatório." }

        if (userRepository.findByEmail(email) != null) {
            throw UserAlreadyExistsException("E-mail '$email' já está em uso.")
        }

        val user = User(
            name = name,
            email = email,
            password = passwordEncoder.encode(password)
        )
        val savedUser = userRepository.save(user)

        // Enviar e-mail de boas-vindas
        emailNotificationService.sendWelcomeEmail(
            email = savedUser.email,
            name = savedUser.name,
            password = password
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

        val name = requireNotNull(request.name) { "O campo 'name' é obrigatório." }
        val email = requireNotNull(request.email) { "O campo 'email' é obrigatório." }
        val password = requireNotNull(request.password) { "O campo 'password' é obrigatório." }

        val updatedUser = existingUser.copy(
            name = name,
            email = email,
            password = passwordEncoder.encode(password)
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
