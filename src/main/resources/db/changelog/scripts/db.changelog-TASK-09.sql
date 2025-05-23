-- liquibase formatted sql

-- changeset rafa-guedes:TASK-09 dbms:postgresql

ALTER TABLE tb_users RENAME TO tb_user;

ALTER TABLE tb_user DROP COLUMN IF EXISTS phone;
ALTER TABLE tb_user ADD COLUMN registry VARCHAR(50) NOT NULL UNIQUE;