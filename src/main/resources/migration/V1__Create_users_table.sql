CREATE TABLE users (
                       login VARCHAR(255) NOT NULL PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255),
                       age INT,
                       gender VARCHAR(10) NOT NULL,
                       hairColor VARCHAR(50),
                       role VARCHAR(50) NOT NULL
);