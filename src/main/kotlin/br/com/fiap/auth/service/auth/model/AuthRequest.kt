package br.com.fiap.auth.service.auth.model

data class AuthRequest(
    val email: String,
    val password: String
)
