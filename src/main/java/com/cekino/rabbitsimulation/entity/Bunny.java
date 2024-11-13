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



    public Bunny(int generation, double reproductionRate, double mutationRate) {
        this.age = 0;
        this.generation = generation;
        this.reproductionRate = reproductionRate;
        this.mutationRate = mutationRate;
        this.alive = true;
        this.health = 100.0;
        this.gender = generateRandomGender();
    }

    private Gender generateRandomGender(){
        Random random = new Random();
        int number= random.nextInt(2);;

        if (number == 0 )
            return Gender.FEMALE;
        return Gender.MALE;
    }

}
