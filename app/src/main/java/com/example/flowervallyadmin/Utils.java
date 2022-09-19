package com.example.flowervallyadmin;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by AuthSafe on 19-09-2022.
 * <br>
 * Copyright (c) 2022 SecureLayer7 Technologies. All rights reserved.
 */
public class Utils {
    public static void replaceFragment(Fragment fragment, FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}
