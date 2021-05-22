package com.example.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("We are in the"," AlarmReceiver class");

        String alarmState = intent.getStringExtra("alarmState");
        System.out.println(alarmState);

        Log.e("What is the alarmState?",String.valueOf(alarmState));

        Intent service_intent = new Intent(context,RingtonePlayingService.class);
        service_intent.putExtra("alarmState", alarmState);

        context.startService(service_intent);
    }
}
