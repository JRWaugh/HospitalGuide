package com.example.hospitalguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ActivityHospitalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);

        int i = getIntent().getIntExtra("hospital", 0);

        Hospital hospital = DatabaseHelper.getInstance(this).getTerveysasema(i);
        ((TextView)findViewById(R.id.tvName)).setText(hospital.toString());
        ((TextView)findViewById(R.id.tvDescription)).setText(Html.fromHtml(hospital.getDescription()));
    }
}
