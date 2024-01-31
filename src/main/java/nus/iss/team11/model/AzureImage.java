package nus.iss.team11.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class AzureImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="catSighting")
	private CatSighting catSighting;

	private String imageURL;

	private String fileName;

	
	public String deriveSighting() {
		if (fileName == null) {
			return null;
		}
		String[] arr = fileName.split("_");
		return arr[0] + "_" + arr[1] + "_" + arr[2];
	}
}
