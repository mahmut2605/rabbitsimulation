package com.cekino.rabbitsimulation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "environment")
public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int carryingCapacity;
    public Environment(int initialCapacity) {
        this.carryingCapacity = initialCapacity;
    }

}

