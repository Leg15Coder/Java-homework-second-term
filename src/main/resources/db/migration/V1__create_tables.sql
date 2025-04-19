CREATE TABLE users (
                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                   email VARCHAR(255) NOT NULL UNIQUE,
                   name VARCHAR(255) NOT NULL,
                   surname VARCHAR(255)
);

CREATE TABLE courses (
                  id BIGINT PRIMARY KEY,
                  name VARCHAR(255) NOT NULL,
                  author UUID REFERENCES users(id) NOT NULL
);

CREATE TABLE universities (
                  id BIGINT PRIMARY KEY,
                  name VARCHAR(255) NOT NULL
);

CREATE TABLE books (
                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                   title VARCHAR(255) NOT NULL
);
