package com.sjsu.sister.Service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.ShakeListener;
import com.sjsu.sister.util.StringUtils;

public class ShakeService extends Service implements ShakeListener.OnShakelistener {

    private ShakeListener shake;
    private String mEmergency;
    private PowerManager.WakeLock mWeakLock;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWeakLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, ShakeService.class.getName());
        mWeakLock.acquire();
        System.out.println("Service onCreate");
        shake = new ShakeListener( this );
        shake.setListener( this );
        shake.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        mEmergency = intent.getStringExtra("Phone");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        startService( new Intent( this, ShakeService.class ) );
        if (mWeakLock != null) {
            mWeakLock.release();
            mWeakLock = null;
        }
        super.onDestroy();
    }

    @Override
    public void onShake() {
        System.out.println("Service onShake");
        System.out.println("Service onShake: " + mEmergency);
        if(StringUtils.isEmpty(mEmergency)){
            CustomToast.popWarning(getApplicationContext(),"Please set emergency phone number first.", Toast.LENGTH_LONG).show();
        }else{
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mEmergency));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }
}
