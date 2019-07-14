package com.example.android.exploreurct;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText reg;
    public Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        reg=findViewById(R.id.pincode);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (reg.getText().toString().matches("")) {
            Toast.makeText(this, "Enter Register number or Roll number.", Toast.LENGTH_SHORT).show();
        }
        else {
            DataBaseHelper th = new DataBaseHelper(this);
                    th.open();
            String mail = th.getMailByAdhar(reg.getText().toString());
            Toast.makeText(this, "Invalid register number."+mail, Toast.LENGTH_SHORT).show();
            if (mail == null) {
                        Toast.makeText(this, "Invalid register number.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "your credentials will send on registered"+mail+" Mail id.", Toast.LENGTH_SHORT).show();
                String password = th.getPasswordByAdhar(reg.getText().toString());
                String fname=th.getFnameByAdhar(reg.getText().toString());
                String lname=th.getLnameByAdhar(reg.getText().toString());
                sendMailTo(mail, password,fname,null,lname);
                th.close();
            }
        }
    }
    boolean sendMailTo(String mail, String password,String fname,String mname,String lname) {
        try {
            LongOperation l = new LongOperation(mail,password,fname,mname,lname);
            l.execute();  //sends the email in background
            Toast.makeText(this, l.get(), Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e)
        {
            Log.e("SendMail", e.getMessage(), e);
            return false;
        }

    }
}

