package com.sjsu.sister.util;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

public class ShakeListener implements SensorEventListener{

    //Shake duration 1/10s
    public static final int LASTTIME = 50;

    //Shake speed
    public static final int SPEED = 3000;

    private OnShakelistener listener = null;

    private long lastTime;
    private float lastX, lastY, lastZ;

    private Context mContext;
    private SensorManager manager = null;
    private Sensor sensor = null;

    public ShakeListener( Context context ) {
        mContext = context;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.out.println("Service onSensorChanged");
        long curTime = System.currentTimeMillis();
        long time = curTime - lastTime;
        if( time < LASTTIME ) return;
        lastTime = curTime;
        float x = sensorEvent.values[0], y = sensorEvent.values[1], z = sensorEvent.values[2];
        float deltaX = x - lastX, deltaY = y - lastY, deltaZ = z - lastZ;
        lastX = x;
        lastY = y;
        lastZ = z;
        if( Math.sqrt( deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ ) / time * 10000 < SPEED    ) return ;

        if( listener != null ) listener.onShake();

        stop();
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                start();
            }
        } , 400 );

    }

    public void start(){
        manager = (SensorManager) mContext.getSystemService( Context.SENSOR_SERVICE );
        if( manager != null ){
            sensor = manager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );
        }
        if( sensor != null ){
            manager.registerListener( this , sensor, SensorManager.SENSOR_DELAY_GAME );
        }
    }

    public void stop(){
        manager.unregisterListener( this );
    }

    public void setListener(OnShakelistener listener) {
        this.listener = listener;
    }

    public interface OnShakelistener{
        public void onShake();
    }

}
