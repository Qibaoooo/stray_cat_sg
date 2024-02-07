package nus.iss.team11.Payload;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewCatSightingRequest {
	private String sightingName;
	private Float locationLat;
	private Float locationLong;
	private long time;
	private String suggestedCatName;
	private String suggestedCatBreed;
	private List<String> tempImageURLs;
	private Map<String, List<Float>> vectorMap;
}
