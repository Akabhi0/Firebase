package com.example.firebase;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationGetter implements LocationListener {

    public double lat, log;

    public double[] sendLatLong() {
        double[] latlng = new double[2];
        latlng[0] = lat;
        latlng[1] = log;
        return latlng;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
