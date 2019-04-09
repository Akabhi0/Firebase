package com.example.firebase;

public class DistaceModel {
    private double latitudeCurrent, longitudeCurrent;
    private double latitudeFixedOne, longitudeFixedOne;
    private String name;
    private int drawable, Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitudeCurrent() {
        return latitudeCurrent;
    }

    public void setLatitudeCurrent(double latitudeCurrent) {
        this.latitudeCurrent = latitudeCurrent;
    }

    public double getLongitudeCurrent() {
        return longitudeCurrent;
    }

    public void setLongitudeCurrent(double longitudeCurrent) {
        this.longitudeCurrent = longitudeCurrent;
    }

    public double getLatitudeFixedOne() {
        return latitudeFixedOne;
    }

    public void setLatitudeFixedOne(double latitudeFixedOne) {
        this.latitudeFixedOne = latitudeFixedOne;
    }

    public double getLongitudeFixedOne() {
        return longitudeFixedOne;
    }

    public void setLongitudeFixedOne(double longitudeFixedOne) {
        this.longitudeFixedOne = longitudeFixedOne;
    }
}
