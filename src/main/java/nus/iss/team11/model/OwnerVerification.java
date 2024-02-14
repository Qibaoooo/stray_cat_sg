package nus.iss.team11.model;

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
	
	@OneToOne(mappedBy="ownerVerification")
	private SCSUser userId;
	
	@OneToOne(mappedBy = "ownerVerification")
	private AzureImage azureImage;
	
	private String status;
	
	@OneToOne(mappedBy="ownerVerification")
	private SCSUser verifiedBy;
}
