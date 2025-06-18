-- liquibase formatted sql

-- changeset rafa-guedes:TASK-01 dbms:postgresql

CREATE TABLE IF NOT EXISTS tb_address (
    id BIGSERIAL PRIMARY KEY,
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    bairro VARCHAR(100),
    localidade VARCHAR(100),
    uf VARCHAR(2),
    estado VARCHAR(100),
    cep VARCHAR(10),
    complemento VARCHAR(255),
    regiao VARCHAR(100),
    ibge VARCHAR(20),
    gia VARCHAR(20),
    ddd VARCHAR(3),
    siafi VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'MANAGER', 'USER')),
    registry VARCHAR(50) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    address_id BIGINT,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES tb_address(id)
);

CREATE TABLE IF NOT EXISTS tb_item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    item_type VARCHAR(50) NOT NULL,
    serial_number VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_reservation (
    id BIGSERIAL PRIMARY KEY,
    date_time TIMESTAMP NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    item_id BIGINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES tb_users(id),
    FOREIGN KEY (item_id) REFERENCES tb_item(id)
);

INSERT INTO tb_address (id, logradouro, numero, bairro, localidade, uf, estado, cep, complemento, regiao, ibge, gia, ddd, siafi)
VALUES
(1, 'Rua das Palmeiras', '100', 'Jardim América', 'São Paulo', 'SP', 'São Paulo', '01401-000', NULL, 'Sudeste', '3550308', '1234', '11', '1234'),
(2, 'Avenida Brasil', '2000', 'Copacabana', 'Rio de Janeiro', 'RJ', 'Rio de Janeiro', '22041-001', NULL, 'Sudeste', '3304557', '2345', '21', '2345'),
(3, 'Rua Goiás', '300', 'Savassi', 'Belo Horizonte', 'MG', 'Minas Gerais', '30140-001', NULL, 'Sudeste', '3106200', '3456', '31', '3456'),
(4, 'Avenida Vitória', '400', 'Centro', 'Vitória', 'ES', 'Espírito Santo', '29010-000', NULL, 'Sudeste', '3205309', '4567', '27', '4567'),
(5, 'Rua Marechal Deodoro', '500', 'Centro', 'Curitiba', 'PR', 'Paraná', '80010-010', NULL, 'Sul', '4106902', '5678', '41', '5678'),
(6, 'Rua Amazonas', '600', 'Centro', 'Manaus', 'AM', 'Amazonas', '69010-000', NULL, 'Norte', '1302603', '6789', '92', '6789'),
(7, 'Avenida Ceará', '700', 'Centro', 'Porto Velho', 'RO', 'Rondônia', '76801-000', NULL, 'Norte', '1100205', '7890', '69', '7890'),
(8, 'Rua Bahia', '800', 'Centro', 'Salvador', 'BA', 'Bahia', '40010-000', NULL, 'Nordeste', '2927408', '8901', '71', '8901'),
(9, 'Avenida Pernambuco', '900', 'Boa Viagem', 'Recife', 'PE', 'Pernambuco', '51020-000', NULL, 'Nordeste', '2611606', '9012', '81', '9012'),
(10, 'Rua Maranhão', '1000', 'Centro', 'Fortaleza', 'CE', 'Ceará', '60010-000', NULL, 'Nordeste', '2304400', '0123', '85', '0123'),
(11, 'Rua Paraíba', '1100', 'Centro', 'Belém', 'PA', 'Pará', '66010-000', NULL, 'Norte', '1501402', '1234', '91', '1234'),
(12, 'Avenida Rio Grande', '1200', 'Centro', 'Porto Alegre', 'RS', 'Rio Grande do Sul', '90010-000', NULL, 'Sul', '4314902', '2345', '51', '2345'),
(13, 'Rua Santa Catarina', '1300', 'Centro', 'Florianópolis', 'SC', 'Santa Catarina', '88010-000', NULL, 'Sul', '4205407', '3456', '48', '3456'),
(14, 'Avenida Goiás', '1400', 'Centro', 'Goiânia', 'GO', 'Goiás', '74010-000', NULL, 'Centro-Oeste', '5208707', '4567', '62', '4567'),
(15, 'Rua Mato Grosso', '1500', 'Centro', 'Campo Grande', 'MS', 'Mato Grosso do Sul', '79010-000', NULL, 'Centro-Oeste', '5002704', '5678', '67', '5678'),
(16, 'Rua Rondônia', '1600', 'Centro', 'Cuiabá', 'MT', 'Mato Grosso', '78010-000', NULL, 'Centro-Oeste', '5103403', '6789', '65', '6789'),
(17, 'Avenida Acre', '1700', 'Centro', 'Rio Branco', 'AC', 'Acre', '69900-000', NULL, 'Norte', '1200401', '7890', '68', '7890'),
(18, 'Rua Tocantins', '1800', 'Centro', 'Palmas', 'TO', 'Tocantins', '77010-000', NULL, 'Norte', '1721000', '8901', '63', '8901'),
(19, 'Avenida Sergipe', '1900', 'Centro', 'Aracaju', 'SE', 'Sergipe', '49010-000', NULL, 'Nordeste', '2800308', '9012', '79', '9012'),
(20, 'Rua Alagoas', '2000', 'Centro', 'Maceió', 'AL', 'Alagoas', '57010-000', NULL, 'Nordeste', '2704302', '0123', '82', '0123');

