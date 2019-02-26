package com.example.hospitalguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityHospitalList extends AppCompatActivity {
    DatabaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        String region = intent.getStringExtra("region");
        ListView lv = findViewById(R.id.lvHospitals);

        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getTerveysasemat(region)));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                Intent hospitalInfo = new Intent(view.getContext(), ActivityHospitalInfo.class);
                hospitalInfo.putExtra("hospital", parent.getItemAtPosition(pos).toString());
                startActivity(hospitalInfo);
            }
        });

    }
}
