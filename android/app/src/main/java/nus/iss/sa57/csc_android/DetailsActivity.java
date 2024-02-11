package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import nus.iss.sa57.csc_android.model.Cat;

public class DetailsActivity extends AppCompatActivity {
    private int catId;
    private Cat cat = new Cat();
    private static String HOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        HOST = getResources().getString(R.string.host_local);
        Intent intent = getIntent();
        catId = intent.getIntExtra("catId",0);
        fetchCat(catId);
    }

    private void fetchCat(int catId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = HOST + "/api/cat/" + String.valueOf(catId);
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String responseData = null;
                try {
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        //read response data line by line
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        responseData = stringBuilder.toString();
                    } else {
                        Log.e("DetailsActivity", "HTTP error code: " + responseCode);
                    }
                } catch (IOException e) {
                    Log.e("DetailsActivity", "Error fetching data from server: " + e.getMessage());
                } finally {
                    //Close and release used resources
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            Log.e("DetailsActivity", "Error closing reader: " + e.getMessage());
                        }
                    }
                }
                Cat responseCat = new Cat();
                if (responseData != null) {
                    try {
//                        JSONObject jsonObject = new JSONObject(responseData);
//                        cat = Cat.parseFromJSON(jsonObject);
                        Gson gson = new Gson();
                        responseCat = gson.fromJson(responseData, Cat.class);
                        Log.d("DetailsAcvivity",responseCat.getCatName());
                        if(responseCat != null){
                            cat = responseCat;
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("DetailsActivity", "Error parsing JSON: " + e.getMessage());
                    }
                } else {
                    Log.e("DetailsActivity", "Failed to fetch data from server");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() { setupLayout(); }
                });
            }
        }).start();
    }

    public void setupLayout(){
        TextView name = findViewById(R.id.detail_name);
        name.setText(cat.getCatName());
        TextView idView = findViewById(R.id.detail_id);
        idView.setText(String.valueOf(cat.getId()));
        TextView breed = findViewById(R.id.detail_breed);
        breed.setText(cat.getBreed());
        TextView labels = findViewById(R.id.detail_labels);
        if(cat.getLabels() != null) {
            labels.setText(String.join(" ", cat.getLabels()));
        } else{
            labels.setText("No Labels Yet!");
        }
        ImageView catphoto = findViewById(R.id.detail_photo);
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File destFile = new File(externalFilesDir, ("img-" + cat.getCatSightings().get(0).getId() + "-0"));
        File destFile = new File(externalFilesDir, ("img-" + "35" + "-0"));
        Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
        catphoto.setImageBitmap(bitmap);
    }
}