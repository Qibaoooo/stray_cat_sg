package nus.iss.team11.dataUtil;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtil {
	public static HashMap<String, List<Float>> readCSVIntoHashMap(String csvFile) throws IOException, CsvException {
		
		FileReader filereader = new FileReader(csvFile);
		CSVReader csvReader = new CSVReader(filereader);
		List<String[]> data = csvReader.readAll();
		System.out.println(data.get(0).toString());
		
		HashMap<String, List<Float>> vMap = new HashMap<>();
        for (String[] row : data) {
        	String sightingName = row[0];
        	List<Float> floats = new ArrayList<>();
        	vMap.put(sightingName, floats);
        	
        	// skip headers
        	if (sightingName.equals("fileName")) {
        		continue;
        	}
        	
        	List<String> vStrings = Arrays.asList(row[1].split("\\s+"));
        	for (String vStr: vStrings) {
        		try {
					Float vFloat = Float.parseFloat(vStr);
					floats.add(vFloat);
				} catch (Exception e) {
					// skip invalid float number
				}
        	}
        	System.out.println(vStrings.size());
        	System.out.println(vStrings.get(5));
        } 
        
        csvReader.close();
		return vMap;
		
	}
}
