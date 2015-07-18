package com.kkroegeraraustech.farmingwithandroid.helpers;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class FieldHelper {
    public interface Interface_Field_Description {
        String FIELD_HEALTHY = "HEALTHY";
        String FIELD_MEDIOCRE = "MEDIOCRE";
        String FIELD_WEAK = "WEAK";
        String FIELD_STRENGTHENING = "STRENGTHENING";
        String FIELD_WEAKENING = "WEAKENING";
        String FIELD_DEFAULT = "DEFAULT";
    }
    public interface Interface_CropType {
        String CROP_CORN = "CORN";
        String CROP_SOYBEANS = "SOYBEANS";
        String CROP_WHEAT = "WHEAT";
        String CROP_TOMATOES = "TOMATOES";
        String CROP_POTATOES = "POTATOES";
        String CROP_DEFAULT = "DEFAULT";
    }

    private String mFieldDesciptor;
    private String mCropType;


    public void setCropType(String cropType){

        mCropType = cropType;
    }

    public String getCropType(){
        String tmpValue = mCropType;
        return(tmpValue);
    }


    public void setFieldDesciptor(String fieldDesciptor) {
        mFieldDesciptor = fieldDesciptor;
    }

    public String getFieldDescriptor(){
        String tmpValue = mFieldDesciptor;
        return(tmpValue);
    }
}
