CREATE TABLE patient (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cin VARCHAR(20) NOT NULL UNIQUE,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE,
    telephone VARCHAR(30),
    email VARCHAR(120),
    antecedents TEXT,
    date_creation DATETIME NOT NULL
);