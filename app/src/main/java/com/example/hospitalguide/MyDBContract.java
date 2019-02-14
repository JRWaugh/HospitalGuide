package com.example.hospitalguide;

import android.provider.BaseColumns;

public final class MyDBContract {
    private MyDBContract() {
    }

    public static class HospitalDatabase implements BaseColumns {
        public static final String TABLE_NAME = "Hospitals";
        public static final String COLUMN_CITY = "City";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_TYPE = "Type"; // meaning hospital or health centre
        public static final String COLUMN_PUBLIC = "Public";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CITY + " TEXT, " + COLUMN_NAME + " TEXT, " + COLUMN_ADDRESS +
                " TEXT, " + COLUMN_TYPE + " TEXT, " + COLUMN_PUBLIC + " BOOLEAN" + ")";

    }
}
