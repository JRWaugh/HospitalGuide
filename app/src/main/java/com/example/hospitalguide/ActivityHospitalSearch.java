package com.example.hospitalguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityHospitalSearch extends AppCompatActivity {
    Spinner locations;
    Spinner suburbs;
    Button search;
    String TABLE;
    String HELSINKI;
    String ESPOO;
    String VANTAA;
    String selectedRegion;

    //Not currently used. Not sure what to save with it yet.
    private SharedPreferences sharedPref;
    DatabaseHelper dbHelper = null;
    ArrayList<Hospital> hospitalList;
    Locale svLocale = new Locale("sv");
    Locale fiLocale = new Locale("fi");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pull resources from strings. Changes with different languages.
        TABLE = getResources().getString(R.string.table);
        HELSINKI = getResources().getString(R.string.helsinki);
        ESPOO = getResources().getString(R.string.espoo);
        VANTAA = getResources().getString(R.string.vantaa);

        // Create database object. Not sure if the populateDB method is necessary. Needs testing!!
        dbHelper = new DatabaseHelper(this);
        dbHelper.populateDB();

        locations = findViewById(R.id.spCities);
        suburbs = findViewById(R.id.spRegions);
        search = findViewById(R.id.btnSearch);

        locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                String selectedCity = parent.getItemAtPosition(pos).toString();
                suburbs.setAdapter(new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_dropdown_item, dbHelper.getRegions(selectedCity)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        suburbs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                selectedRegion = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityHospitalList.class);
                intent.putExtra("region", selectedRegion);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view) {
        // This code was pulled from https://stackoverflow.com/questions/34573201/change-languages-in-app-via-strings-xml
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(view.getId() == R.id.tvFinnish)
            conf.locale = fiLocale;
        else if(view.getId() == R.id.tvSwedish)
            conf.locale = svLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, ActivityHospitalSearch.class);
        startActivity(refresh);
    }
}
