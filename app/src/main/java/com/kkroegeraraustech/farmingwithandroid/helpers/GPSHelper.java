package com.kkroegeraraustech.farmingwithandroid.helpers;

import android.hardware.GeomagneticField;
import android.location.Location;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class GPSHelper {
    public interface Interface_GPS_Tag {
        String GPS_Epicenter = "GPS Epicenter";
        String GPS_User = "GPS User";
        String GPS_Location = "GPS Location";
        String GPS_Sample = "GPS Sample";
        String GPS_Target = "GPS Target";
    }
    public Location mLocation = new Location("");
    private double mValueLatitude;
    private double mValueLongitude;
    private double mValueAltitude;
    private double mDeclination;
    private String mTagDescriptor;

    //default constructor
    public GPSHelper() {
        this.mValueLatitude = 38.761751;
        this.mValueLongitude = -77.098817;
        mDeclination = 0.0;
        mValueAltitude = 0.0;
        this.mLocation.setLatitude(this.mValueLatitude);
        this.mLocation.setLongitude(this.mValueLongitude);
        this.mTagDescriptor = Interface_GPS_Tag.GPS_Location;

    }

    //overloaded constructor
    public GPSHelper(double latitude, double longitude, String TagDescriptor)
    {
        this.mValueLatitude = latitude;
        this.mValueLongitude = longitude;
        this.mTagDescriptor = TagDescriptor;
        this.mLocation.setLatitude(latitude);
        this.mLocation.setLongitude(longitude);
    }

    //set the value of the GPS location
    public void setValueGPS(double latitude, double longitude) {
        this.mValueLatitude = latitude;
        this.mValueLongitude = longitude;
        this.mLocation.setLatitude(latitude);
        this.mLocation.setLongitude(longitude);
    }

    public void setValueLatitude(double latitude){
        this.mValueLatitude = latitude;
        this.mLocation.setLatitude(latitude);
    }

    public void setLocation(Location desLocation){
        mValueLatitude = desLocation.getLatitude();
        mValueLongitude = desLocation.getLongitude();
        mValueAltitude = desLocation.getAltitude();
        GeomagneticField geoField = new GeomagneticField((float)mValueLatitude,(float)mValueLongitude,(float)mValueAltitude, System.currentTimeMillis());
        mDeclination = (double)geoField.getDeclination();
        mLocation.set(desLocation);

    }

    public Location getLocation(){
        Location tempLocation = mLocation;
        return(tempLocation);
    }

    public void setValueLongitude(double longitude) {
        this.mValueLongitude = longitude;
        this.mLocation.setLongitude(longitude);
    }
    public void setValueTag(String TagDescriptor) {
        this.mTagDescriptor = TagDescriptor;
    }

    public double getValue_Latitude() {
        return this.mValueLatitude;
    }

    public double getValue_Longitude() {
        return this.mValueLongitude;
    }

    public String getValue_TagDescription() {
        return this.mTagDescriptor;
    }

    public Location getValue_Location()
    {
        return(mLocation);
    }

    public void setAltitude(double currentAltitude){
        this.mValueAltitude = currentAltitude;
    }

    public double getAltitude(){
        return(mValueAltitude);
    }

    public void setDeclination(double currentDeclination){
        mDeclination = currentDeclination;
    }

    public double getDeclination(){
        return(mDeclination);
    }

    public void assignCopy(GPSHelper copyValue){
        this.mValueLatitude = copyValue.getValue_Latitude();
        this.mValueLongitude = copyValue.getValue_Longitude();
        this.mTagDescriptor = copyValue.getValue_TagDescription();
        this.mDeclination = copyValue.getDeclination();
        this.mLocation = copyValue.getValue_Location();
    }
}
