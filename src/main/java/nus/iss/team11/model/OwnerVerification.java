package nus.iss.team11.model;

import org.json.JSONObject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class OwnerVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private SCSUser user;
	@OneToOne
	private SCSUser verifiedBy;
	
	
	private String ImageURL;
	
	private String status;
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("user", user.getId());
		json.put("status", status);
		if (verifiedBy != null) {			
			json.put("verifiedBy", verifiedBy.getId());
		}
		if (ImageURL != null) {            
	        json.put("imageURL", ImageURL);
	    }
		
		return json;
	}
}
