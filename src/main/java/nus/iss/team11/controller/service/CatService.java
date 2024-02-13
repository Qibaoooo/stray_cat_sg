package nus.iss.team11.controller.service;

import java.util.List;

import nus.iss.team11.model.Cat;

public interface CatService {
	Cat getCatById(int id);
	Cat saveCat(Cat cat);
	List<Cat> getAllCats();
	void deleteCat(Integer id);
	
	
	
}
