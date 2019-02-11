package com.example.hospitalguide;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ActivityHospitalSearch extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button search = findViewById(R.id.btnSearch);
        search.setOnClickListener(new MyClick());

        Spinner treatment = findViewById(R.id.spTreatment);
        treatment.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Hospitals.getInstance().getTreatments()));
        treatment.setOnItemSelectedListener(new MyItemListener());

        Spinner locations = findViewById(R.id.spCities);
        locations.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.cities)));
        locations.setOnItemSelectedListener(new MyItemListener());
    }

    private class MyItemListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Spinner suburbs = findViewById(R.id.spRegions);

            switch(parent.getId()){
                case R.id.spTreatment:
                    treatmentSelected = parent.getItemAtPosition(pos).toString();
                    break;
                case R.id.spCities:
                    suburbs.setAdapter(new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, suburbGetter(parent.getItemAtPosition(pos).toString())));
                    suburbs.setOnItemSelectedListener(new MyItemListener());
                    break;
                case R.id.spRegions:
                    regionSelected = parent.getItemAtPosition(pos).toString();
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) { }
    }

    private class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Spinner treatment = findViewById(R.id.spTreatment);
            Spinner locations = findViewById(R.id.spCities);
            Spinner suburbs = findViewById(R.id.spRegions);

            Intent intent = new Intent(view.getContext(), ActivityHospitalList.class);
            intent.putExtra("treatment", treatment.getSelectedItem().toString());
            intent.putExtra("city", locations.getSelectedItem().toString());
            intent.putExtra("region", suburbs.getSelectedItem().toString());
            startActivity(intent);
        }
    }

    private String[] suburbGetter(String input){
        if (input.equals("Helsinki"))
            return getResources().getStringArray(R.array.helsinkiSuburbs);
        else if (input.equals("Espoo"))
            return getResources().getStringArray(R.array.espooSuburbs);
        else
            return getResources().getStringArray(R.array.vantaaSuburbs);

    }

}
