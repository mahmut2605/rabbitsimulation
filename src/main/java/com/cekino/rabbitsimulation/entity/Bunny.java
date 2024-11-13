package com.cekino.rabbitsimulation.entity;

import com.cekino.rabbitsimulation.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

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
    private Gender gender;

    @Transient
    @Value("${bunny_healt_rate_for_death}")
    private int bunny_healt_rate_for_death;

    @Transient
    @Value("${bunny_reproduce_min_age_equal}")
    private int bunny_reproduce_min_age_equal;

    @Transient
    @Value("${bunny_reproduce_min_healt_limit}")
    private int bunny_reproduce_min_healt_limit;

    @Transient
    @Value("${bunny_min_age_for_death}")
    private int bunny_min_age_for_death;


    public Bunny(int generation, double reproductionRate, double mutationRate) {
        this.age = 0;
        this.generation = generation;
        this.reproductionRate = reproductionRate;
        this.mutationRate = mutationRate;
        this.alive = true;
        this.health = 100.0;
        this.gender = generateRandomGender();
    }

    public void age() {
        this.age++;
        if (this.age > 7 || this.health < 20.0) { // Bunnies die after 7 years or if health is too low
            this.alive = false;
        }
    }

    public boolean canReproduce() {
        return  this.gender.equals(Gender.FEMALE) && this.age >= 2 && this.alive && this.health > 50.0 &&  suitableMutationAndAgeforReproduce();
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

    private Gender generateRandomGender(){
        Random random = new Random();
        int number= random.nextInt(2);;

        if (number == 0 )
            return Gender.FEMALE;
        return Gender.MALE;
    }

}
