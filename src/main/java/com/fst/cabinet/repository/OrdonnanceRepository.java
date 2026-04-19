package com.fst.cabinet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fst.cabinet.entity.Ordonnance;

public interface OrdonnanceRepository extends JpaRepository<Ordonnance, Long> {
}
