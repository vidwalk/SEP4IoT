package com.application.cmapp.model;

public class Reading {

    private double temperature, humidity, co2, light;
    private String dateTime;

    public Reading(double temperature, double humidity, double co2, double light, String dateTime)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.light = light;
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getCo2() {
        return co2;
    }

    public double getLight() {
        return light;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setTemperature(double temperature){
        this.temperature = temperature;
    }

    public void setHumidity(double humidity)
    {
        this.humidity = humidity;
    }

    public void setCo2(double co2)
    {
        this.co2 = co2;
    }

    public void setLight(double light)
    {
        this.light = light;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

}
