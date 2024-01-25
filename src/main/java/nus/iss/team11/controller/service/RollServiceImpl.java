package nus.iss.team11.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.team11.model.Roll;
import nus.iss.team11.repository.RollRepository;

@Service
public class RollServiceImpl implements RollService {
	
	@Autowired
	RollRepository repo;
	
	@Override
	public Roll recordRoll(Roll roll) {
		return repo.save(roll);
	}

}
