-- Table: TB_USERS
CREATE TABLE IF NOT EXISTS TB_USERS (
    id UUID NOT NULL DEFAULT gen_random_uuid(), -- Gera UUID automaticamente (PostgreSQL)
    name VARCHAR(255) NOT NULL,                -- Nome do usuário
    email VARCHAR(255) NOT NULL UNIQUE,        -- Email único
    password VARCHAR(255) NOT NULL,            -- Senha do usuário
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Data de criação
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Última atualização
    CONSTRAINT tb_users_pkey PRIMARY KEY (id) -- Chave primária
);
