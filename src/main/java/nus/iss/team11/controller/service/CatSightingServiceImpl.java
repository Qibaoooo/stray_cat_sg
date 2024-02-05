package nus.iss.team11.controller.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.team11.model.CatSighting;
import nus.iss.team11.repository.CatSightingRepository;

@Service
public class CatSightingServiceImpl implements CatSightingService {

	@Autowired
	CatSightingRepository catSightingRepository;

	@Override
	public List<CatSighting> getAllCatSightings() {
		return catSightingRepository.findAll();
	}
	
	@Override
	public CatSighting getCatSightingById(int id) {
		return catSightingRepository.getReferenceById(id);
	}
	
	@Override
	public CatSighting createSighting(CatSighting Sighting){
		return catSightingRepository.saveAndFlush(Sighting);
	}

}
