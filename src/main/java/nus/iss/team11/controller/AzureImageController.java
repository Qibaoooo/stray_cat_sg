package nus.iss.team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import nus.iss.team11.azureUtil.AzureContainerUtil;
import nus.iss.team11.controller.service.AzureImageService;
import nus.iss.team11.model.AzureImage;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class AzureImageController {
	@Autowired
	AzureImageService azureImageService;
	
	@Autowired
	AzureContainerUtil azureContainerUtil;
	
	@GetMapping(value = "/api/vectors/{fileName}")
	public ResponseEntity<String> getVector(@PathVariable String fileName) {
		AzureImage ai = azureImageService.findImageByFileName(fileName);
		return new ResponseEntity<>(ai.getVector().toString(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/api/images")
	public ResponseEntity<String> upload(@RequestBody byte[] imageFile, @RequestParam String fileName) {
		
		if (imageFile.length == 0) {
			return new ResponseEntity<>("no imageFile in request body", HttpStatus.BAD_REQUEST);
		}
		if (fileName == null) {
			return new ResponseEntity<>("no fileName in request param", HttpStatus.BAD_REQUEST);
		}
		
		String blobUrl = azureContainerUtil.uploadToTempContainer(imageFile, fileName);
		return new ResponseEntity<>(blobUrl, HttpStatus.OK);
	}
	
}
