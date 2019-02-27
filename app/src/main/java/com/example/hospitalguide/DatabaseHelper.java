package com.example.hospitalguide;
/* Lifted heavily from https://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/ */

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper dbHelper;
    private final Context context;
    private SQLiteDatabase myDataBase;
    private static String currentTable;

    // Column names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_REGION = "region";
    private static final String KEY_CITY = "city";
    private static final String KEY_DESCRIPTION = "description";

    private static String DATABASE_PATH = "";
    private static String DATABASE_NAME = "terveysasemat.db";
    private static final int DATABASE_VERSION = 4;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context.getApplicationContext());
        }
        currentTable = context.getString(R.string.table);
        return dbHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";

    }

    public void createDatabase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){}
        else{
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

        return checkDB != null ? true : false;
    }

    public void copyDatabase() throws IOException{
        String DB_NAME="terveysasemat.db";
        String db_out_path=context.getDatabasePath(DB_NAME).toString();

        File db_out_file = new File(db_out_path);

        InputStream db_in = context.getAssets().open(DB_NAME);
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
        currentTable = this.context.getString(R.string.table);
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
        HashSet<String> regionsSet = new HashSet<>();
        ArrayList<String> regions = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_REGION + " FROM " + currentTable + " WHERE " + KEY_CITY + " = '" + selectedCity + "'";
        //e.g. "Select regions from the table where the description contains a specific city
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                regionsSet.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        regions.addAll(regionsSet);
        Collections.sort(regions, String.CASE_INSENSITIVE_ORDER);
        return regions;
    }

    public ArrayList<Hospital> getTerveysasemat(String selectedRegion) {
        ArrayList<Hospital> hospitalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + currentTable + " WHERE " + KEY_REGION + " = '" + selectedRegion + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Hospital hospital = new Hospital();
                hospital.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                hospital.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                hospital.setRegion(cursor.getString(cursor.getColumnIndex(KEY_REGION)));
                hospital.setCity(cursor.getString(cursor.getColumnIndex(KEY_CITY)));
                hospital.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                hospitalList.add(hospital);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return hospitalList;
    }

    public Hospital getTerveysasema(int id) {
        Hospital hospital = new Hospital();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_NAME + ", " + KEY_DESCRIPTION + " FROM " + currentTable + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Grab the description of the hospital given to the method.
        cursor.moveToFirst();
        hospital.setName(cursor.getString(0));
        hospital.setDescription(cursor.getString(1));

        cursor.close();
        db.close();
        return hospital;
    }

}