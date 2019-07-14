package com.example.android.exploreurct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    boolean noti;

    private void toggleNotification(boolean darkTheme) {
        DataBaseHelper db=new DataBaseHelper(SettingsActivity.this);
        db.open();
        String login=db.getLogin();
        noti=db.getNoti();
        if(noti==false)
        {    db.setNoti("True",login+"");
            Log.e("Ceck notification:","**************tab is "+noti);

        }
        else if(noti==true) {
            db.setNoti("False", login+"");
            Log.e("Ceck notification:","**************tab is "+noti);

        }db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        DataBaseHelper db=new DataBaseHelper(SettingsActivity.this);
        db.open();
        noti=db.getNoti();
        db.close();

        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setChecked(noti);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleNotification(isChecked);
            }
        });

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleNotification(isChecked);
            }
        });
    }
}
