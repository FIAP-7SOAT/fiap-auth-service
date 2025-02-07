package br.com.fiap.auth.service.configuration

import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.crypto.SecretKey

@Component
class JwtAuthenticationFilter(
    private val secretKey: SecretKey
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.substring(7)

        if (token != null) {
            try {
                val claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .body

                val userId = claims["userId"]?.toString() ?: throw RuntimeException("User ID não encontrado no token")
                val roles = (claims["roles"] as? List<String>)?.map { "ROLE_$it" } ?: listOf("ROLE_USER")

                val authorities = roles.map { SimpleGrantedAuthority(it) }

                val userDetails = User(userId, "", authorities)
                val authToken = UsernamePasswordAuthenticationToken(userDetails, null, authorities)

                SecurityContextHolder.getContext().authentication = authToken
            } catch (e: Exception) {
                logger.error("Falha na validação do token JWT: ${e.message}")
            }
        }

        filterChain.doFilter(request, response)
    }
}

