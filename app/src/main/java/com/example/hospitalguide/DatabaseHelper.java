package com.example.hospitalguide;
/* Lifted heavily from https://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/ */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

/** A database which holds tables of health centres, along with their location, phone number, website
 * as well as user made appointment reminders. There are three tables: English, Swedish and Finnish.*/

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper dbHelper;
    private final Context context;
    private SQLiteDatabase myDataBase;
    private String currentTable;

    // Column names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_REGION = "region";
    private static final String KEY_CITY = "city";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_APPOINTMENT = "appointment";

    private static String DATABASE_PATH = "";
    private static final String DATABASE_NAME = "healthcentres.db";
    private static final int DATABASE_VERSION = 1;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.currentTable = this.context.getResources().getString(R.string.table);

    }

    public void createDatabase() {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            /*By calling this method, an empty database will be created into the default system path
            of your application so we are gonna be able to overwrite that database with our database.*/
            this.getReadableDatabase();
            this.close();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error(e.toString());
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String path = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            //database does't exist yet.
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null;
    }

    private void copyDatabase() throws IOException{
        String db_out_path=context.getDatabasePath(DATABASE_NAME).toString();
        File db_out_file = new File(db_out_path);

        InputStream db_in = context.getAssets().open(DATABASE_NAME);
        FileOutputStream db_out = new FileOutputStream(db_out_file);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = db_in.read(buffer))>0){
            db_out.write(buffer, 0, length);
        }
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    
    public ArrayList<String> getRegions(String selectedCity) {
        //We first add the results to a set, because a set does not include duplicates.
        HashSet<String> regionsSet = new HashSet<>();
        ArrayList<String> regions = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_REGION + " FROM " + currentTable + " WHERE " + KEY_CITY + " = '" + selectedCity + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                regionsSet.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        db.close();
        //The entirety of the set is added into a standard ArrayList that will be used by the regions spinner adapter.
        regions.addAll(regionsSet);
        Collections.sort(regions, String.CASE_INSENSITIVE_ORDER);
        return regions;
    }

    public ArrayList<Integer> getIDsByRegion(String selectedRegion) {
        //This method takes a region input and produces a list of health centre IDs associated with that region.
        ArrayList<Integer> idList = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_ID + " FROM " + currentTable + " WHERE " + KEY_REGION + " = '" + selectedRegion + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                idList.add(cursor.getInt(0));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return idList;
    }

    public ArrayList<Hospital> getAllTerveysasemat(){
        //This method pulls all health centres from the table associated with the current language.
        ArrayList<Hospital> hospitalList = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_ID + ", " + KEY_NAME + " FROM " + currentTable;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()){
            do {
                Hospital hospital = new Hospital();
                hospital.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                hospital.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                hospitalList.add(hospital);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hospitalList;
    }

    public Hospital getTerveysasema(int id) {
        //Pulls a specific health centre based on its ID.
        Hospital hospital = new Hospital();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_ADDRESS + ", " + KEY_PHONE + ", " + KEY_WEBSITE + ", " + KEY_APPOINTMENT + " FROM " + this.currentTable + " WHERE " + KEY_ID + " = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()) {
            hospital.setId(cursor.getInt(0));
            hospital.setName(cursor.getString(1));
            hospital.setAddress(cursor.getString(2));
            hospital.setPhone(cursor.getString(3));
            hospital.setWebsite(cursor.getString(4));
            hospital.setAppointment(cursor.getString(5));
        }
        cursor.close();
        db.close();
        return hospital;
    }

    public void setReminder(int id, String date){
        //Adds a reminder to a specific health centre in each table.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_APPOINTMENT, date);
        db.update("en_terveysasema", values, KEY_ID + " = " + id, null);
        db.update("fi_terveysasema", values, KEY_ID + " = " + id, null);
        db.update("sv_terveysasema", values, KEY_ID + " = " + id, null);
        db.close();
    }

    public ArrayList<Hospital> getReminders() throws ParseException {
        //Generates a list of hospital which have reminders.
        //updateReminders is called first to delete any reminders which have expired.
        updateReminders();
        ArrayList<Hospital> reminders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_APPOINTMENT + " FROM " + this.currentTable + " WHERE " + KEY_APPOINTMENT + " IS NOT NULL ORDER BY " + KEY_APPOINTMENT;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                Hospital hospital = new Hospital();
                hospital.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                hospital.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                hospital.setAppointment(cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT)));
                reminders.add(hospital);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reminders;
    }

    private void updateReminders() throws ParseException {
        //Deletes any reminders which have expired.
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_ID + ", " + KEY_APPOINTMENT + " FROM " + this.currentTable + " WHERE " + KEY_APPOINTMENT + " IS NOT NULL";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null && cursor.moveToFirst()) {
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = sdf.parse(cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT)));
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                date.setSeconds(59);
                if (date.before(new Date())) {
                    //reminder is deleted by using setReminder() method with a null date.
                    setReminder(cursor.getInt(cursor.getColumnIndex(KEY_ID)), null);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    public void setTable(String table){
        this.currentTable = table;
    }
}