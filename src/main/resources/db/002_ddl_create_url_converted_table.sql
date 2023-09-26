CREATE TABLE url_converted (
    id SERIAL PRIMARY KEY NOT NULL,
    url TEXT NOT NULL UNIQUE,
    url_shortcut VARCHAR(10) NOT NULL UNIQUE,
    visits INT NOT NULL,
    site_id INT,
    CONSTRAINT fk_site_id FOREIGN KEY (site_id) REFERENCES site(id)
);