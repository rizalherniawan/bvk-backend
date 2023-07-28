CREATE TABLE IF NOT EXISTS users(
    id varchar(36) PRIMARY KEY,
    name varchar(255),
    email varchar(255),
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);