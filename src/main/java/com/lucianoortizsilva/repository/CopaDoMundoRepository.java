package com.lucianoortizsilva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucianoortizsilva.model.CopaDoMundo;

@Repository
public interface CopaDoMundoRepository extends JpaRepository<CopaDoMundo, Long> {}