package com.fst.cabinet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fst.cabinet.entity.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByCin(String cin);

    Optional<Patient> findByCin(String cin);

    List<Patient> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrCinContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
        String nom,
        String prenom,
        String cin,
        String telephone
);
}