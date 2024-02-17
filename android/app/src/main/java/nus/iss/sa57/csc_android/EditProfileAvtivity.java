package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import nus.iss.sa57.csc_android.model.Comment;
import nus.iss.sa57.csc_android.utils.HttpHelper;

public class EditProfileAvtivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences userInfoPref;
    private SharedPreferences loginPref;
    private String username;
    private String password;
    private static String HOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        HOST = HttpHelper.getLocalHost(this);
        userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
        loginPref = getSharedPreferences("login_info", MODE_PRIVATE);

        Button submit_btn = findViewById(R.id.edit_submit);
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.edit_submit){
            EditText usernameView = findViewById(R.id.edit_username);
            username = usernameView.getText().toString();
            EditText passwordView = findViewById(R.id.edit_password);
            password = passwordView.getText().toString();
            EditText cpasswordView = findViewById(R.id.confirm_password);
            String cpassword = cpasswordView.getText().toString();
            InputMethodManager immName = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            immName.hideSoftInputFromWindow(usernameView.getWindowToken(), 0);
            usernameView.clearFocus();
            InputMethodManager immPwd = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            immPwd.hideSoftInputFromWindow(passwordView.getWindowToken(), 0);
            passwordView.clearFocus();
            InputMethodManager immcPwd = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            immcPwd.hideSoftInputFromWindow(passwordView.getWindowToken(), 0);
            cpasswordView.clearFocus();
            if (!username.isEmpty()) {
                if (!password.isEmpty()) {
                    if(password.equals(cpassword)) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("username", username);
                            jsonObject.put("password", password);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // login() method will take care of starting new intent
                        editProfile(jsonObject.toString());
                    } else{
                        Toast.makeText(this, "Password is not the same!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Username is required!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void editProfile(String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = userInfoPref.getInt("id", 0);
                String urlString = HOST + "/api/scsusers?id=" + id;
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("PUT");
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
                    Log.d("edit profile", String.valueOf(responseCode));

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewAccount();
                            }
                        });
                    }
                } catch (IOException e){

                }
            }
        }).start();
    }

    private void viewAccount(){
        userInfoPref.edit()
                .putString("username", username)
                .commit();
        if(loginPref.getBoolean("isRemember", false)) {
            loginPref.edit()
                    .putString("username", username)
                    .putString("password", password)
                    .commit();
        }
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}