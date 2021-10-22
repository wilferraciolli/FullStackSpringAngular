drop table IF EXISTS post;
create TABLE post
(
    id                   BIGINT       NOT NULL auto_increment,
    user_id              BIGINT       NOT NULL,
    title                VARCHAR(255) NOT NULL,
    content              VARCHAR(5000) NOT NULL,
    creation_date_time   TIMESTAMP,
    PRIMARY KEY (id)
);

drop table IF EXISTS post_tag;
create TABLE post_tag
(
    id                   BIGINT       NOT NULL auto_increment,
    post_id              BIGINT       NOT NULL,
    value                VARCHAR(36)  NOT NULL,
    PRIMARY KEY (id)
);

drop table IF EXISTS post_vote;
create TABLE post_vote
(
    id                   BIGINT       NOT NULL auto_increment,
    user_id              BIGINT       NOT NULL,
    post_id              BIGINT       NOT NULL,
    vote_value           VARCHAR(36)  NOT NULL,
    PRIMARY KEY (id)
);