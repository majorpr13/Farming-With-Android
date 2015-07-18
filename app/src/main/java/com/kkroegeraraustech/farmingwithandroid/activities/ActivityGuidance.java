package com.kkroegeraraustech.farmingwithandroid.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.fragments.UserGuidanceFragment;
import com.kkroegeraraustech.farmingwithandroid.helpers.GPSHelper;
import com.kkroegeraraustech.farmingwithandroid.services.AccelerometerService;
import com.kkroegeraraustech.farmingwithandroid.services.GPSTrackingService;
import com.kkroegeraraustech.farmingwithandroid.services.MagnetometerService;

import com.kkroegeraraustech.farmingwithandroid.extras.Constants;
/**
 * Created by Ken Heron Systems on 6/4/2015.
 * This activity tests the guidance routine
 */
public class ActivityGuidance extends FragmentActivity implements SensorEventListener, Constants {
    private GPSTrackingService mGPSTrackingService = new GPSTrackingService(ActivityGuidance.this);
    private AccelerometerService mAccelService = new AccelerometerService();
    private MagnetometerService mMagService = new MagnetometerService();

    private GPSTrackingService.UpdateGPSListener mGPSListener = new GPSTrackingService.UpdateGPSListener() {
        @Override
        public void onUpdateDeclination(double newDeclination) {
            mCurrentGPS.setDeclination(newDeclination);
        }

        @Override
        public void onUpdateLocation(Location newLocation) {
            mCurrentGPS.setValueGPS(mGPSTrackingService.getLatitude(),mGPSTrackingService.getLongitude());
            //GeomagneticField geoField = new GeomagneticField(mCurrentGPS.getValue_Latitude(),mCurrentGPS.getValue_Longitude(),mCurrentGPS.getValue_Altitude());
            //mDeclination = geoField.getDeclination();
            if(guidanceFragment != null) {
                guidanceFragment.setUserLocation(mCurrentGPS);
            }

        }
    };
    private MagServiceListener mMagListener = new MagServiceListener();
    private AccelServiceListener mAccelListener = new AccelServiceListener();

    private boolean mGPSServiceBounded = false;
    private boolean mACCELerviceBounded = false;
    private boolean mMAGerviceBounded = false;

    private boolean bool_newMagData = false;
    private boolean bool_newAccelData = false;
    private float[] mAccelerometer = new float[3];
    private float[] mMagnetometer = new float[3];

    private float[] mRMatrix = new float[9];
    private float[] mIMatrix = new float[9];
    private float[] mOrientationVector = new float[3];

    private double mDeclination = 0.0;
    private GPSHelper mCurrentGPS = new GPSHelper();

    UserGuidanceFragment guidanceFragment;

    class AccelServiceListener implements AccelerometerService.UpdateAccelListener
    {
        @Override
        public void onUpdateAccel(float[] value) {
            mAccelerometer = value;
            bool_newAccelData = true;
            computeNewBearing();
        }
    }
    class MagServiceListener implements MagnetometerService.UpdateMagListener
    {
        @Override
        public void onUpdateMag(float[] value) {
            mMagnetometer = value;
            bool_newMagData =true;
            computeNewBearing();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Create an instance of ExampleFragment
            guidanceFragment =new UserGuidanceFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentTransaction.replace(R.id.fragment_container, guidanceFragment);
            fragmentTransaction.commit();
        }

        startService(new Intent(this.getBaseContext(), GPSTrackingService.class));
        //startService(new Intent(this.getBaseContext(), AccelerometerService.class));
        //startService(new Intent(this.getBaseContext(), MagnetometerService.class));
        doBindServices();
    }

    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        doUnbindService();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO Auto-generated method stub for sensor accuracy change
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity_using_tab_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection mConnectionGPS = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            mGPSTrackingService = ((GPSTrackingService.LocalBinder)iBinder).getInstance();
            mGPSTrackingService.setOnServiceListener(mGPSListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            mGPSTrackingService = null;
        }
    };

    private ServiceConnection mConnectionAccel = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            mAccelService = ((AccelerometerService.LocalBinder)iBinder).getInstance();
            mAccelService.setOnServiceListener(mAccelListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            mAccelService = null;
        }
    };

    private ServiceConnection mConnectionMag = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            mMagService = ((MagnetometerService.LocalBinder)iBinder).getInstance();
            mMagService.setOnServiceListener(mMagListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            mMagService = null;
        }
    };

    private void doBindServices() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(this, GPSTrackingService.class), mConnectionGPS, Context.BIND_AUTO_CREATE);
        //bindService(new Intent(this,AccelerometerService.class), mConnectionAccel, Context.BIND_AUTO_CREATE);
        //bindService(new Intent(this,MagnetometerService.class), mConnectionMag, Context.BIND_AUTO_CREATE);
        mGPSServiceBounded = true;
        //mACCELerviceBounded = true;
        //mMAGerviceBounded = true;
    }

    private void doUnbindService()
    {
        if (mGPSServiceBounded)
        {
            // Detach our existing connection.
            unbindService(mConnectionGPS);
            mGPSServiceBounded = false;
        }
        if (mACCELerviceBounded)
        {
            // Detach our existing connection.
            unbindService(mConnectionAccel);
            mMAGerviceBounded  = false;
        }
        if (mMAGerviceBounded)
        {
            // Detach our existing connection.
            unbindService(mConnectionMag);
            mMAGerviceBounded  = false;
        }
    }

    private void computeNewBearing()
    {
        if((bool_newAccelData == true) && (bool_newMagData)){
            boolean success = SensorManager.getRotationMatrix(mRMatrix, mIMatrix, mAccelerometer, mMagnetometer);
            if(success == true) {
                SensorManager.getOrientation(mRMatrix,mOrientationVector);
                //TODO rotate the sign by this thing
                /*
                double azimuth = Math.toDegrees(matrixValues[0]);
                double pitch = Math.toDegrees(matrixValues[1]);
                double roll = Math.toDegrees(matrixValues[2]);

                readingAzimuth.setText("Azimuth: " + String.valueOf(azimuth));
                readingPitch.setText("Pitch: " + String.valueOf(pitch));
                readingRoll.setText("Roll: " + String.valueOf(roll));
                */
                //guidanceFragment.setDirection(0.0);
            }
        }
    }



}
