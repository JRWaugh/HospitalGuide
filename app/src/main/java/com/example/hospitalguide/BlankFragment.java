package com.example.hospitalguide;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

public class BlankFragment extends Fragment {

    Locale enLocale = new Locale ("en");
    Locale svLocale = new Locale("sv");
    Locale fiLocale = new Locale("fi");

    View view;
    Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        res = view.getResources();

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
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            if(view.getId() == R.id.tvEnglish)
                conf.locale = enLocale;
            else if(view.getId() == R.id.tvFinnish)
                conf.locale = fiLocale;
            else if(view.getId() == R.id.tvSwedish)
                conf.locale = svLocale;
            res.updateConfiguration(conf, dm);
            DatabaseHelper.getInstance(getContext()).setTable(res.getString(R.string.table));

            getActivity().finish();
            startActivity(getActivity().getIntent());
        }
    }
}