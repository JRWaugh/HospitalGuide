package com.example.hospitalguide;

public class Hospital {
    private String name;
    private String location;
    private String treatment;
    private Boolean isPublic;

    public Hospital(String name, String location, String treatment, Boolean isPublic){
        this.name = name;
        this.location = location;
        this.treatment = treatment;
        this.isPublic = isPublic;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public String getLocation(){
        return this.location;
    }

    public String getName(){
        return this.name;
    }

    public Boolean getIsPublic(){
        return this.isPublic;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
