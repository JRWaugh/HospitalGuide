package com.example.hospitalguide;

public class Hospital {
    private int id;
    private String name;
    private String region;
    private String city;
    private String description;

    public Hospital(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        //Acts as getName. ArrayAdapter calls object's toString method, so this is useful.
        return this.name;
    }

    public void setRegion(String region){
        this.region = region;
    }

    public String getRegion() {
        return this.region;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
