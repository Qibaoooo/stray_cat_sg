package nus.iss.team11.controller.service;

import java.util.List;

import nus.iss.team11.model.CatSighting;

public interface CatSightingService {
	List<CatSighting> getAllCatSightings();
	CatSighting getCatSightingById(int id);
	CatSighting saveSighting(CatSighting Sighting);
	void deleteSighting(int id);
}
