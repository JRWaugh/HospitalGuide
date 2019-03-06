package com.example.hospitalguide;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ActivityHospitalInfo extends AppCompatActivity {
    private String website;
    private static int selected;
    private static Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);

        selected = getIntent().getIntExtra("hospital", 0);
        hospital = DatabaseHelper.getInstance(this).getTerveysasema(selected);

        TextView name = findViewById(R.id.tvName);
        name.setText(hospital.toString());

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

    public void showDatePickerDialog(View v) {
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
            input.set(year, month, day);
            if(input.getTimeInMillis() >= System.currentTimeMillis()){
                Reminder.getInstance().setYear(String.valueOf(year));
                Reminder.getInstance().setMonth(String.valueOf(month));
                Reminder.getInstance().setDay(String.valueOf(day));
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "timePicker");
            } else {
                Toast toast = Toast.makeText(getContext(), "Invalid Date Selected", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

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
            Reminder.getInstance().setHour(String.valueOf(hourOfDay));
            Reminder.getInstance().setMinute(String.valueOf(minute));

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.alert_dialog_layout, null);
            alertDialog.setView(layout);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Confirm Details");
            TextView healthCentre = layout.findViewById(R.id.tvHealthCentre);
            TextView address = layout.findViewById(R.id.tvAddress);
            TextView date = layout.findViewById(R.id.tvDate);
            healthCentre.setText(hospital.toString());
            address.setText(hospital.getAddress());
            date.setText(Reminder.getInstance().toString());
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("Tag", String.valueOf(selected));
                            Log.d("Tag", Reminder.getInstance().formattedDate());
                            DatabaseHelper.getInstance(getContext()).setReminder(selected, Reminder.getInstance().formattedDate());
                            //Toast.makeText(getContext(),"Reminder set!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(getContext(),"Reminder cancelled!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();
        }

    }
}
