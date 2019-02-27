package com.example.hospitalguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ActivityHospitalInfo extends AppCompatActivity {
    DatabaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);

        Intent intent = getIntent();
        int i = intent.getIntExtra("hospital", 0);

        dbHelper = new DatabaseHelper(this);
        Hospital hospital = dbHelper.getTerveysasema(i);
        ((TextView)findViewById(R.id.tvName)).setText(hospital.toString());
        ((TextView)findViewById(R.id.tvDescription)).setText(Html.fromHtml(hospital.getDescription()));
    }
}
