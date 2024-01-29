package nus.iss.team11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import nus.iss.team11.azureUtil.AzureContainerUtil;
import nus.iss.team11.model.Roll;
import nus.iss.team11.repository.RollRepository;

@SpringBootApplication
public class StrayCatsSGApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrayCatsSGApplication.class, args);
	}
	
	@Autowired
	AzureContainerUtil azureContainerUtil;
	
	@Bean
	CommandLineRunner loadData(RollRepository rollRepo) {
		return (args) -> {
			// cleam start
			rollRepo.deleteAll();
			
			Roll r1 = new Roll();
			r1.setResult(99);
			r1.setTimeOfRoll(null);
			rollRepo.save(r1);
			
			
			// test azureContainerUtil
			azureContainerUtil.listAllImages();
		};
	}
	

}
