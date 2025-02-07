package br.com.fiap.auth.service.exception

import br.com.fiap.auth.service.user.dto.ErrorResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.allErrors.map { error ->
            if (error is FieldError) {
                when (error.field) {
                    "name" -> "O campo 'name' é obrigatório."
                    "email" -> "O campo 'email' é obrigatório e deve ser válido."
                    "password" -> "O campo 'password' é obrigatório."
                    else -> "${error.field}: ${error.defaultMessage}"
                }
            } else {
                error.defaultMessage ?: "Erro de validação desconhecido."
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(errors.joinToString("; ")))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ex.message ?: "Requisição inválida."))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("Erro interno do servidor: ${ex.localizedMessage}"))
    }
}
