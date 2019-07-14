package com.example.android.exploreurct;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Admin on 26/10/2018.
 */
public class LocationThread extends HandlerThread {

    private static final long UPDATE_LOCATION_INTERVAL = 0;  //Set this to whatever you had it at
    private static final float UPDATE_LOCATION_MINIMUM_DISTANCE = 0.0f;  //Same with this

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    public LocationThread(String name) {
        super(name);
    }

    //Pass in your location manager and listener to this.  This assumes they are not null!
    public void startLocationUpdates(LocationManager locationManager, LocationListener locationListener) {
        mLocationManager = locationManager;
        mLocationListener = locationListener;
        start();
    }

    //Use this to stop the updates and kill the thread.
    public void stopLocationUpdates() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
            mLocationListener = null;
            mLocationManager = null;
        }

        quit();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onLooperPrepared() {

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                UPDATE_LOCATION_INTERVAL,
                UPDATE_LOCATION_MINIMUM_DISTANCE,
                mLocationListener, getLooper());

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                UPDATE_LOCATION_INTERVAL,
                UPDATE_LOCATION_MINIMUM_DISTANCE,
                mLocationListener, getLooper());
    }
}