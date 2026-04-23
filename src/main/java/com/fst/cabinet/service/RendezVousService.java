package com.fst.cabinet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fst.cabinet.entity.AppUser;
import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.repository.RendezVousRepository;
import com.fst.cabinet.repository.UserRepository;
import com.fst.cabinet.util.SecurityUtils;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final UserRepository userRepository;

    public RendezVousService(RendezVousRepository rendezVousRepository,
                             UserRepository userRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.userRepository = userRepository;
    }

    public List<RendezVous> getRendezVousForCurrentUser() {

        String username = SecurityUtils.getCurrentUsername();

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow();

        switch (user.getRole()) {

            case ADMIN:
            case SECRETAIRE:
                return rendezVousRepository.findAll();

            case MEDECIN:
                return rendezVousRepository.findByMedecin(user.getMedecin());

            case PATIENT:
                return rendezVousRepository.findByPatient(user.getPatient());

            default:
                throw new RuntimeException("Unknown role");
        }
    }
}