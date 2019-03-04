package com.example.hospitalguide;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class ActivityReminderConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_confirm);

        try {
            DatabaseHelper.getInstance(this).createDatabase();
        } catch(IOException ioe){
            throw new Error("Unable to create database");
        }

        try {
            DatabaseHelper.getInstance(this).openDataBase();
        } catch(SQLException sqle){
            throw sqle;
        }

        int selected = getIntent().getIntExtra("hospital", 0);
        Hospital hospital = DatabaseHelper.getInstance(this).getTerveysasema(selected);

        TextView name = findViewById(R.id.tvName);
        name.setText(hospital.toString());

        TextView address = findViewById(R.id.tvAddress);
        address.setText(hospital.getAddress());

        TextView date = findViewById(R.id.tvDate);
        date.setText(Reminder.getInstance().toString());
    }
}
