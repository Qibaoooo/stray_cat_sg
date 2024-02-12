package nus.iss.team11.Payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewComment {
	private String content;
	private List<String>labels;
	private int cat_id;
	private String username;
}
