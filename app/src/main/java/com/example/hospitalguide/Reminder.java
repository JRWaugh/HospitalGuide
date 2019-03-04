package com.example.hospitalguide;

import android.content.Context;

public class Reminder {
    private static Reminder reminder;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;

    public static synchronized Reminder getInstance() {
        if (reminder == null) {
            reminder = new Reminder();
        }
        return reminder;
    }

    public Reminder(){}

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    @Override
    public String toString(){
        return hour + ":" + minute + "   " + day + "/" + month + "/" + year;
    }
}
