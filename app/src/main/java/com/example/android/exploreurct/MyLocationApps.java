package com.example.android.exploreurct;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Admin on 20/10/2018.
 */
/*Intent i = new Intent(context, MyLocationApps.class);
 context.startService(i);*/

public class MyLocationApps extends Service {

    LocationManager locMan;
    Location curLocation;
    Boolean locationChanged;

    Handler handler = new Handler();

    LocationListener gpsListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            Toast.makeText(getBaseContext(), "Inside Location changed", Toast.LENGTH_LONG).show();
            // Log.w("GPS", "Started");
            if (curLocation == null) {
                curLocation = location;
                locationChanged = true;
            }
            float[] results=new float[1];
            Location.distanceBetween(curLocation.getLatitude(),curLocation.getLongitude(),location.getLatitude(),location.getLongitude(),results);
            float distanceInMeters = results[0];
            boolean within1m=distanceInMeters < 1;
            if (within1m==true)
                locationChanged = false;
            else
                locationChanged = true;

            curLocation = location;

            if (locationChanged==true)
            {
                Toast.makeText(getBaseContext(), " Location changed true ", Toast.LENGTH_LONG).show();

                //locMan.removeUpdates(gpsListener);
                Intent intent = new Intent(getApplicationContext(),MyLocationApps.class);
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);
                // Build notification
                // Actions are just fake
                Notification noti = new Notification.Builder(getApplicationContext())
                        .setContentTitle("New notification\n" + "you have "+MainActivity.noticeList.size()+" suggestions")
                        .setContentText("Subject").setSmallIcon(R.drawable.ic_help)
                        .setContentIntent(pIntent)
                        .addAction(R.drawable.ic_info, "View Details", pIntent)
                        .addAction(R.drawable.ic_help, "Ignore",pIntent).build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // hide the notification after its selected
                noti.flags |= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(0, noti);
            }

        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {
            // Log.w("GPS", "Location changed", null);
        }

        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            if (status == 0)// UnAvailable
            {

            } else if (status == 1)// Trying to Connect
            {

            } else if (status == 2) {// Available

            }
        }

    };


    @Override
    public void onCreate() {
        Toast.makeText(getBaseContext(), "Inside onCreate of Service", Toast.LENGTH_LONG).show();

        Log.e(TAG, "Inside onCreate of Service");
        super.onCreate();

        locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
           /*if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                   locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000, 1, gpsListener);
           } else {
                   this.startActivity(new Intent("android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS"));
           }
         */
        if (curLocation != null) {
            double lat = curLocation.getLatitude();
            double lng = curLocation.getLongitude();
            Toast.makeText(getBaseContext(),"Lat : " + String.valueOf(lat) + "\n Long : "+ String.valueOf(lng), Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getBaseContext(),"Didn Get any location", Toast.LENGTH_LONG).show();
        }
    }

    final String TAG="LocationService";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(),"Inside onStartCommand of Service", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Inside onStartCommand of Service");



        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }


    @Override
    public void onStart(Intent i, int startId)
    {
        Toast.makeText(getBaseContext(),"Inside onStart of Service", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Inside onStart of Service");

        handler.postDelayed(GpsFinder,5000);// will start after 5 seconds
    }

    public IBinder onBind(Intent arg0) {
        Log.e(TAG, "Inside onBind of Service");
        return null;
    }
    public Runnable GpsFinder = new Runnable()
    {

        @SuppressLint("MissingPermission")
        public void run()
        {
            // TODO Auto-generated method stub

            if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, gpsListener);
            }
            else
            {
                getApplicationContext().startActivity(new Intent("android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS"));
            }

            if (curLocation != null) {
                double lat = curLocation.getLatitude();
                double lng = curLocation.getLongitude();
                Toast.makeText(getBaseContext(),"Lat : " + String.valueOf(lat) + "\n Long : "+ String.valueOf(lng), Toast.LENGTH_LONG).show();

            }

            handler.postDelayed(GpsFinder,5000);// register again to start after 5 seconds...


        }
    };
}