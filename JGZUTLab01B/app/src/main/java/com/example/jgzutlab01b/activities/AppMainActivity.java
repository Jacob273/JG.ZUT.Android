package com.example.jgzutlab01b.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.jgzutlab01b.R;

import java.util.Locale;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLocale(this, "en");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public void onClick(View view) {

        Intent myIntent = new Intent(AppMainActivity.this, BmiCalculatorActivity.class);
        //myIntent.putExtra("key", null); //Optional parameters
        AppMainActivity.this.startActivity(myIntent);


    }
}
