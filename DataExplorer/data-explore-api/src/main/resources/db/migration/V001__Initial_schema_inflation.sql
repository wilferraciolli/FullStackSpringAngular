-- Initial database schema setup
-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

-- Create indexes for better query performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_active ON users(active);

-- Insert sample users data
INSERT INTO users (id, username, email, password, full_name, active) VALUES
(RANDOM_UUID(), 'admin', 'admin@example.com', 'hashed_password_123', 'Administrator', true),
(RANDOM_UUID(), 'john_doe', 'john.doe@example.com', 'hashed_password_456', 'John Doe', true),
(RANDOM_UUID(), 'jane_smith', 'jane.smith@example.com', 'hashed_password_789', 'Jane Smith', true),
(RANDOM_UUID(), 'bob_wilson', 'bob.wilson@example.com', 'hashed_password_012', 'Bob Wilson', false),
(RANDOM_UUID(), 'alice_johnson', 'alice.johnson@example.com', 'hashed_password_345', 'Alice Johnson', true),
(RANDOM_UUID(), 'charlie.brown', 'charlie.brown@example.com', 'hashed_password_678', 'Charlie Brown', true),
(RANDOM_UUID(), 'diana_prince', 'diana.prince@example.com', 'hashed_password_901', 'Diana Prince', true),
(RANDOM_UUID(), 'evan_taylor', 'evan.taylor@example.com', 'hashed_password_234', 'Evan Taylor', false),
(RANDOM_UUID(), 'fiona_green', 'fiona.green@example.com', 'hashed_password_567', 'Fiona Green', true),
(RANDOM_UUID(), 'george_white', 'george.white@example.com', 'hashed_password_890', 'George White', true);
