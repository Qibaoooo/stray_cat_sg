package nus.iss.team11.Payload;

import java.time.LocalDate;
import java.util.List;

import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.SCSUser;

public class NewCatSightingRequest {
	private String sightingName;
	private Float locationLat;
	private Float locationLong;
	private LocalDate time;
	private String suggestedCatName;
	private String suggestedCatBreed;
	
	public NewCatSightingRequest (String sightingName, Float locationLat, 
			Float locationLong, LocalDate time, String suggestedCatName, 
			String suggestedCatBreed) {
		super();
		this.setSightingName(sightingName);
		this.setLocationLat(locationLat);
		this.setLocationLong(locationLong);
		this.setTime(time);
		this.setSuggestedCatName(suggestedCatName);
		this.setSuggestedCatBreed(suggestedCatBreed);
	}
	public String getSightingName() {
		return sightingName;
	}
	public void setSightingName(String sightingName) {
		this.sightingName = sightingName;
	}
	public Float getLocationLat() {
		return locationLat;
	}
	public void setLocationLat(Float locationLat) {
		this.locationLat = locationLat;
	}
	public Float getLocationLong() {
		return locationLong;
	}
	public void setLocationLong(Float locationLong) {
		this.locationLong = locationLong;
	}
	public LocalDate getTime() {
		return time;
	}
	public void setTime(LocalDate time) {
		this.time = time;
	}
	public String getSuggestedCatName() {
		return suggestedCatName;
	}
	public void setSuggestedCatName(String suggestedCatName) {
		this.suggestedCatName = suggestedCatName;
	}
	public String getSuggestedCatBreed() {
		return suggestedCatBreed;
	}
	public void setSuggestedCatBreed(String suggestedCatBreed) {
		this.suggestedCatBreed = suggestedCatBreed;
	}
	

}
