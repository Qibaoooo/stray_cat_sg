package nus.iss.team11;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import nus.iss.team11.azureUtil.AzureContainerUtil;
import nus.iss.team11.dataUtil.CSVUtil;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.LostCat;
import nus.iss.team11.model.Roll;
import nus.iss.team11.repository.AzureImageRepository;
import nus.iss.team11.repository.CatSightingRepository;
import nus.iss.team11.repository.CatRepository;
import nus.iss.team11.repository.LostCatRepository;
import nus.iss.team11.repository.RollRepository;

@SpringBootApplication
public class StrayCatsSGApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrayCatsSGApplication.class, args);
	}

	@Autowired
	AzureContainerUtil azureContainerUtil;

	@Bean
	CommandLineRunner loadData(RollRepository rollRepo, AzureImageRepository azureImageRepository,
			CatSightingRepository catSightingRepository, CatRepository catRepository,
			LostCatRepository lostCatRepository) {
		return (args) -> {

			HashMap<String, List<Float>> vMap = 
					CSVUtil.readCSVIntoHashMap("./src/main/resources/vectors.csv");

			// clean start
			rollRepo.deleteAll();
			azureImageRepository.deleteAll();
			catSightingRepository.deleteAll();
			catRepository.deleteAll();
			lostCatRepository.deleteAll();

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
					cs.setSuggestedCatName("test cat 1");
					cs.setSuggestedCatBreed("test cat breed 1");

					setLatLongForCatSighting(cs);

					csMap.put(sightingName, cs);
					// we must run `save(cs)` before `save(ai)`!
					cs = catSightingRepository.save(cs);
				}

				ai.setCatSighting(cs);
				try {
					ai.setImageURL(azureContainerUtil.deriveImageURL(imageFile));
				} catch (Exception e) {
					e.printStackTrace();
				}
				azureImageRepository.save(ai);
			});
			;

			// group cat sightings into cats
			// only for first 5 sightings!
			csMap.keySet().stream().limit(5).forEach((csName) -> {
				// init cat from sighting
				Cat cat = new Cat();
				CatSighting cs = csMap.get(csName);
				cat.setCatName("cat from " + cs.getSightingName());
				cat.setLabels(Arrays.asList("test0", "test1", "test2"));
				catRepository.save(cat);

				// update relationship
				cs.setCat(cat);
				cs.setApproved(true);
				catSightingRepository.save(cs);
			});

		};
	}

	private void setLatLongForCatSighting(CatSighting cs) {
		// left of sg
		// {"lat":1.3289069765802501,"lng":103.65466244818562}
		// right of sg
		// {"lat":1.3287119649067418,"lng":103.9591429253922}

		// top of sg
		// {"lat":1.4448702372292983,"lng":103.79211437104709}
		// bottom of sg
		// {"lat":1.2883596413134546,"lng":103.80069743989475}
		Random r = new Random();
		float lng = (float) (103.65 + r.nextFloat() * (103.95 - 103.65));
		float lat = (float) (1.288 + r.nextFloat() * (1.444 - 1.288));
		cs.setLocationLat(lat);
		cs.setLocationLong(lng);
	}

}
