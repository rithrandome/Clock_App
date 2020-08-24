package com.example.clock;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class StopwatchFragment extends Fragment {

    Chronometer chronometer;
    ImageButton btStart, btStop;

    private boolean isResume;
    Handler handler;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        chronometer = view.findViewById(R.id.chronometer);
        btStart = view.findViewById(R.id.bt_start);
        btStop= view.findViewById(R.id.bt_stop);

        handler = new Handler();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.setTextColor(Color.parseColor("#0a8f44"));
                    chronometer.start();
                    isResume = true;
                    btStop.setVisibility(View.INVISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
                else {
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.setTextColor(Color.parseColor("#eb0000"));
                    chronometer.stop();
                    isResume = false;
                    btStop.setVisibility(View.VISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    chronometer.setTextColor(Color.parseColor("#ffffff"));
                    tBuff = 0L;
                    tStart = 0L;
                    tUpdate = 0L;
                    tMilliSec = 0L;
                    sec = 0;
                    min = 0;
                    milliSec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });

        return view;

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
             tMilliSec = SystemClock.uptimeMillis() - tStart;
             tUpdate = tBuff + tMilliSec;
             sec = (int) (tUpdate/1000);
             min = sec/60;
             sec = sec%60;
             milliSec = (int) (tUpdate%100);
             chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",milliSec));
             handler.postDelayed(this, 60);
        }
    };


}
