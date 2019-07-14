package com.example.android.exploreurct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.location.Location.distanceBetween;
import org.apache.commons.io.IOUtils;

/**
 * Created by Admin on 21/09/2018.
 */

public class DataBaseHelper {
    public static final String C_ID="cid";
    public static final String C_LAT="clatitude";
    public static final String C_LONG="clongitude";

    public static final String NOTI_ID="notify";
    public static final String NOTI_BOOL="notifystatus";
    public static final String NOTI_LOGIN="logeduser";

    public static final String USER_ID="uid";
    public static final String USER_FNAME="ufname";
    public static final String USER_LNAME="ulname";
    public static final String USER_PHONE_NO="uphoneno";
    public static final String USER_GENDER="ugender";
    public static final String USER_ADDHAR_NO="adharno";
    public static final String USER_EMAIL_ID="umailid";
    public static final String USER_DOB="udob";
    public static final String USER_PINCODE="upincode";
    public static final String USER_PASSWORD="upassword";

    public static final String LOC_ID="lid";
    public static final String LOC_NAME="lname";
    public static final String LOC_CAT="lcat";
    public static final String LOC_LAT="latitude";
    public static final String LOC_LANG="longitude";
    public static final String LOC_URL="url";
    public static final String LOC_DISC="discription";
    public static final String LOC_VISIT_FLAG="visitflag";

    private static final String INTEREST_ID="iid";
    private static final String INTEREST_CAT="icategory";
    private static final String INTEREST_BOOL="isinterested";

    public static final int DATABASE_VERSION=13;
    public static final String DATABASE_NAME="CTEXPLORE";
    public static final String DATABASE_UTABLE="user";
    public static final String DATABASE_ITABLE="interest";
    public static final String DATABASE_LTABLE="location";
    public static final String DATABASE_NTABLE="notification";
    public static final String DATABASE_CTABLE="clocation";;

    public DBHelper ourHelper;
    public final Context ourContext;
    public SQLiteDatabase ourDatabase;
    public static SQLiteDatabase odb;

