package com.app.santabanta.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;

public class Splash extends AppCompatActivity {

    public SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pref = Utils.getSharedPref(Splash.this);
        setThemePreference();
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(Splash.this,MainActivity.class));
            finish();
        },1000);
    }

    private void setThemePreference() {
        if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
            setTheme(R.style.AppThemeLight);
        } else {
            setTheme(R.style.AppThemeDark);
        }
    }
}