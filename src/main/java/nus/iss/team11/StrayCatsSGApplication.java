package nus.iss.team11;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.opencsv.exceptions.CsvException;

import nus.iss.team11.azureUtil.AzureContainerUtil;
import nus.iss.team11.dataUtil.CSVUtil;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.Comment;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.LostCat;
import nus.iss.team11.model.Roll;
import nus.iss.team11.model.SCSUser;
import nus.iss.team11.repository.AzureImageRepository;
import nus.iss.team11.repository.CatSightingRepository;
import nus.iss.team11.repository.CatRepository;
import nus.iss.team11.repository.LostCatRepository;
import nus.iss.team11.repository.RollRepository;
import nus.iss.team11.repository.SCSUserRepository;
import nus.iss.team11.repository.CommentRepository;

@SpringBootApplication
public class StrayCatsSGApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrayCatsSGApplication.class, args);
	}

	@Autowired
	AzureContainerUtil azureContainerUtil;

	@Autowired
	CSVUtil csvUtil;

	@Autowired
	PasswordEncoder encoder;

	@Bean
	CommandLineRunner loadData(AzureImageRepository azureImageRepository, CatSightingRepository catSightingRepository,
			CatRepository catRepository, SCSUserRepository scsUserRepository, LostCatRepository lostCatRepository,
			CommentRepository commentRepository) {
		return (args) -> {

			// clean start
			azureImageRepository.deleteAll();
			commentRepository.deleteAll();
			catSightingRepository.deleteAll();
			catRepository.deleteAll();
			lostCatRepository.deleteAll();
			scsUserRepository.deleteAll();

			// init some test users
			String[] public_users = { "public_user1", "public_user2", "public_user13" };
			String[] owners = { "owner1", "owner2", "owner3" };
			String[] admins = { "admin1", "admin2", "admin3" };

			for (String u : public_users) {
				SCSUser user = new SCSUser();
				user.setUsername(u);
				user.setPassword(encoder.encode(u));
				scsUserRepository.save(user);
			}
			for (String u : owners) {
				SCSUser user = new SCSUser();
				user.setUsername(u);
				user.setPassword(encoder.encode(u));
				user.setOwner(true);
				scsUserRepository.save(user);
			}
			for (String u : admins) {
				SCSUser user = new SCSUser();
				user.setUsername(u);
				user.setPassword(encoder.encode(u));
				user.setAdmin(true);
				scsUserRepository.save(user);
			}

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
			// only do .setApproved(true) for first 5 sightings!
			Integer approvedCount = 0;
			for (Entry<String, CatSighting> entry: csMap.entrySet()) {
				CatSighting cs = entry.getValue();
				
				Cat cat = new Cat(cs);

				// update relationship
				cs.setCat(cat);
				cat.addLabel("forTesting");
				
				// approve the first 5 sightings
				if (approvedCount < 5) {
					cs.setApproved(true);
					cat.setApproved(true);
					approvedCount++;
				}
				
				// set cat and cs
				catRepository.save(cat);
				catSightingRepository.save(cs);
			}
			
			// dummy comments
			SCSUser cuser = new SCSUser();
			cuser.setUsername("commentuser");
			cuser.setPassword(encoder.encode("commentuser"));
			scsUserRepository.save(cuser);
			Comment c1 = new Comment();
			c1.setContent("comment 1");
			c1.setCat(csMap.get("cat_sightings_12").getCat());
			c1.setTime(new Date());
			c1.setScsUser(cuser);
			c1.setNewlabels(new ArrayList<String>());
			commentRepository.save(c1);

			// load vector for test images
			loadVectors(azureImageRepository);
		};
	}

	private void loadVectors(AzureImageRepository azureImageRepository) throws IOException, CsvException {
		boolean readFromLocal = false;
		HashMap<String, String> vMap;
		if (readFromLocal) {
			vMap = csvUtil.readCSVIntoHashMap("./src/main/resources/vectors.csv");
		} else {
			vMap = azureContainerUtil.readCSVIntoHashMap("vectors.csv");
		}

		vMap.keySet().stream().forEach(fileName -> {

			AzureImage ai = azureImageRepository.findByFileName(fileName);
			System.out.println("setting for file " + fileName);
			ai.setVector(vMap.get(fileName));
			azureImageRepository.save(ai);

		});
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
