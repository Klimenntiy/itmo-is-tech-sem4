CREATE TABLE users (
                       login VARCHAR(255) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       age INT NOT NULL,
                       gender VARCHAR(50) NOT NULL,
                       hair_color VARCHAR(50) NOT NULL
);

CREATE TABLE user_friends (
                              user_login VARCHAR(255),
                              friend_login VARCHAR(255),
                              FOREIGN KEY (user_login) REFERENCES users(login),
                              FOREIGN KEY (friend_login) REFERENCES users(login),
                              PRIMARY KEY (user_login, friend_login)
);
