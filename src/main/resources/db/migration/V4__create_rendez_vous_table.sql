CREATE TABLE rendez_vous (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    date_heure DATETIME NOT NULL,
    duree_minutes INT NOT NULL,
    statut VARCHAR(20) NOT NULL,
    motif VARCHAR(255),

    CONSTRAINT fk_rdv_patient
        FOREIGN KEY (patient_id) REFERENCES patient(id),

    CONSTRAINT fk_rdv_medecin
        FOREIGN KEY (medecin_id) REFERENCES medecin(id)
);