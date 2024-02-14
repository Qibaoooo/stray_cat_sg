package nus.iss.team11.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.team11.model.OwnerVerification;
import nus.iss.team11.repository.OwnerVerificationRepository;

@Service
public class OwnerVerificationServiceImpl implements OwnerVerificationService {

	@Autowired
	OwnerVerificationRepository ownerVerificationRepository;
	@Override
	public List<OwnerVerification> findAllOVs() {
		return ownerVerificationRepository.findAll();
	}
	
}
