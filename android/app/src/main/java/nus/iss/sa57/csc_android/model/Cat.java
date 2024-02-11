package nus.iss.sa57.csc_android.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cat {
    public Cat() {
    }

    private int id;
    private String catName;
    private String breed;
    private boolean isApproved;
    private List<String> labels;
    private List<CatSighting> catSightings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<CatSighting> getCatSightings() {
        return catSightings;
    }

    public void setCatSightings(List<CatSighting> catSightings) {
        this.catSightings = catSightings;
    }

//    public static Cat parseFromJSON(JSONObject js) throws JSONException {
//        Cat cat = new Cat();
//        cat.setId(js.getInt("id"));
//        cat.setCatName(js.getString("catName"));
//        cat.setBreed(js.getString("catBreed"));
//        cat.setApproved(js.getBoolean("isApproved"));
//
//        JSONArray labelsArray = js.getJSONArray("labels");
//        if (labelsArray.length() > 0) {
//            List<String> labelsList = new ArrayList<>();
//            for (int i = 0; i < labelsArray.length(); i++) {
//                String label = labelsArray.getString(i);
//                labelsList.add(label);
//            }
//            cat.setLabels(labelsList);
//        } else{
//            cat.setLabels(new ArrayList<>());
//        }
//
//        JSONArray sightingsArray = js.getJSONArray("catSightings");
//        List<CatSighting> sightingsList = new ArrayList<>();
//        for (int i = 0; i < sightingsArray.length(); i++) {
//            JSONObject sightingObject = sightingsArray.getJSONObject(i);
//            //CatSighting cs = CatSighting.parseFromJSON(sightingObject);
//            //sightingsList.add(cs);
//        }
//        cat.setCatSightings(sightingsList);
//
//        return cat;
//    }
}
