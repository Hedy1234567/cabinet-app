package com.fst.cabinet.service;

import java.time.LocalDateTime;
import java.util.List;
import com.fst.cabinet.entity.RendezVous;

public interface RendezVousService {

    List<RendezVous> getAllAppointments();

    RendezVous save(RendezVous r);

    void deleteById(Long id);

    RendezVous findById(Long id);

    List<RendezVous> findByPatientUsername(String username);

    Long countPatientsByMedecin(Long medecinId); // ✅ ADD THIS ONLY
    List<RendezVous> findByDateBetween(LocalDateTime start, LocalDateTime end);
}