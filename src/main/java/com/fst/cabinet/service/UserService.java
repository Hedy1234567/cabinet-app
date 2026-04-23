package com.fst.cabinet.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fst.cabinet.entity.AppUser;
import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.Role;
import com.fst.cabinet.repository.PatientRepository;
import com.fst.cabinet.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PatientRepository patientRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(AppUser user, Patient formPatient) {

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.PATIENT);

    Patient patient = new Patient();
    patient.setNom(formPatient.getNom());
    patient.setPrenom(formPatient.getPrenom());
    patient.setEmail(formPatient.getEmail());
    patient.setTelephone(formPatient.getTelephone());
    patient.setDateNaissance(formPatient.getDateNaissance());
    patient.setDateCreation(LocalDateTime.now());

    patient.setCin("TEMP_" + System.currentTimeMillis());

    patient = patientRepository.save(patient);

    user.setPatient(patient);

    userRepository.save(user);
}
}