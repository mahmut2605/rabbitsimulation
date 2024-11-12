package com.cekino.rabbitsimulation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Random random = new Random();

    public Environment(int initialCapacity) {
        this.carryingCapacity = initialCapacity;
    }

    public void applyEnvironmentalFactors(Bunny bunny, int currentPopulation) {
        double healthFactor = 0;


        if (currentPopulation > carryingCapacity) {
            healthFactor =  -1 * (((double) (currentPopulation / carryingCapacity) * 10) + (1 / bunny.getMutationRate()));
        }

        if (currentPopulation < carryingCapacity) {
            healthFactor =  (((double) currentPopulation / carryingCapacity) * (1 / bunny.getMutationRate())) + 5;
        }

        if (random.nextDouble() < 0.1) { // 10% chance of a disease outbreak
            healthFactor -= 5;
        }

        bunny.adjustHealth(healthFactor);
    }

    public void fluctuateResources() {
        if (random.nextDouble() < 0.2) { // 20% chance of resource fluctuation
            this.carryingCapacity += random.nextInt(20) - 10; // Increase or decrease carrying capacity by up to 10
            if (this.carryingCapacity < 0) {
                this.carryingCapacity = 0;
            }
        }
    }

    // Getter and setter omitted for brevity

    public int getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(int carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}

