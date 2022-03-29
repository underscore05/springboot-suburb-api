CREATE TABLE suburbs (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    suburb VARCHAR(128) NOT NULL,
    postcode VARCHAR(128) NOT NULL,
    suburb_postcode VARCHAR(128) NOT NULL,
    UNIQUE KEY suburb_postcode (suburb_postcode),
    PRIMARY KEY (id)
);