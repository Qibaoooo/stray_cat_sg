package nus.iss.sa57.csc_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import nus.iss.sa57.csc_android.model.CatSighting;
import nus.iss.sa57.csc_android.utils.CatSightingAdapter;
import nus.iss.sa57.csc_android.utils.HttpHelper;
import nus.iss.sa57.csc_android.utils.ImageDownloadThread;
import nus.iss.sa57.csc_android.utils.MessageHelper;
import nus.iss.sa57.csc_android.utils.NavigationBarHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static String HOST;
    private List<CatSighting> csList = new ArrayList<>();
    private SharedPreferences listPref;
    private ProgressBar progressBar;
    private TextView progressText;
    private RelativeLayout progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HOST = HttpHelper.getLocalHost(this);
        checkLoginStatus();
        setupButtons();

        listPref = getSharedPreferences("list_info", MODE_PRIVATE);
        if (listPref.getBoolean("isFetched", false)) {
            String responseData = listPref.getString("listData", null);
            try {
                Type listType = new TypeToken<List<CatSighting>>() {
                }.getType();
                Gson gson = new Gson();
                csList = gson.fromJson(responseData, listType);
            } catch (JsonSyntaxException e) {
                MessageHelper.showErrMessage(this);
            }
            setupList();
        } else {
            progressBar = findViewById(R.id.progressBar);
            progressBar.setProgress(0);
            progressText = findViewById(R.id.progressText);
            progressView = findViewById(R.id.progressView);
            fetchCatSightingList();
        }
    }

    private void setupButtons() {
        ImageButton upload_btn = findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(this);
        ImageButton map_btn = findViewById(R.id.map_btn);
        map_btn.setOnClickListener(this);
        new NavigationBarHandler(this) {
        }.setupAccount();//don't want to setup cat
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upload_btn) {
            Intent intent = new Intent(this, UploadActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.map_btn) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("catId", csList.get(pos).getCat());
        startActivity(intent);
    }

    private void fetchCatSightingList() {
        progressText.setText("Fetching Cat Sighting List...");
        progressBar.setProgress(0);
        new Thread(() -> {
            //pending=false?
            String urlString = HOST + "/api/cat_sightings?pending=false";
            String responseData = HttpHelper.getResponse(urlString);

            //Deal with response
            List<CatSighting> responseList = new ArrayList<>();
            if (responseData != null) {
                try {
                    Type listType = new TypeToken<List<CatSighting>>() {
                    }.getType();
                    Gson gson = new Gson();
                    responseList = gson.fromJson(responseData, listType);
                    if (!responseList.isEmpty()) {
                        csList = responseList;
                    }
                    listPref.edit().putString("listData", responseData)
                            .putBoolean("isFetched", true).commit();
                    runOnUiThread(() -> {
                        progressBar.setProgress(100);
                        downloadImgFiles();
                    });
                } catch (JsonSyntaxException e) {
                    Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
                    runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
                }
            } else {
                Log.e("MainActivity", "Failed to fetch data from server");
                runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
            }
        }).start();
    }

    public void downloadImgFiles() {
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = externalFilesDir.listFiles();
        if (files != null) {
            for (File f : files
            ) {
                f.delete();
            }
        }
        int sum = 0;
        for (CatSighting cs : csList) {
            sum += cs.getImagesURLs().size();
        }
        final CountDownLatch latch = new CountDownLatch(sum);
        progressText.setText("Downloading Images...");
        progressBar.setProgress(0);
        for (CatSighting cs : csList) {
            for (int i = 0; i < cs.getImagesURLs().size(); i++) {

                File destFile = new File(externalFilesDir, ("img-" + cs.getId() + "-" + i));
                new Thread(new ImageDownloadThread(cs, destFile, latch,
                        progressBar, progressText, sum)).start();
            }
        }
        new Thread(() -> {
            try {
                latch.await();
                runOnUiThread(() -> {
                    progressView.setVisibility(View.GONE);
                    setupList();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
            }
        }).start();
    }

    private void setupList() {
        CatSightingAdapter adapter = new CatSightingAdapter(this, csList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void checkLoginStatus() {
        SharedPreferences userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
        if (userInfoPref.getString("username", null) == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("notLoggedin", true);
            finish();
            startActivity(intent);
        }
    }

}

