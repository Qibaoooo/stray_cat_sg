package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
import nus.iss.sa57.csc_android.payload.LoginResponse;
import nus.iss.sa57.csc_android.utils.BitmapHelper;
import nus.iss.sa57.csc_android.utils.CommentAdapter;
import nus.iss.sa57.csc_android.utils.HttpHelper;
import nus.iss.sa57.csc_android.utils.MessageHelper;
import nus.iss.sa57.csc_android.utils.NavigationBarHandler;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private int catId;
    private Cat cat = new Cat();
    private List<Comment> comments = new ArrayList<>();
    private static String HOST;
    private SharedPreferences userInfoPref;
    private List<String> labels;
    boolean[] checkedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        HOST = HttpHelper.getLocalHost(this);

        userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
        checkLoginStatus();

        new NavigationBarHandler(this) {
        }.setupBar();

        ImageButton send_btn = findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);

        ImageView addLabelsButton = findViewById(R.id.add_labels);
        addLabelsButton.setOnClickListener(this);

        checkedItems = new boolean[getResources().getStringArray(R.array.labels_array).length];

        Intent intent = getIntent();
        catId = intent.getIntExtra("catId", 0);
        fetchCat(catId);
    }

    private void fetchCat(int catId) {
        new Thread(() -> {
            String urlString = HOST + "/api/cat/" + catId;
            String responseData = HttpHelper.getResponse(urlString);
            Cat responseCat = new Cat();
            if (responseData != null) {
                try {
                    Gson gson = new Gson();
                    responseCat = gson.fromJson(responseData, Cat.class);
                    if (responseCat != null) {
                        cat = responseCat;
                    }
                } catch (JsonSyntaxException e) {
                    Log.e("DetailsActivity", "Error parsing JSON: " + e.getMessage());
                    runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
                }
            } else {
                Log.e("DetailsActivity", "Failed to fetch cat from server");
                runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
            }

            urlString = HOST + "/api/comments/" + catId;
            responseData = HttpHelper.getResponse(urlString);
            List<Comment> responseComments;
            if (responseData != null) {
                try {
                    Type listType = new TypeToken<List<Comment>>() {
                    }.getType();
                    Gson gson = new Gson();
                    responseComments = gson.fromJson(responseData, listType);
                    if (responseComments != null) {
                        comments = responseComments;
                    }
                } catch (JsonSyntaxException e) {
                    Log.e("DetailsActivity", "Error parsing comment JSON: " + e.getMessage());
                    runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
                }
            } else {
                Log.e("DetailsActivity", "Failed to fetch comment from server");
                runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
            }
            runOnUiThread(() -> setupLayout());
        }).start();
    }

    private void setupLayout() {
        TextView name = findViewById(R.id.detail_name);
        name.setText(cat.getCatName());
        TextView idView = findViewById(R.id.detail_id);
        idView.setText(String.valueOf(cat.getId()));
        TextView breed = findViewById(R.id.detail_breed);
        breed.setText(cat.getCatBreed());
        TextView labels = findViewById(R.id.detail_labels);
        if (!cat.getLabels().isEmpty()) {
            Log.d("labels",cat.getLabels().toString());
            labels.setText(String.join(", ", cat.getLabels()));
        } else {
            labels.setText("No Labels Yet!");
        }
        ImageView catphoto = findViewById(R.id.detail_photo);
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File destFile = new File(externalFilesDir, ("img-" + cat.getCatSightings().get(0).getId() + "-0"));
        Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
        catphoto.setImageBitmap(bitmap);

        setupCommentList();

        ImageView avatar = findViewById(R.id.detail_avator);
        Bitmap avatorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avator);
        avatar.setImageBitmap(BitmapHelper.getCircleBitmap(avatorBitmap));
    }

    private void setupCommentList() {
        EditText contentView = findViewById(R.id.editText);
        contentView.setText("");
        contentView.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);

        ListView commentList = findViewById(R.id.detail_commentlist);
        CommentAdapter adapter = new CommentAdapter(this, comments);
        commentList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_btn) {
            EditText contentView = findViewById(R.id.editText);
            String content = String.valueOf(contentView.getText());
            if (content != null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("content", content);
                    JSONArray labelsArray = new JSONArray();
                    if (!(labels == null)) {
                        for (String l : labels) {
                            labelsArray.put(l);
                        }
                    }
                    jsonObject.put("labels", labelsArray);
                    Log.d("labels", labelsArray.toString());
                    jsonObject.put("cat_id", catId);
                    String username = userInfoPref.getString("username", null);
                    jsonObject.put("username", username);
                    jsonObject.put("flag", false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendComment(jsonObject.toString());
            }
        } else if (v.getId() == R.id.add_labels) {
            labels = new ArrayList<>();
            String[] options = getResources().getStringArray(R.array.labels_array);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose labels");
            builder.setMultiChoiceItems(options, checkedItems, (dialog, which, isChecked) -> {
                int selectedCount = 0;
                for (boolean checked : checkedItems) {
                    if (checked) {
                        selectedCount++;
                    }
                }

                if (selectedCount > 5) {
                    ((AlertDialog) dialog).getListView().setItemChecked(which, false);
                    checkedItems[which] = !isChecked;
                    Toast.makeText(this, "You can only select up to 4 options", Toast.LENGTH_SHORT).show();
                } else {
                    checkedItems[which] = isChecked;
                }
            });

            builder.setPositiveButton("Confirm", (dialog, which) -> {
                for (int i = 0; i < options.length; i++) {
                    if (checkedItems[i]) {
                        labels.add(options[i]);
                    }
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void sendComment(String data) {
        new Thread(() -> {
            String urlString = HOST + "/api/comments";
            HttpURLConnection urlConnection = null;
            try {
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
                            new InputStreamReader(
                                    urlConnection.getInputStream()));
                    Gson gson = new Gson();
                    Comment ncomment = gson.fromJson(in, Comment.class);
                    comments.add(ncomment);
                    runOnUiThread(() -> {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                        }
                        fetchCat(catId);
                    });
                } else {
                    Log.e("LoginActivity", String.valueOf(responseCode));
                    runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
                }
            } catch (IOException e) {
                Log.e("LoginActivity", "Error fetching data from server: " + e.getMessage());
                runOnUiThread(() -> MessageHelper.showErrMessage(getApplicationContext()));
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