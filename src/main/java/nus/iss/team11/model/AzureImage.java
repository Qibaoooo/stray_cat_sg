package nus.iss.team11.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import nus.iss.team11.dataUtil.CSVUtil;

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
	
	
	public String deriveSighting() {
		if (fileName == null) {
			return null;
		}
		String[] arr = fileName.split("_photo_");
		return arr[0];
	}
	
}
