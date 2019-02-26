package com.example.hospitalguide;

import java.util.ArrayList;

class Hospitals {
    private ArrayList<Hospital> hospitalList = new ArrayList<>();
    private static Hospitals hospitals;

    private Hospitals(){}

    static Hospitals getInstance(){
        if(hospitals == null){
            hospitals = new Hospitals();
        }
        return hospitals;
    }

    public void addHospital(Hospital hospital){
        hospitalList.add(hospital);
    }

    public void clearHospitalList(){
        /*Should be called when a selection is made in the city spinner. hospitallist should only
        ever contain hospitals from the selected city.*/
        hospitalList.clear();
    }

    /*Hospital getHospital(String name){
        for(Hospital hospital : hospitalList){
            if(hospital.getName().equals(name))
                return hospital;
        }
        return null;
    }*/
}
