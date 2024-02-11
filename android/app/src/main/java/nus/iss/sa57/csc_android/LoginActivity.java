package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import nus.iss.sa57.csc_android.payload.LoginResponse;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameView;
    private EditText passwordView;
    private CheckBox rememberBox;
    private Button login_btn;
    String username;
    String password;
    private boolean isRemember;
    private boolean isLoginSuccess = false;
    private static String HOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HOST = getResources().getString(R.string.host_local);
        loggedinUser();
        login_btn = findViewById(R.id.login);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("LoginActivity", "Button Clicked");
        usernameView = findViewById(R.id.username);
        username = usernameView.getText().toString();
        passwordView = findViewById(R.id.password);
        password = passwordView.getText().toString();
        rememberBox = findViewById(R.id.remember);
        isRemember = rememberBox.isChecked();
        if (v.getId() == R.id.login) {
            if (!username.isEmpty()) {
                if (!password.isEmpty()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", username);
                        jsonObject.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // login() method will take care of starting new intent
                    login(jsonObject.toString());
                } else {
                    Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Username is required!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean login(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = HOST + "/api/auth/login";
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                    outputStream.writeBytes(data);
                    outputStream.flush();
                    outputStream.close();
                    int responseCode = urlConnection.getResponseCode();


                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        isLoginSuccess = true;

                        // parse login response and save to SharedPreferences
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                        urlConnection.getInputStream()));
                        Gson gson = new Gson();
                        LoginResponse lr = gson.fromJson(in, LoginResponse.class);
                        SharedPreferences userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor userInfoEditor = userInfoPref.edit();
                        userInfoEditor.putString("jwt", lr.getJwt());
                        userInfoEditor.putInt("id", lr.getId());
                        userInfoEditor.putString("username", lr.getUsername());
                        userInfoEditor.putString("role", lr.getRole());
                        userInfoEditor.putLong("expirationTime", lr.getExpirationTime());
                        userInfoEditor.commit();

                        SharedPreferences pref = getSharedPreferences("login_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        if (isRemember) {
                            editor.putBoolean("isRemember", true);
                            editor.putString("username", username);
                            editor.putString("password", password);
                        } else {
                            editor.clear();
                        }
                        editor.commit();
                        runOnUiThread(() -> {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        });
                    } else {
                        // show error?
                        isLoginSuccess = false;
                    }
                } catch (IOException e) {
                    Log.e("LoginActivity", "Error fetching data from server: " + e.getMessage());
                }
            }
        }).start();
        return isLoginSuccess;
    }

    private void loggedinUser() {
        SharedPreferences pref = getSharedPreferences("login_info", MODE_PRIVATE);
        String m =String.valueOf(pref.getBoolean("isRemember", false));
        if (pref.getBoolean("isRemember", false)) {
            username = pref.getString("username", null);
            password = pref.getString("password", null);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            login(jsonObject.toString());
        }
    }
}