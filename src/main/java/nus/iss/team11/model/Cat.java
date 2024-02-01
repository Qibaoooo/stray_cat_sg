package nus.iss.team11.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "cat")
	private List<CatSighting> catSightings;

	private String catName;
	private String catBreed;

	private List<String> labels;

	@OneToMany(mappedBy = "cat")
	private List<Comment> comments;

	// TODO: remove this field if it is not used
	private Float catLocation;

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();

		json.put("id", id);
		json.put("catName", catName);
		json.put("catBreed", catBreed);
		json.put("labels", labels);
		json.put("catSightings", catSightings
				.stream()
				.map(CatSighting::toJSON)
				.collect(Collectors.toList()));

		return json;
	}
}