INSERT INTO tb_users (name, email, password, role, registry, address_id)
VALUES
('João da Silva', 'joao.silva@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'ADMIN', 'REG001', 1),
('Maria Oliveira', 'maria.oliveira@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'MANAGER', 'REG002', 2),
('Carlos Pereira', 'carlos.pereira@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG003', 3),
('Ana Costa', 'ana.costa@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG004', 4),
('Pedro Santos', 'pedro.santos@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG005', 5),
('Lucas Almeida', 'lucas.almeida@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG006', 6),
('Fernanda Lima', 'fernanda.lima@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'MANAGER', 'REG007', 7),
('Rafael Souza', 'rafael.souza@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG008', 8),
('Juliana Mendes', 'juliana.mendes@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG009', 9),
('Gabriel Rocha', 'gabriel.rocha@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG010', 10),
('Paula Ribeiro', 'paula.ribeiro@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG011', 11),
('Thiago Martins', 'thiago.martins@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG012', 12),
('Camila Silva', 'camila.silva@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG013', 13),
('Rodrigo Alves', 'rodrigo.alves@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG014', 14),
('Beatriz Cunha', 'beatriz.cunha@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG015', 15),
('André Ferreira', 'andre.ferreira@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG016', 16),
('Larissa Gomes', 'larissa.gomes@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG017', 17),
('Eduardo Barros', 'eduardo.barros@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG018', 18),
('Patrícia Nunes', 'patricia.nunes@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG019', 19),
('Bruno Teixeira', 'bruno.teixeira@gmail.com', '$2a$10$eImiTXuWVxfM37uY4JANjQ==', 'USER', 'REG020', 20);

INSERT INTO tb_item (name, description, item_type, serial_number, status, created_at, updated_at)
VALUES
('Notebook Dell Inspiron', 'Notebook com processador Intel i5 e 8GB de RAM', 'LAPTOP', 'SN12345', 'AVAILABLE', NOW(), NOW()),
('Projetor Epson X200', 'Projetor Full HD com 3000 lumens', 'PROJECTOR', 'SN67890', 'AVAILABLE', NOW(), NOW()),
('Câmera Canon EOS 90D', 'Câmera DSLR com lente 18-135mm', 'CAMERA', 'SN11223', 'RESERVED', NOW(), NOW()),
('Notebook Lenovo ThinkPad', 'Notebook com processador AMD Ryzen 7 e 16GB de RAM', 'LAPTOP', 'SN44556', 'AVAILABLE', NOW(), NOW()),
('Projetor BenQ MW612', 'Projetor WXGA com 4000 lumens', 'PROJECTOR', 'SN77889', 'RESERVED', NOW(), NOW()),
('Tablet Samsung Galaxy Tab', 'Tablet com tela de 10 polegadas e 64GB', 'TABLET', 'SN99887', 'AVAILABLE', NOW(), NOW()),
('Impressora HP LaserJet', 'Impressora a laser com conexão Wi-Fi', 'PRINTER', 'SN55443', 'AVAILABLE', NOW(), NOW()),
('Monitor LG UltraWide', 'Monitor ultrawide de 29 polegadas', 'MONITOR', 'SN66778', 'AVAILABLE', NOW(), NOW()),
('Teclado Mecânico Razer', 'Teclado mecânico com iluminação RGB', 'KEYBOARD', 'SN33445', 'AVAILABLE', NOW(), NOW()),
('Mouse Logitech MX Master', 'Mouse sem fio com alta precisão', 'MOUSE', 'SN22334', 'AVAILABLE', NOW(), NOW()),
('Fone de Ouvido Sony WH-1000XM4', 'Fone com cancelamento de ruído', 'HEADPHONE', 'SN88990', 'AVAILABLE', NOW(), NOW()),
('Smartphone iPhone 13', 'Smartphone Apple com 128GB', 'SMARTPHONE', 'SN11234', 'AVAILABLE', NOW(), NOW()),
('Notebook HP Pavilion', 'Notebook com processador Intel i7 e 16GB de RAM', 'LAPTOP', 'SN55667', 'AVAILABLE', NOW(), NOW()),
('Projetor Sony VPL', 'Projetor 4K com 5000 lumens', 'PROJECTOR', 'SN77880', 'AVAILABLE', NOW(), NOW()),
('Câmera Nikon D750', 'Câmera DSLR com lente 24-120mm', 'CAMERA', 'SN99001', 'RESERVED', NOW(), NOW()),
('Tablet Apple iPad Pro', 'Tablet com tela de 12.9 polegadas e 256GB', 'TABLET', 'SN33456', 'AVAILABLE', NOW(), NOW()),
('Impressora Epson EcoTank', 'Impressora com tanque de tinta', 'PRINTER', 'SN44567', 'AVAILABLE', NOW(), NOW()),
('Monitor Dell UltraSharp', 'Monitor 4K de 27 polegadas', 'MONITOR', 'SN55678', 'AVAILABLE', NOW(), NOW()),
('Teclado Logitech K380', 'Teclado Bluetooth compacto', 'KEYBOARD', 'SN66789', 'AVAILABLE', NOW(), NOW()),
('Mouse Microsoft Arc', 'Mouse dobrável e portátil', 'MOUSE', 'SN77890', 'AVAILABLE', NOW(), NOW());

INSERT INTO tb_reservation (date_time, start_time, end_time, code, status, user_id, item_id, created_at, updated_at, deleted)
VALUES
(NOW(), NOW() + INTERVAL '16 HOURS', NOW() + INTERVAL '17 HOURS', 'RES021', 'CONFIRMED', 1, 1, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '17 HOURS', NOW() + INTERVAL '18 HOURS', 'RES022', 'PENDING', 2, 2, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '18 HOURS', NOW() + INTERVAL '19 HOURS', 'RES023', 'CANCELLED', 3, 3, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '19 HOURS', NOW() + INTERVAL '20 HOURS', 'RES024', 'CONFIRMED', 4, 4, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '20 HOURS', NOW() + INTERVAL '21 HOURS', 'RES025', 'PENDING', 5, 5, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '1 HOUR', NOW() + INTERVAL '2 HOURS', 'RES006', 'CONFIRMED', 6, 6, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '2 HOURS', NOW() + INTERVAL '3 HOURS', 'RES007', 'PENDING', 7, 7, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '3 HOURS', NOW() + INTERVAL '4 HOURS', 'RES008', 'CANCELLED', 8, 8, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '4 HOURS', NOW() + INTERVAL '5 HOURS', 'RES009', 'CONFIRMED', 9, 9, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '5 HOURS', NOW() + INTERVAL '6 HOURS', 'RES010', 'PENDING', 10, 10, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '6 HOURS', NOW() + INTERVAL '7 HOURS', 'RES011', 'CONFIRMED', 11, 11, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '7 HOURS', NOW() + INTERVAL '8 HOURS', 'RES012', 'PENDING', 12, 12, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '8 HOURS', NOW() + INTERVAL '9 HOURS', 'RES013', 'CANCELLED', 13, 13, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '9 HOURS', NOW() + INTERVAL '10 HOURS', 'RES014', 'CONFIRMED', 14, 14, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '10 HOURS', NOW() + INTERVAL '11 HOURS', 'RES015', 'PENDING', 15, 15, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '11 HOURS', NOW() + INTERVAL '12 HOURS', 'RES016', 'CONFIRMED', 16, 16, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '12 HOURS', NOW() + INTERVAL '13 HOURS', 'RES017', 'PENDING', 17, 17, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '13 HOURS', NOW() + INTERVAL '14 HOURS', 'RES018', 'CANCELLED', 18, 18, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '14 HOURS', NOW() + INTERVAL '15 HOURS', 'RES019', 'CONFIRMED', 19, 19, NOW(), NOW(), FALSE),
(NOW(), NOW() + INTERVAL '15 HOURS', NOW() + INTERVAL '16 HOURS', 'RES020', 'PENDING', 20, 20, NOW(), NOW(), FALSE);