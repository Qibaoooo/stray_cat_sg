package nus.iss.team11.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import nus.iss.team11.controller.service.RollService;
import nus.iss.team11.model.Roll;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;


@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommonController {
	
	@Autowired
	RollService rollService;
	
	@GetMapping(value = "/roll")
	public ResponseEntity<String> getRoll(Integer elements) {
		Random ran = new Random();
		Integer randInt = ran.nextInt(7);
		
		Roll roll = new Roll();
		roll.setResult(randInt);
		roll.setTimeOfRoll(LocalDate.now());
		
		rollService.recordRoll(roll);
		
		return new ResponseEntity<>(randInt.toString(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/android/test")
	public ResponseEntity<CatSighting> testComm(){
		CatSighting cs11 = new CatSighting();
		cs11.setSightingName("android test 111");
		cs11.setTime(LocalDate.now());
		cs11.setLocationLat(0f);
		cs11.setLocationLong(0f);
		
		List<AzureImage> imgs = new ArrayList<>();
		AzureImage img1 = new AzureImage();
		img1.setFileName("123");
		AzureImage img2 = new AzureImage();
		img2.setFileName("456");
		imgs.add(img1);
		imgs.add(img2);
		cs11.setImages(imgs);
		return new ResponseEntity<CatSighting>(cs11,HttpStatus.OK);
	}
	
	@GetMapping(value = "/android/test2")
	public ResponseEntity<List<CatSighting>> testComm2(){
		CatSighting cs11 = new CatSighting();
		cs11.setSightingName("android test 111");
		cs11.setTime(LocalDate.now());
		cs11.setLocationLat(0f);
		cs11.setLocationLong(0f);
		
		List<AzureImage> imgs = new ArrayList<>();
		AzureImage img1 = new AzureImage();
		img1.setFileName("123");
		AzureImage img2 = new AzureImage();
		img2.setFileName("456");
		imgs.add(img1);
		imgs.add(img2);
		cs11.setImages(imgs);
		
		CatSighting cs12 = new CatSighting();
		cs12.setSightingName("android test 111");
		cs12.setTime(LocalDate.now());
		cs12.setLocationLat(0f);
		cs12.setLocationLong(0f);
		
		List<AzureImage> imgs2 = new ArrayList<>();
		AzureImage img12 = new AzureImage();
		img12.setFileName("123");
		AzureImage img22 = new AzureImage();
		img22.setFileName("456");
		imgs2.add(img1);
		imgs2.add(img2);
		cs12.setImages(imgs2);
		List<CatSighting> csList = new ArrayList<>();
		csList.add(cs11);
		csList.add(cs12);
		return new ResponseEntity<List<CatSighting>>(csList,HttpStatus.OK);
	}

}
