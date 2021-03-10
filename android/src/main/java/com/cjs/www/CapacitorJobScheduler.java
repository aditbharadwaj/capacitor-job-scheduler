package com.cjs.www;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cjs.www.capacitorjobscheduler.R;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

@NativePlugin(
        permissions={
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
        }
        // A random integer which is hopefully unique to this plugin.
      //  permissionRequestCode = 28351
)
public class CapacitorJobScheduler extends Plugin {
    private static final String TAG = "MainActivity";
    Button b2;

    private PersistableBundle persistentState;

    public void onCreate(Bundle savedInstanceState) {
        super.getActivity().onCreate(savedInstanceState,persistentState );
        getActivity().setContentView(R.layout.activity_main);
        b2 = getActivity().findViewById(R.id.button2);

        Intent intent = new Intent(getContext(),MyService.class);
        //scheduleJob();
        setAlarm(1000);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleAlarmManager();

            }
        });

       /* if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            startForegroundService(intent);
        }

        else
        {
            startService(intent);
        }*/

    }

    @PluginMethod(returnType=PluginMethod.RETURN_CALLBACK)
    public void scheduleJob(final PluginCall call)
    {
       // call.save();
        ComponentName componentName = new ComponentName(getContext(), MyJobService.class);
        JobInfo info = new JobInfo.Builder(555,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true).build();

        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        int verification = scheduler.schedule(info);
        if(verification == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "scheduleJob: Success");
            Toast.makeText(getContext(), "scheduleJob: Success", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d(TAG, "failed");
            Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
        }

    }

    @PluginMethod
    public void cancelJob()
    {
        JobScheduler scheduler=(JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(555);
        Log.d(TAG, "Job Cancel ");
    }




    private void setAlarm(long timeinmilliseconds) {
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent ii = new Intent(getContext(),IntervalSetter.class);
        PendingIntent pi = PendingIntent.getBroadcast(getContext(),0,ii,0);
        am.setRepeating(AlarmManager.RTC_WAKEUP,timeinmilliseconds,timeinmilliseconds,pi);
        Toast.makeText(getContext(), "Alarm is set", Toast.LENGTH_LONG).show();
    }

    private void cancleAlarmManager()
    {
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent ii = new Intent(getContext(),IntervalSetter.class);
        PendingIntent pi = PendingIntent.getBroadcast(getContext(),0,ii,0);
        //am.setRepeating(AlarmManager.RTC_WAKEUP,timeinmilliseconds,timeinmilliseconds,pi);
        Toast.makeText(getContext(), "Alarm Cancled", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Alarm Cancled");
        am.cancel(pi);

    }
}
