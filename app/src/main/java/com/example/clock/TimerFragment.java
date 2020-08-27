package com.example.clock;

import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class TimerFragment extends Fragment {

    private EditText mHoursInput, mSecInput, mMinInput;
    private TextView mTextViewCountDown, colon1, colon2;
    private FloatingActionButton mButtonStartPause;
    private FloatingActionButton mButtonReset;
    private Button mButtonSet;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        mButtonStartPause = view.findViewById(R.id.fbt_startpause);
        mButtonReset = view.findViewById(R.id.fbt_reset);
        mButtonSet = view.findViewById(R.id.bt_set);
        colon1 = view.findViewById(R.id.colon1);
        colon2 = view.findViewById(R.id.colon2);

        mMinInput = view.findViewById(R.id.min_input);
        mSecInput = view.findViewById(R.id.sec_input);
        mHoursInput = view.findViewById(R.id.hrs_input);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_input, s_input, h_input;
                if(mMinInput.getText().toString().equals(""))
                    m_input = "0";
                else
                    m_input = mMinInput.getText().toString();

                if(mSecInput.getText().toString().equals(""))
                    s_input = "0";
                else
                    s_input = mSecInput.getText().toString();

                if(mHoursInput.getText().toString().equals(""))
                    h_input = "0";
                else
                    h_input = mHoursInput.getText().toString();

                long millisInput = (Long.parseLong(m_input) * 60000) + (Long.parseLong(s_input) * 1000) + (Long.parseLong(h_input) * 3600000);

                setTime(millisInput);
                mMinInput.setText("");
                mHoursInput.setText("");
                mSecInput.setText("");

            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
//                timerEndSound(0);
//                blinkTextView(0);
            }
        });

        return view;
    }



    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
//        closeKeyboard();
    }
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }
    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        timeLeftFormatted = String.format(Locale.getDefault(),
                "%02d:%02d:%02d", hours, minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void updateWatchInterface() {
        if (mTimerRunning) {
            mMinInput.setVisibility(View.INVISIBLE);
            mHoursInput.setVisibility(View.INVISIBLE);
            mSecInput.setVisibility(View.INVISIBLE);
            colon1.setVisibility(View.INVISIBLE);
            colon2.setVisibility(View.INVISIBLE);

            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        } else {
            mMinInput.setVisibility(View.VISIBLE);
            mHoursInput.setVisibility(View.VISIBLE);
            mSecInput.setVisibility(View.VISIBLE);
            colon1.setVisibility(View.VISIBLE);
            colon2.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
//                blinkTextView(1);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }
//    private void closeKeyboard() {
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
    @Override
    public void onStop() {
        super.onStop();
//        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putLong("startTimeInMillis", mStartTimeInMillis);
//        editor.putLong("millisLeft", mTimeLeftInMillis);
//        editor.putBoolean("timerRunning", mTimerRunning);
//        editor.putLong("endTime", mEndTime);
//        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
//            timerEndSound(1);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
//        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
//        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
//        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateWatchInterface();
        if (mTimerRunning) {
//            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    private void timerEndSound(int state){
        MediaPlayer timer_end = MediaPlayer.create(getActivity(),R.raw.alarm_tone2 );
        if(state == 1) {
            timer_end.start();
            timerEndSound(1);
        }
        else
            timer_end.stop();
    }

    private void blinkTextView(final int state) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;
                try{Thread.sleep(timeToBlink);}catch (Exception ignored) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(state == 1) {
                            if (mTextViewCountDown.getVisibility() == View.VISIBLE) {
                                mTextViewCountDown.setVisibility(View.INVISIBLE);
                            } else {
                                mTextViewCountDown.setVisibility(View.VISIBLE);
                            }
                            blinkTextView(1);
                        }
                        else{
                            Thread.currentThread().stop();
                        }
                    }
                });
            }
        }).start();
    }


}

