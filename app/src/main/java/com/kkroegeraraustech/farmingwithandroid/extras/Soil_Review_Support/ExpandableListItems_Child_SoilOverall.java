package com.kkroegeraraustech.farmingwithandroid.extras.Soil_Review_Support;

/**
 * Created by Ken Heron Systems on 6/22/2015.
 */
public class ExpandableListItems_Child_SoilOverall {

    private String name = "DEFAULT";
    private String descriptor = "DEFAULT";
    private String value = "DEFAULT";

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescriptor(String desiredDescriptor){
        this.descriptor = desiredDescriptor;
    }

    public void setValue(String desiredValue){
        this.value = desiredValue;
    }

}
