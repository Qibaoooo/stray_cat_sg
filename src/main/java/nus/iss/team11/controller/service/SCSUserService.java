package nus.iss.team11.controller.service;

import java.util.List;

import nus.iss.team11.model.SCSUser;

public interface SCSUserService {
	List<SCSUser> getAllSCSUsers();
	SCSUser getUserById(int id);
	SCSUser saveSCSUser(SCSUser scsUser);
	void deleteSCSUser(int id);

}
