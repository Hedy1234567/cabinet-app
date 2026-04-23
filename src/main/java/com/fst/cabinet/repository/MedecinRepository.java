package com.fst.cabinet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.cabinet.entity.Medecin;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    Optional<Medecin> findByNumeroOrdre(String numeroOrdre);

    boolean existsByNumeroOrdre(String numeroOrdre);

    List<Medecin> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrSpecialiteContainingIgnoreCaseOrNumeroOrdreContainingIgnoreCase(
        String nom,
        String prenom,
        String specialite,
        String numeroOrdre
    );
}