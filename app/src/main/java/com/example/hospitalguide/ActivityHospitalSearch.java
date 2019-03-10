package com.example.hospitalguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;

/** The main activity of this application. It provides users with a search box and drop-down lists
 * to find the health centre they are looking for. This activity also provides access to all reminders set*/

public class ActivityHospitalSearch extends AppCompatActivity {
    private Spinner locations;
    private Spinner suburbs;
    private Button buttonSearch;
    private Button reminder;
    private Hospital hospital;
    private AutoCompleteTextView textSearch;
    private String selectedRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locations = findViewById(R.id.spCities);
        locations.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.cities)));

        suburbs = findViewById(R.id.spRegions);
        buttonSearch = findViewById(R.id.btnSearch);
        reminder = findViewById(R.id.btnReminder);
        textSearch = findViewById(R.id.actSearch);
        textSearch.setThreshold(2);
        textSearch.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DatabaseHelper.getInstance(this).getAllTerveysasemat()));

        locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                String selectedCity = parent.getItemAtPosition(pos).toString();
                //Populates suburbs spinner based on location selected in this spinner.
                suburbs.setAdapter(new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_dropdown_item, DatabaseHelper.getInstance(parent.getContext()).getRegions(selectedCity)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        suburbs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                //Sets selectedRegion string to value of the region selected in this spinner.
                selectedRegion = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityHospitalList.class);
                /*Builds a list of health centres IDs to be sent to ActivityHospitalList, based on the
                health centres in the selected region. It is necessary to convert to send IDs
                instead of the string so that the language can be switched in ActivityHospitalList*/
                ArrayList<Integer> terveysasemat = DatabaseHelper.getInstance(view.getContext()).getIDsByRegion(selectedRegion);
                intent.putIntegerArrayListExtra("terveysasemat", terveysasemat);
                startActivity(intent);
            }
        });

        textSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //The listener for the AutoCompleteTextView search box.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                hospital = (Hospital)parent.getItemAtPosition(pos);
                Intent intent = new Intent(view.getContext(), ActivityHospitalInfo.class);
                intent.putExtra("hospital", hospital.getId());
                startActivity(intent);
            }
        });

        reminder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityRemindersList.class);
                startActivity(intent);
            }
        });

    }
}
