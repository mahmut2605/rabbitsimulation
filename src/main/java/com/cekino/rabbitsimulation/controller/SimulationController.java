package com.cekino.rabbitsimulation.controller;


import com.cekino.rabbitsimulation.service.SimulationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {
    private final SimulationService simulationService;

    @Value("${initial_bunny_count}")
    private int initialBunnyCount;


    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping("/run")
    public String runSimulation() {
        simulationService.runYearlyCycle();
        System.out.println(initialBunnyCount);
        return "";
    }

}
