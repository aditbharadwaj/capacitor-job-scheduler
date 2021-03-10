package com.cjs.www;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyIntervalLessThan15 extends BroadcastReceiver  {


    private static final String TAG="Alarm Service";


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Triggered ny alarm", Toast.LENGTH_SHORT).show();
        for(int i=0; i<10;i++)
        {
            Log.d(TAG,"Alarm : "+i);



        }








    }




}


