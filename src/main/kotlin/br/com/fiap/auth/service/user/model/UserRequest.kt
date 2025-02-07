package br.com.fiap.auth.service.user.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequest(
    @field:NotBlank
    val name: String? = null,  // Permite que Jackson processe o JSON corretamente

    @field:NotBlank
    @field:Email
    val email: String? = null,

    @field:NotBlank
    val password: String? = null
)