    public DataBaseHelper(Context c){ourContext=c;}
    public DataBaseHelper open()throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){ourHelper.close();}

    public long createUserEntry(String fn, String ln ,String reg ,String dept,String an,String mail,String phone,String addr,String password) {
        ContentValues cv=new ContentValues();
        cv.put(USER_FNAME,fn);
        cv.put(USER_LNAME,ln);
        cv.put(USER_PHONE_NO,reg);
        cv.put(USER_GENDER,dept);
        cv.put(USER_ADDHAR_NO,an);
        cv.put(USER_EMAIL_ID,mail);
        cv.put(USER_DOB,phone);
        cv.put(USER_PINCODE,addr);
        cv.put(USER_PASSWORD,password);
        return ourDatabase.insert(DATABASE_UTABLE,null,cv);
        //inserts n and s into DATABASE_TABLE
    }

    public String getFnameByAdhar(String reg)
    {
        String[] columns=new String[]{USER_ID,USER_FNAME,USER_LNAME,USER_PHONE_NO,USER_GENDER,USER_ADDHAR_NO,USER_EMAIL_ID,USER_DOB,USER_PINCODE,USER_PASSWORD};
        Cursor c=ourDatabase.query(DATABASE_UTABLE,columns,USER_ADDHAR_NO+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(1);
            //String scale=c.getString(2);
            return name;
        }
        return null;
    }

    public String getLname(String reg)
    {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};
        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,"lid =" +reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(1);
            return name;
        }
        return null;
    }

    public String getLCat(String reg)
    {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};
        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,LOC_ID+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(2);
            return name;
        }
        return null;
    }

    public String getLLat(String reg)
    {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};
        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,LOC_ID+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(3);
            return name;
        }
        return null;
    }




    public String getLLang(String reg)
    {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};
        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,LOC_ID+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(4);
            return name;
        }
        return null;
    }

    public String getLUrl(String reg)
    {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};
        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,LOC_ID+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(5);
            return name;
        }
        return null;
    }

    public String getLDisc(String reg)
    {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};
        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,LOC_ID+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(6);
            return name;
        }
        return null;
    }

    public boolean markAsVisited(String reg){
        ContentValues cv=new ContentValues();

        cv.put(LOC_NAME,getLname(reg));
        cv.put(LOC_CAT,getLCat(reg));
        cv.put(LOC_LAT,getLLat(reg));
        cv.put(LOC_LANG,getLLang(reg));
        cv.put(LOC_URL,getLUrl(reg));
        cv.put(LOC_DISC,getLDisc(reg));
        cv.put(LOC_VISIT_FLAG,"True");
        //  return ourDatabase.update(DATABASE_TTABLE,cv,"where trno = "+reg,null);
        if(ourDatabase.update(DATABASE_LTABLE, cv,"lid = "+reg,null)>0)
            return true;
        else
            return false;
    }

    public boolean markAsUnVisited(String reg){
        ContentValues cv=new ContentValues();

        cv.put(LOC_NAME,getLname(reg));
        cv.put(LOC_CAT,getLCat(reg));
        cv.put(LOC_LAT,getLLat(reg));
        cv.put(LOC_LANG,getLLang(reg));
        cv.put(LOC_URL,getLUrl(reg));
        cv.put(LOC_DISC,getLDisc(reg));
        cv.put(LOC_VISIT_FLAG,"False");
        //  return ourDatabase.update(DATABASE_TTABLE,cv,"where trno = "+reg,null);
        if(ourDatabase.update(DATABASE_LTABLE, cv,"lid = "+reg,null)>0)
            return true;
        else
            return false;
    }


    public String getLnameByAdhar(String reg)
    {
        String[] columns=new String[]{USER_ID,USER_FNAME,USER_LNAME,USER_PHONE_NO,USER_GENDER,USER_ADDHAR_NO,USER_EMAIL_ID,USER_DOB,USER_PINCODE,USER_PASSWORD};
        Cursor c=ourDatabase.query(DATABASE_UTABLE,columns,USER_ADDHAR_NO+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(2);
            //String scale=c.getString(2);
            return name;
        }
        return null;
    }

    public String getPasswordByAdhar(String reg)
    {
        String[] columns=new String[]{USER_ID,USER_FNAME,USER_LNAME,USER_PHONE_NO,USER_GENDER,USER_ADDHAR_NO,USER_EMAIL_ID,USER_DOB,USER_PINCODE,USER_PASSWORD};
        Cursor c=ourDatabase.query(DATABASE_UTABLE,columns,USER_ADDHAR_NO+" = "+reg,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(9);
            //String scale=c.getString(2);
            return name;
        }
        return null;
    }

    public String getMailByAdhar(String reg)
    {
        String[] columns=new String[]{USER_ID,USER_FNAME,USER_LNAME,USER_PHONE_NO,USER_GENDER,USER_ADDHAR_NO,USER_EMAIL_ID,USER_DOB,USER_PINCODE,USER_PASSWORD};
        Cursor c=ourDatabase.query(DATABASE_UTABLE,columns,USER_ADDHAR_NO+" = "+reg,null,null,null,null);
        Log.e("checkwrong entrycoursor","**********mail id"+c);
        int ipass = c.getColumnIndex(USER_EMAIL_ID);
        String name=null;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            //c.moveToFirst();
            Log.e("entrycoursor in if ","**********mail id  :"+c);
             name=c.getString(6);
            //String scale=c.getString(2);
            Log.e("check wrong entry","**********mail id"+name);
            if(name!=null)
            return name;
        }
        return null;
    }
    public boolean checklogin(String s, String s1) {
        String[] columns=new String[]{USER_EMAIL_ID,USER_PASSWORD};

        Cursor c=ourDatabase.query(DATABASE_UTABLE,columns,null,null,null,null,null);
        int ireg=c.getColumnIndex(USER_EMAIL_ID);
        int ipass=c.getColumnIndex(USER_PASSWORD);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
            if(c.getString(ireg).equals(s) && c.getString(ipass).equals(s1))
                return true;
        }

        return false;
    }


    public boolean isSetCb(String i) {
        String[] columns=new String[]{INTEREST_ID,INTEREST_CAT,INTEREST_BOOL};

        Cursor c=ourDatabase.query(DATABASE_ITABLE,columns,null,null,null,null,null);
        int ireg=c.getColumnIndex(INTEREST_ID);
        int ipass=c.getColumnIndex(INTEREST_BOOL);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
            if(c.getString(ireg).equals(i) && c.getString(ipass).equals("True"))
                return true;
        }

        return false;
    }

    public long udateInt(String s, String aTrue) {
        ContentValues cv=new ContentValues();
        cv.put(INTEREST_BOOL,aTrue);
        //  return ourDatabase.update(DATABASE_TTABLE,cv,"where trno = "+reg,null);
        return ourDatabase.update(DATABASE_ITABLE, cv,"iid = "+s,null);
    }

    public  long setNoti(String aTrue,String login) {
        ContentValues cv=new ContentValues();
        cv.put(NOTI_BOOL,aTrue);
        cv.put(NOTI_LOGIN,login);
        //  return ourDatabase.update(DATABASE_TTABLE,cv,"where trno = "+reg,null);
        return ourDatabase.update(DATABASE_NTABLE, cv,NOTI_ID+" = 1",null);
    }

    public  Boolean getNoti( ) {
        String[] columns=new String[]{NOTI_ID,NOTI_BOOL};
        Cursor c=ourDatabase.query(DATABASE_NTABLE,columns,NOTI_ID+" = 1",null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(1);
            if(name.equals("True"))
                return true;
            else
                return false;
        }
        return false;
    }

    public  long setLogin(String noti,String login) {
        ContentValues cv=new ContentValues();
        cv.put(NOTI_BOOL,noti);
        cv.put(NOTI_LOGIN,login);
        //  return ourDatabase.update(DATABASE_TTABLE,cv,"where trno = "+reg,null);
        return ourDatabase.update(DATABASE_NTABLE, cv,NOTI_ID+" = 1",null);
    }

    public  long setClocation(String lat,String longi) {
        ContentValues cv=new ContentValues();
        cv.put(C_LAT,lat);
        cv.put(C_LONG,longi);
        //  return ourDatabase.update(DATABASE_TTABLE,cv,"where trno = "+reg,null);
        return ourDatabase.update(DATABASE_CTABLE, cv,C_ID+" = 1",null);
    }

    public  String getLat( ) {
        String[] columns=new String[]{C_ID,C_LAT,C_LONG};
        Cursor c=ourDatabase.query(DATABASE_CTABLE,columns,C_ID+" = 1",null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(1);
            return name;
        }
        return null;
    }

    public  String getLang( ) {
        String[] columns=new String[]{C_ID,C_LAT,C_LONG};
        Cursor c=ourDatabase.query(DATABASE_CTABLE,columns,C_ID+" = 1",null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(2);
            return name;
        }
        return null;
    }

    public  String getLogin( ) {
        String[] columns=new String[]{NOTI_ID,NOTI_BOOL,NOTI_LOGIN};
        Cursor c=ourDatabase.query(DATABASE_NTABLE,columns,NOTI_ID+" = 1",null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(2);
           return name;
        }
        return null;
    }

    void getUpdates(List<LLocation> noticeList,Double currentlat,Double currentLang ) throws ParseException {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};
        noticeList.clear();
        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,null,null,null,null,null);
        String result="";
        int id=c.getColumnIndex(LOC_ID);
        int name=c.getColumnIndex(LOC_NAME);
        int cat=c.getColumnIndex(LOC_CAT);
        int lat=c.getColumnIndex(LOC_LAT);
        int lang=c.getColumnIndex(LOC_LANG);
        int url=c.getColumnIndex(LOC_URL);
        int disc=c.getColumnIndex(LOC_DISC);
        int vf=c.getColumnIndex(LOC_VISIT_FLAG);

        //  Double currentlat=MainActivity.lat;
        //   Double currentLang=MainActivity.lang;
        Log.e("check current lat lang=",currentlat+",,,,,,,,,"+currentLang);
        float[] results=new float[1];

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {

            Double storedlat= Double.parseDouble(c.getString(lat));
            Double storedLang = Double.parseDouble(c.getString(lang));
            // String distanceroad=getDistance(currentlat,storedlat,currentLang,storedLang);
            // Log.e("distance by road==",distanceroad+"*****************");
            Location.distanceBetween(storedlat,storedLang,currentlat,currentLang,results);
            float distanceInMeters = results[0];
            boolean within500m=distanceInMeters < 1000;
            if(c.getString(vf).equals("False") && within500m)
            {

                //  result=result+c.getString(iRow)+"\t"+c.getString(itfname)+" "+c.getString(itlname)+"\t"+c.getString(itemail)+"\t"+c.getString(itphone)+"**"+c.getString(itid)+"** \n";
                LLocation loc = new LLocation(c.getString(id),c.getString(name),c.getString(cat),c.getString(lat),c.getString(lang),c.getString(url),c.getString(disc),c.getString(vf),distanceInMeters+"");
                noticeList.add(loc);
            }
        }

    }



    void printLocation(List<LLocation> noticeList,Double currentlat,Double currentLang ) throws ParseException {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};

        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,null,null,null,null,null);
        String result="";
        int id=c.getColumnIndex(LOC_ID);
        int name=c.getColumnIndex(LOC_NAME);
        int cat=c.getColumnIndex(LOC_CAT);
        int lat=c.getColumnIndex(LOC_LAT);
        int lang=c.getColumnIndex(LOC_LANG);
        int url=c.getColumnIndex(LOC_URL);
        int disc=c.getColumnIndex(LOC_DISC);
        int vf=c.getColumnIndex(LOC_VISIT_FLAG);

     //  Double currentlat=MainActivity.lat;
     //   Double currentLang=MainActivity.lang;
        Log.e("check current lat lang=",currentlat+",,,,,,,,,"+currentLang);
        float[] results=new float[1];

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {

            Double storedlat= Double.parseDouble(c.getString(lat));
            Double storedLang = Double.parseDouble(c.getString(lang));
           // String distanceroad=getDistance(currentlat,storedlat,currentLang,storedLang);
           // Log.e("distance by road==",distanceroad+"*****************");
            Log.e("check stored lat lang==",storedlat+",,,,,,,,,"+storedLang);
            Location.distanceBetween(storedlat,storedLang,currentlat,currentLang,results);
            float distanceInMeters = results[0];
            boolean within500m=distanceInMeters < 1000;

            Log.e("check distance======",distanceInMeters+"meters ");

            if(c.getString(vf).equals("False") && within500m && intresed(c.getString(cat)))
            {

                //  result=result+c.getString(iRow)+"\t"+c.getString(itfname)+" "+c.getString(itlname)+"\t"+c.getString(itemail)+"\t"+c.getString(itphone)+"**"+c.getString(itid)+"** \n";
                LLocation loc = new LLocation(c.getString(id),c.getString(name),c.getString(cat),c.getString(lat),c.getString(lang),c.getString(url),c.getString(disc),c.getString(vf),distanceInMeters+"");
                noticeList.add(loc);
            }
        }

    }

    boolean intresed(String cat)
    {
        if(isSetCb(cat))
        return true;
        else
        return false;
    }

    void printVisited(List<LLocation> noticeList) throws ParseException {
        String[] columns=new String[]{LOC_ID,LOC_NAME,LOC_CAT,LOC_LAT,LOC_LANG,LOC_URL,LOC_DISC,LOC_VISIT_FLAG};

        Cursor c=ourDatabase.query(DATABASE_LTABLE,columns,null,null,null,null,null);
        String result="";
        int id=c.getColumnIndex(LOC_ID);
        int name=c.getColumnIndex(LOC_NAME);
        int cat=c.getColumnIndex(LOC_CAT);
        int lat=c.getColumnIndex(LOC_LAT);
        int lang=c.getColumnIndex(LOC_LANG);
        int url=c.getColumnIndex(LOC_URL);
        int disc=c.getColumnIndex(LOC_DISC);
        int vf=c.getColumnIndex(LOC_VISIT_FLAG);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {

              if(c.getString(vf).equals("True"))
             {
                LLocation loc = new LLocation(c.getString(id),c.getString(name),c.getString(cat),c.getString(lat),c.getString(lang),c.getString(url),c.getString(disc),c.getString(vf),"");
                noticeList.add(loc);
             }

        }

    }



    public String getFname(String email) {
        String[] columns=new String[]{USER_ID,USER_FNAME,USER_LNAME,USER_PHONE_NO,USER_GENDER,USER_ADDHAR_NO,USER_EMAIL_ID,USER_DOB,USER_PINCODE,USER_PASSWORD};
        Cursor c=ourDatabase.query(DATABASE_UTABLE,columns,USER_EMAIL_ID+" = '"+email+"'" ,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(1);
            //String scale=c.getString(2);
            return name;
        }
        return null;
    }

    public static class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String admreg="1111";
            String admpassword="admin123";

            db.execSQL("CREATE TABLE "+DATABASE_UTABLE+"("+
                    USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    USER_FNAME+" TEXT NOT NULL , "+
                    USER_LNAME+" TEXT NOT NULL , "+
                    USER_PHONE_NO+" TEXT NOT NULL , "+
                    USER_GENDER+" TEXT NOT NULL , "+
                    USER_ADDHAR_NO+" TEXT NOT NULL , "+
                    USER_EMAIL_ID+" TEXT NOT NULL , "+
                    USER_DOB+" TEXT NOT NULL , "+
                    USER_PINCODE+" TEXT NOT NULL , "+
                    USER_PASSWORD+" TEXT NOT NULL ); ");

            db.execSQL("CREATE TABLE "+DATABASE_ITABLE+"("+
                    INTEREST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    INTEREST_CAT+" TEXT NOT NULL , "+
                    INTEREST_BOOL+" TEXT NOT NULL ); ");

            db.execSQL("CREATE TABLE "+DATABASE_LTABLE+"("+
                    LOC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    LOC_NAME+" TEXT NOT NULL , "+
                    LOC_CAT+" TEXT NOT NULL , "+
                    LOC_LAT+" TEXT NOT NULL , "+
                    LOC_LANG+" TEXT NOT NULL , "+
                    LOC_URL+" TEXT NOT NULL , "+
                    LOC_DISC+" TEXT NOT NULL , "+
                    LOC_VISIT_FLAG+" TEXT NOT NULL ); ");

            db.execSQL("CREATE TABLE "+DATABASE_NTABLE+"("+
                    NOTI_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    NOTI_BOOL+" TEXT NOT NULL , "+
                    NOTI_LOGIN+" TEXT NOT NULL ); ");

            db.execSQL("CREATE TABLE "+DATABASE_CTABLE+"("+
                    C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    C_LAT+" TEXT NOT NULL , "+
                    C_LONG+" TEXT NOT NULL ); ");

            insertLocation(db);

