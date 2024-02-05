package nus.iss.team11.controller;

import org.json.JSONArray;
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

import nus.iss.team11.Payload.NewCatSightingRequest;
import nus.iss.team11.controller.service.CatSightingService;
import nus.iss.team11.model.CatSighting;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CatSightingController {

	@Autowired
	CatSightingService catSightingService;

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
	public ResponseEntity<String> createNewCatSighting(@RequestBody NewCatSightingRequest newSightingRequest) {
		CatSighting newCatSighting = new CatSighting();
		return saveCatSightingToDB(newSightingRequest, newCatSighting);
	}

	@PutMapping(value = "/api/cat_sightings")
	public ResponseEntity<String> updateNewCatSighting(@RequestBody NewCatSightingRequest newSightingRequest,
			@RequestParam Integer id) {
		CatSighting csToBeUpdated = catSightingService.getCatSightingById(id);
		return saveCatSightingToDB(newSightingRequest, csToBeUpdated);
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

	private ResponseEntity<String> saveCatSightingToDB(NewCatSightingRequest newSightingRequest,
			CatSighting csToBeSaved) {
		csToBeSaved.setSightingName(newSightingRequest.getSightingName());
		csToBeSaved.setLocationLat(newSightingRequest.getLocationLat());
		csToBeSaved.setLocationLong(newSightingRequest.getLocationLong());
		csToBeSaved.setTime(newSightingRequest.getTime());
		csToBeSaved.setSuggestedCatName(newSightingRequest.getSuggestedCatName());
		csToBeSaved.setSuggestedCatBreed(newSightingRequest.getSuggestedCatBreed());

		csToBeSaved = catSightingService.saveSighting(csToBeSaved);

		return new ResponseEntity<>("Saved : " + String.valueOf(csToBeSaved.getId()), HttpStatus.OK);
	}

}
