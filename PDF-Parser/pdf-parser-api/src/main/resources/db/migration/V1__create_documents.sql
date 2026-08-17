CREATE TABLE documents (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    size BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE document_content (
    document_id UUID PRIMARY KEY REFERENCES documents (id) ON DELETE CASCADE,
    data BLOB NOT NULL
);
