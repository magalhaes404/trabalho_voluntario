package com.numustec.voluntario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView menu;
    Layout screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menu = (BottomNavigationView) findViewById(R.id.bt_menu);
        screen = (Layout)findViewById(R.id.home_screen);
        menu.setOnNavigationItemSelectedListener(
                menuItem -> {
                    if(menuItem.getItemId() == R.id.nav_home){

                    }
                    else if(menuItem.getItemId() == R.id.nav_canditate){

                    }
                    else if(menuItem.getItemId() == R.id.nav_post){

                    }
                    else if(menuItem.getItemId() == R.id.nav_profile){

                    }
                    return true;
                }
        );
    }
}