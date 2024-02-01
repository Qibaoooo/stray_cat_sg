package nus.iss.team11.controller;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	
}
