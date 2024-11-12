package com.cekino.rabbitsimulation.repository;

import com.cekino.rabbitsimulation.entity.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {
}