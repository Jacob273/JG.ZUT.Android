package com.example.jgzutlab01b.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.jgzutlab01b.R;

public class BmiCalculatorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do extra stuff on your resources, using findViewById on your layout_for_activity1setContentView(R.layout.content_bmi_calculator);
        setContentView(R.layout.content_bmi_calculator);
    }

}
