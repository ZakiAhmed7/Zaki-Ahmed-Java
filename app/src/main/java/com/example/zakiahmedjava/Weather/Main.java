package com.example.zakiahmedjava.Weather;

// class Main(
//    val feels_like: Double,
//    val humidity: Int,
//    val pressure: Int,
//    val temp: Double,
//    val temp_max: Double,
//    val temp_min: Double
//)

public class Main {
    protected Double feels_like;
    protected int humidity;
    protected int pressure;
    protected Double temp;
    protected Double temp_max;
    protected double temp_min;

    public Double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }
}