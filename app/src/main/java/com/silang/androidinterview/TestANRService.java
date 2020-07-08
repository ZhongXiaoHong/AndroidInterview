package com.silang.androidinterview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TestANRService extends Service {
    public TestANRService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
