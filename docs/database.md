# Documentação do Banco de Dados

A aplicação **Fiap Auth Service** utiliza um banco de dados simples com apenas uma tabela para o gerenciamento de usuários. Este documento detalha a estrutura do banco de dados e os motivos para a escolha do PostgreSQL como SGBD.

## Estrutura do Banco de Dados

### Tabela: TB_USERS

- **id** *(UUID, PK)*: Identificador único do usuário.
- **name** *(VARCHAR(255))*: Nome do usuário.
- **email** *(VARCHAR(255), único)*: Endereço de e-mail do usuário.
- **password** *(VARCHAR(255))*: Senha criptografada do usuário.
- **created_at** *(TIMESTAMP)*: Data e hora de criação do registro.
- **updated_at** *(TIMESTAMP)*: Data e hora da última atualização do registro.

### Relacionamentos

Atualmente, não há relacionamentos entre tabelas, pois a aplicação é focada exclusivamente no gerenciamento de usuários.

## Justificativa da Escolha do PostgreSQL

PostgreSQL foi selecionado como o Sistema de Gerenciamento de Banco de Dados (SGBD) devido às seguintes vantagens:

1. **Suporte a Tipos de Dados Complexos:**
   - O suporte a UUID garante identificadores únicos globais e é ideal para aplicações distribuídas.

2. **Conformidade ACID:**
   - Garantia de transações confiáveis e seguras, essencial para dados sensíveis como informações de usuários.

3. **Escalabilidade e Desempenho:**
   - Capacidade de lidar com grandes volumes de dados e consultas simultâneas.

4. **Comunidade Ativa e Extensões:**
   - Uma vasta comunidade que oferece suporte e extensões úteis para otimização de desempenho e funcionalidades adicionais.

## Considerações Finais

A estrutura simples do banco de dados foi projetada para atender às necessidades da aplicação Fiap Auth Service, permitindo expansões futuras caso necessário.

Caso precise de mais detalhes ou suporte, consulte a documentação oficial do PostgreSQL ou a equipe de desenvolvimento.

