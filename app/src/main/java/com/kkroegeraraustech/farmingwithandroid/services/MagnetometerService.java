package com.kkroegeraraustech.farmingwithandroid.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.logging.Handler;

public class MagnetometerService extends Service implements SensorEventListener{

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            getMagnetometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface UpdateMagListener
    {
        public void onUpdateMag(float[] value);
    }
    public static String MAG_SERVICE = "MAG_MEAS_SERVICE";

    //Sensor Directional Variables
    //TODO Implement Low-Pass Filter on this data
    private SensorManager mSensorManager;
    private Sensor mMag;
    private float[] mLastMagnetometer = new float[3];
    private float[] mCurrentMagnetometer = new float[3];
    //End Sensor Directional Variables


    private final IBinder mIBinder = new LocalBinder();
    private Handler mHandler = null;

    private Context mContext;
    private UpdateMagListener mOnServiceListenerMag_Remote = null;

    public MagnetometerService() {
    }

    public MagnetometerService(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        this.mContext = getApplicationContext();
        mSensorManager = (SensorManager)getApplicationContext().getSystemService(SENSOR_SERVICE);
        mMag = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this,mMag,SensorManager.SENSOR_DELAY_NORMAL);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startID) {
        super.onStartCommand(intent,flag,startID);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(this);
        if(mHandler != null)
        {
            mHandler = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public class LocalBinder extends Binder {
        public MagnetometerService getInstance() {
            return MagnetometerService.this;
        }
    }

    public void setHandler(Handler handler)
    {
        mHandler = handler;
    }

    public void getAccelValues(float[] magValues){
        magValues[0] = mCurrentMagnetometer[0];
        magValues[1] = mCurrentMagnetometer[1];
        magValues[2] = mCurrentMagnetometer[2];
    }
    public void setOnServiceListener(UpdateMagListener serviceListener) {
        mOnServiceListenerMag_Remote = serviceListener;
    }

    public void getMagnetometer(SensorEvent event) {
        float[] tmpMag = event.values;
        mLastMagnetometer = mCurrentMagnetometer;
        mCurrentMagnetometer = tmpMag;
        if(mOnServiceListenerMag_Remote != null) {
            mOnServiceListenerMag_Remote.onUpdateMag(tmpMag);
        }
    }


}
