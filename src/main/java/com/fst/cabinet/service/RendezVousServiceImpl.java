package com.fst.cabinet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.repository.RendezVousRepository;

@Service
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    public RendezVousServiceImpl(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    @Override
    public List<RendezVous> getAllAppointments() {
        return rendezVousRepository.findAll();
    }

    @Override
    public RendezVous save(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public void deleteById(Long id) {
        rendezVousRepository.deleteById(id);
    }

    @Override
    public RendezVous findById(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
public List<RendezVous> findByPatientUsername(String username) {
    return rendezVousRepository.findByPatient_AppUser_Username(username);
}
}