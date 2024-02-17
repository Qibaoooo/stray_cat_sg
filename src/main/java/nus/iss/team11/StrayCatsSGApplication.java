package nus.iss.team11;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

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
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.Comment;
import nus.iss.team11.model.OwnerVerification;
import nus.iss.team11.model.SCSUser;
import nus.iss.team11.repository.AzureImageRepository;
import nus.iss.team11.repository.CatRepository;
import nus.iss.team11.repository.CatSightingRepository;
import nus.iss.team11.repository.CommentRepository;
import nus.iss.team11.repository.LostCatRepository;
import nus.iss.team11.repository.OwnerVerificationRepository;
import nus.iss.team11.repository.SCSUserRepository;

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
			CommentRepository commentRepository, OwnerVerificationRepository ownerVerificationRepository) {
		return (args) -> {

			// clean start
			azureImageRepository.deleteAll();
			commentRepository.deleteAll();
			catSightingRepository.deleteAll();
			catRepository.deleteAll();
			lostCatRepository.deleteAll();
			ownerVerificationRepository.deleteAll();
			scsUserRepository.deleteAll();

			// init some test users
			String[] public_users = { "public_user1", "public_user2", "public_user3", "public_user4", "public_user5" };
			String[] owners = { "owner1", "owner2", "owner3" };
			String[] admins = { "admin1", "admin2", "admin3" };

			// init random sign-up dates
			Random random = new Random();
			// 1 to 100 inclusive

			// Subtract the random number of days from the current date

			for (String u : public_users) {
				SCSUser user = new SCSUser();
				user.setUsername(u);
				user.setPassword(encoder.encode(u));
				int randomDays = 1 + random.nextInt(100);
				user.setTime(LocalDate.now().minusDays(randomDays));

				user = scsUserRepository.save(user);
			}
			for (String u : owners) {
				SCSUser user = new SCSUser();
				user.setUsername(u);
				user.setPassword(encoder.encode(u));
				int randomDays = 1 + random.nextInt(100);
				user.setTime(LocalDate.now().minusDays(randomDays));
				user.setOwner(true);
				scsUserRepository.save(user);
			}
			for (String u : admins) {
				SCSUser user = new SCSUser();
				user.setUsername(u);
				user.setPassword(encoder.encode(u));
				user.setAdmin(true);
				int randomDays = 1 + random.nextInt(100);
				user.setTime(LocalDate.now().minusDays(randomDays));
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
					// Creating artificial cat sighting dates
					int randomDays = 1 + random.nextInt(100); // 1 to 100 inclusive

					// Subtract the random number of days from the current date
					LocalDate randomDateBeforeNow = LocalDate.now().minusDays(randomDays);

					cs.setTime(randomDateBeforeNow);
					cs.setSuggestedCatName("test cat");
					cs.setSuggestedCatBreed(getRandomBreed());

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
			Integer processedCount = 1;
			for (Entry<String, CatSighting> entry : csMap.entrySet()) {
				CatSighting cs = entry.getValue();

				Cat cat = new Cat(cs);

				// update relationship
				cs.setCat(cat);
        
				Optional<SCSUser> uploader;
				// set random users as the uploader
				if (processedCount < 4) {
					uploader = scsUserRepository
							.findByUsername("owner" + ((processedCount % 3) + 1));
				}
				else {
				uploader = scsUserRepository
						.findByUsername("public_user" + ((processedCount % 5) + 1));}
				cs.setScsUser(uploader.get());
				processedCount++;

				// approve the all sightings
				cs.setApproved(true);
				cat.setApproved(true);
				cat.setCatBreed(getRandomBreed());

				// set cat and cs
				catRepository.save(cat);
				catSightingRepository.save(cs);
			}

			// dummy comments
			SCSUser cuser = new SCSUser();
			cuser.setUsername("commentuser");
			cuser.setPassword(encoder.encode("commentuser"));
			int randomDays = 1 + random.nextInt(100);
			cuser.setTime(LocalDate.now().minusDays(randomDays));
			scsUserRepository.save(cuser);
			Comment c1 = new Comment();
			c1.setContent("he is a cute cat");
			c1.setCat(csMap.get("Ameer").getCat());
			c1.setTime(new Date());
			c1.setScsUser(cuser);
			c1.setNewlabels(new ArrayList<String>());
			c1.setFlagged(random.nextInt(100) < 30); // 30% chance of flagged
			commentRepository.save(c1);

			// Creating more dummy comments
			List<String> csMapKeys = new ArrayList<>(csMap.keySet());
			for (int i = 1; i <= 15; i++) {
				// Create a new user for each comment
				SCSUser temp = new SCSUser();
				temp.setUsername("commentuser" + i);
				temp.setPassword(encoder.encode("password" + i));

				// Creating artificial user sign up and cat sighting dates
				randomDays = 1 + random.nextInt(100);
				LocalDate randomDateBeforeNow = LocalDate.now().minusDays(randomDays);
				temp.setTime(randomDateBeforeNow);
				scsUserRepository.save(temp); // Save the new user

				// Create a new comment
				Comment comment = new Comment();
				comment.setContent("comment " + i); // Unique content for each comment
				int randomCS = random.nextInt(csMapKeys.size());
				comment.setCat(csMap.get(csMapKeys.get(randomCS)).getCat());
				comment.setFlagged(random.nextInt(100) < 30);

				// Subtract the random number of days from the current date and to Date object
				// Prevent comment date to be before sign up date
				LocalDate commentDate = randomDateBeforeNow.plusDays(random.nextInt(100));
				randomDateBeforeNow = LocalDate.now().isBefore(commentDate) ? LocalDate.now() : commentDate;
				Date date = Date.from(randomDateBeforeNow.atStartOfDay(ZoneId.systemDefault()).toInstant());

				comment.setTime(date);
				comment.setScsUser(temp);
				comment.setNewlabels(new ArrayList<>());
				commentRepository.save(comment);
			}

			// load vector for test images
			loadVectors(azureImageRepository);
		};
	}

	private String getRandomBreed() {
		List<String> breeds = List.of("Abyssinian", "British Shorthair", "Burmese", "Cornish Rex", "Devon Rex",
				"Himalayan", "Maine Coon", "Manx", "Persian", "Russian Blue", "Scottish Fold", "Siamese", "Sphynx",
				"Turkish Angora", "Turkish Van");
		return breeds.get(new Random().nextInt(breeds.size()));
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
