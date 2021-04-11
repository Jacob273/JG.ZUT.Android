package com.example.jgzutlab03.uiMessages;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Messenger {

    public static void DisplayToast(String msg, Context context)
    {
        if(canToast)
        {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
        }
    }


    public static boolean canToast = true;

}
