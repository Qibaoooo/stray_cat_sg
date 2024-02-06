package nus.iss.team11.Payload;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.Comment;
import nus.iss.team11.model.LostCat;
import nus.iss.team11.model.OwnerVerification;
import nus.iss.team11.model.SCSUser;

public class NewSCSUserRequest {
	private String username;
	private String password;
	private OwnerVerification ownerVerification;
	
	public NewSCSUserRequest (String username, String password, 
			OwnerVerification OwnerVerification) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.setOwnerVerification(OwnerVerification);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public OwnerVerification getOwnerVerification() {
		return ownerVerification;
	}
	public void setOwnerVerification(OwnerVerification ownerVerification) {
		this.ownerVerification = ownerVerification;
	}
	

}
