package nus.iss.team11.model;

import java.time.LocalDate;
import java.util.List;

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
	
	
	private Float catLocation;
	
}
