package com.fst.cabinet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fst.cabinet.entity.LigneMedicament;

public interface LigneMedicamentRepository extends JpaRepository<LigneMedicament, Long> {
}