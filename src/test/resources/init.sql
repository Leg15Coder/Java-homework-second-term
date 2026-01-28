CREATE TABLE universities (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              country VARCHAR(100) NOT NULL
);

CREATE TABLE students (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          university_id INTEGER REFERENCES universities(id),
                          age INT NOT NULL
);
