-- liquibase formatted sql

-- changeset rafa-guedes:TASK-18 dbms:postgresql

ALTER TABLE tb_reservation
    ALTER COLUMN item_id DROP NOT NULL;