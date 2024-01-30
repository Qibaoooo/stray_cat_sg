package nus.iss.team11.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LostCat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Float lostLocation;
	private boolean isFound;
	
	/*
	@ManyToOne
	private SCSUser user;
	*/
	private LocalDate lostTime;
	
	//private List<AzureImage> images;
	
	private String ownerName;
	private String notes;
	
}
