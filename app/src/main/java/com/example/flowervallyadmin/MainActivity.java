package com.example.flowervallyadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flowervallyadmin.fragment.LoginFragment;
import com.example.flowervallyadmin.fragment.MordernDashboardFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String mobile = "";
    private CardView addBanner, viewBanner, addFlower, viewFlower, viewOrder, viewProfile;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBanner = findViewById(R.id.add_banner);
        viewBanner = findViewById(R.id.view_banner);
        addFlower = findViewById(R.id.add_flower);
        viewFlower = findViewById(R.id.view_flower);
        viewOrder = findViewById(R.id.view_orders);
        viewProfile = findViewById(R.id.view_profile);

        addBanner.setOnClickListener(this);
        viewBanner.setOnClickListener(this);
        addFlower.setOnClickListener(this);
        viewFlower.setOnClickListener(this);
        viewOrder.setOnClickListener(this);
        viewProfile.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_flower: {
                callIntent("addFlower");
                break;
            }
            case R.id.view_flower: {
                callIntent("viewAllFlower");
                break;
            }
            case R.id.add_banner: {
                callIntent("addBanner");
                break;
            }
            case R.id.view_banner: {
                callIntent("viewAllBanner");
                break;
            }
            case R.id.view_orders: {
                callIntent("viewOrder");
                break;
            }
            case R.id.view_profile: {
                callIntent("viewProfile");
                break;
            }
        }
    }

    private void callIntent(String value) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("fragment_name", value);
        startActivity(intent);
    }
}