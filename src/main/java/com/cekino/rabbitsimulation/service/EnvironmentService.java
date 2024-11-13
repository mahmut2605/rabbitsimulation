package com.cekino.rabbitsimulation.service;

import com.cekino.rabbitsimulation.entity.Bunny;
import com.cekino.rabbitsimulation.entity.Environment;
import com.cekino.rabbitsimulation.repository.EnvironmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class EnvironmentService {
    private Random random = new Random();
    private final BunnyService bunnyService;
    private final EnvironmentRepository environmentRepository;

    public void saveEnvironment(Environment environment){
        environmentRepository.save(environment);
    }
    public void deleteAllEnvironment(){
        environmentRepository.deleteAll();
    }

    public void applyEnvironmentalFactors(Bunny bunny, int currentPopulation , Environment environment) {
        double healthFactor = 0;


        if (currentPopulation > environment.getCarryingCapacity()) {
            healthFactor =  -1 * (((double) (currentPopulation / environment.getCarryingCapacity()) * 10) + (1 / bunny.getMutationRate()));
        }

        // TO:DO bu formüle bak
        if (currentPopulation < environment.getCarryingCapacity()) {
            healthFactor =  (((double) currentPopulation / environment.getCarryingCapacity()) * (bunny.getMutationRate() )) + 5;
        }

        if (random.nextDouble() < 0.1) { // %10 ihtimalle hastalık gibi durum olabilir ve hayvanın sağlığı ekstra düşebilir
            healthFactor -= 5 * (1 / bunny.getMutationRate());
        }

        bunnyService.adjustHealth(bunny,healthFactor);
    }

    public void fluctuateResources(Environment environment) { // ortam kapasitesini dalgalandırıyorum artıp azalabilir
        if (random.nextDouble() < 0.2) {
            environment.setCarryingCapacity(environment.getCarryingCapacity() + random.nextInt(20) - 10);
            if (environment.getCarryingCapacity() < 0) {
                environment.setCarryingCapacity(0);
            }
        }
    }
}
