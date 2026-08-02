CREATE TABLE processed_files (
    id UUID PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100),
    size BIGINT,
    raw_text TEXT,
    extracted_data JSONB,
    processed_at TIMESTAMP NOT NULL
);
