package nus.iss.sa57.csc_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import nus.iss.sa57.csc_android.utils.NavigationBarHandler;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        View nav_bar = findViewById(R.id.nav_bar);
        NavigationBarHandler nav_handler = new NavigationBarHandler(nav_bar,this);
        nav_handler.setupCat();

        SharedPreferences userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = userInfoPref.getString("username", null);
        int id = userInfoPref.getInt("id", 0);
        String role = userInfoPref.getString("role", null);

        TextView usernameView = findViewById(R.id.account_username);
        usernameView.setText(username);
        TextView idView = findViewById(R.id.account_id);
        idView.setText(String.valueOf(id));
        TextView roleView = findViewById(R.id.account_role);
        roleView.setText(role);

        TextView editView = findViewById(R.id.account_edit);
        editView.setOnClickListener(this);
        TextView ownerView = findViewById(R.id.account_owner);
        if(role == "owner"){
            ownerView.setText("Post Lost Cat Notification   >>");
        } else{
            ownerView.setText("Apply to be an Owner    >>");
        }
        ownerView.setOnClickListener(this);
        TextView logoutView = findViewById(R.id.account_logout);
        logoutView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.account_logout){
            SharedPreferences userInfoPref = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfoPref.edit();
            editor.clear();
            editor.commit();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}