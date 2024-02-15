package nus.iss.team11.controller;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import nus.iss.team11.Payload.NewVerificationRequest;
import nus.iss.team11.controller.service.OwnerVerificationService;
import nus.iss.team11.model.OwnerVerification;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class OwnerVerificationController {
	@Autowired
	OwnerVerificationService ownerVerificationService;

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
	public ResponseEntity<String> createOwnerVerification(@RequestBody NewVerificationRequest newVerificationRequest) {
		OwnerVerification verificationToBeSaved = new OwnerVerification();

		return SaveVerificationToDB(newVerificationRequest, verificationToBeSaved);
	}

	public ResponseEntity<String> SaveVerificationToDB(NewVerificationRequest newVerificationRequest,
			OwnerVerification verificationToBeSaved) {
		verificationToBeSaved.setStatus("Pending");
		verificationToBeSaved.setImageURL((newVerificationRequest.getImageURL()));
		verificationToBeSaved.setId(newVerificationRequest.getUserId());
		verificationToBeSaved = ownerVerificationService.saveOwnerVerification(verificationToBeSaved);
		return new ResponseEntity<>("Saved: " + String.valueOf(verificationToBeSaved.getId()), HttpStatus.OK);
	}

}
