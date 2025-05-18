-- liquibase formatted sql

-- changeset rafa-guedes:TASK-07 dbms:postgresql

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

CREATE INDEX idx_item_type ON tb_item(item_type);

CREATE INDEX idx_item_status ON tb_item(status);