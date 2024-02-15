package nus.iss.team11.Payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class NewVerificationRequest {
	private String ImageURL;
	private int userId;
	
}
