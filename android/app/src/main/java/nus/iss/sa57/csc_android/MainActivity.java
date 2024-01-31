package nus.iss.sa57.csc_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nus.iss.sa57.csc_android.model.AzureImage;
import nus.iss.sa57.csc_android.model.CatSighting;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //change this host to switch to deployed server
    private static String HOST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HOST = getResources().getString(R.string.host_local);
        setupList();
    }

    private void setupList() {
        List<CatSighting> csList = fetchCatSightingList();
        //need an adapter to inflate the listView
        CatSightingAdapter adapter=new CatSightingAdapter(this,csList);
        ListView listView=findViewById(R.id.listView);
        if(listView!=null){
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        //need onClickListener
    }
@Override
public void onItemClick(AdapterView<?>av, View v, int pos, long id){
        //TO DO: TO BE COMPLETED
}
    private List<CatSighting> fetchCatSightingList(){
        List<CatSighting> csList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Here HOST is declared at the top of the java file
                //change that to switch to deployed server
                String urlString = HOST + "/android/test2";
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
                if (responseData != null) {
                    try {
                        //What we get is a ResponseEntity<List<CatSighting>>
                        //in the form of a JSONArray
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            //public static CatSighting parseFromJSON(JSONObject js)
                            //This is a custom method to convert JSONObject to CatSighting
                            //it reads every field in the JSONObject and bind the data
                            //to the CatSighting object
                            CatSighting cs = CatSighting.parseFromJSON(jsonObject);

                            //Record Test Infomation, only for testing
                            String sightingName = jsonObject.getString("sightingName");
                            Log.d("MainActivity", "CatSighting Name: " + sightingName);
                            Log.d("MainActivity", "CatSighting Name: " + cs.getSightingName());
                            Log.d("MainActivity", "CatSighting Time: " + cs.getTime());
                            List<AzureImage> imgs = cs.getImages();
                            for (AzureImage ai : imgs) {
                                Log.d("MainActivity", "CatSighting Img Id: " + ai.getFileName());
                            }

                            csList.add(cs);
                        }
                    } catch (JSONException e) {
                        Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
                    }
                } else {
                    Log.e("MainActivity", "Failed to fetch data from server");
                }
            }
        }).start();
        return csList;
    }
}

//    private void fetchSingleCatSighting() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String urlString = "http://10.0.2.2:8080/android/test";
//                HttpURLConnection urlConnection = null;
//                BufferedReader reader = null;
//                String responseData = null;
//                try {
//                    URL url = new URL(urlString);
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestMethod("GET");
//
//                    int responseCode = urlConnection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                        StringBuilder stringBuilder = new StringBuilder();
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            stringBuilder.append(line).append("\n");
//                        }
//                        responseData = stringBuilder.toString();
//                    } else {
//                        Log.e("MainActivity", "HTTP error code: " + responseCode);
//                    }
//                } catch (IOException e) {
//                    Log.e("MainActivity", "Error fetching data from server: " + e.getMessage());
//                } finally {
//                    if (urlConnection != null) {
//                        urlConnection.disconnect();
//                    }
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            Log.e("MainActivity", "Error closing reader: " + e.getMessage());
//                        }
//                    }
//                }
//                if (responseData != null) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(responseData);
//                        String sightingName = jsonObject.getString("sightingName");
//                        Log.d("MainActivity", "CatSighting Name: " + sightingName);
//                        CatSighting cs = CatSighting.parseFromJSON(jsonObject);
//                        Log.d("MainActivity", "CatSighting Name: " + cs.getSightingName());
//                        Log.d("MainActivity", "CatSighting Time: " + cs.getTime());
//                        List<AzureImage> imgs = cs.getImages();
//                        for (AzureImage ai : imgs){
//                            Log.d("MainActivity", "CatSighting Img Id: " + ai.getFileName());
//                        }
//                    } catch (JSONException e) {
//                        Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
//                    }
//                } else {
//                    Log.e("MainActivity", "Failed to fetch data from server");
//                }
//            }
//        }).start();
//    }

