package com.example.clock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Objects;


public class AlarmFragment extends Fragment {

    int hour, minute, current_hour, current_minute;
    String hour_string, minute_string;
    TimePicker alarmTimePicker;
    AlarmManager alarmManager;
    RelativeLayout dateLayout, repeatLayout, ringtoneLayout ;
    DatePicker setAlarmDate;
    TextView date, repeatDays;
    SwitchCompat repeatState;
    Button setAlarm, cancelAlarm;
    PendingIntent pending_intent;


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        alarmManager = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);
        alarmTimePicker = view.findViewById(R.id.time_picker);
        setAlarm = view.findViewById(R.id.setAlarm);
        cancelAlarm = view.findViewById(R.id.cancelAlarm);

        dateLayout = view.findViewById(R.id.date_layout);
        ringtoneLayout = view.findViewById(R.id.ringtone_layout);
        repeatLayout = view.findViewById(R.id.repeat_layout);
        date = view.findViewById(R.id.date);
        repeatDays = view.findViewById(R.id.repeat_days);
        repeatState = view.findViewById(R.id.repeat_state);

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert true;
            }

        });

        repeatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert true;
            }
        });

        ringtoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert true;
            }
        });

//        repeatState.setOnCheckedChangeListener(onCheckedChanged());

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                current_hour = Calendar.HOUR;
                current_minute = Calendar.MINUTE;
                hour = alarmTimePicker.getHour();
                minute = alarmTimePicker.getMinute();
                hour_string = String.valueOf(hour);
                minute_string = String.valueOf(minute);

//                if(hour > 0 && hour > current_hour)
//                    Toast.makeText(getActivity(),"Alarm set for " + (hour - current_hour) + " hours and " + (minute - current_minute) + " minutes from now", Toast.LENGTH_LONG).show();


                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                Intent intent = new Intent (getActivity(), AlarmReceiver.class);
                intent.putExtra("alarmState","Alarm on");

                pending_intent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,pending_intent);
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pending_intent != null)
                    alarmManager.cancel(pending_intent);

                Intent intent = new Intent (getActivity(), AlarmReceiver.class);

                intent.putExtra("alarmState","Alarm off");

                Objects.requireNonNull(getActivity()).sendBroadcast(intent);

            }
        });

//         setAlarmDate = view.findViewById()




        return view;
    }

//    private CompoundButton.OnCheckedChangeListener onCheckedChanged(){
//        return  new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                switch (compoundButton.getId()){
//                    case R.id.repeat_state:
//                        assert true;
//                }
//            }
//        };
//    }
}
