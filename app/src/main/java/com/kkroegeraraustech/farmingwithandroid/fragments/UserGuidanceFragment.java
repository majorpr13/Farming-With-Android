package com.kkroegeraraustech.farmingwithandroid.fragments;

import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//Import Google MAPS Relevant File
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

//Import Relevant Kenny Files
import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.helpers.GPSHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.MarkerHelper;
/**
 * Created by Ken Heron Systems on 6/6/2015.
 * This fragment handles the user guidance routine to a waypoint
 */
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserGuidanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserGuidanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserGuidanceFragment extends Fragment implements SensorEventListener,View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static View mLayout;

    //Data Fields within the Fragment
    private EditText textLat;
    private EditText textLon;

    //Sensor Directional Variables
    //TODO Implement Low-Pass Filter on this data
        private SensorManager mSensorManager;
        private Sensor mAccel;
        private Sensor mMag;
        private float[] mLastAccelerometer = new float[3];
        private float[] mLastMagnetometer = new float[3];
        private boolean mLastAccelerometerSet = false;
        private boolean mLastMagnetomerSet = false;

        private float[] mR = new float[9];
        private float[] mOrientation = new float[3];
        private float mCurrentDegree = 0f;
        private ImageView mCompassImage;

        //this enables us to get the declination
        float tempFloat  = 0;
        long tempLong = 0;
        private GeomagneticField geoField = new GeomagneticField(tempFloat,tempFloat,tempFloat,tempLong);
        private float mDeclination;
    //End Sensor Directional Variables

    //GPS Positioning Variables
        private float valueRotate = 0;

        private MapView mMapView;
        private GoogleMap mMap;

        //flag for GPS Status
        private boolean isGPSEnabled = false;

        //flag for Network Status
        private boolean isNetworkEnabled = false;
        private boolean canGetLocation = false;

        private static final long minDistanceUpdate = 3; //33 meters
        private static final long minTimUpdates = 5000; //5 seconds time is in milliseconds

        private GPSHelper mCurrentLocation = new GPSHelper();
        private GPSHelper mTargetLocation = new GPSHelper(0.0,0.0,GPSHelper.Interface_GPS_Tag.GPS_Target);

        MarkerHelper mMarkerHelper = new MarkerHelper();
    //End Positioning Variables


    private FragmentActivity mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserGuidanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserGuidanceFragment newInstance(String param1, String param2) {
        UserGuidanceFragment fragment = new UserGuidanceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public UserGuidanceFragment() {
        // Required empty public constructor
    }

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
        mCurrentLocation.setValueTag(GPSHelper.Interface_GPS_Tag.GPS_User);
        mDeclination = geoField.getDeclination();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle;
        Boolean evaluateBool = false;
        // Inflate the layout for this fragment
        mLayout  = inflater.inflate(R.layout.fragment_user_guidance, container, false);
        View layout = mLayout;
        if(savedInstanceState != null){
            bundle = savedInstanceState;
            evaluateBool = true;
        }
        else if(getArguments() != null) {
            bundle = getArguments();
            evaluateBool = true;
        }
        //if (evaluateBool != false) {
            //Establish the compass pointer setup
            mCompassImage = (ImageView)mLayout.findViewById(R.id.compassPointer);
            mCompassImage = (ImageView)mLayout.findViewById(R.id.compassPointer);
            setDirection(0.0f);

        //}

        textLat = (EditText)layout.findViewById(R.id.editNumLat);
        textLon = (EditText)layout.findViewById(R.id.editNumLon);

        textLat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpString = s.toString();
                double tmpLat = 0.0;
                if((tmpString != null) && (tmpString.isEmpty() != true)) {
                    tmpLat = Double.parseDouble(tmpString);
                }
                else {
                    textLat.setText("0.0");
                }
                mTargetLocation.setValueLatitude(tmpLat);
                setTargetLocation(mTargetLocation);
                setDistance();
            }
        });
        textLon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpString = s.toString();
                double tmpLon = 0.0;
                if((tmpString != null) && (tmpString.isEmpty() != true)) {
                    tmpLon = Double.parseDouble(tmpString);
                }
                else {
                    textLat.setText("0.0");
                }

                mTargetLocation.setValueLongitude(tmpLon);
                setTargetLocation(mTargetLocation);
                setDistance();
            }
        });

        MapsInitializer.initialize(getActivity());
        mMapView = (MapView)layout.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        setUpMapIfNeeded(layout);
        mMap = mMapView.getMap();

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
        //mSensorManager.unregisterListener(this, mAccel);
        //mSensorManager.unregisterListener(this, mMag);
        //TODO Items that need to be paused in the guidance routine

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO Auto-generated method stub for sensor accuracy change
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mAccel)
        {
            System.arraycopy(event.values,0,mLastAccelerometer,0,event.values.length);
            mLastAccelerometerSet = true;
        }
        else if(event.sensor == mMag)
        {
            System.arraycopy(event.values,0,mLastMagnetometer,0,event.values.length);
            mLastMagnetomerSet = true;
        }
        if(mLastAccelerometerSet && mLastMagnetomerSet)
        {
            mLastMagnetomerSet = false;
            mLastAccelerometerSet = false;
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthRad = mOrientation[0];
            float azimuthDeg = (float)(Math.toDegrees(azimuthRad)+360)%360;
            setDirection(azimuthDeg);
        }
    }

    public void setDirection(float directionDeg) {
        //float bearing = mCurrentLocation.mLocation.bearingTo(mTargetLocation.mLocation);
        //float heading = mCurrentLocation.mLocation.getBearing();
        //heading += mDeclination;
        //heading = (bearing - heading) * -1;
        //heading = (heading + 360) % 360;

        RotateAnimation ra = new RotateAnimation(mCurrentDegree, -directionDeg, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(200);
        ra.setFillAfter(true);
        mCompassImage.startAnimation(ra);
        //mCurrentDegree = -heading;
        mCurrentDegree = -directionDeg;
    }

    public void setUserLocation(GPSHelper currentLocation) {
        mMarkerHelper.removeMarker("Current Location");
        mCurrentLocation.setValueGPS(currentLocation.getValue_Latitude(), currentLocation.getValue_Longitude());
        mCurrentLocation.setDeclination(currentLocation.getDeclination());

        Double.toString(currentLocation.getValue_Latitude());
        Double.toString(currentLocation.getValue_Longitude());
        String snippetString = " ";

        Marker currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(mCurrentLocation.getValue_Latitude() , mCurrentLocation.getValue_Longitude())).title("USER").snippet(snippetString));
        mMarkerHelper.addMarker("Current Location", currentMarker);
        setDistance();
    }
    public void setTargetLocation(GPSHelper currentLocation) {
        mMarkerHelper.removeMarker("Target Location");
        mTargetLocation.setValueGPS(currentLocation.getValue_Latitude(),currentLocation.getValue_Longitude());
        Double.toString(currentLocation.getValue_Latitude());
        Double.toString(currentLocation.getValue_Longitude());
        String snippetString = " ";
        Marker currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(mTargetLocation.getValue_Latitude() , mTargetLocation.getValue_Longitude())).title("TARGET").snippet(snippetString));
        mMarkerHelper.addMarker("Target Location", currentMarker);
        setDistance();
    }
    private void setDistance(){
        valueRotate = valueRotate + 5;
        //this.setDirection(valueRotate);
        float[] resultArray = new float[1];
        double cLat = mCurrentLocation.getValue_Latitude();
        double cLon = mCurrentLocation.getValue_Longitude();
        double tLat = mTargetLocation.getValue_Latitude();
        double tLon = mTargetLocation.getValue_Longitude();
        Location.distanceBetween(cLat, cLon, tLat, tLon, resultArray);
        float value_rotation = 15;
        setDirection(value_rotation);
        //DecimalFormat df = new DecimalFormat("#.00");
        String tmpString = String.format("%.2f",resultArray[0]);
        TextView txtDistance = (TextView)mLayout.findViewById(R.id.textView_distanceActual);
        txtDistance.setText(tmpString);
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
        // For dropping a marker at a point on the Map
        Marker currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(mCurrentLocation.getValue_Latitude() , mCurrentLocation.getValue_Longitude())).title("ME").snippet("Current Location"));
        mMarkerHelper.addMarker("Current Location" , currentMarker);
        Marker targetMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(mTargetLocation.getValue_Latitude() , mTargetLocation.getValue_Longitude())).title("TARGET").snippet("Target Location"));
        mMarkerHelper.addMarker("Target Location" , targetMarker);
        // For zooming automatically to the Dropped PIN Location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getValue_Latitude(), mCurrentLocation.getValue_Longitude()), 10);
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onClick(View v) {

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

    //End Google Maps Positioning Functions

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
