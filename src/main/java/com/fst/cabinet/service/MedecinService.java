package com.fst.cabinet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.repository.MedecinRepository;

@Service
public class MedecinService {

    private final MedecinRepository medecinRepository;

    public MedecinService(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    public Medecin save(Medecin medecin) {
    return medecinRepository.save(medecin);
}

    // CREATE / UPDATE
    public Medecin saveMedecin(Medecin medecin) {

        medecinRepository.findByNumeroOrdre(medecin.getNumeroOrdre())
                .ifPresent(existing -> {
                    if (medecin.getId() == null ||
                        !existing.getId().equals(medecin.getId())) {
                        throw new RuntimeException("Numero ordre already exists");
                    }
                });

        return medecinRepository.save(medecin);
    }

    // GET ALL
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    // GET BY ID
    public Medecin getById(Long id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medecin not found"));
    }

    // DELETE
    public void deleteMedecin(Long id) {
        if (!medecinRepository.existsById(id)) {
            throw new RuntimeException("Medecin not found");
        }
        medecinRepository.deleteById(id);
    }

    // SEARCH
    public List<Medecin> searchMedecins(String keyword) {
        return medecinRepository
            .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrSpecialiteContainingIgnoreCaseOrNumeroOrdreContainingIgnoreCase(
                keyword, keyword, keyword, keyword
            );
    }
}