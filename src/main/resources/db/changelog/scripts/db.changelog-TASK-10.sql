-- liquibase formatted sql

-- changeset rafa-guedes:TASK-10 dbms:postgresql

ALTER TABLE tb_reservation ADD COLUMN start_time TIMESTAMP;
ALTER TABLE tb_reservation ADD COLUMN end_time TIMESTAMP;