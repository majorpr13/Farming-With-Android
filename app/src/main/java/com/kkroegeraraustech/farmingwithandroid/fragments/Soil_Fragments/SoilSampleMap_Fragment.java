package com.kkroegeraraustech.farmingwithandroid.fragments.Soil_Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.helpers.GPSHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.MarkerHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.SoilHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken Heron Systems on 7/22/2015.
 * Some Default Description of the purpose of this class
 */
public class SoilSampleMap_Fragment extends Fragment implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static View mLayout;

    private MapView mMapView;
    private GoogleMap mMap;
    Marker mMarkerLocation;

    private GPSHelper mCurrentLocation;
    private GPSHelper mStoredLocation;

    private MarkerHelper mMarkerHelper;
    private SoilHelper mSoilHelper;
    private FragmentActivity mContext;
    //End Positioning Variables


    private FragmentActivity mListener;


    private interface CallbackListener{
        void onSaveClicked(CharSequence text);
        void onForgetClicked();
        void onCancelClicked();
    }

    private CallbackListener mTestListener = new CallbackListener() {
        @Override
        public void onSaveClicked(CharSequence text) {
            mMarkerLocation = addSample(mStoredLocation);
            double compactionDouble = Double.parseDouble(text.toString());
            mSoilHelper.addSample(compactionDouble,0.0,mMarkerLocation);
        }

        @Override
        public void onForgetClicked() {

        }

        @Override
        public void onCancelClicked() {
            mMarkerLocation = addSample(mStoredLocation);
            mSoilHelper.addSample(0.0,0.0,mMarkerLocation);
        }
    };
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserGuidanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoilSampleMap_Fragment newInstance(String param1, String param2) {
        SoilSampleMap_Fragment fragment = new SoilSampleMap_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SoilSampleMap_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity)activity;
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
        mCurrentLocation = new GPSHelper(38.8,-77.0,GPSHelper.Interface_GPS_Tag.GPS_User);
        mMarkerHelper = new MarkerHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle;
        Boolean evaluateBool = false;
        // Inflate the layout for this fragment
        mLayout  = inflater.inflate(R.layout.fragment_soil_samples_map, container, false);
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


    public void setUserLocation(GPSHelper currentLocation){
        LatLng tmpLatLng = new LatLng(mCurrentLocation.getValue_Latitude(), mCurrentLocation.getValue_Longitude());
        mMarkerHelper.removeMarker("Current Location");
        mMarkerHelper.removeCircle("Current Location");

        mCurrentLocation.setLocation(currentLocation.getLocation());

        Marker currentMarker = mMap.addMarker(new MarkerOptions().position(tmpLatLng).title("USER").flat(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMarkerHelper.addMarker("Current Location", currentMarker);

        Circle currentCircle = mMap.addCircle(new CircleOptions().center(tmpLatLng).radius(currentLocation.mLocation.getAccuracy()).strokeColor(Color.BLUE).fillColor(0x2A0000FF));   // you can choose any color, I just set some randomly.fillColor(0x2A0000FF));
        mMarkerHelper.addCircle("Current Location", currentCircle);

    }

    public void setSampleLocation(LatLng sampleLocation){
        Marker soilMarker = mMap.addMarker(new MarkerOptions().position(sampleLocation).flat(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMarkerHelper.addMarker(soilMarker.getId(), soilMarker);
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

        /*
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addSample(latLng);
            }
        });
        */
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMarkerHelper.removeMarker(marker.getId());
                marker.remove();
                return true;
            }
        });
        /*
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                mMarkerHelper.removeMarker(marker.getId());

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mMarkerHelper.addMarker(marker.getId(),marker);
            }
        });
        */
        // For dropping a marker at a point on the Map

        // For zooming automatically to the Dropped PIN Location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(0.0, 0.0), 10);
        mMap.animateCamera(cameraUpdate);
    }

    private Marker addSample(GPSHelper samplePoint)
    {
        LatLng tempLatLng = new LatLng(samplePoint.getLocation().getLatitude(),samplePoint.getLocation().getLongitude());
        Marker tmpMarker = mMap.addMarker(new MarkerOptions().position(tempLatLng).flat(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
        mMarkerHelper.addMarker(tmpMarker.getId(), tmpMarker);
        return(tmpMarker);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_SoilMap_Add:
                LatLng currentLocation = new LatLng(0.0,0.0);
                mStoredLocation.assignCopy(mCurrentLocation);
                //TODO Define the method for getting the compaction level
                AlertDialog dialogCompaction = compactionAlertDialog();
                dialogCompaction.show();
                break;
            case R.id.button_SoilMap_Back:
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


    /**
     * THE FOLLOWING FUNCTIONS HANDLE THE COMPACTION ALERT DIALOG
     */




    /**
     * THIS ENDS FUNCTIONS HANDLE THE COMPACTION ALERT DIALOG
     */

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

    private AlertDialog compactionAlertDialog(){

        View view = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog_compaction, null);
        final EditText tv = (EditText)view.findViewById(R.id.compactionValue);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTestListener.onSaveClicked(tv.getText());

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTestListener.onCancelClicked();
                    }
                })
                .setNeutralButton("FORGET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTestListener.onForgetClicked();
                    }
                });
        AlertDialog dialog = builder.create();
        return (dialog);
    }

}
