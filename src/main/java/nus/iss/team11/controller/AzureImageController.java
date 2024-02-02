package nus.iss.team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import nus.iss.team11.controller.service.AzureImageService;
import nus.iss.team11.model.AzureImage;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class AzureImageController {
	@Autowired
	AzureImageService azureImageService; 
	
	@GetMapping(value = "/api/vectors/{fileName}")
	public ResponseEntity<String> getVector(@PathVariable String fileName) {
		AzureImage ai = azureImageService.findImageByFileName(fileName);
		return new ResponseEntity<>(ai.getVector().toString(), HttpStatus.OK);
	}

	
}
