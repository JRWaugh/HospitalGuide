package com.example.hospitalguide;

import java.util.ArrayList;

public class Hospitals {
    private ArrayList<Hospital> hospitalList = new ArrayList<>();
    private static Hospitals hospitals;

    private Hospitals(){
        hospitalList.add(new Hospital("Helsinki Hospital", "Bulevardi 22, Helsinki", "Surgery", false));
        hospitalList.add(new Hospital("Mehiläinen Espoo Leppävaara", "Hevosenkenkä 3, Espoo", "General", false));
        hospitalList.add(new Hospital("Lastenlinna Children's Castle", "Lastenlinnantie 2, Helsinki", "Children's hospital", true));
    }

    public static Hospitals getInstance(){
        if(hospitals == null){
            hospitals = new Hospitals();
        }
        return hospitals;
    }

    public ArrayList<String> getTreatments(){
        ArrayList<String> treatments = new ArrayList<>();
        for(Hospital hospital : this.hospitalList){
            treatments.add(hospital.getTreatment());
        }
        return treatments;
    }

    public ArrayList<Hospital> filterHospitals(String location, String treatment){
        ArrayList <Hospital> filteredList = new ArrayList<>();
        for(Hospital hospital : hospitalList){
            if(hospital.getLocation().contains(location) && hospital.getTreatment().contains(treatment))
                filteredList.add(hospital);
        }
        return filteredList;
    }

    public Hospital getHospital(String name){
        for(Hospital hospital : hospitalList){
            if(hospital.getName().equals(name))
                return hospital;
        }
        return null;
    }
}
