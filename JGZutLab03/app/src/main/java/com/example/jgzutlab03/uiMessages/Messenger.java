package com.example.jgzutlab03.uiMessages;

import android.content.Context;
import android.widget.Toast;

public class Messenger {

    public static void DisplayToast(String msg, Context context)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
