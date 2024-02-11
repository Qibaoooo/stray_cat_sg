package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nus.iss.sa57.csc_android.model.Cat;
import nus.iss.sa57.csc_android.model.CatSighting;
import nus.iss.sa57.csc_android.model.Comment;
import nus.iss.sa57.csc_android.utils.CommentAdapter;
import nus.iss.sa57.csc_android.utils.HttpHelper;
import nus.iss.sa57.csc_android.utils.NavigationBarHandler;

public class DetailsActivity extends AppCompatActivity {
    private int catId;
    private Cat cat = new Cat();
    private List<Comment> comments = new ArrayList<>();
    private static String HOST;
    private ListView commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        HOST = getResources().getString(R.string.host_local);

        View nav_bar = findViewById(R.id.nav_bar);
        NavigationBarHandler nav_handler = new NavigationBarHandler(nav_bar,this);
        nav_handler.setupBar();

        Intent intent = getIntent();
        catId = intent.getIntExtra("catId",0);
        fetchCat(catId);

        commentList = findViewById(R.id.detail_commentlist);
    }

    private void fetchCat(int catId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = HOST + "/api/cat/" + String.valueOf(catId);
                String responseData = HttpHelper.getResponse(urlString);
                Cat responseCat = new Cat();
                if (responseData != null) {
                    try {
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

                urlString = HOST + "/api/comments/" + String.valueOf(catId);
                responseData = HttpHelper.getResponse(urlString);
                List<Comment> responseComments = new ArrayList<>();
                if(responseData != null){
                    try {
                        Type listType = new TypeToken<List<Comment>>(){}.getType();
                        Gson gson = new Gson();
                        responseComments = gson.fromJson(responseData, listType);
                        if(responseComments != null){
                            comments = responseComments;
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

    private void setupLayout(){
        TextView name = findViewById(R.id.detail_name);
        name.setText(cat.getCatName());
        TextView idView = findViewById(R.id.detail_id);
        idView.setText(String.valueOf(cat.getId()));
        TextView breed = findViewById(R.id.detail_breed);
        breed.setText(cat.getCatBreed());
        TextView labels = findViewById(R.id.detail_labels);
        if(cat.getLabels() != null) {
            labels.setText(String.join(" ", cat.getLabels()));
        } else{
            labels.setText("No Labels Yet!");
        }
        ImageView catphoto = findViewById(R.id.detail_photo);
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File destFile = new File(externalFilesDir, ("img-" + cat.getCatSightings().get(0).getId() + "-0"));
        Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
        catphoto.setImageBitmap(bitmap);

        CommentAdapter adapter = new CommentAdapter(this,comments);
        commentList = findViewById(R.id.detail_commentlist);
        commentList.setAdapter(adapter);
    }

}