package com.example.jgzutlab01b.state;

import java.util.Objects;

public class StateHolder {

    private static String accumulatedBmi = "";
    private static int accumulationCounter = 0;

    public static String getAccumulatedBmi(){
        return accumulatedBmi;
    }

    public static void accumulateBmi(String bmi)
        {
            if(accumulationCounter >= 4)
            {
                reset();
            }

            if(bmi == null)
            {
                return;
            }
            accumulatedBmi += "<br>" + bmi;
            accumulationCounter++;

        }

    private static void reset(){
        StateHolder.accumulatedBmi = "";
        StateHolder.accumulationCounter = 0;
    }



}
