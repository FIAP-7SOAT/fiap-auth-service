package br.com.fiap.auth.service.notification.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailNotificationService(private val mailSender: JavaMailSender) {

    fun sendWelcomeEmail(email: String, name: String, password: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(email)
        helper.setSubject("Bem-vindo ao nosso serviço!")
        helper.setText("""
            Olá $name,

            Bem-vindo à nossa plataforma!

            Aqui estão seus dados de acesso:
            Login: $email
            Senha: $password

            Atenciosamente,
            Equipe de Suporte.
        """.trimIndent(), false)
        mailSender.send(message)
    }
}
