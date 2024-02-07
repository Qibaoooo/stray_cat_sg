package nus.iss.team11.controller.service;

import java.util.List;
import java.util.Optional;

import nus.iss.team11.model.SCSUser;

public interface SCSUserService {
	List<SCSUser> getAllSCSUsers();
	SCSUser getUserById(int id);
	Optional<SCSUser> getUserByUsername(String username);
	SCSUser saveSCSUser(SCSUser scsUser);
	void deleteSCSUser(int id);
    SCSUser findUserByUsername(String username);
}
