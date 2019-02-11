package com.example.hospitalguide;

import java.util.ArrayList;
import java.util.HashMap;

public class Locations {
    private static Locations locations;
    private HashMap<String, ArrayList<String>> cities = new HashMap<>();
    private String selected;

    private Locations(){
        this.selected = "Helsinki";
        this.cities.put("Espoo", getEspooSuburbs());
        this.cities.put("Helsinki", getHelsinkiSuburbs());
        this.cities.put("Vantaa", getVantaaSuburbs());
    }

    public static Locations getInstance(){
        if(locations == null){
            locations = new Locations();
        }
        return locations;
    }

    public ArrayList<String> getLocationList(){
        ArrayList<String> cityNames = new ArrayList<>();
        cityNames.addAll(this.cities.keySet());
        return cityNames;
    }

    public ArrayList<String> getSelectedSuburbs(){
        return this.cities.get(this.selected);

    }

    public void setSelectedCity(String selected){
        this.selected = selected;
    }

    public String getSelectedCity(){
        return this.selected;
    }

    public ArrayList<String> getEspooSuburbs(){
        ArrayList<String> espooSuburbs = new ArrayList<>();
        espooSuburbs.add("Leppavaara");
        espooSuburbs.add("Kilo");
        espooSuburbs.add("Espoon Keskus");
        return espooSuburbs;
    }

    public ArrayList<String> getHelsinkiSuburbs(){
        ArrayList<String> helsinkiSuburbs = new ArrayList<>();
        helsinkiSuburbs.add("Pasila");
        helsinkiSuburbs.add("Kamppi");
        helsinkiSuburbs.add("Töölö");
        return helsinkiSuburbs;
    }

    public ArrayList<String> getVantaaSuburbs(){
        ArrayList<String> vantaaSuburbs = new ArrayList<>();
        vantaaSuburbs.add("Tikkurila");
        vantaaSuburbs.add("Hakunila");
        vantaaSuburbs.add("Koivukylä");
        vantaaSuburbs.add("Korso");
        vantaaSuburbs.add("Aviapolis");
        vantaaSuburbs.add("Myyrmäki");
        vantaaSuburbs.add("Kivistö");
        return vantaaSuburbs;
    }
}
