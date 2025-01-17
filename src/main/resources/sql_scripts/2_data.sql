-- Inserindo o usuário admin na tabela TB_USERS
INSERT INTO tb_users
(id, "name", email, "password", created_at, updated_at)
VALUES(
'979230e1-7e62-4e0b-a939-2c2c987e88a1'::uuid,
'Usuario Administrador',
'admin@fiap.com',
'$2a$10$hI4.6tcvQauKkStDLrRRTe0aFhsNmHJiEKYBneHGM.0UNByWdl0VS',
'2025-01-17 18:53:49.561',
'2025-01-17 18:53:49.561');