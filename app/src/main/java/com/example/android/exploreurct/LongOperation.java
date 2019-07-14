package com.example.android.exploreurct;

import android.os.AsyncTask;
import android.util.Log;


/**
 * Created by DELL on 21-Apr-18.
 */


public class LongOperation extends AsyncTask<Void, Void, String> {
    private String mailto,password,fname,mname,lname;
    public LongOperation(String mailto, String password, String fname, String mname, String lname) {
        this.mailto=mailto;
        this.password=password;
        this.fname=fname;
        this.mname=mname;
        this.lname=lname;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(mname==null)
            mname=" ";
        try {
            GmailSender sender = new GmailSender("cityguidemh12@gmail.com", "cityguide2018");
            sender.sendMail("Forgot Password Link","Login Details for AGC User :- \n \n"+fname+" "+mname+" "+lname+"\nLogin password : "+password+"  .","agcplacementproject@gmail.com",mailto);
            Log.e("mailid : "+mailto,"password : "+password);
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            return "Email Not Sent";
        }
        return "Email Sent";
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("LongOperation",result+"");
    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
