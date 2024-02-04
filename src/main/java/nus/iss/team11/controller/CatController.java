package nus.iss.team11.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import nus.iss.team11.controller.service.CatService;
import nus.iss.team11.model.Cat;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CatController {
	
	@Autowired
	CatService catService;

	@GetMapping(value = "/api/cat/{id}")
	public ResponseEntity<String> getCat(@PathVariable Integer id) {
		Cat cat = catService.findCatById(id);
		return new ResponseEntity<>(cat.toJSON().toString(), HttpStatus.OK);
	}

	@GetMapping(value = "/api/cat")
	public ResponseEntity<String> getAllCats() {
		List<Cat> cats = catService.findAllCats();
		
		return new ResponseEntity<>(
				cats.stream().map(Cat::toJSON).collect(Collectors.toList()).toString(),
				HttpStatus.OK
				);
	}

}



