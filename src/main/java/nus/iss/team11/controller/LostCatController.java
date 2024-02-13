package nus.iss.team11.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import nus.iss.team11.Payload.LostCatVector;
import nus.iss.team11.azureUtil.AzureContainerUtil;
import nus.iss.team11.controller.service.AzureImageService;
import nus.iss.team11.controller.service.CatService;
import nus.iss.team11.controller.service.CatSightingService;
import nus.iss.team11.controller.service.SCSUserService;
import nus.iss.team11.dataUtil.CSVUtil;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class LostCatController {
	@Autowired
	CatSightingService catSightingService;

	@Autowired
	CatService catService;

	@Autowired
	SCSUserService scsUserService;

	@Autowired
	CSVUtil csvUtil;

	@Autowired
	AzureContainerUtil azureContainerUtil;

	@Autowired
	AzureImageService azureImageService;

	@PostMapping(value = "/api/get_top_cats")
	public ResponseEntity<String> getTop5Cats(@RequestBody LostCatVector lostCatVector) {
		Map<String, List<Float>> vectorMap = lostCatVector.getVectorMap();
		List<String> tempUrls = lostCatVector.getTempImageURLs();
		List<Float> queryVector = vectorMap.get(tempUrls.get(0));
		List<AzureImage> images = azureImageService.findAll();

		JSONObject vectorsDictJson = new JSONObject();

		for (AzureImage image : images) {
			int imageId = image.getCatSighting().getId();
			List<Float> imageVectors = csvUtil.getVectorFromString(image.getVector());

			// For each image, create or get the existing JSONObject for its ID
			JSONObject imageVectorJson = vectorsDictJson.optJSONObject(String.valueOf(imageId));
			if (imageVectorJson == null) {
				imageVectorJson = new JSONObject();
			}
			
			if (imageVectors.size() > 1) {
				// Put the vector list into the JSON object for this image
				imageVectorJson.put(image.getFileName(), new JSONArray(imageVectors));

				// Put the updated JSON object back into the vectors dictionary
				vectorsDictJson.put(String.valueOf(imageId), imageVectorJson);
			} else {
				System.out.println("empty vector detected, please clean DB data.");
				System.out.println(image.getFileName());
			}

		}

		JSONObject json = new JSONObject();
		json.put("query_vector", new JSONArray(queryVector));
		json.put("vectors_dict", vectorsDictJson);
		System.out.println(json.toString());

		HttpClient client = HttpClient.newHttpClient();
		String machineLearningIP = "https://stray-cats-ml-container.azurewebsites.net/gettopsimilarcats/";

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(machineLearningIP))
				.headers("Content-Type", "application/json", "Access-Control-Allow-Origin", "*",
						"Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS",
						"Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token", "origin",
						"https://stray-cats-ml-container.azurewebsites.net")
				.POST(BodyPublishers.ofString(json.toString())).build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Response status code: " + response.statusCode());
//            System.out.println("Response body: " + response.body());
			if (response.statusCode() == 200) {
				return new ResponseEntity<>(response.body().toString(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("ML API error: " + response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Exception: " + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/api/show_top_cats")
	public ResponseEntity<String> showTopCats(@RequestBody Map<String, Object> payload) {

		System.out.println(payload.get("data"));
		JSONArray topCats = new JSONArray();
		Map<String, Object> data = (Map<String, Object>) payload.get("data");
		if (data != null) {
			Map<String, Double> groups = (Map<String, Double>) data.get("top_similar_groups");

			for (String key : groups.keySet()) {
				CatSighting potentialCatSighting = catSightingService.getCatSightingById(Integer.parseInt(key));
				JSONObject sightingJSON = potentialCatSighting.toJSON();

				// Get the probability value for the current CatSighting using its key
				Double probability = groups.get(key);

				// Add the probability to the sightingJSON
				sightingJSON.put("probability", probability);
				topCats.put(sightingJSON);
			}

			return new ResponseEntity<>(topCats.toString(), HttpStatus.OK);

		}

		return null;
	}

}
