package com.fst.cabinet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.Medecin;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findByPatient(Patient patient);

    List<RendezVous> findByMedecin(Medecin medecin);
}