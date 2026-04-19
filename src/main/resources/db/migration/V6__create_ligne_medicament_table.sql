CREATE TABLE ligne_medicament (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ordonnance_id BIGINT NOT NULL,
    nom_medicament VARCHAR(150) NOT NULL,
    posologie VARCHAR(255),
    duree VARCHAR(100),

    CONSTRAINT fk_ligne_medicament_ordonnance
        FOREIGN KEY (ordonnance_id) REFERENCES ordonnance(id)
);