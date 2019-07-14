package com.example.android.exploreurct;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;

import static android.widget.Toast.makeText;

public class AddInterestActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    public CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interest);

        cb1=findViewById(R.id.cb1);
        cb2=findViewById(R.id.cb2);
        cb3=findViewById(R.id.cb3);
        cb4=findViewById(R.id.cb4);
        cb5=findViewById(R.id.cb5);
        cb6=findViewById(R.id.cb6);
        cb7=findViewById(R.id.cb7);
        cb8=findViewById(R.id.cb8);
        cb9=findViewById(R.id.cb9);
        cb10=findViewById(R.id.cb10);

        checksetting();

        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        cb4.setOnCheckedChangeListener(this);
        cb5.setOnCheckedChangeListener(this);
        cb6.setOnCheckedChangeListener(this);
        cb7.setOnCheckedChangeListener(this);
        cb8.setOnCheckedChangeListener(this);
        cb9.setOnCheckedChangeListener(this);
        cb10.setOnCheckedChangeListener(this);
    }

    private void checksetting() {
        DataBaseHelper dt=new DataBaseHelper(AddInterestActivity.this);
        dt.open();
        if(dt.isSetCb("1")==true) cb1.setChecked(true);

        if(dt.isSetCb("2")==true) cb2.setChecked(true);

        if(dt.isSetCb("3")==true) cb3.setChecked(true);

        if(dt.isSetCb("4")==true) cb4.setChecked(true);

        if(dt.isSetCb("5")==true) cb5.setChecked(true);

        if(dt.isSetCb("6")==true) cb6.setChecked(true);

        if(dt.isSetCb("7")==true) cb7.setChecked(true);

        if(dt.isSetCb("8")==true) cb8.setChecked(true);

        if(dt.isSetCb("9")==true) cb9.setChecked(true);

        if(dt.isSetCb("10")==true) cb10.setChecked(true);
        dt.close();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        DataBaseHelper dt=new DataBaseHelper(AddInterestActivity.this);
        dt.open();
        if(cb1.isChecked()){dt.udateInt("1","True");}
        if(!cb1.isChecked()){dt.udateInt("1","False");}

        if(cb2.isChecked()){dt.udateInt("2","True");}
        if(!cb2.isChecked()){dt.udateInt("2","False");}

        if(cb3.isChecked()){dt.udateInt("3","True");}
        if(!cb3.isChecked()){dt.udateInt("3","False");}

        if(cb4.isChecked()){dt.udateInt("4","True");}
        if(!cb4.isChecked()){dt.udateInt("4","False");}

        if(cb5.isChecked()){dt.udateInt("5","True");}
        if(!cb5.isChecked()){dt.udateInt("5","False");}


        if(cb6.isChecked()){dt.udateInt("6","True");}
        if(!cb6.isChecked()){dt.udateInt("6","False");}

        if(cb7.isChecked()){dt.udateInt("7","True");}
        if(!cb7.isChecked()){dt.udateInt("7","False");}

        if(cb8.isChecked()){dt.udateInt("8","True");}
        if(!cb8.isChecked()){dt.udateInt("8","False");}

        if(cb9.isChecked()){dt.udateInt("9","True");}
        if(!cb9.isChecked()){dt.udateInt("9","False");}


        if(cb10.isChecked()){dt.udateInt("10","True");}
        if(!cb10.isChecked()){dt.udateInt("10","False");}

        try {
            dt.printLocation(MainActivity.noticeList,MainActivity.currentlat,MainActivity.currentlang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dt.close();

    }

    @Override
    public void onBackPressed(){

        //do nothing
        //finish();
        //System.exit(0);
        Bundle b=new Bundle();
        b.putString("email",MainActivity.email);
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        i.putExtras(b);
        startActivity(i);

    }

}
