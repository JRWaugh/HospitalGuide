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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
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
    private static int selected;
    private static Hospital hospital;
    private static View reminderBox;
    static final String BROADCAST_ACTIVITY_CLOSE = "com.example.hospitalguide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);

        selected = getIntent().getIntExtra("hospital", 0);
        hospital = DatabaseHelper.getInstance(this).getTerveysasema(selected);

        TextView name = findViewById(R.id.tvName);
        name.setText(hospital.toString());

        TextView address = findViewById(R.id.tvAddress);
        address.setText(hospital.getAddress());

        TextView phone = findViewById(R.id.tvPhone);
        phone.append(hospital.getPhone());
        //Makes phone number clickable
        Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
        website = hospital.getWebsite();

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTIVITY_CLOSE);
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);

        reminderBox = findViewById(R.id.layoutReminder);
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

    }


    public void openLink(View view) {
        //Method to go to health centre's website when button is clicked
        Uri url = Uri.parse(website);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }

    public void showDatePickerDialog(View v) {
        //Launches DatePickerFragment
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        //Pulled from https://developer.android.com/reference/android/app/DatePickerDialog
        private int year;
        private int month;
        private int day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar current = Calendar.getInstance();
            this.year = current.get(Calendar.YEAR);
            this.month = current.get(Calendar.MONTH);
            this.day = current.get(Calendar.DAY_OF_MONTH);

            // Return new instance of DatePickerDialog
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar input = Calendar.getInstance();
            //I've set hh/mm/ss so that the if statement below won't fail
            input.set(year, month, day, 23, 59, 59);
            if(input.getTime().compareTo(new Date()) >= 0){
                Reminder.getInstance().setYear(String.valueOf(year));
                //Month count starts at 0 in the Calendar class for some reason, so we add 1.
                Reminder.getInstance().setMonth(String.valueOf(month+1));
                Reminder.getInstance().setDay(String.valueOf(day));

                //Launches TimePickerFragment
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "timePicker");
            } else {
                Toast toast = Toast.makeText(getContext(), "Invalid Date Selected", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Return new instance of TimePickerDialog
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Date input = new Date();
            input.setHours(hourOfDay);
            input.setMinutes(minute);
            input.setSeconds(59);
            Reminder.getInstance().setHour(String.valueOf(hourOfDay));
            Reminder.getInstance().setMinute(String.valueOf(minute));
            //Compare selected hh/mm to current time. Selected seconds set to 59.
            if(input.compareTo(new Date()) >= 0) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.alert_dialog_layout, null);
                alertDialog.setView(layout);
                alertDialog.setCancelable(false);
                alertDialog.setTitle(getContext().getString(R.string.confirm));
                TextView healthCentre = layout.findViewById(R.id.tvHealthCentre);
                TextView address = layout.findViewById(R.id.tvAddress);
                TextView date = layout.findViewById(R.id.tvDate);
                healthCentre.setText(hospital.toString());
                address.setText(hospital.getAddress());
                date.setText(Reminder.getInstance().toString());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.getInstance(getContext()).setReminder(selected, Reminder.getInstance().formattedDate());
                        dialog.dismiss();
                        Intent RTReturn = new Intent(ActivityHospitalInfo.BROADCAST_ACTIVITY_CLOSE);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(RTReturn);
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, alertDialog.getContext().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(alertDialog.getContext(), getContext().getString(R.string.reminderCancelledToast), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            } else {
                Toast toast = Toast.makeText(getContext(), "Invalid Date Selected", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        /*Taken from https://stackoverflow.com/questions/28821125/wait-for-dialog-click-to-restart-an-activity
        I could truly not figure out how to do this in a nice way. A fragment might have been nice.*/
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BROADCAST_ACTIVITY_CLOSE)) {
                recreate();
            }
        }
    };
}
