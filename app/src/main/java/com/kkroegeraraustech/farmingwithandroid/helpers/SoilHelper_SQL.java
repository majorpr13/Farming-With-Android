package com.kkroegeraraustech.farmingwithandroid.helpers;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Ken Heron Systems on 7/24/2015.
 */
public class SoilHelper_SQL {

    private double actualCompaction = 0.0;
    private double actualPH = 7.0;
    private double sampleLatitude = 0.0;
    private double sampleLongitude = 0.0;


    /**
     * A Default Constructor
     */
    public void SoilHelper_SQL(){
        this.actualCompaction = 0.0;
        this.actualPH = 0.0;
        this.sampleLatitude = 0.0;
        this.sampleLongitude = 0.0;
    }
    /**
     * An Overloaded Default Constructor
     * @param compaction
     * @param pH
     * @param sampleLat
     * @param sampleLon
     */
    public void setValues(double sampleLat, double sampleLon , double compaction, double pH)
    {
        this.actualCompaction = compaction;
        this.actualPH = pH;
        this.sampleLatitude = sampleLat;
        this.sampleLongitude = sampleLon;
    }
}
