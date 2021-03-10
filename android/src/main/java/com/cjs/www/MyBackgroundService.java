package com.cjs.www;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class MyBackgroundService extends JobService {

    private static final String TAG="Background service ";
    private boolean jobended=false;

    @Override
    public boolean onStartJob(JobParameters params) {

        Intent intent1 = new Intent(this, CapacitorJobScheduler.class);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,0);

        Log.d(TAG,"OnStartJob Triggered");

        mytasks(params);
        return true;
    }



    @Override
    public boolean onStopJob(JobParameters params) {

        Log.d(TAG,"onStopJob Cancelled abruptly");
        jobended=true;


        return false;
    }

    private void mytasks(final JobParameters parameter)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i=0; i<10;i++)
                {
                    Log.d(TAG,"Running : "+i);

                    if(jobended)
                    {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG,"Executed");
                jobFinished(parameter,false);

            }
        }).start();

    }








}
