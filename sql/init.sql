/*
    Create a schema and its structure, populate the tables with sample data
    Part of 'sample_login_sandbox' repository and 'login' project
 */

DROP SCHEMA donjoe;

CREATE SCHEMA donjoe;

CREATE TABLE IF NOT EXISTS donjoe.user (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(30) NOT NULL,
    password     VARCHAR(45) NOT NULL,
    email        VARCHAR(50) NOT NULL,
    access_level INT DEFAULT 0
);

INSERT INTO donjoe.user (username, password, email, access_level)
VALUES ('testUser', '123456', 'test@test.com', 1),
       ('testUser2', '1234567', 'test2@test.com', 2);

SELECT * FROM donjoe.user;