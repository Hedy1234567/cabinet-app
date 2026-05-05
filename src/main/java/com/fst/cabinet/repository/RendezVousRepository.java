package com.fst.cabinet.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fst.cabinet.entity.RendezVous;
import java.time.LocalDateTime;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    
    List<RendezVous> findByPatient_AppUser_Username(String username);
    List<RendezVous> findByDateHeureBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(r) FROM RendezVous r WHERE r.medecin.id = :id")
    Long countByMedecinId(@Param("id") Long id);
}