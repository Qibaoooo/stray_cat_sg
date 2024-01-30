package nus.iss.sa57.csc_android.model;
import java.time.LocalDate;
import java.util.List;

import nus.iss.sa57.csc_android.model.AzureImage;

public class CatSighting {
    private int id;
    private String sightingName;
    private Float locationLat;
    private Float locationLong;
    private LocalDate time;
    private List<AzureImage> images;
    private String suggestedCatName;
    private String suggestedCatBreed;
    private boolean isApproved;

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

    public List<AzureImage> getImages() {
        return images;
    }

    public void setImages(List<AzureImage> images) {
        this.images = images;
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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
