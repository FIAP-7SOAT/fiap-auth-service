package br.com.fiap.auth.service

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class BeanPrinter(context: ApplicationContext) {
    init {
        println("===== Beans registrados no contexto da aplicação =====")
        context.beanDefinitionNames.sorted().forEach { println(it) }
        println("======================================================")
    }
}
