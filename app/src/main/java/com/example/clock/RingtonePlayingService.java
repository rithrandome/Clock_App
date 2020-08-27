package com.example.clock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class RingtonePlayingService extends Service {

    MediaPlayer ringtone;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String alarmState = intent.getStringExtra("alarmState");

        Log.e("Ringtone state   ",String.valueOf(alarmState));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 =  new Intent(this.getApplicationContext(), AlarmFragment.class);

        Notification notification_popup = new Notification.Builder(this)
                .setSmallIcon(R.drawable.clock)
                .setContentTitle("Alarm !!")
                .setContentText("Click to open")
                .setContentIntent(PendingIntent.getActivity(this, 0, intent1,0))
                .setAutoCancel(true)
                .build();

        assert alarmState!=null;
        if(alarmState.toLowerCase().equals("alarm on")) startId = 1;
        else if(alarmState.toLowerCase().equals("alarm off")) startId = 0;
        else startId = 0;


        if(!this.isRunning && startId == 1){
            Log.e("no music","u wanna start");

            ringtone = MediaPlayer.create(this,R.raw.alarm_tone1);
            ringtone.setLooping(true);
            ringtone.start();

            this.isRunning = true;
            this.startId = 0;

            notificationManager.notify(0,notification_popup);


        }
        else if(this.isRunning && startId == 0){
            Log.e("yes music","u wanna stop");

            ringtone.stop();
            ringtone.reset();

            this.isRunning = false;
            this.startId = 0;


        }
        else if (!this.isRunning && startId == 0){
            Log.e("no music","u wanna stop");

            this.isRunning = false;
            this.startId = 0;

        }
        else if (this.isRunning && startId == 1){
            Log.e("yes music","u wanna start");

            this.isRunning = true;
            this.startId = 1;

        }
        else{
            Log.e("somehow","u reached this");

        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.isRunning = false;
    }
}
