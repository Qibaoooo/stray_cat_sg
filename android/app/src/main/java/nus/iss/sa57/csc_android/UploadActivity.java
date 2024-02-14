package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nus.iss.sa57.csc_android.model.Comment;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static String HOST;
    SharedPreferences userInfoPref;
    List<String> tempUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        HOST = getResources().getString(R.string.host_local);
        userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
        ImageView upload_img = findViewById(R.id.upload_img);
        upload_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upload_img) {
            tempUrls = new ArrayList<>();
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "SelectPicture"), PICK_IMAGE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imgUri = data.getClipData().getItemAt(i).getUri();
                    try {
                        uploadToAzure(imgUri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (data.getData() != null) {
                Uri imgUri = data.getData();
                try {
                    uploadToAzure(imgUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void uploadToAzure(Uri uri) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] imgData = getBytesFromUri(uri);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("imageFile", imgData);
                    String filename = String.valueOf(System.currentTimeMillis());
                    String urlString = HOST + "/api/images?filename=" + filename;
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
                    Log.d("post comment", String.valueOf(responseCode));

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = urlConnection.getInputStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            stringBuilder.append(new String(buffer, 0, bytesRead));
                        }
                        String blobUrl = stringBuilder.toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tempUrls.add(blobUrl);
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
}