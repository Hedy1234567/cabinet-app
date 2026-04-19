CREATE TABLE ordonnance (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rendez_vous_id BIGINT NOT NULL UNIQUE,
    date_emission DATE NOT NULL,
    observations TEXT,

    CONSTRAINT fk_ordonnance_rdv
        FOREIGN KEY (rendez_vous_id) REFERENCES rendez_vous(id)
);