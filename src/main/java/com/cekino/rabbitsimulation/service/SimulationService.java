package com.cekino.rabbitsimulation.service;


import com.cekino.rabbitsimulation.entity.Bunny;
import com.cekino.rabbitsimulation.entity.Environment;
import com.cekino.rabbitsimulation.entity.Simulation;
import com.cekino.rabbitsimulation.repository.BunnyRepository;
import com.cekino.rabbitsimulation.repository.EnvironmentRepository;
import com.cekino.rabbitsimulation.repository.SimulationRepository;
import lombok.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimulationService {

    private int initial_bunny_count = 10;
    private  int howmany_year_similation = 100;
    private final BunnyRepository bunnyRepository;
    private final EnvironmentRepository environmentRepository;
    private final SimulationRepository simulationRepository;
    private List<Bunny> bunnies;
    private Environment environment;
    private int year;

    public SimulationService(BunnyRepository bunnyRepository, EnvironmentRepository environmentRepository, SimulationRepository simulationRepository) {
        this.bunnyRepository = bunnyRepository;
        this.environmentRepository = environmentRepository;
        this.simulationRepository = simulationRepository;
        initializeSimulation();
    }

    private void initializeSimulation() {
        deleteAppData();

        this.environment = new Environment(100);
        environmentRepository.save(environment);

        this.bunnies = new ArrayList<>();
        for (int i = 0; i < initial_bunny_count; i++) {
            Bunny bunny = new Bunny(0, 1.0, 0.1);
            bunnyRepository.save(bunny);
            bunnies.add(bunny);
        }
        this.year = 2024;

        // Create a Simulation instance and save it
        Simulation initialSimulation = new Simulation(environment.getCarryingCapacity(), bunnies.size(), year);
        simulationRepository.save(initialSimulation);

        run();
    }


    private void deleteAppData(){
        bunnyRepository.deleteAll();
        environmentRepository.deleteAll();
        simulationRepository.deleteAll();
    }
    private void run(){
        for (int i = 0; i<howmany_year_similation; i++){
            runYearlyCycle();
        }
    }

    @Transactional
    public void runYearlyCycle() {
        List<Bunny> newBunnies = new ArrayList<>();
        List<Bunny> deadBunnies = new ArrayList<>();
        environment.fluctuateResources();
        environmentRepository.save(environment);

        Iterator<Bunny> iterator = bunnies.iterator();
        while (iterator.hasNext()) {
            Bunny bunny = iterator.next();
//            bunny.age();
//            environment.applyEnvironmentalFactors(bunny, getCurrentAlivePopulation());

            if (!bunny.isAlive()) {
                bunnyRepository.save(bunny);
                deadBunnies.add(bunny);
            } else {
                bunny.age();
                environment.applyEnvironmentalFactors(bunny, getCurrentAlivePopulation());
                bunnyRepository.save(bunny); // Update existing bunny health and age
                if (bunny.canReproduce()) {
                    Bunny offspring = bunny.reproduce();
                    if (offspring != null) {
                        newBunnies.add(offspring);
                    }
                }
            }
        }
        bunnies.addAll(newBunnies);
        bunnies.removeAll(deadBunnies);
        bunnyRepository.saveAll(newBunnies); // Save new bunnies to the database

        year++;

        // Create a new Simulation instance with the updated population and save it
        Simulation yearlySimulation = new Simulation(environment.getCarryingCapacity(), getCurrentAlivePopulation(), year);
        simulationRepository.save(yearlySimulation);
    }

    public int getCurrentPopulation() {
        return bunnies.size();
    }

    public int getCurrentAlivePopulation() {
        return bunnies.stream()
                .filter(Bunny::isAlive) // sadece alive = true olanları alır
                .collect(Collectors.toList()).size(); // sonucu liste olarak toplar
    }



    public int getYear() {
        return year;
    }

    public void setEnvironmentParameters(int carryingCapacity) {
        environment.setCarryingCapacity(carryingCapacity);
        environmentRepository.save(environment);
    }

    public void setBunnyParameters(double reproductionRate, double mutationRate) {
        bunnies.forEach(bunny -> {
            bunny.setReproductionRate(reproductionRate);
            bunny.setMutationRate(mutationRate);
            bunnyRepository.save(bunny);
        });
    }
}
