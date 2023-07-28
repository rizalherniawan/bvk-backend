CREATE TABLE IF NOT EXISTS carts(
    id varchar(36) PRIMARY KEY,
    item_id varchar(36),
    transaction_id varchar(36),
    user_id varchar(36),
    quantity integer,
    constraint fk_items foreign key(item_id) references items(id) ON DELETE CASCADE,
    constraint fk_transactions foreign key(transaction_id) references transactions(id) ON DELETE CASCADE,
    constraint fk_users foreign key(user_id) references users(id) ON DELETE CASCADE,
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);