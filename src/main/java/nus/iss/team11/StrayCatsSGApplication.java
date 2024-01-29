package nus.iss.team11;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import nus.iss.team11.azureUtil.AzureContainerUtil;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.Roll;
import nus.iss.team11.repository.AzureImageRepository;
import nus.iss.team11.repository.CatSightingRepository;
import nus.iss.team11.repository.RollRepository;

@SpringBootApplication
public class StrayCatsSGApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrayCatsSGApplication.class, args);
	}
	
	@Autowired
	AzureContainerUtil azureContainerUtil;
	
	@Bean
	CommandLineRunner loadData(RollRepository rollRepo,
			AzureImageRepository azureImageRepository,
			CatSightingRepository catSightingRepository) {
		return (args) -> {
			// clean start
			rollRepo.deleteAll();
			azureImageRepository.deleteAll();
			catSightingRepository.deleteAll();
			
			Roll r1 = new Roll();
			r1.setResult(99);
			r1.setTimeOfRoll(null);
			rollRepo.save(r1);
			
			
			// load cat sighting test data
			HashMap<String, CatSighting> csMap = new HashMap<String, CatSighting>();
			
			azureContainerUtil.listAllImages().stream().forEach(imageFile -> {
				AzureImage ai = new AzureImage();
				ai.setFileName(imageFile);
				
				String sightingName = ai.deriveSighting();
				CatSighting cs = csMap.get(sightingName);
				
				if (cs == null) {
					// csMap does not have this sighting yet, create one
					cs = new CatSighting();
					
					cs.setSightingName(sightingName);
					cs.setTime(LocalDate.now());
					cs.setApproved(false);
					
					csMap.put(sightingName, cs);
					// we must run `save(cs)` before `save(ai)`!
					catSightingRepository.save(cs);
				}
				
				ai.setCatSighting(cs);
				try {
					ai.setImageURL(azureContainerUtil.deriveImageURL(imageFile));
				} catch (Exception e) {
					e.printStackTrace();
				}
				azureImageRepository.save(ai);
			});;
			
		};
	}
	

}
