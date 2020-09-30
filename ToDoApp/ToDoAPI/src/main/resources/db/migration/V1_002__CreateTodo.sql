drop table IF EXISTS todo;
create TABLE todo
(
    id                  BIGINT       NOT NULL auto_increment,
    person_id           BIGINT       NOT NULL,
    type_id             VARCHAR(255) NOT NULL,
    name                VARCHAR(255) NOT NULL,
    description         VARCHAR(2000),
    created_date_time   TIMESTAMP    NOT NULL,
    completed_date_time TIMESTAMP,
    state_id            VARCHAR(100) NOT NULL,
    completion_stats    DOUBLE,
    enabled             BIT          NOT NULL,
    PRIMARY KEY (id)
);
