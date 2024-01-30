package nus.iss.team11.model;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	@JoinColumn(name="catid")
	private Cat cat;
	private Date time;
	
	@ManyToOne
	@JoinColumn(name="SCSUserid")
	private SCSUser scsuser;
	
	private List<String> newlabels;
	

}
