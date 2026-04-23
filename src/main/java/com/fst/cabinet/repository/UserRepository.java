package com.fst.cabinet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.cabinet.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}