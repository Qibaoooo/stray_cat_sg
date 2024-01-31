package nus.iss.team11.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CatSighting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	@ManyToOne
	private SCSUser scsUser;
	
	@ManyToOne
	private Cat cat;
	
	private String sightingName;
	private Float locationLat;
	private Float locationLong;
	
	private LocalDate time;
	
	@OneToMany(mappedBy = "catSighting")
	private List<AzureImage> images;
	
	private String suggestedCatName;
	private String suggestedCatBreed;
	
	private boolean isApproved;
}
