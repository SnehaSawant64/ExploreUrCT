package com.example.android.exploreurct;

import com.google.android.gms.location.LocationListener;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.exploreurct.GetNearbyPlacesData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,LocationListener{

    public static String email;
    int backButtonCount;
    private EditText search;
    private GoogleMap mMap;
    //run thread
    float distanceInMeters;
    float[] results=new float[1];


    public  static int filtrflag;

    public static List<LLocation> filteredList = new ArrayList<>();
    public static List<LLocation> noticeList = new ArrayList<>();

    public boolean noti;
    private RecyclerView recyclerView;
    private LLocationAdapter notice_Adapter;
    private TextView area,info;
    public static double currentlat,currentlang;
    Location location;
    private Boolean visitedLocations;
    public static Double lat,lang;
    public static LocationTrack locationTrack;

    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    public Context getCtx() {
        return ctx;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final long TIME=1;
        final Handler h=new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                locationTrack.getLatitude();
                locationTrack.getLongitude();
              //  locationTrack.getLocation();
                h.post(this );
            }
        });
        ctx = this;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }


        filtrflag=0;
        visitedLocations=false;
        Bundle b=getIntent().getExtras();
        email=b.getString("email");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                Bundle bb=new Bundle();
                bb.putString("cat_key","floting");
                i.putExtras(bb);
                startActivity(i);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        area=findViewById(R.id.areaid);
        info=findViewById(R.id.info);

        if(visitedLocations==false)
            info.setText("Nearby Places you can visit");
        recyclerView = (RecyclerView) findViewById(R.id.teacher_recyclerview);

        notice_Adapter = new LLocationAdapter(noticeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notice_Adapter);

        search=findViewById(R.id.ad_search);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            String dname;

            @Override
            public void onClick(View view, int position) {
                if(filtrflag==0) {
                    makeText(getApplicationContext(), "nearby ", Toast.LENGTH_SHORT).show();
                    LLocation notice = noticeList.get(position);
                    lat = Double.parseDouble(notice.getLat());
                    lang = Double.parseDouble(notice.getLang());
                    dname = notice.getName();
                    //final SpannableString s=new SpannableString(notice.getUrl());
                    //Linkify.addLinks(s,Linkify.WEB_URLS);
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(notice.getName());
                    String distance = notice.getDistance();
                    Log.e("direct =====", "**********" + distance);
                    //  distance=getDistanceTimeMain();
                    Log.e("by road====", "******" + distance);
                    alertDialog.setMessage(notice.getDisc() + "\n\n" + notice.getUrl() + "\n\n location= (" + lat + "," + lang + ")" +
                            "\n\nDistance from current location=" + distance + " meters");
                    //   alertDialog.setMessage(s);
                    alertDialog.setIcon(R.drawable.ic_directions);
                    alertDialog.setButton("Mark as visited", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            makeText(getApplicationContext(), "Marked as visited!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setButton("ignore", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            makeText(getApplicationContext(), "Ok!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setButton("view map", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                     /*   Intent ii=new Intent(getApplicationContext(),MapsActivity.class);
                        Bundle bb=new Bundle();f
                        bb.putString("cat_key","null");
                        bb.putDouble("lat",lat);
                        bb.putDouble("lang",lang);
                        ii.putExtras(bb);
                        startActivity(ii);*/
                            Log.e("Check ", "**" + lat + "," + lang + "----" + dname + "");
                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f(%s)", lat, lang, dname);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            intent.setPackage("com.google.android.apps.maps");
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                try {
                                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(unrestrictedIntent);
                                } catch (ActivityNotFoundException innerEx) {
                                    makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        }
                    });
                    alertDialog.show();
                }
                else if(filtrflag==1)
                {
                    makeText(getApplicationContext(), "showing visited ", Toast.LENGTH_SHORT).show();
                    LLocation notice = filteredList.get(position);
                    lat = Double.parseDouble(notice.getLat());
                    lang = Double.parseDouble(notice.getLang());
                    dname = notice.getName();
                    //final SpannableString s=new SpannableString(notice.getUrl());
                    //Linkify.addLinks(s,Linkify.WEB_URLS);
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(notice.getName());
                    String distance = notice.getDistance();
                    Log.e("direct =====", "**********" + distance);
                    //  distance=getDistanceTimeMain();
                    Log.e("by road====", "******" + distance);
                    alertDialog.setMessage(notice.getDisc() + "\n\n" + notice.getUrl() + "\n\n location= (" + lat + "," + lang + ")" +
                            "\n\nDistance from current location=" + distance + " meters");
                    //   alertDialog.setMessage(s);
                    alertDialog.setIcon(R.drawable.ic_directions);
                    alertDialog.setButton("Mark as visited", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            makeText(getApplicationContext(), "Marked as visited!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setButton("ignore", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            makeText(getApplicationContext(), "Ok!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setButton("view map", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                     /*   Intent ii=new Intent(getApplicationContext(),MapsActivity.class);
                        Bundle bb=new Bundle();f
                        bb.putString("cat_key","null");
                        bb.putDouble("lat",lat);
                        bb.putDouble("lang",lang);
                        ii.putExtras(bb);
                        startActivity(ii);*/
                            Log.e("Check ", "**" + lat + "," + lang + "----" + dname + "");
                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f(%s)", lat, lang, dname);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            intent.setPackage("com.google.android.apps.maps");
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                try {
                                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(unrestrictedIntent);
                                } catch (ActivityNotFoundException innerEx) {
                                    makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        }
                    });
                    alertDialog.show();
                }
            }
            @Override
            public void onLongClick(View view, int position) {
                backButtonCount=0;
                final LLocation notice = noticeList.get(position);

                AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(notice.getName());
                alertDialog.setMessage(notice.getUrl());
                alertDialog.setIcon(R.drawable.ic_info);
                if(visitedLocations==true) {
                    alertDialog.setButton("Add as not visited ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataBaseHelper th = new DataBaseHelper(MainActivity.this);
                            th.open();
                            int id = Integer.parseInt(notice.getId());
                            if (th.markAsUnVisited(id+"") == true)
                                makeText(getApplicationContext(), "Marked " + notice.getName() + "as not visited !",
                                        Toast.LENGTH_SHORT).show();
                            else
                                makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                            th.close();
                            noticeList.clear();
                            prepareLocation();
                        }
                    });
                }
                else {
                    alertDialog.setButton("Mark as visited", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataBaseHelper th = new DataBaseHelper(MainActivity.this);
                            th.open();
                            int id = Integer.parseInt(notice.getId());
                            makeText(getApplicationContext(), "location id="+id, Toast.LENGTH_SHORT).show();

                            if (th.markAsVisited(id+"") == true)
                                makeText(getApplicationContext(), "Marked " + notice.getName() + "as visited !",
                                        Toast.LENGTH_SHORT).show();
                            else
                                makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                            th.close();
                            noticeList.clear();
                            prepareLocation();
                        }
                    });
                }
                alertDialog.show();
