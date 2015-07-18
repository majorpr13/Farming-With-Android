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

import java.util.logging.Handler;

public class MagnetometerService extends Service {
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
    private MagSensorListener mOnServiceListenerMag_Local = null;
    private UpdateMagListener mOnServiceListenerMag_Remote = null;

    public MagnetometerService() {
    }

    public MagnetometerService(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startID) {
        mSensorManager = (SensorManager)getApplicationContext().getSystemService(SENSOR_SERVICE);
        mMag = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mOnServiceListenerMag_Local,mMag,SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(mOnServiceListenerMag_Local);
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

    public void getAccelerometer(SensorEvent event) {
        float[] tmpMag = event.values;
        mLastMagnetometer = mCurrentMagnetometer;
        mCurrentMagnetometer = tmpMag;
        mOnServiceListenerMag_Remote.onUpdateMag(tmpMag);
    }

    public class MagSensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                getAccelerometer(event);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


}
