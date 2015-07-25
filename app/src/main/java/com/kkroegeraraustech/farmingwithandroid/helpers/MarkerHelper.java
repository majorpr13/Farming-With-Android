package com.kkroegeraraustech.farmingwithandroid.helpers;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by Ken Heron Systems on 7/14/2015.
 */
public class MarkerHelper {

    HashMap<String,Marker> mMarkerStorage;
    HashMap<String,Circle> mCircleStorage;
    //default constructor
    public MarkerHelper() {
        mMarkerStorage = new HashMap<String,Marker>();
        mCircleStorage = new HashMap<String,Circle>();
    }

    public void addMarker(String descriptor, Marker markerAdd){
        mMarkerStorage.put(descriptor,markerAdd);
    }

    public void addCircle(String descriptor, Circle circleAdd){
        mCircleStorage.put(descriptor,circleAdd);
    }

    public void removeMarker(String descriptor) {
        Marker removableMarker = mMarkerStorage.get(descriptor);
        if(removableMarker != null){
            removableMarker.remove();
            mMarkerStorage.remove(descriptor);
        }

    }
    public void removeCircle(String descriptor) {
        Circle removableCircle = mCircleStorage.get(descriptor);
        if(removableCircle != null){
            removableCircle.remove();
            mCircleStorage.remove(descriptor);
        }
    }

    public Marker getMarker(String descriptor){
        Marker tmpMarker = mMarkerStorage.get(descriptor);
        return (tmpMarker);
    }

    public Circle getCircle(String descriptor){
        Circle tmpMarker = mCircleStorage.get(descriptor);
        return (tmpMarker);
    }
}
