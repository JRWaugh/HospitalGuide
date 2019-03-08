package com.example.hospitalguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityHospitalList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        //Imports list of IDs generated in ActivityHospital Search
        ArrayList<Integer> selected = getIntent().getIntegerArrayListExtra("terveysasemat");
        //Builds list of Hospitals from current language table based on IDs
        ArrayList<Hospital> hospitalList = new ArrayList<>();
        for(int i : selected)
            hospitalList.add(DatabaseHelper.getInstance(this).getTerveysasema(i));
        ListView lv = findViewById(R.id.lvHospitals);
        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hospitalList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                Intent hospitalInfo = new Intent(view.getContext(), ActivityHospitalInfo.class);
                Hospital hospital = (Hospital)parent.getItemAtPosition(pos);
                hospitalInfo.putExtra("hospital", hospital.getId());
                startActivity(hospitalInfo);
            }
        });

    }
}
