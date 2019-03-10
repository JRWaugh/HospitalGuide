package com.example.hospitalguide;

public class Hospital {
    private int id;
    private String name;
    private String region;
    private String city;
    private String address;
    private String phone;
    private String website;
    private String appointment;

    public Hospital(){}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        //Acts as getName. ArrayAdapter calls object's toString method, so this is useful.
        return this.name;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region){
        this.region = region;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }
}
