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
    phone VARCHAR(20) NOT NULL,
    address_id BIGINT,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES tb_address(id)
);
