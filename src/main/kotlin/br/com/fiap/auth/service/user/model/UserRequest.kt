package br.com.fiap.auth.service.user.model

data class UserRequest(
    val name: String,
    val email: String,
    val password: String
)
