package com.kkroegeraraustech.farmingwithandroid.helpers;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class SoilHelper {

    HashMap<String,SoilDetails> mSoilSampleHashMap;

    private double expectedCompaction_HIGH = 1.0;
    private double expectedCompaction_LOW = 0.0;
    private double expectedPH_HIGH = 8.0;
    private double expectedPH_LOW = 6.0;

    private String FieldName = "";
    private String AreaName = "";

    private GregorianCalendar mDateTaken;

    public SoilHelper(String fieldName, String areaName){
        this.FieldName = fieldName;
        this.AreaName = areaName;
    }

    public void setCollectionDate(int year, int month, int day){
        this.mDateTaken = new GregorianCalendar();
        this.mDateTaken.set(year,month,day);
    }


    /**
     * DEFUALT CONSTRUCTOR
     */
    public SoilHelper(){
        mSoilSampleHashMap = new HashMap<String,SoilDetails>();
        mDateTaken = new GregorianCalendar();
    }



    public void setFieldName(String desFieldName){
        this.FieldName= desFieldName;
    }
    public String getFieldName(){
        return(FieldName);
    }


    public void setAreaName(String desAreaName){
        this.AreaName = desAreaName;
    }
    public String getAreaName(){
        return(AreaName);
    }

    public GregorianCalendar getDate(){
        return(mDateTaken);
    }
    public class SoilDetails{
        private double actualCompaction = 0.0;
        private double actualPH = 7.0;
        private Marker location = new Marker(null);

        public SoilDetails(double compaction, double pH, Marker sampleLocation)
        {
            this.actualCompaction = compaction;
            this.actualPH = pH;
            this.location = sampleLocation;
        }
        public void setCompaction(double measuredCompaction) {
            this.actualCompaction = measuredCompaction;
        }
        public double getCompaction(){
            return(actualCompaction);
        }

        public void setpH(double measuredpH){
            this.actualPH = measuredpH;
        }
        public double getpH(){
            return(actualPH);
        }

        public void setLocation(Marker sampleLocation){
            this.location = sampleLocation;
        }
        public Marker getLocation(){
            return(location);
        }
    }

    public void setPHLevels(double pH_Low, double pH_High) {
        this.expectedPH_HIGH = pH_High;
        this.expectedPH_LOW = pH_Low;
    }

    public double getExpectedPH_HIGH(){
        return(expectedPH_HIGH);
    }

    public double getExpectedPH_LOW(){
        return(expectedPH_LOW);
    }


    public void setCompactionLevels(double compaction_LOW, double compaction_HIGH) {
        this.expectedCompaction_LOW = compaction_LOW;
        this.expectedCompaction_HIGH = compaction_HIGH;
    }

    public double getExpectedCompaction_HIGH(){
        return(expectedCompaction_HIGH);
    }

    public double getExpectedCompaction_LOW(){
        return(expectedCompaction_LOW);
    }

    public void addSample(double compaction, double pH, Marker sampleLocation)
    {
        SoilDetails tmpSoilDetails = new SoilDetails(compaction,pH,sampleLocation);
        mSoilSampleHashMap.put(sampleLocation.getId(),tmpSoilDetails);
    }

    public SoilDetails getSample(String sampleName)
    {
        SoilDetails tmpSoilDetails = mSoilSampleHashMap.get(sampleName);
        return(tmpSoilDetails);
    }

    public List<SoilDetails> getAllSamples(){
        List<SoilDetails> tmpList = new ArrayList<SoilDetails>(mSoilSampleHashMap.values());
        return(tmpList);
    }


}
