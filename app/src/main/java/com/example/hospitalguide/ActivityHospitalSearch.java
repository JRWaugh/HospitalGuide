package com.example.hospitalguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

public class ActivityHospitalSearch extends AppCompatActivity {
    Spinner locations;
    Spinner suburbs;
    Button buttonSearch;
    Hospital hospital;
    AutoCompleteTextView textSearch;
    String selectedRegion;
    //Not currently used. Not sure what to save with it yet.
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locations = findViewById(R.id.spCities);
        suburbs = findViewById(R.id.spRegions);
        buttonSearch = findViewById(R.id.btnSearch);
        textSearch = findViewById(R.id.actSearch);
        textSearch.setThreshold(2);
        textSearch.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DatabaseHelper.getInstance(this).getAllTerveysasemat()));

        locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                String selectedCity = parent.getItemAtPosition(pos).toString();
                suburbs.setAdapter(new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_dropdown_item, DatabaseHelper.getInstance(parent.getContext()).getRegions(selectedCity)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        suburbs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                selectedRegion = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityHospitalList.class);
                intent.putExtra("region", selectedRegion);
                startActivity(intent);
            }
        });

        textSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                hospital = (Hospital)parent.getItemAtPosition(pos);
                Intent intent = new Intent(view.getContext(), ActivityHospitalInfo.class);
                intent.putExtra("hospital", hospital.getId());
                startActivity(intent);
            }
        });
    }
}
