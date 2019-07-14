package com.example.android.exploreurct;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePicker datePicker;
    private Spinner gender;
    private EditText firstName,lastName,phoneNo,emailId,pinCode,password,confirmPassword,adharNo;
    private TextView dateView;
    private Calendar calander;
    private int year,month,day;
    private Button submit,selectDate;
    private CheckBox checkterms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstName=findViewById(R.id.first_name);
        lastName=findViewById(R.id.last_name);
        adharNo=findViewById(R.id.adhar_no);
        phoneNo=findViewById(R.id.phone_no);
        emailId=findViewById(R.id.email_id);
        pinCode=findViewById(R.id.pincode);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirm_password);
        gender=(Spinner)findViewById(R.id.gender);
        checkterms=findViewById(R.id.termsandconditions);

        dateView=findViewById(R.id.dateView);
        calander=Calendar.getInstance();
        year=calander.get(Calendar.YEAR);
        month=calander.get(Calendar.MONTH);
        day=calander.get(Calendar.DAY_OF_MONTH);

        submit=(Button)findViewById(R.id.save_and_proceed);
        selectDate=findViewById(R.id.set_date);

        selectDate.setOnClickListener(this);
        submit.setOnClickListener(this);
    }
    public void setDate(View view){
        if (view.getId()==selectDate.getId())
            showDialog(1);
        else
            Toast.makeText(getApplicationContext(),"ca",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        return new DatePickerDialog(this,myDateListener,year,month,day);
    }

    private DatePickerDialog.OnDateSetListener myDateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //i=year
            //i1=month
            //i2=day
            showDate(i,i1+1,i2);
        }
    };

    boolean validDob(int year,int month,int day)
    {
        SimpleDateFormat sdf;
        Date d;

        sdf=new SimpleDateFormat("dd/MM/yyyy");

        d=new Date();
        sdf.format(d);
        try {
            d=sdf.parse(day +"/"+month+"/"+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.format(d);


        //Calendar c=Calendar.getInstance();
        Date today=new Date();
        Date result = new Date(today.getYear()+1900-18,today.getMonth(),today.getDay());
        d.setYear(d.getYear()+1900);

        sdf.format(result);

        if(d.compareTo(result) < 0 )
        {
            Log.i("Check","true "+result.getDate()+"/"+(result.getMonth()+1)+"/"+result.getYear());
            Log.i("<"," < "+d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getYear());
            return true;
        }
        else
        {
            Log.i("Check","false "+result.getDate()+"/"+result.getMonth()+1+"/"+result.getYear());
            Log.i(">","> "+d.getDate()+"/"+d.getMonth()+1+"/"+d.getYear());
            return false;
        }
    }

    private void showDate(int year,int month,int day){;
        //if(validDob(year,month,day))
        {  dateView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
        }
        //else
        //    Toast.makeText(getApplicationContext(),"Age should be more than 18 years.",Toast.LENGTH_LONG).show();
    }

    boolean checkStrongNess(String input)
    {
        int n = input.length();

        // Checking lower alphabet in string
        boolean hasLower = false, hasUpper = false;
        boolean hasDigit = false, specialChar = false;
        String normalChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ";

        for (int i = 0; i < n; i++) {
            if (Character.isLowerCase(input.charAt(i)))
                hasLower = true;
            if (Character.isUpperCase(input.charAt(i)))
                hasUpper = true;
            if (Character.isDigit(input.charAt(i)))
                hasDigit = true;
            Pattern p= Pattern.compile("[^A-Za-z0-9]");
            Matcher m=p.matcher(input);
            boolean b=m.find();
            if (b==true)
                specialChar = true;
            //     size_t special = input.find_first_not_of(normalChars);
            //     if (special != String::npos)
            //         specialChar = true;
        }
        // Strength of password
        //  cout << "Strength of password:-";
        if (hasLower && hasUpper && hasDigit && specialChar && (n >= 8))
            return true;
        else if ((hasLower || hasUpper) && specialChar && (n >= 6))
            return true;
        else
            return false;
    }

    public static boolean checkValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.save_and_proceed:
                if(firstName.getText().toString().matches("") ||
                        adharNo.getText().toString().matches("")||
                        lastName.getText().toString().matches("") ||
                        pinCode.getText().toString().matches("")  ||
                        password.getText().toString().matches("")  ||
                        confirmPassword.getText().toString().matches("")  ||
                        dateView.getText().toString().matches("birth date")  ||
                        gender.getSelectedItem().toString().matches("Select")
                        ){
                    password.setText("");
                    confirmPassword.setText("");
                    Toast.makeText(this,"All fields are compulsary.",Toast.LENGTH_SHORT).show();
                }
                else if(checkValidEmail(emailId.getText().toString())==false){
                    Toast.makeText(this,"Invalid email address.",Toast.LENGTH_SHORT).show();
                }
                else if(pinCode.length()<6){
                    Toast.makeText(this,"Invalid pincode",Toast.LENGTH_SHORT).show();
                }
                else if(adharNo.length()<10){
                    Toast.makeText(this,"Invalid phone number",Toast.LENGTH_SHORT).show();
                }
                else if(checkStrongNess(password.getText().toString())==false){
                    password.setText("");
                    confirmPassword.setText("");
                    Toast.makeText(this,"Password is not Strong , Password should contain atleast one special character and digit.",Toast.LENGTH_SHORT).show();
                }
                else if(checkterms.isChecked()==false){
                    Toast.makeText(this,"Accept terms and conditions before proceeding",Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().equals(confirmPassword.getText().toString())){
                    boolean diditwork = true;
                    try {
                        Log.e("@try r.id.update", "error");
                        DataBaseHelper entry = new DataBaseHelper(SignUpActivity.this);
                        entry.open();///***********
                        entry.createUserEntry(firstName.getText().toString(),
                                                lastName.getText().toString(),
                                                phoneNo.getText().toString(),
                                                gender.getSelectedItem().toString(),
                                                adharNo.getText().toString(),
                                                emailId.getText().toString(),
                                                dateView.getText().toString(),
                                                pinCode.getText().toString(),
                                                password.getText().toString());
                        entry.close();
                    } catch (Exception e) {
                        Log.e("@catch r.id.update", "error");
                        String error = e.toString();
                        diditwork = false;
                        Dialog d = new Dialog(this);
                        d.setTitle("Noo!");
                        TextView tv = new TextView(this);
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();
                    } finally {
                        if (diditwork) {
                            Toast.makeText(this,"Registration done Successfully..Thank You!",Toast.LENGTH_SHORT).show();
                            //Log.e("@finally r.id.update", "error");
                            //Dialog d = new Dialog(this);
                            //d.setTitle("Hack ya!");
                            // TextView tv = new TextView(this);
                            // tv.setText("Registration Done!!!Successfully Thank You!");
                            // d.setContentView(tv);
                            // d.show();
                        }

                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                }}
                else {
                    password.setText("");
                    confirmPassword.setText("");
                    Toast.makeText(this,"password does not match.",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.set_date:
                showDialog(999);
                break;

        }
    }

    public void onClickterms(View view) {

        AlertDialog alertDialog=new AlertDialog.Builder(SignUpActivity.this).create();
        alertDialog.setTitle("Terms and Conditions");
        alertDialog.setMessage("\n\nAPPLICATION\n\t\t\tThis application is specially designed for " +
                "Travelers and Explorers, who wants to travel across" +
                "the city(Pune) and explore the culture or capture the essence of the city.\n\nApplication need to access your " +
                "current location and need permissions for notification.\n this application also use your goofl maps " +
                "application for directions. " +
                "\n\n\t\t\tThis application will notify you about the nearest places based on your" +
                " interest and show the directions \n");
        alertDialog.setIcon(R.drawable.ic_info);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(),"Ok!",Toast.LENGTH_SHORT).show();
            }
        });
        //alertDialog.setP("Delete",finish());
        alertDialog.show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }*/


}
