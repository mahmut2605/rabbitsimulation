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
@Table(name= "bunny")
public class Bunny {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int age;
    private int generation;
    private double reproductionRate;
    private double mutationRate;
    private boolean alive;
    private double health;

    public Bunny(int generation, double reproductionRate, double mutationRate) {
        this.age = 0;
        this.generation = generation;
        this.reproductionRate = reproductionRate;
        this.mutationRate = mutationRate;
        this.alive = true;
        this.health = 100.0;
    }

    public void age() {
        this.age++;
        if (this.age > 7 || this.health < 20.0) { // Bunnies die after 7 years or if health is too low
            this.alive = false;
        }
    }

    public boolean canReproduce() {
        return this.age >= 2 && this.alive && this.health > 50.0 &&  suitableMutationAndAgeforReproduce();
    }

    private boolean suitableMutationAndAgeforReproduce(){
        if ( (1 / this.getMutationRate()) * this.age >10 ){
            return true;
        }

        return  false;
    }

    public Bunny reproduce() {
        if (canReproduce()) {
            Random random = new Random();
            // Mutation could affect reproduction rate and mutation rate of offspring
            double newReproductionRate = reproductionRate + (mutationRate * (random.nextDouble() - 0.5));
            double newMutationRate = mutationRate + (mutationRate * (random.nextDouble() - 0.5));
            return new Bunny(this.generation + 1, newReproductionRate, newMutationRate);
        }
        return null;
    }

    public void adjustHealth(double factor) {
        this.health += factor;
        if (this.health < 0)
            this.health = 0;
        else if (this.health >=100) {
            this.health=100;
        }
    }

    // Getters and setters omitted for brevity

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public double getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(double reproductionRate) {
        this.reproductionRate = reproductionRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
