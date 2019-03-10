package com.example.hospitalguide;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/** This activity is accessed after a health centre has been selected in another activity.
 * This activity will search the Database for the health centre selected and display all information
 */

public class ActivityHospitalInfo extends AppCompatActivity {
    private String website;
    private Hospital hospital;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private AlertDialog alertDialog;
    private Context mContext;
    private LinearLayout reminderBox;

    //Creates a Calendar to hold time selected in calendar/clock dialogs.
    private Calendar input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);
        reminderBox = findViewById(R.id.layoutReminder);
        input = Calendar.getInstance();
        mContext = this;

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

        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //I've set hh/mm/ss so that the if statement below won't fail
                input.set(year, month, day, 23, 59, 59);
                if(input.getTime().after(new Date())){
                    timePicker.show();
                } else
                    Toast.makeText(view.getContext(), getString(R.string.invalid), Toast.LENGTH_SHORT).show();
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                //Date input = new Date();
                input.set(Calendar.HOUR_OF_DAY, hour);
                input.set(Calendar.MINUTE, minute);
                input.set(Calendar.SECOND, 59);

                //Compare selected hh/mm to current time. Selected seconds set to 59.
                if(input.getTime().after(new Date())) {
                    //Creates a string in a format suited for human readability
                    String alertDateString = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(input.getTime());
                    alertDialog = new AlertDialog.Builder(mContext).create();
                    //Grabs the layout for this dialog so that the views can be accessed
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.alert_dialog_layout, null);
                    TextView alertHealthCentre = layout.findViewById(R.id.tvHealthCentre);
                    TextView alertAddress = layout.findViewById(R.id.tvAddress);
                    TextView alertDate = layout.findViewById(R.id.tvDate);
                    alertHealthCentre.setText(hospital.toString());
                    alertAddress.setText(hospital.getAddress());
                    alertDate.setText(alertDateString);
                    alertDialog.setView(layout);
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getString(R.string.confirm));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Creates a string in a format suited for the database
                            String databaseDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(input.getTime());
                            DatabaseHelper.getInstance(mContext).setReminder(hospital.getId(), databaseDateString);
                            hospital.setAppointment(databaseDateString);
                            Toast.makeText(mContext, getString(R.string.reminderToast), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            displayReminder();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, alertDialog.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, getString(R.string.reminderCancelledToast), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else
                    Toast.makeText(view.getContext(), getString(R.string.invalid), Toast.LENGTH_SHORT).show();
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(hospital.getAppointment() != null)
            displayReminder();
    }

    public void setReminder(View v) {
        datePicker.show();
    }

    public void openLink(View view) {
        //Method to go to health centre's website when button is clicked
        Uri url = Uri.parse(website);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }

    private void displayReminder(){
        //Creates a date object from the date string in the database
        Date reminder = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            reminder = sdf.parse(hospital.getAppointment());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        reminder.setSeconds(59);
        if(reminder.before(new Date())) {
            DatabaseHelper.getInstance(this).setReminder(hospital.getId(), null);
            reminderBox.setVisibility(View.INVISIBLE);
        } else {
            //Converts the date object back into a human readable string
            String date = new SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault()).format(reminder);
            TextView time = findViewById(R.id.tvTime);
            time.setText(date);
            reminderBox.setVisibility(View.VISIBLE);
        }
    }

    public void cancelReminder(View view) {
        final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(mContext);
        cancelDialog.setTitle(getString(R.string.confirmCancel));
        cancelDialog.setCancelable(false);
        cancelDialog.setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                /*If confirmation is given, the reminder in the database will be cleared and the
                reminderBox layout will be set back to invisible.*/
                DatabaseHelper.getInstance(mContext).setReminder(hospital.getId(), null);
                reminderBox.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        }).setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        cancelDialog.show();
    }
}
