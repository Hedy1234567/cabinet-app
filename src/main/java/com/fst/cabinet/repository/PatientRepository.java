package com.fst.cabinet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fst.cabinet.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByCin(String cin);

    Optional<Patient> findByCin(String cin);
}