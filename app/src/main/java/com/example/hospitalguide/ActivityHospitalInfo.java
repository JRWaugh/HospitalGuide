package com.example.hospitalguide;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ActivityHospitalInfo extends AppCompatActivity {
    private String website;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);
        Log.d("Tag", "On Create");
        Configuration conf = getResources().getConfiguration();

        int i = getIntent().getIntExtra("hospital", 0);

        Hospital hospital = DatabaseHelper.getInstance(this).getTerveysasema(i);
        ((TextView)findViewById(R.id.tvName)).setText(hospital.toString());

        TextView address = findViewById(R.id.tvAddress);
        address.append(hospital.getAddress());

        TextView phone = findViewById(R.id.tvPhone);
        phone.append(hospital.getPhone());
        //Makes phone number clickable
        Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
        website = hospital.getWebsite();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Tag", "On Resume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Tag", "On Start");
    }

    public void openLink(View view) {
        //Method to go to health centre's website when button is clicked
        Uri url = Uri.parse(website);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }
}
