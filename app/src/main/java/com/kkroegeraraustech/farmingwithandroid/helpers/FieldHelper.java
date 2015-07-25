package com.kkroegeraraustech.farmingwithandroid.helpers;

import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class FieldHelper {
    /**
     *
     */
    public interface Interface_Field_Description {
        String FIELD_HEALTHY = "HEALTHY";
        String FIELD_MEDIOCRE = "MEDIOCRE";
        String FIELD_WEAK = "WEAK";
        String FIELD_STRENGTHENING = "STRENGTHENING";
        String FIELD_WEAKENING = "WEAKENING";
        String FIELD_DEFAULT = "DEFAULT";
    }

    /**
     *
     */
    public interface Interface_CropType {
        String CROP_CORN = "CORN";
        String CROP_SOYBEANS = "SOYBEANS";
        String CROP_WHEAT = "WHEAT";
        String CROP_TOMATOES = "TOMATOES";
        String CROP_POTATOES = "POTATOES";
        String CROP_DEFAULT = "DEFAULT";
    }
    public String [] valuesCrop =
            {Interface_CropType.CROP_DEFAULT,Interface_CropType.CROP_CORN,Interface_CropType.CROP_SOYBEANS,
                    Interface_CropType.CROP_WHEAT,Interface_CropType.CROP_TOMATOES,Interface_CropType.CROP_POTATOES};

    private int id;
    private String FieldName;
    private String mFieldDesciptor;
    private String mCropType;

    private List<LatLng> mListFieldEnclosure;

    /**
     *
     * @param desID
     * @param desFieldName
     */
    public FieldHelper(int desID, String desFieldName){
        this.id = desID;
        this.FieldName = desFieldName;
        mListFieldEnclosure = new ArrayList<LatLng>();
    }

    /**
     *
     * @param desFieldName
     */
    public FieldHelper(String desFieldName){
        this.FieldName = desFieldName;
        mListFieldEnclosure = new ArrayList<LatLng>();
    }

    /**
     *
     */
    public FieldHelper(){
        mListFieldEnclosure = new ArrayList<LatLng>();
    }

    /**
     *
     * @param polygonList
     */
    public void setPolygonList(List<LatLng> polygonList)
    {
        mListFieldEnclosure.clear();
        for(int i = 0; i<polygonList.size(); i++){
            this.mListFieldEnclosure.add(polygonList.get(i));
        }
    }

    /**
     *
     * @return
     */
    public List<LatLng> getPolygonList(){
         return(mListFieldEnclosure);
    }

    /**
     *
     * @param insertPoint
     */
    public void addPolygonPoint(LatLng insertPoint){
        mListFieldEnclosure.add(insertPoint);
    }

    /**
     *
     * @param removePoint
     */
    public void removePolygonPoint(LatLng removePoint){
        mListFieldEnclosure.remove(removePoint);
    }

    /**
     *
     * @param desID
     */
    public void setId(int desID){
        this.id = desID;
    }

    /**
     *
     * @return
     */
    public int getId(){
        int tmpInt = this.id;
        return(tmpInt);
    }

    /**
     *
     * @param desFieldName
     */
    public void setFieldName(String desFieldName){
        this.FieldName = desFieldName;
    }

    /**
     *
     * @return
     */
    public String getFieldName(){
        String tmpString = this.FieldName;
        return(tmpString);
    }

    /**
     *
     * @param cropType
     */
    public void setCropType(String cropType){

        mCropType = cropType;
    }

    /**
     *
     * @return
     */
    public String getCropType(){
        String tmpValue = mCropType;
        return(tmpValue);
    }

    /**
     *
     * @param fieldDesciptor
     */
    public void setFieldDesciptor(String fieldDesciptor) {
        mFieldDesciptor = fieldDesciptor;
    }

    /**
     *
     * @return
     */
    public String getFieldDescriptor(){
        String tmpValue = mFieldDesciptor;
        return(tmpValue);
    }
}
