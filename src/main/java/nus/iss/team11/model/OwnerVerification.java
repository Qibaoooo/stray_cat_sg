package nus.iss.team11.model;

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
public class OwnerVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(mappedBy="ownerVerification")
	private SCSUser applicant;
	
	@OneToMany(mappedBy = "ownerVerification")
	private List<AzureImage> azureImages;
	
	private String status;
	
	@OneToOne(mappedBy="ownerVerification")
	private SCSUser verifiedBy;
}
