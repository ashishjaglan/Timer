package com.example.dell.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String total;
    CountDownTimer countDownTimer;
    Boolean timerStarted=false, tstart=false;
    SeekBar timerSeekBar;
    TextView timer;
    TextView totalTime;
    Button button;

    public void resetTimer(){
        tstart=false;
        button.setText("Start");
        timerSeekBar.setProgress(30);
        timer.setText("0:30");
        totalTime.setVisibility(View.INVISIBLE);
        timerSeekBar.setVisibility(View.VISIBLE);
        timerStarted=false;
        countDownTimer.cancel();

    }

    public void updateTimer(int secondsLeft){
        int m=secondsLeft/60;
        int s=secondsLeft%60;
        String min = Integer.toString(m);
        String sec = Integer.toString(s);
        if(s<=9){
            sec="0"+sec;
        }
        timer.setText(min + ":" + sec);

        if(timerStarted==false) {
            total = "Total time  " + min + ":" + sec;
            totalTime.setText(total);
            timerStarted=true;
        }
    }

    public void startTimer(View view){

        if(tstart==false) {
            tstart=true;
            //timerSeekBar.setEnabled(false);
            timerSeekBar.setVisibility(View.INVISIBLE);
            button.setText("Stop");
            totalTime.setVisibility(View.VISIBLE);

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //timerSeekBar.setProgress((int)millisUntilFinished);
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mplayer.start();
                    resetTimer();


                }
            }.start();
        }else{
           resetTimer();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timer= (TextView)findViewById(R.id.timer);
        totalTime = (TextView)findViewById(R.id.totalTime);
        button = (Button)findViewById(R.id.button);
        totalTime.setVisibility(View.INVISIBLE);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timerStarted=false;
               updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
    }
}
