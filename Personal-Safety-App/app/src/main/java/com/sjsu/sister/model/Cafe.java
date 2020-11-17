package com.sjsu.sister.model;

public class Cafe {
    private String vicinity = "";
    private String name = "";
    private String rating =  "";
    private String lat =  "";
    private String lng =  "";

    public Cafe() {
    }

    public Cafe(String vicinity, String name, String rating, String lat, String lng ) {
        this.vicinity = vicinity;
        this.name = name;
        this.rating = rating;
        this.lat = lat;
        this.lng =lng;
    }

    public String getVicinity() {
        return vicinity;
    }

    public String getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}