package com.kkroegeraraustech.farmingwithandroid.helpers;

/**
 * Created by Ken Heron Systems on 6/24/2015.
 */
public class AreaHelper {

    //the base units will always be converted to square meters
    public interface Interface_Field_Unit {
        String AREA_M= "Square M";
        String AREA_KMS = "Square KM";
        String AREA_FT = "Square FT";
        String AREA_ACRES = "ACRES";
    }
    private double mFieldSize = 0.0;
    private String mAreaUnit = Interface_Field_Unit.AREA_ACRES;

    public double getFieldArea(String DesiredUnit) {

        return mFieldSize;
    }

    public void setFieldArea(String SettingUnit, double fieldSize){
        this.mFieldSize = fieldSize;
    }


    public String getAreaUnit() {
        return mAreaUnit;
    }

    public void setFieldUnit(String areaUnit) {
        mAreaUnit = areaUnit;
    }

    private void convertUnit_Input(String CurrentUnit, double FieldSize)
    {
        switch(CurrentUnit){
            case Interface_Field_Unit.AREA_M:
                mFieldSize = FieldSize;
                break;
            case Interface_Field_Unit.AREA_KMS:
                mFieldSize = FieldSize * (1000.0 * 1000.0);
                break;
            case Interface_Field_Unit.AREA_FT:
                mFieldSize = FieldSize * 0.092903;
                break;
            case Interface_Field_Unit.AREA_ACRES:
                mFieldSize = FieldSize * 4046.86;
                break;
        }
    }

    private double convertUnit_Output(String RequestedUnit)
    {
        double outputValue = 0.0;
        switch(RequestedUnit){
            case Interface_Field_Unit.AREA_M:
                outputValue = mFieldSize;
                break;
            case Interface_Field_Unit.AREA_KMS:
                outputValue  = mFieldSize / (1000.0 * 1000.0);
                break;
            case Interface_Field_Unit.AREA_FT:
                outputValue = mFieldSize / 0.092903;
                break;
            case Interface_Field_Unit.AREA_ACRES:
                outputValue = mFieldSize / 4046.86;
                break;
        }
        return(outputValue);
    }

}
