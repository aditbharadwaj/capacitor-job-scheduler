package com.cjs.www;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public interface MyForegroundServiceInterface {


    int onStartCommand(Intent intent, int flags, int startId, String ContentTitle, String ContentText);

    void onDestroy();

    @Nullable
    IBinder onBind(Intent intent);
}
