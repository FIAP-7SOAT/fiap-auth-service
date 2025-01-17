package br.com.fiap.auth.service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<Any> {
        val errorDetails = ErrorDetails("Invalid argument", ex.message ?: "Error")
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.map { it as FieldError }
        val errorMessages = errors.map { "${it.field}: ${it.defaultMessage}" }
        val errorDetails = ErrorDetails("Validation failed", errorMessages.joinToString(", "))
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val errorDetails = ErrorDetails("Internal Server Error", ex.message ?: "Unknown error")
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    data class ErrorDetails(val title: String, val details: String)
}
