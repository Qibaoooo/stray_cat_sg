package nus.iss.team11.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
public class AzureImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String imageURL;
	private String fileName;
	
	@Lob
	@Column(length = 100000)
	private String vector;
	
	@ManyToOne
	@JoinColumn(name="catSighting")
	private CatSighting catSighting;
	
	@OneToOne
	@JoinColumn(name="ownerVerification")
	private OwnerVerification ownerVerification;
	
	public String deriveSighting() {
		if (fileName == null) {
			return null;
		}
		String[] arr = fileName.split("_photo_");
		return arr[0];
	}
}
