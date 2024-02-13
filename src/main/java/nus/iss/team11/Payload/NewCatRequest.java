package nus.iss.team11.Payload;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nus.iss.team11.model.Comment;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewCatRequest {
	private String catName;
	private  String catBreed;
	private  List<String> labels;
	private float catLocation;
	private List<Comment> comments;
	}
	


