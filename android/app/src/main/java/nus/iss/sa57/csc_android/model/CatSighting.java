package nus.iss.sa57.csc_android.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nus.iss.sa57.csc_android.model.AzureImage;

public class CatSighting {
    private int id;
    private String sightingName;
    private Float locationLat;
    private Float locationLong;
    private LocalDate time;
    //private List<AzureImage> images;
    private List<String> imagesURLs;
    private String suggestedCatName;
    private String suggestedCatBreed;

    public CatSighting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

//    public List<AzureImage> getImages() {
//        return images;
//    }
//
//    public void setImages(List<AzureImage> images) {
//        this.images = images;
//    }
    public List<String> getImagesURLs() {
        return imagesURLs;
    }

    public void setImagesURLs(List<String> imagesURLs) {
        this.imagesURLs = imagesURLs;
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

    public static CatSighting parseFromJSON(JSONObject js) throws JSONException {
        CatSighting catSighting = new CatSighting();
        catSighting.setId(js.getInt("id"));
        catSighting.setSightingName(js.getString("sightingName"));
        catSighting.setLocationLat((float) js.getDouble("locationLat"));
        catSighting.setLocationLong((float) js.getDouble("locationLong"));
        String timeString = js.getString("time");
        LocalDate date = LocalDate.parse(timeString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        catSighting.setTime(date);

        JSONArray imagesURLsArray = js.getJSONArray("imagesURLs");
        List<String> imagesURLsList = new ArrayList<>();
        for (int i = 0; i < imagesURLsArray.length(); i++) {
            String url = imagesURLsArray.getString(i);
            imagesURLsList.add(url);
        }
        catSighting.setImagesURLs(imagesURLsList);

        catSighting.setSuggestedCatName(js.getString("suggestedCatName"));
        catSighting.setSuggestedCatBreed(js.getString("suggestedCatBreed"));

        return catSighting;
    }
}
