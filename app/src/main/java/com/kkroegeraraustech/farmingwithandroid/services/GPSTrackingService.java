package com.kkroegeraraustech.farmingwithandroid.services;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GPSTrackingService extends Service implements LocationListener{

    public interface UpdateGPSListener
    {
        public void onUpdateLocation(Location newLocation);
        public void onUpdateDeclination(double newDeclination);
    }

    public static String GPS_SERVICE = "GPS_TRACKING_SERVICE";

    private final IBinder mIBinder = new LocalBinder();
    private Handler mHandler = null;

    private Context mContext;

    private LocationListener mOnServiceListenerGPS_Local;
    private UpdateGPSListener mOnServiceListenerGPS_Remote;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;
    //
    Location mLocation; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES_LOOSE = 10;
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5; // 5 seconds
    private static final long MIN_TIME_BW_UPDATES_LOOSE = 1000 * 1; // 5 seconds
    // Declaring a Location Manager
    protected LocationManager mLocationManager;

    @Override
    public void onCreate() {
        Log.e("TAG","onCreate");
        this.mContext = getApplicationContext();
        try {
            Log.e("TAG","IN THE TRY");
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            Log.d("TAG", "LOCATION SUBSCRIBED");
            // getting GPS status
            Boolean tempBoolean = false;
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d("TAG", Boolean.toString(isGPSEnabled));
            // getting network status
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.d("TAG", Boolean.toString(isNetworkEnabled));

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.d("TAG", "NOTHING ENABLED");
                // no network provider is enabled
            }
            else {
                Log.d("TAG", "IN THE ELSE");
                this.canGetLocation = true;
                // First get location from Network Provider
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (mLocation == null) {
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {
                            mLocation = mLocationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (mLocation != null) {
                                latitude = mLocation.getLatitude();
                                longitude = mLocation.getLongitude();
                            }
                        }
                    }
                }
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (mLocationManager != null) {
                        mLocation = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (mLocation != null) {
                            latitude = mLocation.getLatitude();
                            longitude = mLocation.getLongitude();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startID) {
        Log.e("TAG","onStartCommand");
        super.onStartCommand(intent,flag,startID);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mLocationManager != null){
            mLocationManager.removeUpdates(mOnServiceListenerGPS_Local);
        }
        if(mHandler != null) {
            mHandler = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public class LocalBinder extends Binder {
        public GPSTrackingService getInstance() {
            return GPSTrackingService.this;
        }
    }

    public void setHandler(Handler handler)
    {
        mHandler = handler;
    }
    /*
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */

    //End of the binding functionalities of the service

    public GPSTrackingService(){

    }

    public GPSTrackingService(Context context) {
        //mLocationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        Log.d("TAG", "IN THE CONSTRUCTOR");
        this.mContext = context;
    }

    public void setOnServiceListener(UpdateGPSListener serviceListener) {
        mOnServiceListenerGPS_Remote = serviceListener;
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (mLocation != null) {
            latitude = mLocation.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (mLocation != null) {
            longitude = mLocation.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     * */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

        @Override
        public void onLocationChanged(Location location) {
            Log.e("TAG","NEW LOCATION");
            double tempDouble = location.getLatitude();
            String tempString = Double.toString(tempDouble);
            Log.e("TAG",tempString);
            tempDouble = location.getLongitude();
            tempString = Double.toString(tempDouble);
            Log.e("TAG",tempString);
            mOnServiceListenerGPS_Remote.onUpdateLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
}