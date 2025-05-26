-- liquibase formatted sql

-- changeset rafa-guedes:TASK-10 dbms:postgresql

INSERT INTO tb_address (logradouro, numero, bairro, localidade, uf, estado, cep, complemento, regiao, ibge, gia, ddd, siafi)
VALUES
('Rua A', '123', 'Bairro 1', 'Cidade 1', 'SP', 'São Paulo', '12345-678', 'Apto 1', 'Sudeste', '1234567', '1234', '11', '1234'),
('Rua B', '456', 'Bairro 2', 'Cidade 2', 'RJ', 'Rio de Janeiro', '23456-789', 'Casa', 'Sudeste', '2345678', '2345', '21', '2345'),
('Rua C', '789', 'Bairro 3', 'Cidade 3', 'MG', 'Minas Gerais', '34567-890', NULL, 'Sudeste', '3456789', '3456', '31', '3456'),
('Rua D', '101', 'Bairro 4', 'Cidade 4', 'ES', 'Espírito Santo', '45678-901', 'Bloco B', 'Sudeste', '4567890', '4567', '27', '4567'),
('Rua E', '202', 'Bairro 5', 'Cidade 5', 'PR', 'Paraná', '56789-012', NULL, 'Sul', '5678901', '5678', '41', '5678'),
('Rua F', '303', 'Bairro 6', 'Cidade 6', 'SC', 'Santa Catarina', '67890-123', 'Apto 2', 'Sul', '6789012', '6789', '48', '6789'),
('Rua G', '404', 'Bairro 7', 'Cidade 7', 'RS', 'Rio Grande do Sul', '78901-234', NULL, 'Sul', '7890123', '7890', '51', '7890'),
('Rua H', '505', 'Bairro 8', 'Cidade 8', 'BA', 'Bahia', '89012-345', 'Casa', 'Nordeste', '8901234', '8901', '71', '8901'),
('Rua I', '606', 'Bairro 9', 'Cidade 9', 'PE', 'Pernambuco', '90123-456', NULL, 'Nordeste', '9012345', '9012', '81', '9012'),
('Rua J', '707', 'Bairro 10', 'Cidade 10', 'CE', 'Ceará', '01234-567', 'Bloco A', 'Nordeste', '0123456', '0123', '85', '0123');

INSERT INTO tb_users (name, email, registry, address_id)
VALUES
('Usuário 1', 'user1@example.com', 'REG001', 1),
('Usuário 2', 'user2@example.com', 'REG002', 2),
('Usuário 3', 'user3@example.com', 'REG003', 3),
('Usuário 4', 'user4@example.com', 'REG004', 4),
('Usuário 5', 'user5@example.com', 'REG005', 5),
('Usuário 6', 'user6@example.com', 'REG006', 6),
('Usuário 7', 'user7@example.com', 'REG007', 7),
('Usuário 8', 'user8@example.com', 'REG008', 8),
('Usuário 9', 'user9@example.com', 'REG009', 9),
('Usuário 10', 'user10@example.com', 'REG010', 10);

INSERT INTO tb_item (name, description, item_type, serial_number, status, created_at, updated_at)
VALUES
('Item 1', 'Descrição do Item 1', 'LAPTOP', 'SN001', 'AVAILABLE', NOW(), NOW()),
('Item 2', 'Descrição do Item 2', 'PROJECTOR', 'SN002', 'AVAILABLE', NOW(), NOW()),
('Item 3', 'Descrição do Item 3', 'CAMERA', 'SN003', 'RESERVED', NOW(), NOW()),
('Item 4', 'Descrição do Item 4', 'LAPTOP', 'SN004', 'AVAILABLE', NOW(), NOW()),
('Item 5', 'Descrição do Item 5', 'PROJECTOR', 'SN005', 'RESERVED', NOW(), NOW()),
('Item 6', 'Descrição do Item 6', 'CAMERA', 'SN006', 'AVAILABLE', NOW(), NOW()),
('Item 7', 'Descrição do Item 7', 'LAPTOP', 'SN007', 'AVAILABLE', NOW(), NOW()),
('Item 8', 'Descrição do Item 8', 'PROJECTOR', 'SN008', 'RESERVED', NOW(), NOW()),
('Item 9', 'Descrição do Item 9', 'CAMERA', 'SN009', 'AVAILABLE', NOW(), NOW()),
('Item 10', 'Descrição do Item 10', 'LAPTOP', 'SN010', 'AVAILABLE', NOW(), NOW());