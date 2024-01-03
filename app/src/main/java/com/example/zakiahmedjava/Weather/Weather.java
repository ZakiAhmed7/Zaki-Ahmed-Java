package com.example.zakiahmedjava.Weather;

// class Weather(
//    val description: String,
//    val icon: String,
//    val id: Int,
//    val main: String
//)

public class Weather {
    protected String description;
    protected String icon;

    protected int id;
    protected String main;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}