//            cv2.put(LOC_LAT,"18.512948");
//            cv2.put(LOC_LANG,"73.840812");
            ContentValues cv=new ContentValues();
            cv.put(C_LAT,"18.512948");
            cv.put(C_LONG,"73.840812");
            db.insert(DATABASE_CTABLE,null,cv);

             cv=new ContentValues();
            cv.put(NOTI_BOOL,"True");
            cv.put(NOTI_LOGIN,"0");
            db.insert(DATABASE_NTABLE,null,cv);


            ContentValues cvu=new ContentValues();
            cvu.put(USER_FNAME,"trial");
            cvu.put(USER_LNAME,"trials");
            cvu.put(USER_PHONE_NO,"8877667766");
            cvu.put(USER_GENDER,"female");
            cvu.put(USER_ADDHAR_NO,"667766776677");
            cvu.put(USER_EMAIL_ID,"scsawant1997@gmail.com");
            cvu.put(USER_DOB,"06/04/1997");
            cvu.put(USER_PINCODE,"411008");
            cvu.put(USER_PASSWORD,"abc");
            db.insert(DATABASE_UTABLE,null,cvu);

            cv=new ContentValues();
            cv.put(INTEREST_CAT,"Food");
            cv.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv);

            ContentValues cv1=new ContentValues();
            cv1.put(INTEREST_CAT,"Museums");
            cv1.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv1);

            ContentValues cv2=new ContentValues();
            cv2.put(INTEREST_CAT,"Ancient Buildings");
            cv2.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv2);
            
            ContentValues cv3=new ContentValues();
            cv3.put(INTEREST_CAT,"Historical places");
            cv3.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv3);

            ContentValues cv4=new ContentValues();
            cv4.put(INTEREST_CAT,"Religious places");
            cv4.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv4);

            ContentValues cv5=new ContentValues();
            cv5.put(INTEREST_CAT,"Universitied");
            cv5.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv5);

            ContentValues cv6=new ContentValues();
            cv6.put(INTEREST_CAT,"Shopping malls");
            cv6.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv6);

            ContentValues cv7=new ContentValues();
            cv7.put(INTEREST_CAT,"Gardens");
            cv7.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv7);

            ContentValues cv8=new ContentValues();
            cv8.put(INTEREST_CAT,"Forts");
            cv8.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv8);

            ContentValues cv9=new ContentValues();
            cv9.put(INTEREST_CAT,"Hotels");
            cv9.put(INTEREST_BOOL,"False");
            db.insert(DATABASE_ITABLE,null,cv9);
        }

        private void insertLocation(SQLiteDatabase db) {

            ContentValues cv1=new ContentValues();
            cv1.put(LOC_NAME,"Chatushrungi");
            cv1.put(LOC_CAT,"5");
            cv1.put(LOC_LAT,"18.5389894");
            cv1.put(LOC_LANG,"73.8278481");
            cv1.put(LOC_URL,"www.chaturshrungitemple.com");
            cv1.put(LOC_DISC,"Chatushrungi temple is a beautiful temple, located on a hill " +
                    "surrounded by lot of trees in centeral pune,you can visit if you have 30 min extra in your schedule" +
                    "and experience the peace in cental city ");
            cv1.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv1);

            ContentValues cv2=new ContentValues();
            cv2.put(LOC_NAME,"Sawarkar Smarak");
            cv2.put(LOC_CAT,"4");
            cv2.put(LOC_LAT,"18.512948");
            cv2.put(LOC_LANG,"73.840812");
            cv2.put(LOC_URL,"www.savarkarsmarak.com");
            cv2.put(LOC_DISC,"Chatushrungi temple is a beautiful temple, located on a hill " +
                    "surrounded by lot of trees in centeral pune,you can visit if you have 30 min extra in your schedule" +
                    "and experience the peace in cental city ");
            cv2.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv2);

            cv2=new ContentValues();
            cv2.put(LOC_NAME,"Central Mall(Deccan)");
            cv2.put(LOC_CAT,"7");
            cv2.put(LOC_LAT,"18.5108126");
            cv2.put(LOC_LANG,"73.8386433");
            cv2.put(LOC_URL,"www.punecentral.com");
            cv2.put(LOC_DISC,"One of the best shopping mall in pune, " +
                    "near garware college. also have mcDonald at the ground floore." +
                    "Enjoy your shopping.");
            cv2.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv2);

            cv2=new ContentValues();
            cv2.put(LOC_NAME,"Bipin Snacks");
            cv2.put(LOC_CAT,"1");
            cv2.put(LOC_LAT,"18.5130298");
            cv2.put(LOC_LANG,"73.8399545");
            cv2.put(LOC_URL,"");
            cv2.put(LOC_DISC,"One of the best street food centre in pune, " +
                    "near garware college. Wadapav served here is teasty as well as budget friendly ." +
                    "Bon Apetit!.");
            cv2.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv2);

            ContentValues cv3=new ContentValues();
            cv3.put(LOC_NAME,"Chatrapati Sambhaji Udhyan");
            cv3.put(LOC_CAT,"8");
            cv3.put(LOC_LAT,"18.520273");
            cv3.put(LOC_LANG,"73.846405");
            cv3.put(LOC_URL,"www.ChatrapatiSambhajiUdyan.com");
            cv3.put(LOC_DISC,"This garden is good place for all age.Life can be happy on busy roads." +
                    "Cycling on cycle path is yet another surprising experience.");
            cv3.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv3);

            ContentValues cv4=new ContentValues();
            cv4.put(LOC_NAME,"PuneOkayamaFriendshipGarden");
            cv4.put(LOC_CAT,"8");
            cv4.put(LOC_LAT,"18.491860");
            cv4.put(LOC_LANG,"73.836884");
            cv4.put(LOC_URL,"www.Pune Okayama Friendship Garden.com");
            cv4.put(LOC_DISC,"Pune Okayama Friendship Garden is a  Peaceful and quiet. " +
                            "The garden is well maintained and contains natural flow of water from canal which"+
                            "is been spread across the garden.");
                    cv4.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv4);

            ContentValues cv5=new ContentValues();
            cv5.put(LOC_NAME,"Mughal Garden");
            cv5.put(LOC_CAT,"8");
            cv5.put(LOC_LAT,"18.4933723");
            cv5.put(LOC_LANG,"73.8355443");
            cv5.put(LOC_URL,"www.MughalGarden.com");
            cv5.put(LOC_DISC,"Nice & quite place good for photography, family fun or with friends." +
                            "The garden is well maintained and contains natural flow of water from canal."+
                            "Some of the typical features include pools, fountains and canals inside the gardens.");
                    cv5.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv5);

            ContentValues cv6=new ContentValues();
            cv6.put(LOC_NAME,"Butterfly Park");
            cv6.put(LOC_CAT,"8");
            cv6.put(LOC_LAT,"18.4925317");
            cv6.put(LOC_LANG,"73.8277695");
            cv6.put(LOC_URL,"www.ButterflyPark.com");
            cv6.put(LOC_DISC,"Butterfly Park is a truly a beautiful place." +
                    "The garden is small yet amazing. You may see lots and varieties of butterflies"+
                    "playing around here and still it will soothe you.");
            cv6.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv6);

            ContentValues cv7=new ContentValues();
            cv7.put(LOC_NAME,"Bagul Udyan");
            cv7.put(LOC_CAT,"8");
            cv7.put(LOC_LAT,"18.4910368");
            cv7.put(LOC_LANG,"73.8487073");
            cv7.put(LOC_URL,"www.BagulUdyan.com");
            cv7.put(LOC_DISC,"Bagul Udyan is One of the best place to visit. " +
                    "Nice place to visit with friends, family. 3d studio is superb."+
                    "Music fountain also very attractive.");
            cv7.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv7);

            ContentValues cv8=new ContentValues();
            cv8.put(LOC_NAME,"Empress Botanical Garden");
            cv8.put(LOC_CAT,"8");
            cv8.put(LOC_LAT,"18.5122021");
            cv8.put(LOC_LANG,"73.8956093");
            cv8.put(LOC_URL,"www.EmpressBotanicalGarden.com");
            cv8.put(LOC_DISC,"Empress Botanical Garden is a good place to relax your self." +
                    "Sprawling botanical garden specializing in regional plants, with rare trees," +
                    " flowers & a kids' area.");
            cv8.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv8);

            ContentValues cv9=new ContentValues();
            cv9.put(LOC_NAME,"Saras Bag");
            cv9.put(LOC_CAT,"8");
            cv9.put(LOC_LAT,"18.5008678");
            cv9.put(LOC_LANG,"73.8513955");
            cv9.put(LOC_URL,"www.SarasBag.com");
            cv9.put(LOC_DISC,"Saras Bag is a great place to go for morning walks." +
                    " Open air gym is available in the garden for warm up excercise."+
                    "Very nice place, excellent for spending quality time.");
            cv9.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv9);

            ContentValues cv10=new ContentValues();
            cv10.put(LOC_NAME,"J J Garden");
            cv10.put(LOC_CAT,"8");
            cv10.put(LOC_LAT,"18.4925897");
            cv10.put(LOC_LANG,"73.8015046");
            cv10.put(LOC_URL,"www.JJGarden.com");
            cv10.put(LOC_DISC,"J J Garden is a  Peaceful and quiet. " +
                    "Bustling neighborhood square known for its numerous food vendors, cafes & clothing stores."+
                    "Good place for the children and early morning joggers ");
            cv10.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv10);


            ContentValues cv11=new ContentValues();
            cv11.put(LOC_NAME,"RajaDinkarKelkarMuseum");
            cv11.put(LOC_CAT,"2");
            cv11.put(LOC_LAT,"18.5410936");
            cv11.put(LOC_LANG,"73.8095261");
            cv11.put(LOC_URL,"www.Raja Dinkar Kelkar Museum.com");
            cv11.put(LOC_DISC,"Raja Dinkar Kelkar Museum is a Wonderful museum,located in Shukrawar Peth. " +
                    "Museum housing one man's varied collection of 20,000+ Indian artifacts ranging from toys to artwork" +
                    "and  has artifacts from all parts of India ");
            cv11.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv11);

            ContentValues cv12=new ContentValues();
            cv12.put(LOC_NAME,"AgaKhanPalace");
            cv12.put(LOC_CAT,"2");
            cv12.put(LOC_LAT,"18.5410936");
            cv12.put(LOC_LANG,"73.8095261");
            cv12.put(LOC_URL,"www.Aga Khan Palace.com");
            cv12.put(LOC_DISC,"Aga Khan Palace is a historical palace,located near Ramvadi" +
                    "Palace built in 19th century by Aga Khan & the site of Gandhi's 2-year imprisonment in 1940s." +
                    "It’s one of the memorable historical places");
            cv12.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv12);

            ContentValues cv13=new ContentValues();
            cv13.put(LOC_NAME,"Mahatma Phule Museum");
            cv13.put(LOC_CAT,"2");
            cv13.put(LOC_LAT,"18.5410936");
            cv13.put(LOC_LANG,"73.8095261");
            cv13.put(LOC_URL,"www.MahatmaPhuleMuseum.com");
            cv13.put(LOC_DISC,"Mahatma Phule Museum is a small museum located in heart of city." +
                    "Museum founded in 1875 featuring historical objects such as handicrafts, weapons & taxidermy.");
            cv13.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv13);

            ContentValues cv14=new ContentValues();
            cv14.put(LOC_NAME,"Joshi's Museum of Miniature Railways");
            cv14.put(LOC_CAT,"2");
            cv14.put(LOC_LAT,"18.5410936");
            cv14.put(LOC_LANG,"73.8095261");
            cv14.put(LOC_URL,"www.JoshiMuseumofMiniatureRailways.com");
            cv14.put(LOC_DISC,"Joshi's Museum of Miniature Railways is opened in 1998" +
                    "This museum presents demonstrations of miniature trains on a model railway system." +
                    "The working miniature trains and wagons are mesmerizing.");
            cv14.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv14);

            ContentValues cv15=new ContentValues();
            cv15.put(LOC_NAME,"Blades of Glory Cricket Museum");
            cv15.put(LOC_CAT,"2");
            cv15.put(LOC_LAT,"18.5410936");
            cv15.put(LOC_LANG,"73.8095261");
            cv15.put(LOC_URL,"www.BladesofGloryCricketMuseum.com");
            cv15.put(LOC_DISC,"Blades of Glory Cricket Museum is a Sports museum "+
                    "devoted to the history of cricket, with signed gear & memorabilia from iconic players." +
                    "Even if cricket isn't your game , it is worth visiting once");
            cv15.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv15);

            ContentValues cv16=new ContentValues();
            cv16.put(LOC_NAME,"Lokmanya Tilak Museum");
            cv16.put(LOC_CAT,"2");
            cv16.put(LOC_LAT,"18.5158061");
            cv16.put(LOC_LANG,"73.8467183");
            cv16.put(LOC_URL,"www.LokmanyaTilakMuseum.com");
            cv16.put(LOC_DISC,"Lokmanya Tilak Museum is well maintained."+
                    "If you have interest in the Indian history and want to better understand how things "+
                    "unfolded in that era this is the place you should not miss.");
            cv16.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv16);

            ContentValues cv17=new ContentValues();
            cv17.put(LOC_NAME,"Maratha History Museum");
            cv17.put(LOC_CAT,"2");
            cv17.put(LOC_LAT,"18.5410936");
            cv17.put(LOC_LANG,"73.8095261");
            cv17.put(LOC_URL,"www.MarathaHistoryMuseum.com");
            cv17.put(LOC_DISC,"Maratha History Museum is One of the best place to visit. " +
                    "Nice place to visit with friends, family to know about Marathi history.");
            cv17.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv17);

            ContentValues cv18=new ContentValues();
            cv18.put(LOC_NAME,"Maharshi Karve Museum");
            cv18.put(LOC_CAT,"2");
            cv18.put(LOC_LAT,"18.5410936");
            cv18.put(LOC_LANG,"73.8095261");
            cv18.put(LOC_URL,"www.MaharshiKarveMuseum.com");
            cv18.put(LOC_DISC,"Maharshi Karve Museum is a very clam and nice place." +
                    "It's a spiritual place in the m k s s s sanstha ");
            cv18.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv18);

            ContentValues cv19=new ContentValues();
            cv19.put(LOC_NAME,"Erandwane Fire Museum");
            cv19.put(LOC_CAT,"2");
            cv19.put(LOC_LAT,"18.5066219");
            cv19.put(LOC_LANG,"73.8307877");
            cv19.put(LOC_URL,"www.ErandwaneFireMuseum.com");
            cv19.put(LOC_DISC,"Erandwane Fire Museum is a  one of the oldest and the only fire museum in Maharashtra." +
                    " There are fire  water monitors from years ago to recently upgraded trends.");
            cv19.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv19);

            ContentValues cv20=new ContentValues();
            cv20.put(LOC_NAME,"Yashlaxmi Numismatic Museum");
            cv20.put(LOC_CAT,"2");
            cv20.put(LOC_LAT,"18.4920494");
            cv20.put(LOC_LANG,"73.8214551");
            cv20.put(LOC_URL,"www.YashlaxmiNumismaticMuseum.com");
            cv20.put(LOC_DISC,"Yashlaxmi Numismatic Museum is a great place."+
                    "All over the world currency are there. Its very informative place" +
                    "Most wonderful,Largest coin and note collection in Pune.");
            cv20.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv20);








            ContentValues cv21=new ContentValues();
            cv21.put(LOC_NAME,"Mahadji Shinde Chhatri");
            cv21.put(LOC_CAT,"4");
            cv21.put(LOC_LAT,"18.5413933");
            cv21.put(LOC_LANG,"73.8095259");
            cv21.put(LOC_URL,"www.MahadjiShindeChhatri.com");
            cv21.put(LOC_DISC,"Mahadji Shinde Chhatri is famous for its architectural design," +
                    " Architecture is so expressive and whole place is quite peaceful"+
                    " having historical background of more than 200 years");
            cv21.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv21);

            ContentValues cv22=new ContentValues();
            cv22.put(LOC_NAME,"Lal Mahal");
            cv22.put(LOC_CAT,"4");
            cv22.put(LOC_LAT,"18.5413933");
            cv22.put(LOC_LANG,"73.8095259");
            cv22.put(LOC_URL,"www.LalMahal.com");
            cv22.put(LOC_DISC,"Lal Mahal is a Modern reconstruction of the Red Palace." +
                    " It is a witness of Chatrapati Shivaji Maharaj's Staying place during their young age ."+
                    " It's an construction with awesome interiors and today also stands strong as it was .");
            cv22.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv22);

            ContentValues cv23=new ContentValues();
            cv23.put(LOC_NAME,"National War Memorial Southern Command");
            cv23.put(LOC_CAT,"4");
            cv23.put(LOC_LAT,"18.523145");
            cv23.put(LOC_LANG,"73.8874876");
            cv23.put(LOC_URL,"www.NationalWarMemorialSouthernCommand.com");
            cv23.put(LOC_DISC,"National War Memorial Southern Command is the best national war memorial in India." +
                    "A respectful tribute to our national defence heroes who fought bravely to keep the civilians safe.");
            cv23.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv23);

            ContentValues cv24=new ContentValues();
            cv24.put(LOC_NAME,"Nana Wada");
            cv24.put(LOC_CAT,"4");
            cv24.put(LOC_LAT,"18.5182563");
            cv24.put(LOC_LANG,"73.8537751");
            cv24.put(LOC_URL,"www.NanaWada.com");
            cv24.put(LOC_DISC,"Nana Wada is a great historical place near Shaniwar Wada" +
                    "Famous courtier of peshwa, Nana fadanwis used to stay.");
            cv24.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv24);

            ContentValues cv25=new ContentValues();
            cv25.put(LOC_NAME,"Vishram Baug Wada");
            cv25.put(LOC_CAT,"4");
            cv25.put(LOC_LAT,"18.5413933");
            cv25.put(LOC_LANG,"73.8095259");
            cv25.put(LOC_URL,"www.VishramBaugWada.com");
            cv25.put(LOC_DISC," Vishram Baug Wada is the top historical place to Visit in Pune." +
                    "Pune's heritage, and old memories."+
                    "Wada concept is what you can explore here.You can also get slice of history here");
            cv25.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv25);

            ContentValues cv26=new ContentValues();
            cv26.put(LOC_NAME,"Chatrapati Shivaji Maharaj Statue");
            cv26.put(LOC_CAT,"4");
            cv26.put(LOC_LAT,"18.5696894");
            cv26.put(LOC_LANG,"73.8452374");
            cv26.put(LOC_URL,"www.ChatrapatiShivajiMaharajStatue.com");
            cv26.put(LOC_DISC,"Chatrapati Shivaji Maharaj Statue Landmark in Khadaki."+
                    " Located centrally with all different facilities around.");
            cv26.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv26);

            ContentValues cv27=new ContentValues();
            cv27.put(LOC_NAME,"Sardar Purandare Wada");
            cv27.put(LOC_CAT,"4");
            cv27.put(LOC_LAT,"18.341959");
            cv27.put(LOC_LANG,"73.9590332");
            cv27.put(LOC_URL,"www.Sardar Purandar eWada.com");
            cv27.put(LOC_DISC," very old and beautiful place" +
                    "Tha main door of the wada is a small replica of the Shaniwar wada." +
                    " The main wada is not open to public. There are various temples of God Shiva.");
            cv27.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv27);

            ContentValues cv28=new ContentValues();
            cv28.put(LOC_NAME,"Parvati");
            cv28.put(LOC_CAT,"4");
            cv28.put(LOC_LAT,"18.5413933");
            cv28.put(LOC_LANG,"73.8095259");
            cv28.put(LOC_URL,"www.Parvati.com");
            cv28.put(LOC_DISC,"Parvati is a good place to relax your self." +
                    " It's a beautiful place from where one could see the bird view of Pune." +
                    "There's  temples and a museum at top.");
            cv28.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv28);

            ContentValues cv29=new ContentValues();
            cv29.put(LOC_NAME,"Ohel David Synagogue");
            cv29.put(LOC_CAT,"4");
            cv29.put(LOC_LAT,"18.5193533");
            cv29.put(LOC_LANG,"73.8724686");
            cv29.put(LOC_URL,"www.OhelDavidSynagogue.com");
            cv29.put(LOC_DISC,"Ohel David synagogue is an ancient Gothic jewish architecture."+
                    " It's looks so vibrant in red colour from outside."+
                    "Judaism is one of the oldest Abrahamic religion of the world has its origin  in middle East");
            cv29.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv29);

            ContentValues cv30=new ContentValues();
            cv30.put(LOC_NAME,"Shivsrushti Pune");
            cv30.put(LOC_CAT,"4");
            cv30.put(LOC_LAT,"18.4569401");
            cv30.put(LOC_LANG,"73.8336208");
            cv30.put(LOC_URL,"www.ShivsrushtiPune.com");
            cv30.put(LOC_DISC,"Sarkarwada is a replica of the ancient Peshwale architecture."+
                    "We can see in the Shivasruti the beautiful stone wall, chowk, " +
                            "magnificent entrance, ocean, pudding, wood and stone walls.");
            cv30.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv30);





            ContentValues cv41=new ContentValues();
            cv41.put(LOC_NAME,"Omkareshwar Mandir");
            cv41.put(LOC_CAT,"5");
            cv41.put(LOC_LAT,"18.5198778");
            cv41.put(LOC_LANG,"73.8467402");
            cv41.put(LOC_URL,"www.Omkareshwar Mandir.com");
            cv41.put(LOC_DISC,"Omkareshwar temple is a piece of splendid architecture of the 17th century." +
                    "The temple is magnificant with its rich history.The atmosphere is serene and spiritual." +
                    "You can do meditate for a long time without any disturbance.");
            cv41.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv41);

            ContentValues cv42=new ContentValues();
            cv42.put(LOC_NAME,"Pataleshwar Temple");
            cv42.put(LOC_CAT,"5");
            cv42.put(LOC_LAT,"18.5268422");
            cv42.put(LOC_LANG,"73.8157007");
            cv42.put(LOC_URL,"www.PataleshwarTemplePune.com");
            cv42.put(LOC_DISC,"Pataleshwar Temple is Dedicated to the Hindu god Shiva,"+
                    " this compact rock-cut cave temple dates to the 8th century" +
                    "You cannot find more soothing and relaxing place than this in the middle of the city.");
            cv42.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv42);

            ContentValues cv43=new ContentValues();
            cv43.put(LOC_NAME,"Dagdusheth Halwai Ganpati Temple");
            cv43.put(LOC_CAT,"5");
            cv43.put(LOC_LAT,"18.4699287");
            cv43.put(LOC_LANG,"73.8680055");
            cv43.put(LOC_URL,"www.DagdushethHalwaiGanpatiTemple.com");
            cv43.put(LOC_DISC," Dagdusheth Halwai Ganpati Temple is a hindu temple "+
                    "popular with pilgrims, with marble interiors & 7.5-ft Ganesh idol in gold." +
                    "Ganpati Bappa's Beautiful temple. It is very famous temple in pune.");
            cv43.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv43);

            ContentValues cv44=new ContentValues();
            cv44.put(LOC_NAME,"Narayani Dham");
            cv44.put(LOC_CAT,"5");
            cv44.put(LOC_LAT,"18.4699287");
            cv44.put(LOC_LANG,"73.8680055");
            cv44.put(LOC_URL,"www.NarayaniDham.com");
            cv44.put(LOC_DISC,"Narayani Dham is a  Beautiful place , very big campus , "+
                    "devotional place , Temple of Mata 'NARAYANI'." +
                    "Very clean and well maintained. Blissful and very serene.");
            cv44.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv44);

            ContentValues cv45=new ContentValues();
            cv45.put(LOC_NAME,"Trishund Ganpati Mandir");
            cv45.put(LOC_CAT,"5");
            cv45.put(LOC_LAT,"18.5215915");
            cv45.put(LOC_LANG,"73.8260493");
            cv45.put(LOC_URL,"www.TrishundGanpatiMandir.com");
            cv45.put(LOC_DISC," Trishund Ganpati Mandir Worth Visiting Once, Historical Background ." +
                    "This is kinda very underrated Ganesh temple from Peshva era.");
            cv45.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv45);

            ContentValues cv46=new ContentValues();
            cv46.put(LOC_NAME,"Katraj Jain Temple, Aagam Mandir");
            cv46.put(LOC_CAT,"5");
            cv46.put(LOC_LAT,"18.4925317");
            cv46.put(LOC_LANG,"73.8277695");
            cv46.put(LOC_URL,"www.KatrajJainTempleAagamMandir.com");
            cv46.put(LOC_DISC,"Katraj Jain Temple, Aagam Mandir is a truly a beautiful place." +
                    "The garden is small yet amazing. You may see lots and varieties of butterflies"+
                    "playing around here and still it will soothe you.");
            cv46.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv46);

            ContentValues cv47=new ContentValues();
            cv47.put(LOC_NAME,"Aai Mata Mandir");
            cv47.put(LOC_CAT,"5");
            cv47.put(LOC_LAT,"18.4717913");
            cv47.put(LOC_LANG,"73.8723974");
            cv47.put(LOC_URL,"www.AaiMataMandir.com");
            cv47.put(LOC_DISC,"Aai Mata Mandir is a very beautiful temple"+
                    " with lots of architectural work and its placement at the top of hill makes it such a temple "+
                    "from where you can see the whole Pune city");
            cv47.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv47);

            ContentValues cv48=new ContentValues();
            cv48.put(LOC_NAME,"Sankatharan Temple");
            cv48.put(LOC_CAT,"5");
            cv48.put(LOC_LAT,"18.4758537");
            cv48.put(LOC_LANG,"73.8935438");
            cv48.put(LOC_URL,"www.SankatharanTemple.com");
            cv48.put(LOC_DISC,"Lord Shankar Temple along with 5 temples Ganpati, Radha Krishna, Maruti and Navagraha."+
                    " Also have separate shani temple. You can also find Gaushala in same campus."+
                    " Very positive and peaceful place.");
            cv48.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv48);

            ContentValues cv49=new ContentValues();
            cv49.put(LOC_NAME,"Kausar Bagh Masjid");
            cv49.put(LOC_CAT,"5");
            cv49.put(LOC_LAT,"18.4729849");
            cv49.put(LOC_LANG,"73.8905537");
            cv49.put(LOC_URL,"www.KausarBaghMasjid.com");
            cv49.put(LOC_DISC,"Kausar Bagh Masjid is Nice mosque for praying 5 times."+
                    " Well maintained inside and outside.Great marvel of architecture, beautifully crafted design."+
                    " Embedded in the heart of the kaushar baug");
            cv49.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv49);

            ContentValues cv50=new ContentValues();
            cv50.put(LOC_NAME,"Shankar Maharaj Math");
            cv50.put(LOC_CAT,"5");
            cv50.put(LOC_LAT,"18.4707085");
            cv50.put(LOC_LANG,"73.8554027");
            cv50.put(LOC_URL,"www.ShankarMaharajMath.com");
            cv50.put(LOC_DISC,"Shankar Maharaj Math is a Peaceful place with positive vibes"+
                    " and presence of shankar baba maharaj.It is considered as jagrut temple."+
                    "Best place to sit in peace and spirituality.");
            cv50.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv50);







            ContentValues cv51=new ContentValues();
            cv51.put(LOC_NAME,"Sinhagad Fort");
            cv51.put(LOC_CAT,"9");
            cv51.put(LOC_LAT,"18.3663091");
            cv51.put(LOC_LANG,"73.7536873");
            cv51.put(LOC_URL,"www.SinhagadFort.com");
            cv51.put(LOC_DISC,"Sinhagad Fort is a awesome place to visit for a trek or a day outing." +
                    "A mild cold and misty look with the shower will make you feel incredibly lucky one."+
                    " In addition, historical 2000 years ago built an old fort got a beauty and enticing views.");
            cv51.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv51);

            ContentValues cv52=new ContentValues();
            cv52.put(LOC_NAME,"Rajgad Fort");
            cv52.put(LOC_CAT,"9");
            cv52.put(LOC_LAT,"18.2468133");
            cv52.put(LOC_LANG,"73.6811742");
            cv52.put(LOC_URL,"www.RajgadFort.com");
            cv52.put(LOC_DISC,"Rajgad Fort is a historical hilltop fortress and"+
                    " popular trekking site, with palace ruins, caves & water cisterns." +
                    "This place should be preferred when you are looking for easy to moderate trek.");
            cv52.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv52);

            ContentValues cv53=new ContentValues();
            cv53.put(LOC_NAME,"Torna Fort");
            cv53.put(LOC_CAT,"9");
            cv53.put(LOC_LAT,"18.2769418");
            cv53.put(LOC_LANG,"73.6197271");
            cv53.put(LOC_URL,"www.TornaFort.com");
            cv53.put(LOC_DISC,"Torna Fort Ruins of hilltop fort offering scenic view "+
                    "and challenging mountain hikes, especially in rainy season." +
                    " It's just a really fabulous place and ancient place to visit.");
            cv53.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv53);

            ContentValues cv54=new ContentValues();
            cv54.put(LOC_NAME,"Purandar Fort");
            cv54.put(LOC_CAT,"9");
            cv54.put(LOC_LAT,"18.282636");
            cv54.put(LOC_LANG,"73.973374");
            cv54.put(LOC_URL,"www.PurandarFort.com");
            cv54.put(LOC_DISC,"Purandar Fort is a historic mountaintop fort with sweeping views"+
                    " on the site of an army training camp (ID required)." +
                    " A must visit to enjoy nature's beauty at its best with very less crowd.");
            cv54.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv54);

            ContentValues cv55=new ContentValues();
            cv55.put(LOC_NAME,"Lohgad Fort");
            cv55.put(LOC_CAT,"9");
            cv55.put(LOC_LAT,"18.7072583");
            cv55.put(LOC_LANG,"73.477302");
            cv55.put(LOC_URL,"www.LohgadFort.com");
            cv55.put(LOC_DISC," Lohgad Fort is a nice spot to loose yourself in the fog." +
                    "The adventurous steps having water flowing through them continuously shows "+
                    "how much far you can go to enjoy the nature.");
            cv55.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv55);

            ContentValues cv56=new ContentValues();
            cv56.put(LOC_NAME,"VisapurFort");
            cv56.put(LOC_CAT,"9");
            cv56.put(LOC_LAT,"18.7609025");
            cv56.put(LOC_LANG,"73.144061");
            cv56.put(LOC_URL,"www.Visapur Fort.com");
            cv56.put(LOC_DISC,"Visapur Fort is a Popular hike to the remains of an 18th-century fortress"+
                    " with panoramic valley views.The best part is you need to climb the rocks on which"+
                    " water keeps running down like a waterfall. ");
            cv56.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv56);

            ContentValues cv57=new ContentValues();
            cv57.put(LOC_NAME,"TikonaFort");
            cv57.put(LOC_CAT,"9");
            cv57.put(LOC_LAT,"18.7609025");
            cv57.put(LOC_LANG,"73.144061");
            cv57.put(LOC_URL,"www.TikonaFort.com");
            cv57.put(LOC_DISC,"is an ancient pyramid-shaped fort, popular with trekkers, "+
                    "known for its sweeping views & steep ascent.A very very beautiful place to visit during monsoon season."+
                    " The scenery you will get to see here is fantastic.");
            cv57.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv57);

            ContentValues cv58=new ContentValues();
            cv58.put(LOC_NAME,"ShivneriFort");
            cv58.put(LOC_CAT,"9");
            cv58.put(LOC_LAT,"18.7609025");
            cv58.put(LOC_LANG,"73.144061");
            cv58.put(LOC_URL,"www.Shivneri Fort.com");
            cv58.put(LOC_DISC,"Shivneri Fort is a hilltop fort with a variety of protective gates,"+
                    " mud walls & a mosque, offering expansive views.The fort has its own importance as being the birth place "+
                    "of the great Chatrapati Shivaji Maharaj.");
            cv58.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv58);

            ContentValues cv59=new ContentValues();
            cv59.put(LOC_NAME,"RajmachiFort");
            cv59.put(LOC_CAT,"9");
            cv59.put(LOC_LAT,"18.7609025");
            cv59.put(LOC_LANG,"73.144061");
            cv59.put(LOC_URL,"www.Rajmachi Fort.com");
            cv59.put(LOC_DISC,"Rajmachi Fort is  Perfect trekking fort for adventure lover."+
                    "Nice mountain and valleys.Full of Slippery clay and rocks road,nice for off-road biking."+
                    " At fort hiking starting patch seems little dangerous");
            cv59.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv59);

            ContentValues cv60=new ContentValues();
            cv60.put(LOC_NAME,"MalhargadFort");
            cv60.put(LOC_CAT,"9");
            cv60.put(LOC_LAT,"18.7609025");
            cv60.put(LOC_LANG,"73.144061");
            cv60.put(LOC_URL,"www.Malhargad Fort.com");
            cv60.put(LOC_DISC,"Malhargad Fort is one of the very simple and serene forts." +
                    "There is a temple inside. The fort is having 4 doors to it. And is surrounded by beautiful mountains"+
                    " which is giving it nice landscape view");
            cv60.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv60);

           cv60=new ContentValues();
            cv60.put(LOC_NAME,"Pune University");
            cv60.put(LOC_CAT,"6");
            cv60.put(LOC_LAT,"18.550200");
            cv60.put(LOC_LANG,"73.823836");
            cv60.put(LOC_URL,"www.unipune.ac.in");
            cv60.put(LOC_DISC,"Pune University now named as Savitribai Phule Pune University" +
                    "is also known as Oxford of the east."
                    );
            cv60.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv60);

            cv60=new ContentValues();
            cv60.put(LOC_NAME,"ESquare");
            cv60.put(LOC_CAT,"7");
            cv60.put(LOC_LAT,"18.537713");
            cv60.put(LOC_LANG,"73.836110");
            cv60.put(LOC_URL,"www.wsquare.com");
            cv60.put(LOC_DISC,"Multiplex near pune university" +
                    "you can find shopping mall and mcDonalds near."
            );
            cv60.put(LOC_VISIT_FLAG,"False");
            db.insert(DATABASE_LTABLE,null,cv60);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_UTABLE);
            db.execSQL("DROP TABLE if EXISTS "+DATABASE_ITABLE);
            db.execSQL("DROP TABLE if EXISTS "+DATABASE_LTABLE);
            db.execSQL("DROP TABLE if EXISTS "+DATABASE_NTABLE);
            db.execSQL("DROP TABLE if EXISTS "+DATABASE_CTABLE);
            onCreate(db);
        }
    }
}

