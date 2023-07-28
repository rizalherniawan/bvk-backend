CREATE TABLE IF NOT EXISTS transactions(
    id varchar(36) PRIMARY KEY,
    status varchar,
    total_transaction integer,
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);