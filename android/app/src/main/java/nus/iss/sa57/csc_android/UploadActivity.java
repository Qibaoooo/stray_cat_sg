package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nus.iss.sa57.csc_android.databinding.ActivityMapBinding;
import nus.iss.sa57.csc_android.databinding.ActivityUploadBinding;
import nus.iss.sa57.csc_android.payload.CatSightingResponse;
import nus.iss.sa57.csc_android.utils.HttpHelper;
import nus.iss.sa57.csc_android.utils.NavigationBarHandler;

public class UploadActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    private GoogleMap mMap;
    private ActivityUploadBinding binding;
    //private LocationManager locationManager;
    //private LocationListener locationListener;
    private LatLng center;
    private static String HOST;
    private static String ML_HOST;
    private SharedPreferences userInfoPref;
    private List<String> tempUrls;
    private Map<String, List<Float>> tempVectors;
    private EditText nameView;
    private EditText breedView;
    private ImageView upload_img;
    private int catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HOST = HttpHelper.getLocalHost(this);
        ML_HOST = HttpHelper.getMLHost(this);

        userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
        checkLoginStatus();

        NavigationBarHandler nav_bar = new NavigationBarHandler(this);
        nav_bar.setupBar();

        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        nameView = findViewById(R.id.upload_name);
        breedView = findViewById(R.id.upload_breed);

        Button submit_btn = findViewById(R.id.upload_submit);
        submit_btn.setOnClickListener(this);

        upload_img = findViewById(R.id.upload_img);
        upload_img.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        locationListener = location -> {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            center = new LatLng(latitude, longitude);
//        };
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("UploadActivity", "OnMapReady");
        mMap = googleMap;

        center = new LatLng(1.3, 103.85);
        float zoomLevel = 12.0f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(center, zoomLevel);

        mMap.moveCamera(cameraUpdate);

        Marker centerMarker = mMap.addMarker(new MarkerOptions().position(center));

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                center = mMap.getCameraPosition().target;
                centerMarker.setPosition(center);
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        }
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upload_submit) {
            try {
                submit();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if (v.getId() == R.id.upload_img) {
            tempUrls = new ArrayList<>();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGE_REQUEST);
            //add latch to remove submit onclick when uploading
            Log.d("Img Select", "selecting");
        }
    }

    private void submit() throws JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String name = String.valueOf(nameView.getText());
                    if (name == null) {
                        name = "default name";
                    }
                    String breed = String.valueOf(breedView.getText());
                    JSONObject newCatSightingRequest = new JSONObject();
                    newCatSightingRequest.put("sightingName", name);
                    newCatSightingRequest.put("locationLat", center.latitude);
                    newCatSightingRequest.put("locationLong", center.longitude);
                    newCatSightingRequest.put("time", System.currentTimeMillis());
                    newCatSightingRequest.put("suggestedCatName", name);
                    newCatSightingRequest.put("suggestedCatBreed", breed);
                    JsonArray urlArray = new JsonArray();
                    for (String u : tempUrls) {
                        urlArray.add(u);
                    }
                    newCatSightingRequest.put("tempImageURLs", urlArray.toString());

                    JsonArray vectorArray = new JsonArray();
                    if (!(tempVectors == null)) {
                        for (Map.Entry<String, List<Float>> entry : tempVectors.entrySet()) {
                            String key = entry.getKey();
                            List<Float> value = entry.getValue();
                            JsonArray valArray = new JsonArray();
                            for (Float u : value) {
                                valArray.add(u);
                            }
                            JsonObject vectorPair = new JsonObject();
                            vectorPair.addProperty(key, String.valueOf(valArray));

                            vectorArray.add(vectorPair);
                        }
                    }
                    newCatSightingRequest.put("vectorMap", vectorArray.toString());
                    String data = newCatSightingRequest.toString();
                    String username = userInfoPref.getString("username", null);
                    String urlString = HOST + "/api/cat_sightings";
                    HttpURLConnection urlConnection = null;
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    String jwtToken = userInfoPref.getString("jwt", null);
                    urlConnection.setRequestProperty("Authorization", "Bearer " + jwtToken);
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                    outputStream.writeBytes(data);
                    outputStream.flush();
                    outputStream.close();
                    int responseCode = urlConnection.getResponseCode();
                    Log.d("post comment", String.valueOf(responseCode));

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(urlConnection.getInputStream()));
                        Gson gson = new Gson();
                        CatSightingResponse cr = gson.fromJson(in, CatSightingResponse.class);
                        catId = cr.getCat();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewDetail();
                            }
                        });
                    } else {
                        // show error?
                    }
                } catch (IOException e) {
                    Log.e("LoginActivity", "Error fetching data from server: " + e.getMessage());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    private void viewDetail() {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("catId", catId);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("OnActivityResult", "start");

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                List<Uri> imgUris = new ArrayList<>();
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imgUris.add(imageUri);
                }
                upload_img.setImageURI(imgUris.get(0));
                for (Uri uri : imgUris) {
                    try {
                        uploadToAzure(uri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                upload_img.setImageURI(imageUri);
                try {
                    uploadToAzure(imageUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void uploadToAzure(Uri uri) throws IOException {
        new Thread(() -> {
            try {
                byte[] imgData = getBytesFromUri(uri);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("imageFile", imgData);
                String filename = System.currentTimeMillis() + ".jpg";
                String urlString = HOST + "/api/images?fileName=" + filename;
                HttpURLConnection urlConnection = null;
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                String jwtToken = userInfoPref.getString("jwt", null);
                urlConnection.setRequestProperty("Authorization", "Bearer " + jwtToken);
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                outputStream.writeBytes(jsonObject.toString());
                outputStream.flush();
                outputStream.close();
                int responseCode = urlConnection.getResponseCode();
                Log.d("upload to Azure", String.valueOf(responseCode));

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder stringBuilder = new StringBuilder();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        stringBuilder.append(new String(buffer, 0, bytesRead));
                    }
                    String blobUrl = stringBuilder.toString();
                    tempUrls.add(blobUrl);
                    Log.d("upload to Azure", "Calling ML");
                    setVectors(blobUrl);
                } else {
                    // show error?
                }
            } catch (IOException e) {
                Log.e("LoginActivity", "Error fetching data from server: " + e.getMessage());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    private byte[] getBytesFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void setVectors(String blobUrl) {
        new Thread(() -> {
            Log.d("setVector", blobUrl);
            String url = ML_HOST + "/getembedding/?filename=" + blobUrl;
            String responseData = HttpHelper.getResponse(url);

            List<Float> vector = new ArrayList<>();
            if (responseData != null) {
                try {
                    Type listType = new TypeToken<List<Float>>() {
                    }.getType();
                    Gson gson = new Gson();
                    vector = gson.fromJson(responseData, listType);
                    tempVectors.put(blobUrl, vector);
                    Log.d("setVector", blobUrl);
                } catch (JsonSyntaxException e) {
                    Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
                }
            } else {
                Log.e("MainActivity", "Failed to fetch data from server");
            }
        }).start();
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