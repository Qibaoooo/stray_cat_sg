package nus.iss.sa57.csc_android.utils;

import android.content.Context;
import android.view.View;

import nus.iss.sa57.csc_android.R;

public class NavigationBarHandler {
    View bar;
    Context context;
    public NavigationBarHandler(View bar, final Context context) {
        this.bar = bar;
        this.context = context;
    }
    public void setupBar(){
        setupCat();
        setupAccount();
    }

    public void setupCat(){
        bar.findViewById(R.id.nav_cat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setupAccount(){
        bar.findViewById(R.id.nav_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}