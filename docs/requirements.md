
# Requisitos

Este documento lista os pré-requisitos necessários para executar o projeto Fiap Auth Service corretamente.

## Requisitos de Software

### Java 17+
Certifique-se de ter o Java Development Kit (JDK) instalado. O projeto foi desenvolvido utilizando o Java 17.
Verifique a instalação com o comando:

```bash
java -version
```

### Kotlin
O projeto utiliza Kotlin como linguagem principal. O suporte ao Kotlin já está integrado ao build tool Maven ou Gradle.

### Spring Boot
Framework utilizado para a criação e execução da aplicação.

### PostgreSQL 14+
Banco de dados utilizado para persistência.
Configure o PostgreSQL localmente ou utilize o ambiente Docker fornecido.

### Docker
Requerido para facilitar a criação e configuração do ambiente.
Instale o Docker e o Docker Compose:

- [Guia de instalação do Docker](https://docs.docker.com/get-docker/)
- [Guia de instalação do Docker Compose](https://docs.docker.com/compose/install/)

### Postman (Opcional)
Para testar os endpoints da API.
A coleção Postman já está disponível em `src/main/resources/collection/Fiap Auth Service API.postman_collection.json`.

## Requisitos de Hardware

- **CPU**: Processador de 2 núcleos ou mais.
- **Memória RAM**: 4 GB ou mais recomendados.
- **Espaço em Disco**: Pelo menos 1 GB de espaço livre para o banco de dados e dependências.

## Configuração do Ambiente

### Configurações de Banco de Dados
Certifique-se de que o PostgreSQL esteja acessível no endereço configurado no `application.yml` ou use o ambiente Docker.

### MailDev (Para notificações de e-mail)
O MailDev é utilizado para capturar notificações de e-mail durante os testes. Ele está configurado no ambiente Docker e pode ser acessado em:

- **Interface Web**: [http://localhost:1080](http://localhost:1080)
- **Porta SMTP**: 1025

### Variáveis de Ambiente
Certifique-se de configurar as variáveis de ambiente necessárias ou ajustar os valores padrão no arquivo `application.yml`.

Exemplo de variáveis que podem precisar ser configuradas:

```yaml
spring.datasource.url: jdbc:postgresql://localhost:5432/fiap_auth_db
spring.datasource.username: <DB_USER>
spring.datasource.password: <DB_PASSWORD>
spring.mail.host: smtp.example.com
spring.mail.port: 587
spring.mail.username: <MAIL_USER>
spring.mail.password: <MAIL_PASSWORD>
```
