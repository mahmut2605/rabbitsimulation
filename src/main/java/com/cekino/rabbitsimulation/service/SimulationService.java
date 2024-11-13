package com.cekino.rabbitsimulation.service;


import com.cekino.rabbitsimulation.entity.Bunny;
import com.cekino.rabbitsimulation.entity.Environment;
import com.cekino.rabbitsimulation.entity.Simulation;
import com.cekino.rabbitsimulation.repository.BunnyRepository;
import com.cekino.rabbitsimulation.repository.EnvironmentRepository;
import com.cekino.rabbitsimulation.repository.SimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SimulationService {

    private final BunnyService bunnyService;
    private final EnvironmentService environmentService;
    private final SimulationRepository simulationRepository;

    @Value("${initial_bunny_count}")
    private int initialBunnyCount;

    @Value("${howmany_year_similation}")
    private int howmany_year_similation;

    @Value("${initial_year}")
    private int initial_year;

    private List<Bunny> bunnies;
    private Environment environment;
    private int year;

    public void initializeSimulation() {
        Instant start = Instant.now();
        this.year = initial_year;
        deleteAppData();

        environment = environmentService.getEnvironment();
        environmentService.saveEnvironment(environment);

        this.bunnies = new ArrayList<>();
        for (int i = 0; i < initialBunnyCount; i++) {
            Bunny bunny = bunnyService.getBunny();
            bunnyService.saveBunny(bunny);
            bunnies.add(bunny);
        }

        simulationRepository.save(getSimulation(environment.getCarryingCapacity(), bunnies.size(), year));

        for (int i = 0; i<howmany_year_similation; i++){
            runYearlyCycle();
        }

        Instant finish = Instant.now();
        long timeElapsedSecond = Duration.between(start, finish).toSeconds();

        System.out.println("Similasyon tamamlandı. Veritabanındaki similasyon tablosuna bakabilirsiniz. Proje " + timeElapsedSecond + " saniye tamamlanmıştır." );

    }

    private Simulation getSimulation(int carryingCapacity, int population, int year){
        return new Simulation(carryingCapacity, population, year);
    }

    private void deleteAppData(){
        bunnyService.deleteAllBunny();
        environmentService.deleteAllEnvironment();
        simulationRepository.deleteAll();
    }

    public void runYearlyCycle() {
        List<Bunny> newBunnies = new ArrayList<>();
        List<Bunny> deadBunnies = new ArrayList<>();
        environmentService.fluctuateResources(environment);
        environmentService.saveEnvironment(environment);

        Iterator<Bunny> iterator = bunnies.iterator();
        while (iterator.hasNext()) {
            Bunny bunny = iterator.next();

            if (!bunny.isAlive()) {
                bunnyService.saveBunny(bunny);
                deadBunnies.add(bunny);
            } else {
                bunnyService.bunnyGrowUpOneYear(bunny);

                environmentService.applyEnvironmentalFactors(bunny, getCurrentAlivePopulation(),environment);
                bunnyService.saveBunny(bunny);
                if (bunnyService.canReproduce(bunny)) {
                    Bunny offspring = bunnyService.reproduce(bunny);
                    if (offspring != null) {
                        newBunnies.add(offspring);
                    }
                }
            }
        }
        bunnies.addAll(newBunnies);
        bunnies.removeAll(deadBunnies);
        bunnyService.saveAllBunnies(newBunnies);

        year++;

        simulationRepository.save(getSimulation(environment.getCarryingCapacity(), getCurrentAlivePopulation(), year));
    }

    public int getCurrentAlivePopulation() {
        return bunnies.stream()
                .filter(Bunny::isAlive)
                .collect(Collectors.toList()).size();
    }

}
