package nus.iss.team11.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SCSUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private boolean isAdmin;
	private boolean isOwner;
	@OneToMany(mappedBy = "scsUser")
	private List<CatSighting> cat_sightings;
	//@OneToMany(mappedBy = "scsUser") To DO:lost cat?
	//private List<AzureImage> cat_owner_photos;
	@OneToOne
	private OwnerVerification ownerVerification;
//TO DO:avatar_format;
}
