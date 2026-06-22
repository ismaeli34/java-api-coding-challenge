CREATE TABLE IF NOT EXISTS addresses (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    address_name VARCHAR(255),
    street VARCHAR(255),
    city VARCHAR(255),
    postal_code VARCHAR(255),
    region VARCHAR(255),
    country VARCHAR(255),
    address_type VARCHAR(255)
);