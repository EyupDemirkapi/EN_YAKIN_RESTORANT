package com.example.enyakinrestorant;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String city;
    private String district;
    private double x;
    private double y;
    private List<String> favRestaurants;

    public User() {
        this.city = "İstanbul";
        this.district = "Kadıköy";
        this.x = 100.0;
        this.y = 100.0;
        this.favRestaurants = new ArrayList<>();
    }

    public User(String username, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.city = "İstanbul";
        this.district = "Kadıköy";
        this.x = 100.0;
        this.y = 100.0;
        this.favRestaurants = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSecurityQuestion() { return securityQuestion; }
    public void setSecurityQuestion(String securityQuestion) { this.securityQuestion = securityQuestion; }

    public String getSecurityAnswer() { return securityAnswer; }
    public void setSecurityAnswer(String securityAnswer) { this.securityAnswer = securityAnswer; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public List<String> getFavRestaurants() { return favRestaurants; }
    public void setFavRestaurants(List<String> favRestaurants) { this.favRestaurants = favRestaurants; }
}