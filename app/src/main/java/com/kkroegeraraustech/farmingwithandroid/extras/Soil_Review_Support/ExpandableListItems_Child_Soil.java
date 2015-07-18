package com.kkroegeraraustech.farmingwithandroid.extras.Soil_Review_Support;

import com.kkroegeraraustech.farmingwithandroid.helpers.GPSHelper;

/**
 * Created by Ken Heron Systems on 6/22/2015.
 */
public class ExpandableListItems_Child_Soil {

    private String name = "DEFAULT";
    private String latitude = "DEFAULT";
    private String longitude = "DEFAULT";
    private String compaction = "DEFAULT";

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLatitude(double desLatitude){
        this.latitude = Double.toString(desLatitude);
    }

    public void setLongitude(double desiredLongitude){
        this.longitude = Double.toString(desiredLongitude);
    }

    public void setCompaction(double desiredCompaction){
        this.compaction = Double.toString(desiredCompaction);
    }

}
