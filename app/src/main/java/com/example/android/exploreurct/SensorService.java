package com.example.android.exploreurct;

/**
 * Created by Admin on 25/10/2018.
 */


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by fabio on 30/01/2016.
 */
public class SensorService extends Service {
    public int counter=0;
    float distanceInMeters;
    float[] results=new float[1];

    LocationTrack locationTrack;

    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.example.android.servicedemo.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    // LocationThread mlocationthread;
    //mlocationthread = new LocationThread("MyHandler");
    //Looper looper=mlocationthread.getLooper();
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
   //working correctly
           // timer.schedule(timerTask, 1000, 1000*30); //
        timer.schedule(timerTask, 1000, 1000*60*5); //

    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        final DataBaseHelper db=new DataBaseHelper(getApplicationContext());

        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));

                Boolean same=false;
                db.open();
                Boolean notii= db.getNoti();
                double cl=Double.parseDouble(db.getLat());
                double cln=Double.parseDouble(db.getLang());

//              locationTrack = new LocationTrack(getApplicationContext());

                double longitude =MainActivity.locationTrack.getLongitude();
                double latitude =MainActivity.locationTrack.getLatitude();
                Log.e("AFDBondisplay===="+cl+","+cln,"*********runtime="+latitude+","+longitude);
                Log.e("AFDBdistance changed="+distanceInMeters,"********same flag="+same );
                //Location.distanceBetween(currentlat,currentlang,latitude,longitude,results);
                Location.distanceBetween(cl,cln,latitude,longitude,results);
                distanceInMeters = results[0];
                same= distanceInMeters < 0.6;
                Log.e("AFDBondisplay===="+cl+","+cln,"*********runtime="+latitude+","+longitude);
                Log.e("AFDBdistance changed="+distanceInMeters,"********same flag="+same );
                // your code here...
                // Toast.makeText(getApplicationContext(),"2 minute timer ",Toast.LENGTH_SHORT).show();
                try {
                    db.setClocation(latitude+"",longitude+"");

                    if (!same && notii ) {
                        //  prepareLocation();
                        db.open();
                        db.getUpdates(MainActivity.noticeList,latitude,longitude);
                        db.close();
                        Bundle b = new Bundle();
                        // onCreate(b);
                        if (MainActivity.noticeList.size() >= 1) {
                            b = new Bundle();
                            b.putString("email", MainActivity.email);
                            Log.e("*******","notification send" );
                            //Toast.makeText(getApplicationContext(),"Logged in !",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtras(b);
                            PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),
                                    (int) System.currentTimeMillis(), intent, 0);
                            // Build notification
                            String locs="";

                            for(int i=0;i<MainActivity.noticeList.size();i++)
                                locs=locs+"\n"+MainActivity.noticeList.get(i).getName();

                            // Actions are just fakez

                            Notification noti = new Notification.Builder(getApplicationContext())
                                    .setContentTitle("You have "
                                            + MainActivity.noticeList.size() + " new suggestions")
                                    .setContentText(locs).setSmallIcon(R.drawable.newlogo)
                                    .setContentIntent(pIntent)
                                    .addAction(R.drawable.ic_info, "View Details", pIntent)
                                    .build();
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            // hide the notification after its selected
                            noti.flags |= Notification.FLAG_AUTO_CANCEL;

                            notificationManager.notify(0, noti);
                        }

                    }
                    db.close();
                }catch (Exception e)
                {e.printStackTrace();}
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}