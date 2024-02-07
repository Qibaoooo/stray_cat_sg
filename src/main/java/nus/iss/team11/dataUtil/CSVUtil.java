package nus.iss.team11.dataUtil;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;

@Component
public class CSVUtil {
	public HashMap<String, String> readCSVIntoHashMap(String csvFile) throws IOException, CsvException {

		FileReader filereader = new FileReader(csvFile);
		CSVReader csvReader = new CSVReader(filereader);
		return readCSV(csvReader);

	}

	public HashMap<String, String> readCSVIntoHashMap(InputStream inputStream) throws IOException, CsvException {

		CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
		return readCSV(csvReader);

	}

	private HashMap<String, String> readCSV(CSVReader csvReader) throws IOException, CsvException {
		List<String[]> data = csvReader.readAll();
		System.out.println(data.get(0).toString());

		HashMap<String, String> vMap = new HashMap<>();
		for (String[] row : data) {
			String sightingName = row[0];
			String floats = row[1];

			// skip headers
			if (sightingName.equals("fileName")) {
				continue;
			}

			vMap.put(sightingName, floats);
		}

		csvReader.close();
		return vMap;
	}

	public List<Float> getVectorFromString(String vectorString) {
		List<Float> floats = new ArrayList<>();
		if (vectorString == null) {
			return floats;
		}
		String cleanedVectorString = vectorString.replace("[", "").replace("]", "");
		List<String> vStrings = Arrays.asList(cleanedVectorString.split(","));
		for (String vStr : vStrings) {
			try {
				Float vFloat = Float.parseFloat(vStr);
				floats.add(vFloat);
			} catch (Exception e) {
				// skip invalid float number
			}
		}
		return floats;
	}

	public String getStringFromVector(List<Float> vectorlist) {
		String result = "[";
		for (Float v : vectorlist) {
			result = result + v.toString() + ", ";
		}
		result = result.substring(0, result.length() - 2) + "]";
		return result;
	}

	public JSONObject appendVectorMapToSightingJSON(CatSighting sighting, JSONObject sightingJSON) {
		JSONObject vectorMap = new JSONObject();
		sighting.getImages().forEach(ai->{
			vectorMap.put(ai.getImageURL(), getVectorFromString(ai.getVector()));
		});
		sightingJSON.put("vectorMap", vectorMap);
		return sightingJSON;
	}

}
