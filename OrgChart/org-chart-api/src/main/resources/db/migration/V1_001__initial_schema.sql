CREATE TABLE IF NOT EXISTS org (
    id                              BINARY(16)      PRIMARY KEY,  -- For UNIQUEIDENTIFIER (UUID in MySQL)
    title                           VARCHAR(500)    NOT NUlL,
    start_date                      DATETIME        NOT NULL,
    end_date                        DATETIME        NOT NULL DEFAULT '9999-01-01'
);

CREATE TABLE IF NOT EXISTS org_entity (
    id                              BINARY(16)      PRIMARY KEY,
    parent_id                       BINARY(16)      NOT NULL,     -- can be either ORG or department
    title                           VARCHAR(500)    NOT NULL,
    start_date                      DATETIME        NOT NULL,
    end_date                        DATETIME        NOT NULL DEFAULT '9999-01-01'
);

CREATE TABLE IF NOT EXISTS department (
    id                              BINARY(16)      PRIMARY KEY,
    parent_id                       BINARY(16)      NOT NULL,     -- can be either ORG or another department
    title                           VARCHAR(500)    NOT NULL,
    start_date                      DATETIME        NOT NULL,
    end_date                        DATETIME        NOT NULL DEFAULT '9999-01-01'
);

CREATE TABLE IF NOT EXISTS job (
    id                              BINARY(16)      PRIMARY KEY,
    parent_id                       BINARY(16)      NOT NULL,     -- can be either ORG or another department
    title                           VARCHAR(500)    NOT NULL,
    start_date                      DATETIME        NOT NULL,
    end_date                        DATETIME        NOT NULL DEFAULT '9999-01-01'
);
