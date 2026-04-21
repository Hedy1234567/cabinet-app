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

    public List<Medecin> getAll() {
        return medecinRepository.findAll();
    }

    public Medecin getById(Long id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin not found"));
    }

    public Medecin save(Medecin medecin) {
        validateMedecin(medecin);

        if (medecinRepository.existsByNumeroOrdre(medecin.getNumeroOrdre())) {
            throw new RuntimeException("Numéro d'ordre déjà existant");
        }

        if (medecin.getId() == null) {
            medecin.setActif(medecin.isActif());
        }

        return medecinRepository.save(medecin);
    }

    public Medecin update(Long id, Medecin medecin) {
        Medecin existing = getById(id);

        validateMedecin(medecin);

        if (medecinRepository.existsByNumeroOrdreAndIdNot(medecin.getNumeroOrdre(), id)) {
            throw new RuntimeException("Numéro d'ordre déjà existant");
        }

        existing.setNom(medecin.getNom());
        existing.setPrenom(medecin.getPrenom());
        existing.setSpecialite(medecin.getSpecialite());
        existing.setNumeroOrdre(medecin.getNumeroOrdre());
        existing.setTelephone(medecin.getTelephone());
        existing.setEmail(medecin.getEmail());
        existing.setActif(medecin.isActif());

        return medecinRepository.save(existing);
    }

    public void delete(Long id) {
        medecinRepository.deleteById(id);
    }

    private void validateMedecin(Medecin medecin) {
        if (medecin.getNom() == null || medecin.getNom().isBlank()) {
            throw new RuntimeException("Nom obligatoire");
        }
        if (medecin.getPrenom() == null || medecin.getPrenom().isBlank()) {
            throw new RuntimeException("Prénom obligatoire");
        }
        if (medecin.getSpecialite() == null || medecin.getSpecialite().isBlank()) {
            throw new RuntimeException("Spécialité obligatoire");
        }
        if (medecin.getNumeroOrdre() == null || medecin.getNumeroOrdre().isBlank()) {
            throw new RuntimeException("Numéro d'ordre obligatoire");
        }
    }
}