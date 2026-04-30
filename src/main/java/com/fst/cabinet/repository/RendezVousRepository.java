package com.fst.cabinet.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.cabinet.entity.RendezVous;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    
    List<RendezVous> findByPatient_AppUser_Username(String username);
    
}