package nus.iss.team11.model;

import java.time.LocalDate;
import java.util.List;

import org.json.JSONObject;

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
	private LocalDate time;
	@OneToMany(mappedBy = "scsUser")
	private List<CatSighting> cat_sightings;
	@OneToOne
	private LostCat lostCat;
	@OneToOne
	private OwnerVerification ownerVerification;
	@OneToMany(mappedBy = "scsUser")
	private List<Comment> comments;
	
	//TODO: avatar
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("username", username);
		json.put("isAdmin", isAdmin);
		json.put("isOwner", isOwner);
		json.put("time", time);
		return json;
	}
}
