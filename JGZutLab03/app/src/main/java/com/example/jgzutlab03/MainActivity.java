package com.example.jgzutlab03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.jgzutlab03.spatial.LocationServiceWrapper;
import com.example.jgzutlab03.uiMessages.Messenger;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST = 1;
    private static final String CHANNEL_ID = "BatteryMonitor_01";
    private OnOffPowerReceiver _onOffReceiver;
    private LocationServiceWrapper _locationServiceWrapper;

    int NOTIFICATION_ID = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            listenForCheckboxChanged();

        _locationServiceWrapper = new LocationServiceWrapper(this, (location) ->
            {
                try
                {
                        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                        CreateChannelIfRequired(notificationManager);
                        SendGeoNotification(location, notificationManager);
                }
                catch(Exception ex)
                {
                    System.out.println("Error ocurred while trying to send notification message. Reason : " + ex.getMessage());
                }
            });

            if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                requestPermission();
            }

            _onOffReceiver = new OnOffPowerReceiver(this, () -> {
                    Messenger.DisplayToast("Charger Connected...", this);
                    _locationServiceWrapper.tryRequestCurrentLocation();
            }, ()->
            {
                Messenger.DisplayToast("Charger Disconnected...", this);
            });


        }

    private void listenForCheckboxChanged() {
        this.findViewById(R.id.canToastCheckBox).setOnClickListener(v -> {
            Messenger.canToast = ((CompoundButton) v).isChecked();
        });
    }

    private void SendGeoNotification(Location location, NotificationManager notificationManager) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_text_light)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle("Powiadomienie dotyczące twojej lokalizacji.")
                .setContentText(location.getLatitude() + ", " + location.getLongitude())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void CreateChannelIfRequired(NotificationManager notificationManager) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "BatteryMonitor";
            String Description = "Battery & Geo Monitor";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    private void requestPermission() {
        EasyPermissions.requestPermissions(this, "Acccess geo location...", LOCATION_REQUEST, Manifest.permission.ACCESS_FINE_LOCATION);
    }
}