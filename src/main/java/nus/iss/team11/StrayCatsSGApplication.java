package nus.iss.team11;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import nus.iss.team11.model.Roll;
import nus.iss.team11.repository.RollRepository;

@SpringBootApplication
public class StrayCatsSGApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrayCatsSGApplication.class, args);
	}
	
	@Bean
	CommandLineRunner loadData(RollRepository rollRepo) {
		return (args) -> {
			// cleam start
			rollRepo.deleteAll();
			
			Roll r1 = new Roll();
			r1.setResult(99);
			r1.setTimeOfRoll(null);
			rollRepo.save(r1);
		};
	}
	

}
