package com.numustec.voluntario.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.numustec.voluntario.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Window window = getWindow(); // Get the current window instance
        window.setStatusBarColor(getColor(R.color.purple_500));
        setTitle(getString(R.string.app_name));
    }
}