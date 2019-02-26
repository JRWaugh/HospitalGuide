package com.example.hospitalguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityHospitalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);

        Intent intent = getIntent();
        String i = intent.getStringExtra("hospital");

        /*((TextView)findViewById(R.id.tvName)).setText(Hospitals.getInstance().getHospital(i).toString());
        ((TextView)findViewById(R.id.tvCities)).setText(Hospitals.getInstance().getHospital(i).getRegion());
        ((TextView)findViewById(R.id.tvTreatment)).setText(Hospitals.getInstance().getHospital(i).getTreatment());
        ((TextView)findViewById(R.id.tvPublic)).setText(Hospitals.getInstance().getHospital(i).getIsPublic());*/
    }
}
