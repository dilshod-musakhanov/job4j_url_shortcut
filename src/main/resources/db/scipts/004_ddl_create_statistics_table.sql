CREATE TABLE IF NOT EXISTS statistics (
    id SERIAL PRIMARY KEY NOT NULL,
    url_id INT REFERENCES site_urls(id) NOT NULL,
    visits INT NOT NULL
);