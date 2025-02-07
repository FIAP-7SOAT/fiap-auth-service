package br.com.fiap.auth.service.configuration

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey
import java.util.Base64

@Configuration
class JwtConfig(
    @Value("\${jwt.secret}") private val secret: String // Injeta a chave do application.properties
) {
    @Bean
    fun jwtSecretKey(): SecretKey {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)) // Converte a chave Base64 para SecretKey
    }
}
