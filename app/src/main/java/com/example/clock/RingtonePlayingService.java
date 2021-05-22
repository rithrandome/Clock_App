package com.example.clock;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import static com.example.clock.NotificationHelper.CHANNEL_ID;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class RingtonePlayingService extends Service {

    MediaPlayer ringtone;
    boolean isRunning;
    int startId;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String alarmState = intent.getStringExtra("alarmState");

        Log.e("Ringtone state",String.valueOf(alarmState));

        Intent notificationIntent =  new Intent(this, AlarmFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("ALARM")
                .setContentText("Alarm is Ringing !!")
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();


        assert alarmState!=null;
        System.out.println(alarmState);
        if(alarmState.toLowerCase().equals("alarm on")) startId = 1;
        else if(alarmState.toLowerCase().equals("alarm off")) startId = 0;
        else startId = 0;


        if(!this.isRunning && startId == 1){
            Log.e("no music","u wanna start");

            ringtone = MediaPlayer.create(this,R.raw.alarm_tone1);
            ringtone.setLooping(true);
            ringtone.start();

            startForeground(1, notification);

            this.isRunning = true;
            this.startId = 0;

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
