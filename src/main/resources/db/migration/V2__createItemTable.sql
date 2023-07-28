CREATE TABLE IF NOT EXISTS items(
    id varchar(36) PRIMARY KEY,
    name varchar(255),
    quantity integer,
    price integer,
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);