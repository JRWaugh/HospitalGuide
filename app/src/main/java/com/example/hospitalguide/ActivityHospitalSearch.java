package com.example.hospitalguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class ActivityHospitalSearch extends AppCompatActivity {
    Spinner treatment;
    Spinner locations;
    Spinner suburbs;
    String HELSINKI;
    String ESPOO;
    String VANTAA;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        HELSINKI = getResources().getString(R.string.helsinki);
        ESPOO = getResources().getString(R.string.espoo);
        VANTAA = getResources().getString(R.string.vantaa);

        String selectedCity = sharedPref.getString("selectedCity", HELSINKI);
        
        treatment = findViewById(R.id.spTreatment);
        locations = findViewById(R.id.spCities);
        suburbs = findViewById(R.id.spRegions);

        MyItemListener listener = new MyItemListener();

        ArrayAdapter treatmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Hospitals.getInstance().getTreatments());
        treatment.setAdapter(treatmentAdapter);
        treatment.setOnItemSelectedListener(listener);

        ArrayAdapter locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.cities));
        locations.setAdapter(locationAdapter);
        locations.setOnItemSelectedListener(listener);

        ArrayAdapter suburbAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, suburbGetter(selectedCity));
        suburbs.setAdapter(suburbAdapter);
        suburbs.setOnItemSelectedListener(listener);

        Button search = findViewById(R.id.btnSearch);
        search.setOnClickListener(new MyClick());
    }

    private class MyItemListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Log.d("TAG", "Listener");
            SharedPreferences.Editor editor = sharedPref.edit();
            switch(parent.getId()){
                case R.id.spTreatment:
                    Log.d("TAG", "spTreatment");
                    break;
                case R.id.spCities:
                    Log.d("TAG", "spCities");
                    suburbs.setAdapter(new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_dropdown_item, suburbGetter(parent.getItemAtPosition(pos).toString())));
                    editor.putString("selectedCity", parent.getItemAtPosition(pos).toString());
                    editor.apply();
                    break;
                case R.id.spRegions:
                    Log.d("TAG", "spRegions");
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    private class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ActivityHospitalList.class);
            intent.putExtra("treatment", treatment.getSelectedItem().toString());
            intent.putExtra("city", locations.getSelectedItem().toString().substring(0,1));
            intent.putExtra("region", suburbs.getSelectedItem().toString());
            startActivity(intent);
        }
    }

    private String[] suburbGetter(String input) {
        Log.d("TAG", "subGet");
        Resources res = getResources();
        if(input.equals(HELSINKI))
            return res.getStringArray(R.array.helsinkiSuburbs);
        else if (input.equals(VANTAA))
            return res.getStringArray(R.array.vantaaSuburbs);
        else
            return res.getStringArray(R.array.espooSuburbs);
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
