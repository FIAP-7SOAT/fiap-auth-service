//package br.com.fiap.auth.service.configuration
//
//import jakarta.mail.Authenticator
//import jakarta.mail.PasswordAuthentication
//import jakarta.mail.Session
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.mail.javamail.JavaMailSender
//import org.springframework.mail.javamail.JavaMailSenderImpl
//import java.util.*
//
//@Configuration
//class MailConfig {
//
//    @Bean
//    fun javaMailSender(): JavaMailSender {
//        val props = Properties()
//        props["mail.smtp.host"] = "smtp.exemplo.com"
//        props["mail.smtp.port"] = "587"
//        props["mail.smtp.auth"] = "true"
//        props["mail.smtp.starttls.enable"] = "true"
//
//        // Using javax.mail.Authenticator
//        val session = Session.getInstance(props, object : Authenticator() {
//            override fun getPasswordAuthentication(): PasswordAuthentication {
//                return PasswordAuthentication("usuario", "senha")
//            }
//        })
//
//        return JavaMailSenderImpl().apply {
//            setSession(session)
//        }
//    }
//}
