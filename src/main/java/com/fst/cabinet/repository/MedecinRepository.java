package com.fst.cabinet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fst.cabinet.entity.Medecin;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    boolean existsByNumeroOrdre(String numeroOrdre);

    Optional<Medecin> findByNumeroOrdre(String numeroOrdre);

    List<Medecin> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrSpecialiteContainingIgnoreCaseOrNumeroOrdreContainingIgnoreCase(
    String nom,
    String prenom,
    String specialite,
    String numeroOrdre
);

    
}