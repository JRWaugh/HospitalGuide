package com.example.hospitalguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityHospitalList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        Intent intent = getIntent();
        String treatment = intent.getStringExtra("treatment");
        String location = intent.getStringExtra("city");
        String region = intent.getStringExtra("region");
        ListView lv = findViewById(R.id.lvHospitals);

        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Hospitals.getInstance().filterHospitals(location, treatment)));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent hospitalInfo = new Intent(view.getContext(), ActivityHospitalInfo.class);
                hospitalInfo.putExtra("hospital", adapterView.getItemAtPosition(i).toString());
                startActivity(hospitalInfo);
            }
        });

    }
}
