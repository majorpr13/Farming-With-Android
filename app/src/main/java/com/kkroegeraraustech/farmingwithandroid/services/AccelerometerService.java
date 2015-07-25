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

public class AccelerometerService extends Service implements SensorEventListener {
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface UpdateAccelListener
    {
        public void onUpdateAccel(float[] value);
    }
    public static String ACCEL_SERVICE = "ACCEL_MEAS_SERVICE";

    //Sensor Directional Variables
    private SensorManager mSensorManager;
    private Sensor mAccel;
    private float[] mLastAccelerometer = new float[3];
    private float[] mCurrentAccelerometer = new float[3];
    //End Sensor Directional Variables


    private final IBinder mIBinder = new LocalBinder();
    private Handler mHandler = null;

    private Context mContext;
    private UpdateAccelListener mOnServiceListenerAccel_Remote;

    public AccelerometerService() {
    }

    public AccelerometerService(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        this.mContext = getApplicationContext();
        mSensorManager = (SensorManager)mContext.getSystemService(SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mAccel,SensorManager.SENSOR_DELAY_NORMAL);
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
        public AccelerometerService getService() {
            return AccelerometerService.this;
        }
    }

    public void setHandler(Handler handler)
    {
        mHandler = handler;
    }

    public void getAccelValues(float[] accelValues){
        accelValues[0] = mCurrentAccelerometer[0];
        accelValues[1] = mCurrentAccelerometer[1];
        accelValues[2] = mCurrentAccelerometer[2];
    }
    public void setOnServiceListener(UpdateAccelListener serviceListener) {
        mOnServiceListenerAccel_Remote = serviceListener;
    }

    public void getAccelerometer(SensorEvent event) {
        //TODO Filter the accelerometer readings
        float[] tmpAccel = event.values;
        mLastAccelerometer = mCurrentAccelerometer;
        mCurrentAccelerometer = tmpAccel;
        if(mOnServiceListenerAccel_Remote != null) {
            mOnServiceListenerAccel_Remote.onUpdateAccel(tmpAccel);
        }
    }

}
