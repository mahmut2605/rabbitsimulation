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
        double healthFactor = 1.0;

        if (currentPopulation > carryingCapacity) {
            healthFactor -= 0.05 * ((double) currentPopulation / carryingCapacity);
        }

        if (currentPopulation < carryingCapacity) {
            healthFactor += 0.75 * ((double) carryingCapacity / (currentPopulation + 10 ) ); //currentPopulation+10 to prevent zero divider problem
        }

        if (random.nextDouble() < 0.1) { // 10% chance of a disease outbreak
            healthFactor -= 0.1;
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

