package com.example.hospitalguide;

import android.content.Context;
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

import java.util.Locale;

public class ActivityHospitalSearch extends AppCompatActivity {
    Spinner treatment, locations, suburbs;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int selectedCity = sharedPref.getInt("selectedCity", 0);

        treatment = findViewById(R.id.spTreatment);
        locations = findViewById(R.id.spCities);
        suburbs = findViewById(R.id.spRegions);

        treatment.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Hospitals.getInstance().getTreatments()));
        treatment.setOnItemSelectedListener(new MyItemListener());

        locations.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.cities)));
        locations.setOnItemSelectedListener(new MyItemListener());

        suburbs.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, suburbGetter(selectedCity)));
        suburbs.setOnItemSelectedListener(new MyItemListener());

        Button search = findViewById(R.id.btnSearch);
        search.setOnClickListener(new MyClick());
    }

    private class MyItemListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            SharedPreferences.Editor editor = sharedPref.edit();
            //int selected = getResources().getIdentifier(parent.getSelectedItem().toString().toLowerCase(),"string", ActivityHospitalSearch.this.getPackageName());
            //suburbs.setAdapter(new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, suburbGetter(pos)));
            switch(parent.getId()){
                case R.id.spTreatment:
                    break;
                case R.id.spCities:
                    suburbs.setAdapter(new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, suburbGetter(pos)));
                    suburbs.setOnItemSelectedListener(new MyItemListener());
                    editor.putInt("selectedCity", pos);
                    editor.apply();
                    break;
                case R.id.spRegions:
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) { }
    }

    private class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Spinner treatment = findViewById(R.id.spTreatment);
            Spinner locations = findViewById(R.id.spCities);
            Spinner suburbs = findViewById(R.id.spRegions);

            Intent intent = new Intent(view.getContext(), ActivityHospitalList.class);
            intent.putExtra("treatment", treatment.getSelectedItem().toString());
            intent.putExtra("city", locations.getSelectedItem().toString().substring(0,1));
            intent.putExtra("region", suburbs.getSelectedItem().toString());
            startActivity(intent);
        }
    }

    private String[] suburbGetter(int input) {
        Resources res = getResources();
        switch (input) {
            case 0:
                return res.getStringArray(R.array.helsinkiSuburbs);
            case 1:
                return res.getStringArray(R.array.vantaaSuburbs);
            case 2:
                return res.getStringArray(R.array.espooSuburbs);
            default:
                return new String[3];
        }
    }

    public void onClick(View view) {
        // This code was pulled from https://stackoverflow.com/questions/34573201/change-languages-in-app-via-strings-xml
        Locale svLocale = new Locale("sv");
        Locale fiLocale = new Locale("fi");
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
