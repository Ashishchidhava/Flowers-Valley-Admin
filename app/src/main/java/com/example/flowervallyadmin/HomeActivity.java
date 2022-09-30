package com.example.flowervallyadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.flowervallyadmin.fragment.AddBannerFragment;
import com.example.flowervallyadmin.fragment.AddFlowerFragment;
import com.example.flowervallyadmin.fragment.ProfileFragment;
import com.example.flowervallyadmin.fragment.ViewAllBannerFragment;
import com.example.flowervallyadmin.fragment.ViewAllFlowerFragment;
import com.example.flowervallyadmin.fragment.ViewOrderFragment;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private String fragmentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        if (intent != null) {
            fragmentName = intent.getStringExtra("fragment_name");
        }

        switch (fragmentName) {
            case "addFlower": {
                replaceFragment(new AddFlowerFragment());
                break;
            }
            case "viewAllFlower": {
                replaceFragment(new ViewAllFlowerFragment());
                break;
            }
            case "addBanner": {
                replaceFragment(new AddBannerFragment());
                break;
            }
            case "viewAllBanner": {
                replaceFragment(new ViewAllBannerFragment());
                break;
            }
            case "viewOrder": {
                replaceFragment(new ViewOrderFragment());
                break;
            }
            case "viewProfile": {
                replaceFragment(new ProfileFragment());
                break;
            }
            default: {
                Log.i(TAG, "onCreate: Fragment Name not found.");
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: " + requestCode);
        Log.i(TAG, "onActivityResult: " + resultCode);
        Log.i(TAG, "onActivityResult: " + data);

        if (requestCode == 101 && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();
            Bundle bundle = new Bundle();
            bundle.putString("image_uri", mImageUri.toString());

            AddBannerFragment addBannerFragment = new AddBannerFragment();
            addBannerFragment.setArguments(bundle);
            replaceFragment(addBannerFragment);
        } else if (requestCode == 102 && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();
            Bundle bundle = new Bundle();
            bundle.putString("image_uri", mImageUri.toString());

            AddFlowerFragment addBannerFragment = new AddFlowerFragment();
            addBannerFragment.setArguments(bundle);
            replaceFragment(addBannerFragment);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}