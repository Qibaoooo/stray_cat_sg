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

import com.azure.core.annotation.PathParam;

import nus.iss.team11.Payload.NewCatSightingRequest;
import nus.iss.team11.Payload.NewSCSUserRequest;
import nus.iss.team11.controller.service.SCSUserService;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.SCSUser;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class SCSUserController {
	
	@Autowired
	SCSUserService scsUserService;
	
	@GetMapping(value = "/api/scsusers")
	public ResponseEntity<String> getAllUser(){
		JSONArray scsUsers = new JSONArray();
		scsUserService.getAllSCSUsers().stream().forEach(scsUser ->{
				scsUsers.put(scsUser.toJSON());
		});
		return new ResponseEntity<>(scsUsers.toString(), HttpStatus.OK);
	}

	@GetMapping(value="/api/scsusers/{id}")
	public ResponseEntity<String> getSingleUser(@PathVariable Integer id){
		SCSUser user = scsUserService.getUserById(id);
		return new ResponseEntity<>(user.toJSON().toString(), HttpStatus.OK);
	}
	
	@PostMapping(value="/api/scsusers")
	public ResponseEntity<String> createSCSUser(@RequestBody NewSCSUserRequest newUserRequest){
		SCSUser userToBeSaved = new SCSUser();
		
		return SaveSCSUserToDB(newUserRequest, userToBeSaved);
	}
	
	@PutMapping(value="/api/scsusers")
	public ResponseEntity<String> updateSCSUser(@RequestBody NewSCSUserRequest newUserRequest, 
			@RequestParam Integer id){
		SCSUser userToBeSaved = scsUserService.getUserById(id);
		
		return SaveSCSUserToDB(newUserRequest, userToBeSaved);
	}
	
	@DeleteMapping(value="/api/scsusers")
	public ResponseEntity<String> deleteSCSUser(@RequestParam Integer id){
		SCSUser userToBeDeleted = scsUserService.getUserById(id);
		if (userToBeDeleted == null) {
			return new ResponseEntity<>("Unknown SCSUser id.", HttpStatus.BAD_REQUEST);
		}
		scsUserService.deleteSCSUser(id);
		return new ResponseEntity<>("Deleted: " + String.valueOf(userToBeDeleted.getId()), HttpStatus.OK);
	}
	
	public ResponseEntity<String> SaveSCSUserToDB(NewSCSUserRequest newUserRequest, SCSUser userToBeSaved) {
		userToBeSaved.setUsername(newUserRequest.getUsername());
		userToBeSaved.setPassword(newUserRequest.getPassword());
		userToBeSaved.setOwnerVerification(newUserRequest.getOwnerVerification());
		userToBeSaved = scsUserService.saveSCSUser(userToBeSaved);
		return new ResponseEntity<>("Saved: " + String.valueOf(userToBeSaved.getId()), HttpStatus.OK);
	}
	
	

}
