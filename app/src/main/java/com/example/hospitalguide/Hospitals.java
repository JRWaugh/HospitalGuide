package com.example.hospitalguide;

import java.util.ArrayList;

class Hospitals {
    private ArrayList<Hospital> hospitalList = new ArrayList<>();
    private static Hospitals hospitals;

    private Hospitals(){
        hospitalList.add(new Hospital("Helsinki", "Helsinki Hospital","Bulevardi 22", "Hospital", false));
        hospitalList.add(new Hospital("Leppävaara", "Leppävaara Health Centre", "Konstaapelinkatu 2", "Terveysasema", true));
        hospitalList.add(new Hospital("Leppävaara", "Terveystalo Espoo Leppävaara", "Alberganesplanadi 1", "Terveysasema", false));
        hospitalList.add(new Hospital("Leppävaara", "Terveystalo Leppävaara Sello", "Leppävaarankatu 7 A", "Terveysasema",false));
        hospitalList.add(new Hospital("Leppävaara", "Mehiläinen Espoo Leppävaara", "Hevosenkenkä 3", "Terveysasema", false));
    }

    static Hospitals getInstance(){
        if(hospitals == null){
            hospitals = new Hospitals();
        }
        return hospitals;
    }

    ArrayList<String> getTreatments(){
        ArrayList<String> treatments = new ArrayList<>();
        for(Hospital hospital : this.hospitalList){
            if(!treatments.contains(hospital.getTreatment()))
                treatments.add(hospital.getTreatment());
        }
        return treatments;
    }

    ArrayList<Hospital> filterHospitals(String location, String treatment){
        ArrayList <Hospital> filteredList = new ArrayList<>();
        for(Hospital hospital : hospitalList){
            if(hospital.getLocation().contains(location) && hospital.getTreatment().contains(treatment))
            if(hospital.getLocation().contains(location))
                filteredList.add(hospital);
        }
        return filteredList;
    }

    Hospital getHospital(String name){
        for(Hospital hospital : hospitalList){
            if(hospital.getName().equals(name))
                return hospital;
        }
        return null;
    }
}
