package nus.iss.team11.controller.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.team11.model.Cat;
import nus.iss.team11.repository.CatRepository;

@Service
public class CatServiceImpl implements CatService {

	@Autowired
	CatRepository catRepository;

	@Override
	public Cat getCatById(int id) {
		return catRepository.getReferenceById(id);
	}

	@Override
	public List<Cat> getAllCats() {
		return catRepository.findAll(); 
	}

	@Override
	public Cat saveCat(Cat cat) {
		return catRepository.save(cat);
	}

	@Override
	public void deleteCat(Integer id) {
		catRepository.deleteById(id);
		
	}

}
