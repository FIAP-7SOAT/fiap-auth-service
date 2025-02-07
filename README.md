# Arquitetura do fiap-auth-service

Este documento fornece uma visão geral da arquitetura da aplicação `fiap-auth-service`, destacando o modelo de **arquitetura em camadas** adotado e o papel essencial dessa aplicação como um **microserviço de autenticação** para outros microserviços no ecossistema.

## Visão Geral

O `fiap-auth-service` é um microserviço desenvolvido para fornecer funcionalidades de **autenticação** e **gerenciamento de usuários**. Ele atua como um **ponto centralizado de autenticação** para outros microserviços no sistema, como o `fiap-video-processor`, `fiap-video-management` e `fiap-notification-service`.

A aplicação segue a arquitetura **em camadas**, o que facilita a separação de responsabilidades e promove a manutenção e escalabilidade do sistema. As camadas principais da aplicação são:

- **Camada de Apresentação (Controller)**: Responsável por lidar com as requisições HTTP.
- **Camada de Serviço (Service)**: Contém a lógica de negócios, como autenticação e gerenciamento de usuários.
- **Camada de Persistência (Repository)**: Interage diretamente com o banco de dados para armazenar e recuperar dados dos usuários.

A arquitetura em camadas é um padrão amplamente utilizado para garantir que a aplicação seja modular e de fácil manutenção, permitindo que as diferentes partes da aplicação sejam desenvolvidas, testadas e modificadas de forma isolada.

## Propósito da Aplicação

O `fiap-auth-service` foi desenvolvido como um microserviço que fornece as funcionalidades de **autenticação** e **gestão de usuários**. Ele será consumido por outros microserviços no sistema, garantindo que cada um desses serviços tenha um meio seguro de autenticar usuários e garantir a integridade das informações.

### Microserviços Consumidores

O `fiap-auth-service` é parte de um ecossistema maior de microserviços que inclui os seguintes componentes:

1. **fiap-video-processor**: Processar vídeos enviados para o sistema. Extrair 10 imagens de um vídeo usando FFmpeg. Compactar as imagens em um arquivo .zip e fazer upload para o AWS S3. Notificar outros serviços sobre o status do processamento.
2. **fiap-video-management**: Listar vídeos associados a um usuário. Exibir status e metadados de vídeos. Centralizar dados para consultas por outras APIs ou interfaces.
3. **fiap-notification-service**: Consumir eventos de sucesso/falha de processamento (via SQS). Enviar notificações (e.g., e-mail via MailDev). Registrar logs e implementar retentativas para envios falhados.

O `fiap-auth-service` fornece uma **Basic Authentication** utilizando o **Spring Security** para autenticação. Ao fornecer as credenciais de login (usuário e senha), o serviço valida as credenciais e garante que os outros microserviços possam acessar os recursos do usuário de maneira segura.

## Arquitetura em Camadas

A aplicação segue o padrão de **arquitetura em camadas**, o que significa que as responsabilidades são distribuídas de forma organizada entre diferentes componentes da aplicação. As camadas principais são:

### 1. Camada de Apresentação (Controller)
- Responsável por receber e processar as requisições HTTP.
- Lida com os endpoints de login, registro e gerenciamento de usuários.
- Exemplo de um controlador que lida com autenticação.

#### Endpoints do `UserController`
O `UserController` fornece os seguintes endpoints para gerenciar usuários:

- **POST /users**: Criação de um novo usuário. Após a criação, um e-mail de notificação é enviado ao usuário utilizando o **DevMail**, contendo os dados de login.
- **GET /users/{id}**: Recupera os dados de um usuário específico pelo seu ID.
- **GET /users**: Recupera todos os usuários cadastrados.
- **PUT /users/{id}**: Atualiza os dados de um usuário existente.
- **DELETE /users/{id}**: Exclui um usuário pelo seu ID.
- **GET /users/test-email**: Envia um e-mail de teste de notificação para verificar se o serviço de e-mails está funcionando.

### 2. Camada de Serviço (Service)
- Contém a lógica de negócios, como a autenticação dos usuários e a validação de credenciais.
- Interage com a camada de persistência para recuperar ou salvar dados dos usuários.
- Utiliza o serviço **EmailNotificationService** para enviar e-mails quando um novo usuário é criado.

### 3. Camada de Persistência (Repository)
- Interage diretamente com o banco de dados PostgreSQL.
- Realiza operações de CRUD (Create, Read, Update, Delete) para gerenciar dados dos usuários.

A separação dessas camadas garante que cada componente seja isolado, facilitando a manutenção, testes e escalabilidade da aplicação.

## Tecnologias Utilizadas

A aplicação `fiap-auth-service` é construída utilizando as seguintes tecnologias:

- **Java 17** e **Kotlin**: Linguagens utilizadas para implementar a lógica de negócios.
- **Spring Boot**: Framework utilizado para a criação e execução da aplicação.
- **Spring Security**: Para implementar a autenticação com **Basic Authentication**.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar as informações dos usuários.
- **Docker**: Para criação e gerenciamento de containers.
- **Kubernetes**: Para orquestração e escalabilidade da aplicação em produção.
- **DevMail**: Serviço utilizado para enviar e-mails de notificação, como por exemplo, ao criar um novo usuário.

## Fluxo de Autenticação

O fluxo de autenticação no `fiap-auth-service` é simples e eficaz, sendo baseado no **Basic Authentication** fornecido pelo **Spring Security**:

1. O usuário envia suas credenciais (usuário e senha) para o endpoint de login, utilizando **Basic Authentication**.
2. A aplicação valida as credenciais utilizando **Spring Security**. Se forem corretas, o usuário é autenticado.
3. Com a autenticação realizada, o usuário pode acessar recursos protegidos em outros microserviços que consomem esse serviço de autenticação.

Os outros microserviços (como `fiap-video-processor`, `fiap-video-management` e `fiap-notification-service`) irão utilizar a **autenticação básica** fornecida por este serviço para validar se o usuário tem permissão para acessar suas respectivas funcionalidades.

## Links para Outros README

Abaixo estão os links para os README que detalham os requisitos, configuração de infraestrutura e outros aspectos importantes do projeto:

- [database.md](./database.md): Informações detalhadas sobre a configuração do banco de dados, incluindo como configurar o PostgreSQL para a aplicação.
- [docker.md](./docker.md): Guia de como criar e configurar os containers Docker para o serviço e banco de dados.
- [kubernetes.md](./kubernetes.md): Documentação sobre a configuração do Kubernetes, incluindo comandos `kubectl` para gerenciamento dos serviços em clusters.
- [requirements.md](./requirements.md): Detalhes sobre os pré-requisitos necessários para rodar o serviço, incluindo requisitos de software e hardware.

## Como Usar

Para executar a aplicação, siga os passos nos documentos de **Docker** e **Kubernetes**, além de garantir que os requisitos de software estão atendidos conforme listado no **requirements.md**.

## Conclusão

A arquitetura em camadas adotada para o `fiap-auth-service` promove a organização e a modularidade do código, facilitando a manutenção e a escalabilidade. Além disso, como um microserviço, o `fiap-auth-service` desempenha um papel crucial no ecossistema de microserviços, fornecendo uma solução centralizada e segura para a autenticação de usuários.

Essa abordagem modular permite que a aplicação seja facilmente integrada com outros serviços, como o `fiap-video-processor`, `fiap-video-management` e `fiap-notification-service`, garantindo uma solução eficiente para a gestão de usuários em toda a plataforma.
