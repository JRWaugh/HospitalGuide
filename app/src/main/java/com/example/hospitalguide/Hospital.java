package com.example.hospitalguide;

public class Hospital {
    private String name;
    private String city;
    private String address;
    private String treatment;
    private Boolean isPublic;

    public Hospital(String city, String name, String address, String treatment, Boolean isPublic){
        this.city = city;
        this.name = name;
        this.address = address;
        this.treatment = treatment;
        this.isPublic = isPublic;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public String getLocation(){
        return this.city;
    }

    public String getName(){
        return this.name;
    }

    public String getIsPublic(){
        if(this.isPublic)
            return "This is a public hospital.";
        else return "This is a private hospital.";
    }

    @Override
    public String toString(){
        return this.name;
    }
}
