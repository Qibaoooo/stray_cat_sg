package nus.iss.team11.dataUtil;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtil {
	public static HashMap<String, String> readCSVIntoHashMap(String csvFile) throws IOException, CsvException {
		
		FileReader filereader = new FileReader(csvFile);
		CSVReader csvReader = new CSVReader(filereader);
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
	
	public static List<Float> getVectorForm(String vectorString){
		List<Float> floats = new ArrayList<>();
		String cleanedVectorString = vectorString.replace("[", "").replace("]", "");
		List<String> vStrings = Arrays.asList(cleanedVectorString.split(","));
		for (String vStr: vStrings) {
    		try {
				Float vFloat = Float.parseFloat(vStr);
				floats.add(vFloat);
			} catch (Exception e) {
				// skip invalid float number
			}
    	}
		return floats;
	}
}
