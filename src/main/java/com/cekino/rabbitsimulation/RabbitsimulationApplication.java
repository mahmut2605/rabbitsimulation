package com.cekino.rabbitsimulation;

import com.cekino.rabbitsimulation.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitsimulationApplication implements CommandLineRunner {

	@Autowired
	private SimulationService simulationService;

	public static void main(String[] args) {

		SpringApplication.run(RabbitsimulationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		simulationService.initializeSimulation();
	}
}
