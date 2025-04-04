CREATE TYPE gender AS ENUM ('MALE', 'FEMALE', 'OTHER');
CREATE TYPE hair_color AS ENUM ('BLACK', 'BROWN', 'BLONDE', 'RED', 'GRAY', 'WHITE', 'OTHER');

CREATE TABLE users (
                       login VARCHAR(255) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       age INT NOT NULL,
                       gender gender NOT NULL,
                       hair_color hair_color NOT NULL
);
