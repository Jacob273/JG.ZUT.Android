package com.example.jgzutlab03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.jgzutlab03.base.Callback;
import com.example.jgzutlab03.uiMessages.Messenger;

public class OnOffPowerReceiver extends BroadcastReceiver {

    Context _context;
    private Callback _onConnected;
    private Callback _onDisconnected;

    public OnOffPowerReceiver(Context context, Callback onConnected, Callback onDisconnected){
        super();
        _context = context;
        _context.registerReceiver(this, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        _context.registerReceiver(this, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        _onConnected = onConnected;
        _onDisconnected = onDisconnected;
    }

    @Override
    public void onReceive(Context ctxt, Intent intent) {
        switch(intent.getAction())
        {
            case Intent.ACTION_POWER_CONNECTED:
                _onConnected.Execute();
                break;

            case Intent.ACTION_POWER_DISCONNECTED:
                _onDisconnected.Execute();
                break;
        }
    }



}