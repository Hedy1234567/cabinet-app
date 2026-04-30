package com.fst.cabinet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient savePatient(Patient patient) {
        patientRepository.findByCin(patient.getCin()).ifPresent(existingPatient -> {
            if (patient.getId() == null || !existingPatient.getId().equals(patient.getId())) {
                throw new RuntimeException("CIN already exists");
            }
        });

        if (patient.getId() == null) {
            patient.setDateCreation(LocalDateTime.now());
        } else {
            Patient existingPatient = patientRepository.findById(patient.getId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            patient.setDateCreation(existingPatient.getDateCreation());
        }

        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public List<Patient> searchPatients(String keyword) {
    return patientRepository
        .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrCinContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
            keyword,
            keyword,
            keyword,
            keyword
        );
}
    public Patient findByEmail(String email) {
    return patientRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
}

    public Patient findByAppUserUsername(String username) {
    return patientRepository.findByAppUser_Username(username)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
}

    public Patient getCurrentPatient(Authentication auth) {
    return patientRepository.findByAppUser_Username(auth.getName())
           .orElseThrow();
}

}