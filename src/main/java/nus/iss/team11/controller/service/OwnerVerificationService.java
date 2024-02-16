package nus.iss.team11.controller.service;

import java.util.List;

import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.OwnerVerification;

public interface OwnerVerificationService {
	List<OwnerVerification> findAllOVs();
	OwnerVerification saveOwnerVerification (OwnerVerification ownerVerification);
	OwnerVerification getOwnerVerificationById(int id);
	void deleteVerification(int id);
}
