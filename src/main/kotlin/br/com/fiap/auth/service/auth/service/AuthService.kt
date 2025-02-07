package br.com.fiap.auth.service.auth.service

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class AuthService(
    @Value("\${jwt.expiration}") private val expirationTime: Long,
    private val secretKey: SecretKey
) {
    fun generateToken(email: String, userId: String, roles: List<String>): String {
        val roleClaims = if (roles.isNotEmpty()) roles else listOf("USER") // Garante que pelo menos "USER" seja atribuído

        return Jwts.builder()
            .setSubject(email)
            .claim("userId", userId)
            .claim("roles", roleClaims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey)
            .compact()
    }
}

