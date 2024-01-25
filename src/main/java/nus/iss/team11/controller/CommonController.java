package nus.iss.team11.controller;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import nus.iss.team11.controller.service.RollService;
import nus.iss.team11.model.Roll;


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

}
