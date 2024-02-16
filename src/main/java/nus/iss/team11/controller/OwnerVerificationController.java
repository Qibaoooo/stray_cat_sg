package nus.iss.team11.controller;

import java.security.Principal;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import nus.iss.team11.Payload.NewCatSightingRequest;
import nus.iss.team11.Payload.NewVerificationRequest;
import nus.iss.team11.controller.service.OwnerVerificationService;
import nus.iss.team11.controller.service.SCSUserService;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.OwnerVerification;
import nus.iss.team11.model.SCSUser;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class OwnerVerificationController {
	@Autowired
	OwnerVerificationService ownerVerificationService;
	
	@Autowired
	SCSUserService scsUserService;

	@GetMapping(value = "/api/verification")
	public ResponseEntity<String> getAllOV() {
		List<OwnerVerification> OVs = ownerVerificationService.findAllOVs();
		JSONArray json = new JSONArray();

		OVs.forEach(ov -> {
			json.put(ov.toJSON());
		});

		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
	}

	@PostMapping(value = "/api/verification")
	public ResponseEntity<String> createOwnerVerification(@RequestBody NewVerificationRequest newVerificationRequest, Principal principal) {
		OwnerVerification ovToBeSaved = new OwnerVerification();
		

		SCSUser user;
		try {
			user = scsUserService.getUserByUsername(principal.getName()).get();
		} catch (Exception e) {
			return new ResponseEntity<>("invalid user", HttpStatus.BAD_REQUEST);
		}
		ovToBeSaved.setUser(user);
		
		ovToBeSaved = saveVerificationToDB(newVerificationRequest, ovToBeSaved);
		
		return new ResponseEntity<>("Saved: " + String.valueOf(ovToBeSaved.getId()), HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/api/verification/approve")
	public ResponseEntity<String> updateApprovalStatus(@RequestParam Integer id) {
		OwnerVerification Ov = ownerVerificationService.getOwnerVerificationById(id);
		if (Ov == null) {
			return new ResponseEntity<>("unknown verification id.", HttpStatus.BAD_REQUEST);
		}
		Ov.setStatus("Approved");
		
		SCSUser user = Ov.getUser();
	    user.setOwner(true);
	    
	    scsUserService.saveSCSUser(user);
		ownerVerificationService.saveOwnerVerification(Ov);
		return new ResponseEntity<>("Approved : " + String.valueOf(Ov.getId()), HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/api/verification")
	public ResponseEntity<String> deleteOwnerVerification(@RequestParam Integer id) {
		OwnerVerification ovToBeDeleted = ownerVerificationService.getOwnerVerificationById(id);
		if (ovToBeDeleted == null) {
			return new ResponseEntity<>("unknown owner verification id.", HttpStatus.BAD_REQUEST);
		}
		ownerVerificationService.deleteVerification(id);
		return new ResponseEntity<>("Deleted : " + String.valueOf(ovToBeDeleted.getId()), HttpStatus.OK);
	}
	
	
	

	public OwnerVerification saveVerificationToDB(NewVerificationRequest newVerificationRequest,
			OwnerVerification ovToBeSaved) {
		ovToBeSaved.setStatus("Pending");
		ovToBeSaved.setImageURL((newVerificationRequest.getImageURL()));
//		ovToBeSaved.setId(newVerificationRequest.getUserId());
		ovToBeSaved = ownerVerificationService.saveOwnerVerification(ovToBeSaved);
		return ovToBeSaved;
	}

}
