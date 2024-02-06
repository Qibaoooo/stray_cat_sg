package nus.iss.team11.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import netscape.javascript.JSObject;
import nus.iss.team11.Payload.NewCatSightingRequest;
import nus.iss.team11.azureUtil.AzureContainerUtil;
import nus.iss.team11.controller.service.AzureImageService;
import nus.iss.team11.controller.service.CatService;
import nus.iss.team11.controller.service.CatSightingService;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.CatSighting;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CatSightingController {

	@Autowired
	CatSightingService catSightingService;

	@Autowired
	CatService catService;
	
	@Autowired
	AzureContainerUtil azureContainerUtil;
	
	@Autowired
	AzureImageService azureImageService;

	@GetMapping(value = "/api/cat_sightings")
	public ResponseEntity<String> getAllCS() {
		JSONArray sightings = new JSONArray();

		catSightingService.getAllCatSightings().stream().forEach(sighting -> {
			sightings.put(sighting.toJSON());
		});

		return new ResponseEntity<>(sightings.toString(), HttpStatus.OK);
	}

	@GetMapping(value = "/api/cat_sightings/{id}")
	public ResponseEntity<String> getSingleCS(@PathVariable Integer id) {
		CatSighting cs = catSightingService.getCatSightingById(id);
		return new ResponseEntity<>(cs.toJSON().toString(), HttpStatus.OK);
	}

	@PostMapping(value = "/api/cat_sightings")
	public ResponseEntity<String> createNewCatSighting(@RequestBody NewCatSightingRequest newSightingRequest) throws Exception {
		CatSighting newCatSighting = new CatSighting();
		newCatSighting = saveCatSightingToDB(newSightingRequest, newCatSighting);
		
		// creat cat object
		Cat newCat = new Cat(newCatSighting);
		newCat = catService.saveCat(newCat);

		// update relationship
		newCatSighting.setCat(newCat);
		newCatSighting = catSightingService.saveSighting(newCatSighting);
		
		JSONObject json = new JSONObject();
		json.put("catSighting", newCatSighting.getId());
		json.put("cat", newCat.getId());
		
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);

	}

	@PutMapping(value = "/api/cat_sightings")
	public ResponseEntity<String> updateNewCatSighting(@RequestBody NewCatSightingRequest newSightingRequest,
			@RequestParam Integer id) throws Exception {
		CatSighting csToBeUpdated = catSightingService.getCatSightingById(id);
		csToBeUpdated = saveCatSightingToDB(newSightingRequest, csToBeUpdated);
		
		return new ResponseEntity<>("Updated : " + String.valueOf(csToBeUpdated.getId()), HttpStatus.OK);

	}

	@DeleteMapping(value = "/api/cat_sightings")
	public ResponseEntity<String> deleteNewCatSighting(@RequestParam Integer id) {
		CatSighting csToBeDeleted = catSightingService.getCatSightingById(id);
		if (csToBeDeleted == null) {
			return new ResponseEntity<>("unknown cat sighting id.", HttpStatus.BAD_REQUEST);
		}
		catSightingService.deleteSighting(id);
		return new ResponseEntity<>("Deleted : " + String.valueOf(csToBeDeleted.getId()), HttpStatus.OK);
	}

	private CatSighting saveCatSightingToDB(NewCatSightingRequest newSightingRequest,
			CatSighting csToBeSaved) throws Exception {

		// save catSighting object
		csToBeSaved.setSightingName(newSightingRequest.getSightingName());
		csToBeSaved.setLocationLat(newSightingRequest.getLocationLat());
		csToBeSaved.setLocationLong(newSightingRequest.getLocationLong());
		csToBeSaved.setTime(
				Instant.ofEpochMilli(newSightingRequest.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
		csToBeSaved.setSuggestedCatName(newSightingRequest.getSuggestedCatName());
		csToBeSaved.setSuggestedCatBreed(newSightingRequest.getSuggestedCatBreed());

		csToBeSaved = catSightingService.saveSighting(csToBeSaved);
		
		// save azureImage objects
		List<String> tempImageURLs = newSightingRequest.getTempImageURLs();

		for (int i = 0; i < tempImageURLs.size(); i++) {
			AzureImage ai = new AzureImage();
			ai.setFileName(getFileNameForSightingPhoto(newSightingRequest.getSightingName(), i, tempImageURLs.get(i)));
			ai.setImageURL(azureContainerUtil.deriveImageURL(ai.getFileName()));
			ai.setCatSighting(csToBeSaved);
			
			azureContainerUtil.moveTempImageToImagesContainer(tempImageURLs.get(i), ai.getFileName());
			
			azureImageService.saveImage(ai);
		}
		
		return csToBeSaved;
	}
	
	private String getFileNameForSightingPhoto(String sightingName, int idx, String tempImageURL) {
		String[] urlSplit = tempImageURL.split("\\.");
		String fileType = urlSplit[urlSplit.length - 1];
		return sightingName + "_photo_" + idx + "." + fileType;
	}

}
