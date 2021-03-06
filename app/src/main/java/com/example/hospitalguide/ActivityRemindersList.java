package com.example.hospitalguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.util.List;

/** An activity which displays all reminders which have been set by the user.*/

public class ActivityRemindersList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView lv = findViewById(R.id.lvReminders);
        try {
            lv.setAdapter(new myCustomAdapter(this, R.layout.custom_reminders_layout, DatabaseHelper.getInstance(this).getReminders()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(lv.getAdapter().getCount() == 0){
            TextView isEmpty = findViewById(R.id.tvEmpty);
            isEmpty.setVisibility(View.VISIBLE);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                Intent hospitalInfo = new Intent(view.getContext(), ActivityHospitalInfo.class);
                Hospital hospital = (Hospital)parent.getItemAtPosition(pos);
                hospitalInfo.putExtra("hospital", hospital.getId());
                startActivity(hospitalInfo);
            }
        });
    }

    private class myCustomAdapter extends ArrayAdapter<Hospital> {
        /* Influenced by https://stackoverflow.com/questions/38194830/how-do-i-align-text-to-the-left-and-right-at-the-same-time-in-a-listview
        A custom adapter was needed so that the name AND reminder date would be displayed in the same view*/
        myCustomAdapter(Context context, int resource, List<Hospital> hc) {
            super(context, resource, hc);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater vi = LayoutInflater.from(getContext());
                view = vi.inflate(R.layout.custom_reminders_layout, null);
            }
            Hospital hospital = getItem(position);

            if (hospital != null) {
                TextView name = view.findViewById(R.id.tvHospitalName);
                TextView date = view.findViewById(R.id.tvReminder);
                name.setText(hospital.toString());
                date.setText(hospital.getAppointment());
            }
            return view;
        }
    }
}
