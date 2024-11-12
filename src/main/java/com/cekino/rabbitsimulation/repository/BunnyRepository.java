package com.cekino.rabbitsimulation.repository;

import com.cekino.rabbitsimulation.entity.Bunny;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BunnyRepository extends JpaRepository<Bunny, Long> {
}
