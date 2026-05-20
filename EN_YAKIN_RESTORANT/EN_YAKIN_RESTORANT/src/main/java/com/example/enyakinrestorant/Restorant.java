package com.example.enyakinrestorant;

import java.util.ArrayList;
import java.util.List;

public class Restorant {
    private String name;
    private String type;
    private String city;
    private String district;
    private double x;
    private double y;
    private double rating;
    private List<String> features;

    public Restorant() {
        this.features = new ArrayList<>();
    }

    public Restorant(String name, String type, String city, String district, double x, double y, double rating, List<String> features) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.district = district;
        this.x = x;
        this.y = y;
        this.rating = rating;
        this.features = features;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }
}