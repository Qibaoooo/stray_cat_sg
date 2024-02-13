package nus.iss.team11.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import nus.iss.team11.model.Cat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;
	
	@ManyToOne
	private Cat cat;
	
	private Date time;
	
//	private boolean isHighLight
	
	@ManyToOne
	private SCSUser scsUser;
	
	private List<String> newlabels;
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("time", time);
		json.put("content", content);
		json.put("newlabels", newlabels);
		json.put("scsUser", scsUser.toJSON());
		return json;
	}
}
