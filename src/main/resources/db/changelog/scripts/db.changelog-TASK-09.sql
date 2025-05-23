-- liquibase formatted sql

-- changeset rafa-guedes:TASK-09 dbms:postgresql

ALTER TABLE tb_users DROP COLUMN IF EXISTS phone;
ALTER TABLE tb_users ADD COLUMN registry VARCHAR(50) NOT NULL UNIQUE;