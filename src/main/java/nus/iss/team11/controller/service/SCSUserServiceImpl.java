package nus.iss.team11.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.team11.model.SCSUser;
import nus.iss.team11.repository.SCSUserRepository;

@Service
public class SCSUserServiceImpl implements SCSUserService{
	
	@Autowired
	SCSUserRepository scsUserRepository;
	
	@Override 
	public List<SCSUser> getAllSCSUsers(){
		return scsUserRepository.findAll();
	}
	
	@Override
	public SCSUser getUserById(int id) {
		return scsUserRepository.getReferenceById(id);
	}
	
	@Override
	public SCSUser saveSCSUser(SCSUser scsUser) {
		return scsUserRepository.saveAndFlush(scsUser);
	}
	
	@Override
	public void deleteSCSUser (int id) {
	scsUserRepository.deleteById(id);
	}

}
