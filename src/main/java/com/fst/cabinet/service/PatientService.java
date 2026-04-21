package com.fst.cabinet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient savePatient(Patient patient) {

        if (patient.getCin() == null || patient.getCin().isBlank()) {
            throw new RuntimeException("CIN obligatoire");
        }

        if (patient.getNom() == null || patient.getNom().isBlank()) {
            throw new RuntimeException("Nom obligatoire");
        }

        if (patient.getPrenom() == null || patient.getPrenom().isBlank()) {
            throw new RuntimeException("Prénom obligatoire");
        }

        patientRepository.findByCin(patient.getCin()).ifPresent(existingPatient -> {
            if (patient.getId() == null || !existingPatient.getId().equals(patient.getId())) {
                throw new RuntimeException("CIN déjà existant");
            }
        });

        if (patient.getId() == null) {
            patient.setDateCreation(LocalDateTime.now());
        } else {
            Patient existingPatient = patientRepository.findById(patient.getId())
                    .orElseThrow(() -> new RuntimeException("Patient introuvable"));
            patient.setDateCreation(existingPatient.getDateCreation());
        }

        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> searchPatients(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return patientRepository.findAll();
        }

        return patientRepository
                .findByNomContainingIgnoreCaseOrCinContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
                        keyword, keyword, keyword);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
    }
}