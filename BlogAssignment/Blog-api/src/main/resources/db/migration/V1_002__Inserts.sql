INSERT INTO post(id, user_id, title, content, creation_date_time)
VALUES (2000, 1000, 'Post title', 'Post content', '2020-01-01T00:00:00');

INSERT INTO post(id, user_id, title, content, creation_date_time)
VALUES (2001, 1000, 'Post title Second', 'Post content Second', '2021-01-01T00:00:00');

INSERT INTO post_tag(id, post_id, value)
VALUES (3000, 2000, 'tag1');
INSERT INTO post_tag(id, post_id, value)
VALUES (3001, 2000, 'tag2');
INSERT INTO post_tag(id, post_id, value)
VALUES (3002, 2000, 'tag3');

INSERT INTO post_tag(id, post_id, value)
VALUES (3003, 2001, 'tag1');
INSERT INTO post_tag(id, post_id, value)
VALUES (3004, 2001, 'tag2');
INSERT INTO post_tag(id, post_id, value)
VALUES (3005, 2001, 'tag3');
INSERT INTO post_tag(id, post_id, value)
VALUES (3006, 2001, 'tag4');
INSERT INTO post_tag(id, post_id, value)
VALUES (3007, 2001, 'tag5');
