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
//
//    @GetMapping("/population")
//    public int getCurrentPopulation() {
//        return simulationService.getCurrentPopulation();
//    }
//
//    @PostMapping("/setEnvironment")
//    public String setEnvironmentParameters(@RequestParam int carryingCapacity) {
//        simulationService.setEnvironmentParameters(carryingCapacity);
//        return "Environment carrying capacity updated to " + carryingCapacity;
//    }
//
//    @PostMapping("/setBunnyParameters")
//    public String setBunnyParameters(@RequestParam double reproductionRate, @RequestParam double mutationRate) {
//        simulationService.setBunnyParameters(reproductionRate, mutationRate);
//        return "Bunny parameters updated: reproductionRate=" + reproductionRate + ", mutationRate=" + mutationRate;
//    }
//
//    @PostMapping("/config")
//    public ResponseEntity<String> updateConfig(@RequestBody SimulationConfigDto configDto) {
//        //Access carryingCapacity, mutationRate, and reproductionRate from configDto
//        return ResponseEntity.ok("Config updated successfully");
//    }
}
