package com.application.cmapp.model;

public class Reading {

    private double temperature, humidity, co2, sound;
    private String dateTime;
    private int deviceNo;

    public Reading(double temperature,int deviceNo, double humidity, double co2, double sound, String dateTime)
    {
        this.temperature = temperature;
        this.deviceNo = deviceNo;
        this.humidity = humidity;
        this.co2 = co2;
        this.sound = sound;
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getDeviceNo() {
        return deviceNo;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getCo2() {
        return co2;
    }

    public double getSound() {
        return sound;
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

    public void setSound(double sound)
    {
        this.sound = sound;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

    public void setDeviceNo(int deviceNo)
    {
        this.deviceNo = deviceNo;
    }

}
