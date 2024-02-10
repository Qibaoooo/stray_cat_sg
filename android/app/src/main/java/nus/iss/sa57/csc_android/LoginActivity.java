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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
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
    public void onClick(View v){
        Log.d("LoginActivity", "Button Clicked");
        usernameView = findViewById(R.id.username);
        username = usernameView.getText().toString();
        passwordView = findViewById(R.id.password);
        password = passwordView.getText().toString();
        rememberBox = findViewById(R.id.remember);
        isRemember = rememberBox.isChecked();
        if(v.getId() == R.id.login){
            if(!username.isEmpty()){
                if(!password.isEmpty()){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", username);
                        jsonObject.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(login(jsonObject.toString())){
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this,"Username is required!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean login(final String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = HOST + "/api/auth/login";
                HttpURLConnection urlConnection = null;
                try{
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
                        if(isRemember){
                            SharedPreferences pref = getSharedPreferences("login_info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("isRemember",isRemember);
                            editor.putString("username",username);
                            editor.putString("password",password);
                        }
                    } else{
                        isLoginSuccess = false;
                    }
                } catch (IOException e) {
                    Log.e("LoginActivity", "Error fetching data from server: " + e.getMessage());
                }
            }
        }).start();
        return isLoginSuccess;
    }

    private void loggedinUser(){
        SharedPreferences pref = getSharedPreferences("login_info", MODE_PRIVATE);
        if(pref.getBoolean("isRemember",false)){
            username = pref.getString("username",null);
            password = pref.getString("password",null);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            isLoginSuccess = login(jsonObject.toString());
            if(isLoginSuccess){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}