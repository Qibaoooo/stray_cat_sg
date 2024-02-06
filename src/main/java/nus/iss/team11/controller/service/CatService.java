package nus.iss.team11.controller.service;

import java.util.List;

import nus.iss.team11.model.Cat;

public interface CatService {
	Cat findCatById(int id);
	Cat saveCat(Cat cat);
	List<Cat> findAllCats();
}
