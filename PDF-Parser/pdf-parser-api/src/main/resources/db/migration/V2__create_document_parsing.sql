CREATE TABLE document_parsing (
    document_id UUID PRIMARY KEY REFERENCES documents (id) ON DELETE CASCADE,
    status VARCHAR(30) NOT NULL,
    parsed_text CLOB
);
