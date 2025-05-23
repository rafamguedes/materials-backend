-- liquibase formatted sql

-- changeset rafa-guedes:TASK-08 dbms:postgresql

CREATE TABLE IF NOT EXISTS tb_reservation (
    id BIGSERIAL PRIMARY KEY,
    date_time TIMESTAMP NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tb_users(id),
    FOREIGN KEY (item_id) REFERENCES tb_item(id)
);

CREATE INDEX IF NOT EXISTS idx_reservation_user_id ON tb_reservation(user_id);

CREATE INDEX IF NOT EXISTS idx_reservation_item_id ON tb_reservation(item_id);
