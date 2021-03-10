package com.cjs.www;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.cjs.www.capacitorjobscheduler.R;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

@RequiresApi(api = Build.VERSION_CODES.P)
@NativePlugin(
  permissions = {
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
    super.getActivity().onCreate(savedInstanceState, persistentState);
    getActivity().setContentView(R.layout.activity_main);


    //This is a constructor, you can choose what activities to put here


  }


  // StartForeground Service. There are 2 methods based on whic devices you are targeting
  @PluginMethod
  public void startForegroundServiceAboveOreo(Intent i, String ContentTitle, String ContentText) {

    Intent launchIntent = getContext().getPackageManager().getLaunchIntentForPackage(
      getContext().getPackageName());

    getActivity().startForegroundService(launchIntent);
  }

  @PluginMethod
  public void startForegroundServiceBelowOreo(Intent intent, String ContentTitle, String ContentText) {

    getActivity().startService(intent);
  }


  @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
  public void scheduleJob(final PluginCall call) {

    ComponentName componentName = new ComponentName(getContext(), MyBackgroundService.class);
    JobInfo info = new JobInfo.Builder(555, componentName)
      .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
      .setPersisted(true).build();

    JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
    int verification = scheduler.schedule(info);
    if (verification == JobScheduler.RESULT_SUCCESS) {
      Log.d(TAG, "scheduleJob: Success");
      Toast.makeText(getContext(), "scheduleJob: Success", Toast.LENGTH_LONG).show();
    } else {
      Log.d(TAG, "failed");
      Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
    }

  }

  @PluginMethod
  public void cancelJob() {
    JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
    scheduler.cancel(555);
    Log.d(TAG, "Job Cancel ");
  }


  //MyInterval Less than 15 method  to set interval less than 15 min
  @PluginMethod
  private void setAlarm(long timeinmilliseconds) {
    AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    Intent ii = new Intent(getContext(), MyIntervalLessThan15.class);
    PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, ii, 0);
    am.setRepeating(AlarmManager.RTC_WAKEUP, timeinmilliseconds, timeinmilliseconds, pi);
    Toast.makeText(getContext(), "Alarm is set", Toast.LENGTH_LONG).show();
  }


  //MyInterval Less than 15 method to set interval less than 15 min
  @PluginMethod
  private void cancleAlarmManager() {
    AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    Intent ii = new Intent(getContext(), MyIntervalLessThan15.class);
    PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, ii, 0);
    //am.setRepeating(AlarmManager.RTC_WAKEUP,timeinmilliseconds,timeinmilliseconds,pi);
    Toast.makeText(getContext(), "Alarm Cancled", Toast.LENGTH_LONG).show();
    Log.d(TAG, "Alarm Cancled");
    am.cancel(pi);

  }

}
