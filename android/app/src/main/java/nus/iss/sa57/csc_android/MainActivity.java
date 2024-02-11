package nus.iss.sa57.csc_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import nus.iss.sa57.csc_android.model.CatSighting;
import nus.iss.sa57.csc_android.utils.CatSightingAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    //change this host to switch to deployed server
    private static String HOST;
    List<CatSighting> csList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HOST = getResources().getString(R.string.host_local);
        setupButtons();
        fetchCatSightingList();
    }

    private void setupButtons() {
        ImageButton upload_btn = findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upload_btn) {
            //goto upload activity
        }
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("catId",csList.get(pos).getCatId());
        Log.d("MainActivity", "Calling Details Activity");
        startActivity(intent);
    }

    private void fetchCatSightingList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Here HOST is declared at the top of the java file
                //change that to switch to deployed server
                String urlString = HOST + "/api/cat_sightings";
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
                        Log.e("MainActivity", "HTTP error code: " + responseCode);
                    }
                } catch (IOException e) {
                    Log.e("MainActivity", "Error fetching data from server: " + e.getMessage());
                } finally {
                    //Close and release used resources
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            Log.e("MainActivity", "Error closing reader: " + e.getMessage());
                        }
                    }
                }

                //Deal with response
                List<CatSighting> responseList = new ArrayList<>();
                if (responseData != null) {
                    try {
                        Type listType = new TypeToken<List<CatSighting>>(){}.getType();
                        Gson gson = new Gson();
                        responseList = gson.fromJson(responseData,listType);

                        if(!responseList.isEmpty()){
                            csList = responseList;
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
                    }
                } else {
                    Log.e("MainActivity", "Failed to fetch data from server");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        downloadImgFiles();
                    }
                });
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
        for (CatSighting cs : csList){
            sum += cs.getImagesURLs().size();
        }
        final CountDownLatch latch = new CountDownLatch(sum);
        //for (int i = 0; i < csList.size(); i++) {
        //    CatSighting cs = csList.get(i);
        for (CatSighting cs : csList) {
            for (int i = 0; i < cs.getImagesURLs().size(); i++) {

            File destFile = new File(externalFilesDir, ("img-" + String.valueOf(cs.getId()) + "-" + String.valueOf(i)));
            //File destFile = new File(externalFilesDir, ("img-" + String.valueOf(i)));
            Thread t = new Thread(new ImageDownloadThread(cs, destFile, latch));
            t.start();
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setupList();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setupList() {
        CatSightingAdapter adapter = new CatSightingAdapter(this, csList);
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            Log.e("MainActivity", "ListView not null");
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }



}

class ImageDownloadThread implements Runnable {
    private CatSighting cs;
    private File destFile;
    private CountDownLatch latch;

    public ImageDownloadThread(CatSighting cs, File destFile, CountDownLatch latch) {
        this.cs = cs;
        this.destFile = destFile;
        this.latch = latch;
    }

    @Override
    public void run() {
        String urlString = cs.getImagesURLs().get(0);
        URLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = url.openConnection();

            InputStream in = urlConnection.getInputStream();
            FileOutputStream out = new FileOutputStream(destFile);

            byte[] buf = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
            out.close();
            in.close();
            latch.countDown();
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to download image");
            latch.countDown();
        }
    }
}
