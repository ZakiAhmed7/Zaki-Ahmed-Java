package com.example.zakiahmedjava.Weather;

//class Sys(
//    val country: String,
//    val id: Int,
//    val sunrise: Int,
//    val sunset: Int,
//    val type: Int
//)

public class Sys {
    protected String country;
    protected int id;
    protected int sunrise;
    protected int sunset;
    protected int type;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}