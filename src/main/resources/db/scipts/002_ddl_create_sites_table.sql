CREATE TABLE IF NOT EXISTS sites (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL UNIQUE,
    registration BOOLEAN,
    credential_id INT REFERENCES credentials(id) NOT NULL
);