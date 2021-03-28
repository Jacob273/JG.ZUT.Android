package com.example.jgzutlab01b.activities;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jgzutlab01b.R;
import com.example.jgzutlab01b.constants.Constants;
import com.example.jgzutlab01b.data.model.BmiResult;
import com.example.jgzutlab01b.enums.DistanceUnit;
import com.example.jgzutlab01b.enums.MassUnit;
import com.example.jgzutlab01b.logic.BmiCalculator;

public class BmiCalculatorActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do extra stuff on your resources, using findViewById on your layout_for_activity1setContentView(R.layout.content_bmi_calculator);
        setContentView(R.layout.content_bmi_calculator);

        InitializeTextEdits();
        IntiializeOnClickHandler();
    }

    private void InitializeTextEdits() {
        EditText heightEd = this.findViewById(R.id.Bmi_HeightText);
        EditText weightEd = this.findViewById(R.id.Bmi_WeightText);

        heightEd.setText("1.5");
        weightEd.setText("55");
    }

    private void IntiializeOnClickHandler() {
        this.findViewById((R.id.Bmi_CalculateButton)).setOnClickListener(view -> {
             String weightText = ((EditText)this.findViewById(R.id.Bmi_WeightText)).getText().toString();
             String heightText = ((EditText)this.findViewById(R.id.Bmi_HeightText)).getText().toString();

            if(weightText != null && heightText != null)
            {
                double weight = Double.valueOf(weightText);
                double height = Double.valueOf(heightText);

                Double bmiValue = BmiCalculator.Calculate(weight, height, DistanceUnit.Metres, MassUnit.Kilograms);
                BmiResult bmiResult = new BmiResult(bmiValue, String.format("[w: %s kg] [h: %s m]",weight, height));

                Intent msg = new Intent(this, AppMainActivity.class);
                msg.putExtra(Constants.Intent_BMI, bmiResult);
                startActivity(msg);
            }
        });
    }


}
