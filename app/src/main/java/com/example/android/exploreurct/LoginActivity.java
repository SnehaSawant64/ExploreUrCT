package com.example.android.exploreurct;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login;
    private EditText userName,password;
    private TextView forgotPassword,signup;
    int backButtonCount;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login=findViewById(R.id.loginButton);
        userName=findViewById(R.id.username);
        password=findViewById(R.id.password);
        forgotPassword=findViewById(R.id.forgetPasswordLink);
        signup=findViewById(R.id.createAccountLink);

        userName.setText("");
        password.setText("");
        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signup.setOnClickListener(this);


        locationTrack = new LocationTrack(LoginActivity.this);
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_LONG).show();


        } else {
            locationTrack.showSettingsAlert();
        }
        DataBaseHelper dn=new DataBaseHelper(LoginActivity.this);
        dn.open();
        if(!dn.getLogin().equals("0"))
        {
            Bundle b=new Bundle();
            b.putString("email",dn.getLogin());
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            i.putExtras(b);
            startActivity(i);
        }
        dn.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog alertDialog=new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Information");
            alertDialog.setMessage("\nExplore Your City \n\nAPPLICATION\n\t\t\tThis application is specially designed for " +
                    "Travelers and Explorers, who wants to travel across" +
                    "the city(Pune) and explore the culture or capture the essence of the city." +
                    "\n\n\t\t\tThis application will notify you about the nearest places based on your" +
                    " interest and if you dont know the path dont worry! The application will show you that too.\n\n" +
                    "\t\tEnjoy Exploreing!\n\n\t\tBon Voyage!\n");
            alertDialog.setIcon(R.drawable.ic_info);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.loginButton:
                if (userName.getText().toString().matches(""))
                {
                    Toast.makeText(this,"Enter Username !",Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().matches(""))
                {
                    Toast.makeText(this,"Please Enter Valid Password !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //login
                    DataBaseHelper th=new DataBaseHelper(this);
                    th.open();
                    if(th.checklogin(userName.getText().toString(),password.getText().toString()))
                    {
                        Bundle b=new Bundle();
                        b.putString("email",userName.getText().toString());
                        Toast.makeText(this,"Logged in !  "+th.getNoti() ,Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(this,"Invalid username or password. !",Toast.LENGTH_SHORT).show();
                    }
                    th.close();
                }
                break;
            case R.id.forgetPasswordLink:
                Intent i=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(i);
                break;
            case R.id.createAccountLink:
                i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
                break;

        }
    }
    @Override
    public void onBackPressed(){
        //do nothing
        //finish();
        //System.exit(0);
        if(backButtonCount>=1)
        {
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Press the back button once again to close the application.",Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }




    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }


}
