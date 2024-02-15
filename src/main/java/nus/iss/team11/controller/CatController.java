package nus.iss.team11.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import nus.iss.team11.Payload.NewCatRequest;
import nus.iss.team11.Payload.NewSCSUserRequest;
import nus.iss.team11.controller.service.CatService;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.SCSUser;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CatController {
	
	@Autowired
	CatService catService;

	@GetMapping(value = "/api/cat/{id}")
	public ResponseEntity<String> getCat(@PathVariable Integer id) {
		Cat cat = catService.getCatById(id);
		return new ResponseEntity<>(cat.toJSON().toString(), HttpStatus.OK);
	}

	@GetMapping(value = "/api/cat")
	public ResponseEntity<String> getAllCats() {
		List<Cat> cats = catService.getAllCats();
		
		return new ResponseEntity<>(
				cats.stream().map(Cat::toJSON).collect(Collectors.toList()).toString(),
				HttpStatus.OK
				);
	}
	
	@PostMapping(value="api/cat")
	public ResponseEntity<String> createNewCat(@RequestBody NewCatRequest newCatRequest){
		Cat newCat = new Cat();
		newCat=SaveCatToDB(newCatRequest,newCat);
		return new ResponseEntity<>("created : " + String.valueOf(newCat.getId()), HttpStatus.OK);
	
	}
	
	@PutMapping(value="api/cat")
	public ResponseEntity<String> updateCat(@RequestBody NewCatRequest newCatRequest, 
			@RequestParam Integer id){
		Cat catToBeUpdated = catService.getCatById(id);
		catToBeUpdated=SaveCatToDB(newCatRequest,catToBeUpdated);
		return new ResponseEntity<>("updated : " + String.valueOf(catToBeUpdated.getId()), HttpStatus.OK);
	}
	
	@DeleteMapping(value="api/cat")
	public ResponseEntity<String> deleteCat(@RequestParam Integer id){
		Cat catToBeDeleted = catService.getCatById(id);
		if (catToBeDeleted == null) {
			return new ResponseEntity<>("Unknown Cat ID.", HttpStatus.BAD_REQUEST);
		}
		catService.deleteCat(id);
		return new ResponseEntity<>("Deleted: " + String.valueOf(catToBeDeleted.getId()), HttpStatus.OK);
	}
	
	public Cat SaveCatToDB(NewCatRequest newCatRequest, Cat catToBeSaved) {
		catToBeSaved.setCatName(newCatRequest.getCatName());
		catToBeSaved.setCatBreed(newCatRequest.getCatBreed());
		catToBeSaved.setLabels(newCatRequest.getLabels());
		catToBeSaved.setCatLocation(newCatRequest.getCatLocation());
		catToBeSaved.setComments(newCatRequest.getComments());
		catToBeSaved = catService.saveCat(catToBeSaved);
		return catToBeSaved;
	}
}
	
	



