package com.kkroegeraraustech.farmingwithandroid.helpers;

import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by Ken Heron Systems on 7/14/2015.
 */
public class MarkerHelper {

    HashMap<String,Marker> mMarkerStorage;
    //default constructor
    public MarkerHelper() {
        mMarkerStorage = new HashMap<String,Marker>();
    }

    public void addMarker(String descriptor, Marker markerAdd){
        mMarkerStorage.put(descriptor,markerAdd);
    }
    public void removeMarker(String descriptor) {
        Marker removableMarker = mMarkerStorage.get(descriptor);
        removableMarker.remove();
        mMarkerStorage.remove(descriptor);
    }

    public Marker getMarker(String descriptor){
        Marker tmpMarker = mMarkerStorage.get(descriptor);
        return (tmpMarker);
    }
}
