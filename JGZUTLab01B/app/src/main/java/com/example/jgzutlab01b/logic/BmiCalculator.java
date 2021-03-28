package com.example.jgzutlab01b.logic;

import com.example.jgzutlab01b.enums.DistanceUnit;
import com.example.jgzutlab01b.enums.MassUnit;

public class BmiCalculator {

    public static double Calculate(double weight, double height,
                                   DistanceUnit distanceUnit, MassUnit massUnit) throws UnsupportedOperationException
    {
        if(distanceUnit == DistanceUnit.Metres && massUnit == MassUnit.Kilograms)
        {
            return weight / (height * height);
        }
        else
            {
            throw new UnsupportedOperationException("Cannot calculate BMI for units other than metres & kilgorams");
        }
    }

}
