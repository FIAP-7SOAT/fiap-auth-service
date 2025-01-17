package br.com.fiap.auth.service.user.model

import java.util.*

data class UserResponse(
    val id: UUID,
    val name: String,
    val email: String
)
