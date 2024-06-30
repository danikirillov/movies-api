CREATE TABLE IF NOT EXISTS ratings (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    api_key VARCHAR(255),
    rating INTEGER
);

CREATE TABLE IF NOT EXISTS rated_movie (
    id SERIAL PRIMARY KEY,
    rating INTEGER,
    rating_total BIGINT,
    raters_amount BIGINT,
    title VARCHAR(255),
    boxOffice BIGINT
);