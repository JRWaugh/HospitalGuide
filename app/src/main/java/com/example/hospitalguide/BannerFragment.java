package com.example.hospitalguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Locale;

/** This fragment is placed in each Activity and acts as a banner for the app. This fragment holds
 * three buttons to change the language of the app (English, Swedish and Finnish), as well as a home
 * icon to return the user to the ActivityHospitalSearch activity.*/
public class BannerFragment extends Fragment {

    private SharedPreferences sharedPref;
    private String language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner, container, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = sharedPref.getString("language", "en");
        Resources res = this.getActivity().getResources();
        Configuration conf = res.getConfiguration();
        Locale locale = new Locale(language);
        conf.locale = locale;
        Locale.setDefault(locale);
        res.updateConfiguration(conf, res.getDisplayMetrics());
        DatabaseHelper.getInstance(getContext()).setTable(res.getString(R.string.table));

        //Creates database (or opens database if already created)

        DatabaseHelper.getInstance(getContext()).createDatabase();
        try {
            DatabaseHelper.getInstance(getContext()).openDataBase();
        } catch(SQLException sqle){
            throw sqle;
        }

        MyClickListener listener = new MyClickListener();
        TextView btnEnglish = view.findViewById(R.id.tvEnglish);
        btnEnglish.setOnClickListener(listener);
        TextView btnFinnish = view.findViewById(R.id.tvFinnish);
        btnFinnish.setOnClickListener(listener);
        TextView btnSwedish = view.findViewById(R.id.tvSwedish);
        btnSwedish.setOnClickListener(listener);

        ImageButton btnHome = view.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityHospitalSearch.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //This is to make sure the Locale stays the same when pressing the back button.
        String oldLanguage = language;
        language = sharedPref.getString("language", "en");
        if (!oldLanguage.equals(language)){
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String language = "";
            if(view.getId() == R.id.tvEnglish)
                language = "en";
            else if(view.getId() == R.id.tvFinnish)
                language = "fi";
            else if(view.getId() == R.id.tvSwedish)
                language = "sv";

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("language", language);
            editor.apply();
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }

    }
}