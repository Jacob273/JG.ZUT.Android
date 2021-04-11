package com.example.jgzutlab03.spatial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.example.jgzutlab03.base.Func;
import com.example.jgzutlab03.uiMessages.Messenger;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

public class LocationServiceWrapper extends LocationCallback {

    private Context _context;

    private FusedLocationProviderClient _fusedLocationClient;
    private SettingsClient _settingsClient;
    private LocationSettingsRequest _locationSettingRequest;
    private LocationRequest _locationRequest;

    private Location currentLocation;
    private Func<Location> _onNewLocation;

    public LocationServiceWrapper(Context context, Func<Location> onNewLocation){
        _context = context;
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(_context);
        _settingsClient = LocationServices.getSettingsClient(_context);
        _onNewLocation = onNewLocation;
    }


    public void tryRequestCurrentLocation(){

        initializeLocationSettings();
        initializeLocationRequest();


        _settingsClient.checkLocationSettings(_locationSettingRequest).addOnSuccessListener(locationSettingsResponse ->
        {
            try
            {
                requestLocation();
            }
            catch (Exception ex)
            {
                System.out.println("ERROR OCCUREd while 'tryRequestCurrentLocation'. Reason: " + ex.getMessage());
            }
        });
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        if(locationResult == null)
        {
            return;
        }
        currentLocation = locationResult.getLastLocation();
        _onNewLocation.Execute(currentLocation);

    }

    private void initializeLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(_locationRequest);
        _locationSettingRequest = builder.build();
        builder.setAlwaysShow(true);
    }

    private void initializeLocationRequest() {
        _locationRequest = LocationRequest.create();
        _locationRequest.setInterval(0);
        _locationRequest.setFastestInterval(0);
        _locationRequest.setNumUpdates(1);
        _locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    public void requestLocation(){
        _fusedLocationClient.requestLocationUpdates(_locationRequest, this, null);
    }
}
