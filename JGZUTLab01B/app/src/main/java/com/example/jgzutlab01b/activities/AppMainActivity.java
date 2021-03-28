package com.example.jgzutlab01b.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jgzutlab01b.R;
import com.example.jgzutlab01b.constants.Constants;
import com.example.jgzutlab01b.data.model.BmiResult;
import com.example.jgzutlab01b.state.StateHolder;
import com.example.jgzutlab01b.viewmodels.MainAppViewModel;

import java.util.Locale;

public class AppMainActivity extends AppCompatActivity{

    private MainAppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(this, "en");
        setContentView(R.layout.content_main);

        //        if(savedInstanceState != null)
        //        {
        //            String bmi = savedInstanceState.getString(Constants.CurrentBMIKey);
        //        }
        //viewModel = new ViewModelProvider(this).get(MainAppViewModel.class);



        TextView accumulatedTextView = this.findViewById(R.id.main_bmiResult);
        if (getIntent().getExtras() != null)
        {
            BmiResult newResult = getIntent().getParcelableExtra(Constants.Intent_BMI);
            if(newResult != null){
                StateHolder.accumulateBmi(newResult.getFullBmiDescription());
                accumulatedTextView.setText(Html.fromHtml(StateHolder.getAccumulatedBmi()));
                DisplayToast(newResult);
                accumulatedTextView.setVisibility(View.VISIBLE);
            }
        }
        else{
            accumulatedTextView.setVisibility(View.INVISIBLE);
        }

    }

    private void DisplayToast(BmiResult result) {
        Toast toast = Toast.makeText(this, "Policzono BMI:  " + result.getValueFormatted(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
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
        AppMainActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
