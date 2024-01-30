package nus.iss.sa57.csc_android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class AzureImage {
    private int id;
    private String CONTAINER_NAME;
    private String STORAGE_ACCOUNT_NAME;
    private CatSighting catSighting;
    private String imageURL;
    private String fileName;
    public String deriveSighting() {
        if (fileName == null) {
            return null;
        }
        String[] arr = fileName.split("_");
        return arr[0] + "_" + arr[1] + "_" + arr[2];
    }

    public AzureImage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCONTAINER_NAME() {
        return CONTAINER_NAME;
    }

    public void setCONTAINER_NAME(String CONTAINER_NAME) {
        this.CONTAINER_NAME = CONTAINER_NAME;
    }

    public String getSTORAGE_ACCOUNT_NAME() {
        return STORAGE_ACCOUNT_NAME;
    }

    public void setSTORAGE_ACCOUNT_NAME(String STORAGE_ACCOUNT_NAME) {
        this.STORAGE_ACCOUNT_NAME = STORAGE_ACCOUNT_NAME;
    }

    public CatSighting getCatSighting() {
        return catSighting;
    }

    public void setCatSighting(CatSighting catSighting) {
        this.catSighting = catSighting;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static AzureImage parseFromJSON(JSONObject js) throws JSONException {
        AzureImage azureImage = new AzureImage();
        azureImage.setId(js.getInt("id"));
        azureImage.setCONTAINER_NAME(js.getString("container_NAME"));
        azureImage.setSTORAGE_ACCOUNT_NAME(js.getString("storage_ACCOUNT_NAME"));
        azureImage.setImageURL(js.getString("imageURL"));
        azureImage.setFileName(js.getString("fileName"));
        return azureImage;
    }
}
