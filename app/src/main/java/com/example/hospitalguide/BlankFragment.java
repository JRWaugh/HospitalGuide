package com.example.hospitalguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

public class BlankFragment extends Fragment {

    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String language = sharedPref.getString("language", "en");
        Resources res = this.getActivity().getResources();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language);
        res.updateConfiguration(conf, res.getDisplayMetrics());
        DatabaseHelper.getInstance(getContext()).setTable(res.getString(R.string.table));
        
        //Creates database (or opens database if already created)
        try {
            DatabaseHelper.getInstance(getContext()).createDatabase();
        } catch(IOException ioe){
            throw new Error("Unable to create database");
        }

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
        return view;
    }

    public class MyClickListener implements View.OnClickListener {
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