package com.cekino.rabbitsimulation.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "simulation")
public class Simulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int carryingCapacity;
    private int population;
    private int year;

    //public Simulation(String bunny, List<Bunny> bunnies, Environment environment) {    }

    public Simulation(int carryingCapacity, int population, int year) {
        this.carryingCapacity = carryingCapacity;
        this.population = population;
        this.year = year;
    }

}