package com.cekino.rabbitsimulation.repository;


import com.cekino.rabbitsimulation.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
}
