package nus.iss.sa57.csc_android.payload;

import java.util.List;
import java.util.Map;

public class NewCatSightingRequest {
    private String sightingName;
    private Float locationLat;
    private Float locationLong;
    private long time;
    private String suggestedCatName;
    private String suggestedCatBreed;
    private List<String> tempImageURLs;
    private Map<String, List<Float>> vectorMap;
    public NewCatSightingRequest(){}

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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

    public List<String> getTempImageURLs() {
        return tempImageURLs;
    }

    public void setTempImageURLs(List<String> tempImageURLs) {
        this.tempImageURLs = tempImageURLs;
    }

    public Map<String, List<Float>> getVectorMap() {
        return vectorMap;
    }

    public void setVectorMap(Map<String, List<Float>> vectorMap) {
        this.vectorMap = vectorMap;
    }
}
