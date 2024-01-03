package com.example.zakiahmedjava.Weather;

//class Wind(
//    val deg: Int,
//    val speed: Double
//)

public class Wind {
    protected int deg;
    protected Double speed;

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}