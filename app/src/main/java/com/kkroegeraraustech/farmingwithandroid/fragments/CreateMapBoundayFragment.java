package com.kkroegeraraustech.farmingwithandroid.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.kkroegeraraustech.farmingwithandroid.R;


import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ken Heron Systems on 7/20/2015.
 */
public class CreateMapBoundayFragment extends Fragment implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static String TAB_NAME = "FIELD BOUNDARY";
    public String getTabName(){
        return (TAB_NAME);
    }

    private static View mLayout;
    private FragmentActivity mListener;

    private MapView mMapView;
    private GoogleMap mMap;
    private HashMap<String,LatLng> mMarkerHash = new HashMap<String,LatLng>();
    //private List<LatLng> mMarkerList = new ArrayList<LatLng>();
    PolygonOptions mPolygonOptions = new PolygonOptions();
    Polygon mPolygon;
    static final double EARTH_RADIUS = 6371009;
    static final double SQM_ACRE = 0.000247105;
    TextView textView_FieldSize;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.mListener = (FragmentActivity)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentActivity");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle;
        Boolean evaluateBool = false;
        // Inflate the layout for this fragment
        mLayout  = inflater.inflate(R.layout.fragment_map_boundary, container, false);
        View layout = mLayout;
        if(savedInstanceState != null){
            bundle = savedInstanceState;
            evaluateBool = true;
        }
        else if(getArguments() != null) {
            bundle = getArguments();
            evaluateBool = true;
        }

        MapsInitializer.initialize(getActivity());
        mMapView = (MapView)layout.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        setUpMapIfNeeded(layout);
        mMap = mMapView.getMap();
        textView_FieldSize = (TextView)mLayout.findViewById(R.id.textView_FieldSize);


        //Handle the buttons on the screen
        Button button_Back = (Button)layout.findViewById(R.id.button_MapBoundary_Back);
        button_Back.setOnClickListener(this);
        Button button_Clear = (Button)layout.findViewById(R.id.button_MapBoundary_Clear);
        button_Clear.setOnClickListener(this);
        Button button_Save = (Button)layout.findViewById(R.id.button_MapBoundary_Save);
        button_Save.setOnClickListener(this);

        return layout;
    }
    private void setUpMapIfNeeded(View inflatedView) {
        if (mMap == null) {
            mMap = ((MapView) inflatedView.findViewById(R.id.mapview)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public void onResume() {
        super.onResume();
        mMapView.onResume();

        //mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_GAME);
        //mSensorManager.registerListener(this, mMag, SensorManager.SENSOR_DELAY_GAME);
        //TODO Items that need to be turned on in the guidance routine
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setUpMap() {
        // For showing a move to my location button
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                mMap.animateCamera(cameraUpdate);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addPolygonPoint(latLng);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //clears the old polygon
                if (mPolygon != null) {
                    mPolygon.remove();
                }
                mMarkerHash.remove(marker.getId());
                marker.remove();
                mPolygonOptions = new PolygonOptions();
                if(mMarkerHash.isEmpty() == false) {
                    for (String key : mMarkerHash.keySet()) {
                        mPolygonOptions.add(mMarkerHash.get(key));
                    }
                    mPolygon = mMap.addPolygon(mPolygonOptions.strokeColor(Color.GREEN).fillColor(0x2A00FF00));
                    List<LatLng> tmpList = new ArrayList<LatLng>();
                    for (String key : mMarkerHash.keySet()) {
                        tmpList.add(mMarkerHash.get(key));
                    }
                    double tempArea = computeArea(tmpList) * SQM_ACRE;
                    String tmpString = String.format("%.2f",tempArea);
                    textView_FieldSize.setText(tmpString + "ac");
                }
                return true;
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                //clears the old polygon
                if (mPolygon != null) {
                    mPolygon.remove();
                }
                mMarkerHash.remove(marker.getId());

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mMarkerHash.put(marker.getId(),marker.getPosition());
                mPolygonOptions = new PolygonOptions();
                for (String key : mMarkerHash.keySet()) {
                    mPolygonOptions.add(mMarkerHash.get(key));
                }
                mPolygon = mMap.addPolygon(mPolygonOptions.strokeColor(Color.GREEN).fillColor(0x2A00FF00));
                List<LatLng> tmpList = new ArrayList<LatLng>();
                for (String key : mMarkerHash.keySet()) {
                    tmpList.add(mMarkerHash.get(key));
                }
                double tempArea = computeArea(tmpList) * SQM_ACRE;
                String tmpString = String.format("%.2f",tempArea);
                textView_FieldSize.setText(tmpString + "ac");

            }
        });
        // For dropping a marker at a point on the Map

        // For zooming automatically to the Dropped PIN Location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(0.0, 0.0), 10);
        mMap.animateCamera(cameraUpdate);
    }


    private void addPolygonPoint(LatLng desPolygonPoint)
    {
        if(mPolygon != null){
            mPolygon.remove();
        }
        //double area = computeArea(mMarkerList);
        //Log.d("Calculated Area", Double.toString(area));
        Marker tmpMarker = mMap.addMarker(new MarkerOptions().position(desPolygonPoint).draggable(true));
        mMarkerHash.put(tmpMarker.getId(), tmpMarker.getPosition());
        mPolygonOptions.add(desPolygonPoint);
        mPolygon = mMap.addPolygon(mPolygonOptions.strokeColor(Color.GREEN).fillColor(0x2A00FF00));
        List<LatLng> tmpList = new ArrayList<LatLng>();
        for (String key : mMarkerHash.keySet()) {
            tmpList.add(mMarkerHash.get(key));
        }
        double tempArea = computeArea(tmpList) * SQM_ACRE;
        String tmpString = String.format("%.2f",tempArea);
        textView_FieldSize.setText(tmpString + "ac");
    }
    @Override
    public void onClick(View v) {
        //TODO Update things that the buttons should do
        switch(v.getId()) {
            case R.id.button_MapBoundary_Back:
                //mIntent = new Intent(getActivity().getApplicationContext(), ActivityNewField.class);
                //startActivity(mIntent);
                break;
            case R.id.button_MapBoundary_Clear:
                //mIntent = new Intent(getActivity().getApplicationContext(), ActivityReviewField.class);
                //startActivity(mIntent);
                break;
            case R.id.button_MapBoundary_Save:
                //mIntent = new Intent(getActivity().getApplicationContext(), ActivityReviewField.class);
                //startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    /**
     * Returns the area of a closed path on Earth.
     * @param path A closed path.
     * @return The path's area in square meters.
     */
    public static double computeArea(List<LatLng> path) {
        return abs(computeSignedArea(path));
    }

    /**
     * Returns the signed area of a closed path on Earth. The sign of the area may be used to
     * determine the orientation of the path.
     * "inside" is the surface that does not contain the South Pole.
     * @param path A closed path.
     * @return The loop's area in square meters.
     */
    public static double computeSignedArea(List<LatLng> path) {
        return computeSignedArea(path, EARTH_RADIUS);
    }

    /**
     * Returns the signed area of a closed path on a sphere of given radius.
     * The computed area uses the same units as the radius squared.
     * Used by SphericalUtilTest.
     */
    static double computeSignedArea(List<LatLng> path, double radius) {
        int size = path.size();
        if (size < 3) { return 0; }
        double total = 0;
        LatLng prev = path.get(size - 1);
        double prevTanLat = tan((PI / 2 - toRadians(prev.latitude)) / 2);
        double prevLng = toRadians(prev.longitude);
        // For each edge, accumulate the signed area of the triangle formed by the North Pole
        // and that edge ("polar triangle").
        for (LatLng point : path) {
            double tanLat = tan((PI / 2 - toRadians(point.latitude)) / 2);
            double lng = toRadians(point.longitude);
            total += polarTriangleArea(tanLat, lng, prevTanLat, prevLng);
            prevTanLat = tanLat;
            prevLng = lng;
        }
        return total * (radius * radius);
    }

    /**
     * Returns the signed area of a triangle which has North Pole as a vertex.
     * Formula derived from "Area of a spherical triangle given two edges and the included angle"
     * as per "Spherical Trigonometry" by Todhunter, page 71, section 103, point 2.
     * See http://books.google.com/books?id=3uBHAAAAIAAJ&pg=PA71
     * The arguments named "tan" are tan((pi/2 - latitude)/2).
     */
    private static double polarTriangleArea(double tan1, double lng1, double tan2, double lng2) {
        double deltaLng = lng1 - lng2;
        double t = tan1 * tan2;
        return 2 * atan2(t * sin(deltaLng), 1 + t * cos(deltaLng));
    }
}
