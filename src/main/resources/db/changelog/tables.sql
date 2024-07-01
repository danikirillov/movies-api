-- User rating connection with a movie title
CREATE TABLE IF NOT EXISTS ratings (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    api_key VARCHAR(255),
    rating INTEGER
);

-- Movie rating
CREATE TABLE IF NOT EXISTS rated_movie (
    id SERIAL PRIMARY KEY,
    rating INTEGER,
    rating_total BIGINT,
    raters_amount BIGINT,
    title VARCHAR(255),
    box_office BIGINT
);