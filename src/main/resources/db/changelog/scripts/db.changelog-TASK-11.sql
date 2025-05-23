-- liquibase formatted sql

-- changeset rafa-guedes:TASK-10 dbms:postgresql

INSERT INTO tb_address (id, logradouro, numero, bairro, localidade, uf, estado, cep, complemento, regiao, ibge, gia, ddd, siafi)
VALUES
(1, 'Rua A', '123', 'Bairro 1', 'Cidade 1', 'SP', 'São Paulo', '12345-678', 'Apto 1', 'Sudeste', '1234567', '1234', '11', '1234'),
(2, 'Rua B', '456', 'Bairro 2', 'Cidade 2', 'RJ', 'Rio de Janeiro', '23456-789', 'Casa', 'Sudeste', '2345678', '2345', '21', '2345'),
(3, 'Rua C', '789', 'Bairro 3', 'Cidade 3', 'MG', 'Minas Gerais', '34567-890', NULL, 'Sudeste', '3456789', '3456', '31', '3456'),
(4, 'Rua D', '101', 'Bairro 4', 'Cidade 4', 'ES', 'Espírito Santo', '45678-901', 'Bloco B', 'Sudeste', '4567890', '4567', '27', '4567'),
(5, 'Rua E', '202', 'Bairro 5', 'Cidade 5', 'PR', 'Paraná', '56789-012', NULL, 'Sul', '5678901', '5678', '41', '5678'),
(6, 'Rua F', '303', 'Bairro 6', 'Cidade 6', 'SC', 'Santa Catarina', '67890-123', 'Apto 2', 'Sul', '6789012', '6789', '48', '6789'),
(7, 'Rua G', '404', 'Bairro 7', 'Cidade 7', 'RS', 'Rio Grande do Sul', '78901-234', NULL, 'Sul', '7890123', '7890', '51', '7890'),
(8, 'Rua H', '505', 'Bairro 8', 'Cidade 8', 'BA', 'Bahia', '89012-345', 'Casa', 'Nordeste', '8901234', '8901', '71', '8901'),
(9, 'Rua I', '606', 'Bairro 9', 'Cidade 9', 'PE', 'Pernambuco', '90123-456', NULL, 'Nordeste', '9012345', '9012', '81', '9012'),
(10, 'Rua J', '707', 'Bairro 10', 'Cidade 10', 'CE', 'Ceará', '01234-567', 'Bloco A', 'Nordeste', '0123456', '0123', '85', '0123');

INSERT INTO tb_users (id, name, email, registry, address_id)
VALUES
(1, 'Usuário 1', 'user1@example.com', 'REG001', 1),
(2, 'Usuário 2', 'user2@example.com', 'REG002', 2),
(3, 'Usuário 3', 'user3@example.com', 'REG003', 3),
(4, 'Usuário 4', 'user4@example.com', 'REG004', 4),
(5, 'Usuário 5', 'user5@example.com', 'REG005', 5),
(6, 'Usuário 6', 'user6@example.com', 'REG006', 6),
(7, 'Usuário 7', 'user7@example.com', 'REG007', 7),
(8, 'Usuário 8', 'user8@example.com', 'REG008', 8),
(9, 'Usuário 9', 'user9@example.com', 'REG009', 9),
(10, 'Usuário 10', 'user10@example.com', 'REG010', 10);

INSERT INTO tb_item (id, name, description, item_type, serial_number, status, created_at, updated_at)
VALUES
(1, 'Item 1', 'Descrição do Item 1', 'LAPTOP', 'SN001', 'AVAILABLE', NOW(), NOW()),
(2, 'Item 2', 'Descrição do Item 2', 'PROJECTOR', 'SN002', 'AVAILABLE', NOW(), NOW()),
(3, 'Item 3', 'Descrição do Item 3', 'CAMERA', 'SN003', 'RESERVED', NOW(), NOW()),
(4, 'Item 4', 'Descrição do Item 4', 'LAPTOP', 'SN004', 'AVAILABLE', NOW(), NOW()),
(5, 'Item 5', 'Descrição do Item 5', 'PROJECTOR', 'SN005', 'RESERVED', NOW(), NOW()),
(6, 'Item 6', 'Descrição do Item 6', 'CAMERA', 'SN006', 'AVAILABLE', NOW(), NOW()),
(7, 'Item 7', 'Descrição do Item 7', 'LAPTOP', 'SN007', 'AVAILABLE', NOW(), NOW()),
(8, 'Item 8', 'Descrição do Item 8', 'PROJECTOR', 'SN008', 'RESERVED', NOW(), NOW()),
(9, 'Item 9', 'Descrição do Item 9', 'CAMERA', 'SN009', 'AVAILABLE', NOW(), NOW()),
(10, 'Item 10', 'Descrição do Item 10', 'LAPTOP', 'SN010', 'AVAILABLE', NOW(), NOW());

INSERT INTO tb_reservation (id, date_time, start_time, end_time, code, status, user_id, item_id, created_at, updated_at)
VALUES
(1, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE001', 'PENDING', 1, 1, NOW(), NOW()),
(2, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE002', 'IN_PROGRESS', 2, 2, NOW(), NOW()),
(3, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE003', 'IN_PROGRESS', 3, 3, NOW(), NOW()),
(4, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE004', 'PENDING', 4, 4, NOW(), NOW()),
(5, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE005', 'CONFIRMED', 5, 5, NOW(), NOW()),
(6, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE006', 'COMPLETED', 6, 6, NOW(), NOW()),
(7, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE007', 'PENDING', 7, 7, NOW(), NOW()),
(8, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE008', 'CONFIRMED', 8, 8, NOW(), NOW()),
(9, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE009', 'COMPLETED', 9, 9, NOW(), NOW()),
(10, NOW(), NOW(), NOW() + INTERVAL '1 HOUR', 'CODE010', 'IN_PROGRESS', 10, 10, NOW(), NOW());