package br.com.fiap.auth.service.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter // Injetando o filtro diretamente
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeRequests {
                it.requestMatchers("/auth/login", "/users").permitAll() // Permitir login e criação de usuários
                it.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER", "ADMIN") // Apenas usuários autenticados
                it.requestMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("USER", "ADMIN")
                it.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN") // Apenas ADMIN pode deletar usuários
//                it.requestMatchers(HttpMethod.GET, "/users/{userId}").access("hasRole('USER') or hasRole('ADMIN')") // Permitir acesso a ADMIN ou ao próprio usuário (se necessário)
                it.requestMatchers(HttpMethod.GET, "/users/{userId}")
                    .access("hasRole('USER') and @userService.isUserAuthenticated(authentication.name, #userId) or hasRole('ADMIN')")
                it.anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java) // Corrigido

        return http.build()
    }
}
