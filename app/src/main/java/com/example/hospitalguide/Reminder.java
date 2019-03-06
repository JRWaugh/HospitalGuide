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
        this.year = addLeadingZero(year);
    }

    public void setMonth(String month) {
        this.month = addLeadingZero(month);
    }


    public void setDay(String day) {
        this.day = addLeadingZero(day);
    }

    public void setHour(String hour) {
        this.hour = addLeadingZero(hour);
    }

    public void setMinute(String minute) {
        this.minute = addLeadingZero(minute);
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

    public String addLeadingZero(String input){
        String leadingZero = "";
        if(Integer.valueOf(input) >= 0 && Integer.valueOf(input) <= 9){
            leadingZero = "0";
        }
        return leadingZero + input;
    }

    @Override
    public String toString(){
        return hour + ":" + minute + "   " + day + "/" + month + "/" + year;
    }

    public String formattedDate(){
        return this.year + "-" + this.month + "-" + this.day + " " + this.hour + ":" + this.minute;
    }
}
