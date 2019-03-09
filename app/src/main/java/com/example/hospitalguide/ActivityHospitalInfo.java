package com.example.hospitalguide;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityHospitalInfo extends AppCompatActivity {
    private String website;
    private Hospital hospital;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private AlertDialog alertDialog;
    private String reminderDate;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);
        mContext = this;
        reminderDate = "";

        int selectedHospital = getIntent().getIntExtra("hospital", 0);
        hospital = DatabaseHelper.getInstance(this).getTerveysasema(selectedHospital);

        TextView name = findViewById(R.id.tvName);
        name.setText(hospital.toString());

        TextView address = findViewById(R.id.tvAddress);
        address.setText(hospital.getAddress());

        TextView phone = findViewById(R.id.tvPhone);
        phone.append(hospital.getPhone());

        //Makes phone number clickable
        Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
        website = hospital.getWebsite();

        View reminderBox = findViewById(R.id.layoutReminder);
        if(hospital.getAppointment() != null){
            Date reminder = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("sv"));
            try {
                reminder = sdf.parse(hospital.getAppointment());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            reminder.setSeconds(59);
            if(reminder.compareTo(new Date()) < 0)
                DatabaseHelper.getInstance(this).setReminder(hospital.getId(), null);
            else {
                //These two lines chop off the end of the Date string so it looks nicer.
                String display = reminder.toString().split("GMT")[0];
                display = display.substring(0, display.length() - 4);
                TextView time = findViewById(R.id.tvTime);
                time.setText(display);
                reminderBox.setVisibility(View.VISIBLE);
            }
        }

        Button buttonCancel = findViewById(R.id.btnCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper.getInstance(view.getContext()).setReminder(hospital.getId(), null);
                finish();
                startActivity(getIntent());
            }
        });

        final Calendar c = Calendar.getInstance();

        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar input = Calendar.getInstance();
                //Calendar class counts months from 0, so the number must be incremented
                month++;
                //I've set hh/mm/ss so that the if statement below won't fail
                input.set(year, month, day, 23, 59, 59);
                if(input.getTime().after(new Date())){
                    reminderDate = year + "-" + month + "-" + day + " ";
                    timePicker.show();
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "Invalid Date Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Date input = new Date();
                input.setHours(hour);
                input.setMinutes(minute);
                input.setSeconds(59);
                reminderDate += hour + ":" + minute;
                //Compare selected hh/mm to current time. Selected seconds set to 59.
                if(input.after(new Date())) {
                    alertDialog = new AlertDialog.Builder(mContext).create();
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.alert_dialog_layout, null);
                    TextView alertHealthCentre = layout.findViewById(R.id.tvHealthCentre);
                    TextView alertAddress = layout.findViewById(R.id.tvAddress);
                    TextView alertDate = layout.findViewById(R.id.tvDate);
                    alertHealthCentre.setText(hospital.toString());
                    alertAddress.setText(hospital.getAddress());
                    alertDate.setText(reminderDate);
                    alertDialog.setView(layout);
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getString(R.string.confirm));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper.getInstance(mContext).setReminder(hospital.getId(), reminderDate);
                            dialog.dismiss();
                            recreate();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, alertDialog.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, getString(R.string.reminderCancelledToast), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "Invalid Date Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
    }

    public void setReminder(View v) {
        datePicker.show();
    };

    public void openLink(View view) {
        //Method to go to health centre's website when button is clicked
        Uri url = Uri.parse(website);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }
}