//                Toast.makeText(getApplicationContext(), notice.getTitle() + " is selected! long press", Toast.LENGTH_SHORT).show();

            }
        }));
        setlocation();
        prepareLocation();
        addTextListener();

      //  Intent i = new Intent(this, MyLocationApps.class);
      //  this.startService(i);

//        startthread();
    }




    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }




    public void startthread(){
        final DataBaseHelper db=new DataBaseHelper(MainActivity.this);

        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                Boolean same=false;
                db.open();
                Boolean notii= db.getNoti();
                double cl=Double.parseDouble(db.getLat());
                double cln=Double.parseDouble(db.getLang());

                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();
                Log.e("AFDBondisplay===="+cl+","+cln,"*********runtime="+latitude+","+longitude);
                Log.e("AFDBdistance changed="+distanceInMeters,"********same flag="+same );
                //Location.distanceBetween(currentlat,currentlang,latitude,longitude,results);
                Location.distanceBetween(cl,cln,latitude,longitude,results);
                distanceInMeters = results[0];
                same= distanceInMeters < 0.3;
                Log.e("AFDBondisplay===="+cl+","+cln,"*********runtime="+latitude+","+longitude);
                Log.e("AFDBdistance changed="+distanceInMeters,"********same flag="+same );
                // your code here...
               // Toast.makeText(getApplicationContext(),"2 minute timer ",Toast.LENGTH_SHORT).show();
               try {
                   db.setClocation(latitude+"",longitude+"");

                   if (!same && notii ) {
                     //  prepareLocation();
                       db.open();
                       db.getUpdates(noticeList,latitude,longitude);
                       db.close();
                       Bundle b = new Bundle();
                      // onCreate(b);
                       if (MainActivity.noticeList.size() >= 1) {
                           b = new Bundle();
                           b.putString("email", email);
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

        // schedule the task to run starting now and then every hour...
        timer.schedule (hourlyTask, 01, 1000*1*60);
        // 1000*10*60 every 10 minut
    }



    public void setlocation()
    {
        DataBaseHelper db=new DataBaseHelper(MainActivity.this);
        locationTrack = new LocationTrack(MainActivity.this);
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();

            currentlat=latitude;
            currentlang=longitude;

            Log.e("check main long lat","lat="+latitude+"long="+longitude);

           /* if(longitude==0.0 || latitude==0.0)
            {
                Log.e("if true","lat="+latitude+"long="+longitude);
                AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("No Internet Connection");
                alertDialog.setMessage("Cannot access location, Check your Internet and try again");
                //   alertDialog.setMessage(s);
                alertDialog.setIcon(R.drawable.ic_directions);
                alertDialog.setButton("Try Again!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent ii=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(ii);
                    }
                });
                alertDialog.show();
            }*/

            String loctitle="CURRENT LOCATION";
            String address=getAddress(latitude,longitude);
            area.setText("\bYourcurrent location is :\n "+address);

            makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:"
                    + Double.toString(latitude), Toast.LENGTH_LONG).show();
            db.open();
          //  db.setClocation(latitude+"",longitude+"");
            db.close();


        } else {
            locationTrack.showSettingsAlert();
        }
      /*  Location location = location;
        latitude =location.getLatitude();
        longitude=location.getLongitude();
        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:"
        + Double.toString(latitude), Toast.LENGTH_LONG).show();
    */
    }


    private String getUrl(double latitude , double longitude)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"Your_key");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }


    private String getAddress(double LATITUDE ,double LONGITUDE){
        String srtAdd=" ";
        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses=geocoder.getFromLocation(LATITUDE,LONGITUDE,1);
            if(addresses != null){
                Address returnedAddress=addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for(int i=0;i<=returnedAddress.getMaxAddressLineIndex();i++){
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                srtAdd=strReturnedAddress.toString();
                Log.w("My address",strReturnedAddress.toString());
            }else {
                Log.w("My address","no address returned");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.w("My address" ,"can not get address");
        }
        return srtAdd;
    }

    public void addTextListener(){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                backButtonCount=0;
                //filteredList = new ArrayList<>();
                filteredList.clear();
                for (int i = 0; i < noticeList.size(); i++) {
                    final String text1 = String.valueOf(noticeList.get(i).getName()).toLowerCase();

                    Log.e("search Option" ,"search string="+query+"notice title="+text1);
                    if (text1.contains(query)) {
                        filteredList.add(noticeList.get(i));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                notice_Adapter = new LLocationAdapter(filteredList, MainActivity.this);
                recyclerView.setAdapter(notice_Adapter);
//                NoticeAdapter.notifyDataSetChanged();  // data set changed
                notice_Adapter.notifyDataSetChanged();
                filtrflag = 1;
            }
        });
    }

    private void prepareLocation() {


        DataBaseHelper entry = new DataBaseHelper(MainActivity.this);
        entry.open();///***********
        // entry.createNoticeEntry(title.getText().toString(), note.getText().toString(),
        // dateView.getText().toString(), dateView1.getText().toString(), filePath.getText().toString(), tid);
        try{
            if(visitedLocations==true)
            {
                noticeList.clear();
                entry.printVisited(noticeList);
            }
            else
            {
                noticeList.clear();
                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();
                Log.e("maincurrent lat lang=",latitude+",,,,,,,,,"+longitude);
                entry.printLocation(noticeList,latitude,longitude);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        entry.close();

        //not = new Notice("2"," HADOOP Workshop"," HADOOP Workshop on 27th and 28th
        // March for teachers and students at Av hall Abasaheb Garware College Pune","20/3/2018","27/3/2018");
        //noticeList.add(not);F
        notice_Adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        backButtonCount=0;

        // Inflate the menu; this adds items to the action bar if it is present.
        TextView profilName=(TextView) findViewById(R.id.profile);
        TextView profileEmail=findViewById(R.id.textView);

        DataBaseHelper th = new DataBaseHelper(this);
        th.open();
        String f=th.getFname(email);
        profilName.setText("Hello "+f);
        profileEmail.setText(email);

        noti=th.getNoti();
        String aa;
        if(noti==true)
            aa="True";
        else
            aa="False";
        th.setLogin(aa+"",email+"");
        Log.e("notification bforesetL:","**************tab is "+noti);
        Log.e("notification bforesetL:","**************tab is "+th.getNoti());
        th.close();
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        backButtonCount=0;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Help");
            alertDialog.setMessage("\t\t\tMake sure that location of your device is on.\n\n\t\t\tThe list of locations" +
                    " based on your interest and your current location is displayed on your home screen.\n\n\t\t\tYou can get more" +
                    " information about the place" +
                    " just tap on the place.\n\t\t\tLongpress the specific tab to mark specific" +
                    " location as visited so that you can not get " +
                    "informed about same place again and again " +
                    "\n\n\t\tYou can search nearby hotels " +
                    "or nearby hospitals if needed with the help of tabs provided on navigation menu");
            alertDialog.setIcon(R.drawable.ic_help);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Toast.makeText(getApplicationContext(),"Ok!",Toast.LENGTH_SHORT).show();
                }
            });
            //alertDialog.setP("Delete",finish());
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        backButtonCount=0;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else*/ if (id == R.id.nav_manage) {
            Intent i=new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(i);
        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/else if (id == R.id.nav_suggestions){
            AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Give suggestions or experience");
            //  alertDialog.setMessage(stud.getStart()+"\n\n"+stud.getSubject());
            alertDialog.setIcon(R.drawable.ic_mail);
            alertDialog.setButton("Send mail", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String mail="cityguidemh12@gmail.com";
                    sendMailTo(mail);
                }
            });
            alertDialog.show();
        }
        else if (id ==R.id.nav_interest)
        {
            Intent i=new Intent(getApplicationContext(),AddInterestActivity.class);
            startActivity(i);
        }else if (id == R.id.nav_hotel) {
            Intent i=new Intent(getApplicationContext(),MapsActivity.class);
            Bundle bb=new Bundle();
            bb.putString("cat_key","resto");
            i.putExtras(bb);
            startActivity(i);
        }else if (id == R.id.nav_hospital) {
            Intent i=new Intent(getApplicationContext(),MapsActivity.class);
            Bundle bb=new Bundle();
            bb.putString("cat_key","hospital");
            i.putExtras(bb);
            startActivity(i);
        }else if (id == R.id.nav_logout) {
            DataBaseHelper th=new DataBaseHelper(MainActivity.this);
            th.open();
            noti=th.getNoti();
            String aa;
            if(noti==true)
                aa="True";
                else
                aa="False";
            th.setLogin(aa+"","0");
            Log.e("notification bforesetL:","**************tab is "+noti);
            Log.e("notification bforesetL:","**************tab is "+th.getNoti());
            th.close();
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }else if(id== R.id.nav_recent_visits){
            visitedLocations=true;
            info.setText("Places you have already visited");
            prepareLocation();
        }else if(id== R.id.nearby){
            visitedLocations=false;
            info.setText("Nearby Places you can visit");
            prepareLocation();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    boolean sendMailTo(String mail){

        String to;
        to = mail;
        String subject= " ";
        String msg=new String(" ");

        Intent email=new Intent(Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");

        email.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT,subject);
        email.putExtra(Intent.EXTRA_TEXT,msg);

        //need this to prompts email client only
        //email.setType("message/rfc822");

        try {
            startActivity(Intent.createChooser(email,"send mail..."));
            Bundle b=new Bundle();
            Log.i("Finished sending mail..","");
            return true;
        }
        catch (android.content.ActivityNotFoundException ex){
            makeText(this, "no email cliend installed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    @Override
    public void onBackPressed(){
        //do nothing
        //finish();
        //System.exit(0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(visitedLocations==false){
            //super.onBackPressed();
            if(backButtonCount>=1)
            {
                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                backButtonCount=0;
            }
            else
            {
                makeText(this,"Press the back button once again to close the application.",
                        Toast.LENGTH_SHORT).show();
                backButtonCount++;
            }
        }else if(visitedLocations==true){
            visitedLocations=false;
            if(visitedLocations==false)
            {
                info.setText("Nearby Places you can visit");
            }
            prepareLocation();
            return;
        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
