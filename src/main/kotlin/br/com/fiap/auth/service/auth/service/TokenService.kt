package br.com.fiap.auth.service.auth.service

import br.com.fiap.auth.service.user.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS512) // Gera uma chave segura automaticamente

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.email)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
            .signWith(key)
            .compact()
    }
}