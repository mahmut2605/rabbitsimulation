package com.cekino.rabbitsimulation.service;

import com.cekino.rabbitsimulation.entity.Bunny;
import com.cekino.rabbitsimulation.entity.Environment;
import com.cekino.rabbitsimulation.enums.Gender;
import com.cekino.rabbitsimulation.repository.BunnyRepository;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class BunnyService {

    private final BunnyRepository bunnyRepository;

    @Value("${bunny_healt_rate_for_death}")
    private int bunny_healt_rate_for_death;

    @Value("${bunny_reproduce_min_age_equal}")
    private int bunny_reproduce_min_age_equal;

    @Value("${bunny_reproduce_min_healt_limit}")
    private int bunny_reproduce_min_healt_limit;

    @Value("${bunny_min_age_for_death}")
    private int bunny_min_age_for_death;


    public Bunny getBunny(){
        return  new Bunny(0, 1.0, 0.1);
    }

    public void bunnyGrowUpOneYear(Bunny bunny) {
        bunny.setAge(bunny.getAge() + 1);
        if (bunny.getAge() > bunny_min_age_for_death || bunny.getHealth() < bunny_healt_rate_for_death) {
            bunny.setAlive(false);
        }
    }
    public void adjustHealth(Bunny bunny, double factor) {
        bunny.setHealth(bunny.getHealth() + factor);
        if (bunny.getHealth() < 0)
            bunny.setHealth(0);
        else if (bunny.getHealth() >=100) {
            bunny.setHealth(100);
        }
    }

    public Bunny reproduce(Bunny bunny) {
        if (canReproduce(bunny)) {
            Random random = new Random();

            double newReproductionRate =bunny.getReproductionRate() + (bunny.getMutationRate() * (random.nextDouble() - 0.5));
            double newMutationRate = bunny.getMutationRate() + (bunny.getMutationRate() * (random.nextDouble() - 0.5));
            return new Bunny(bunny.getGeneration()+ 1, newReproductionRate, newMutationRate);
        }
        return null;
    }

    public boolean canReproduce(Bunny bunny) {
        return  bunny.getGender().equals(Gender.FEMALE) && bunny.getAge() >= bunny_reproduce_min_age_equal && bunny.isAlive() && bunny.getHealth() > bunny_reproduce_min_healt_limit &&  suitableMutationAndAgeforReproduce(bunny);
    }
    private boolean suitableMutationAndAgeforReproduce(Bunny bunny){
        if ( (1 / bunny.getMutationRate()) * bunny.getAge() >10 ){
            return true;
        }

        return  false;
    }

    public void saveBunny(Bunny bunny){
        bunnyRepository.save(bunny);
    }

    public void deleteAllBunny(){
        bunnyRepository.deleteAll();
    }
    public void saveAllBunnies(List<Bunny> bunnyList){
        bunnyRepository.deleteAll();
    }



}
