package com.rahulmakhija.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    CountDownTimer countDownTimer;
    boolean start = true;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign View Elements to variables
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        textView = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);

        //Set Maximum and current value of seekbar
        seekBar.setMax(600);
        seekBar.setProgress(10);

        //When seekbar value is changed
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //When the position is changed
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("progress", String.valueOf(progress)); //progress in seconds
                updateTextView(progress);
            }

            //When change has started
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //When change has ended
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

                //When button is clicked-> Start the Countdown Timer
                public void startTimer(View view) {
                    if (start) {
                        button.setText("Stop");
                        start = !start;
                        seekBar.setEnabled(false);
                        Log.i("seekbar", String.valueOf(seekBar.getProgress())); //in seconds
                        countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                            //Ever Timer Tick-> Update textview to match current position
                            @Override
                            public void onTick(long msLeft) {
                                int x = (int) msLeft / 1000;
                                updateTextView(x);
                                Log.i("Ticking", String.valueOf(x));
                            }

                            @Override
                            public void onFinish() {
                                updateTextView(0);
                                Log.i("Finished", "Done");
                                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                                mediaPlayer.start();
                            }
                        };
                        countDownTimer.start();
                    }
                    else {
                        button.setText("Start");
                        start = !start;
                        seekBar.setEnabled(true);
                        countDownTimer.cancel();
                    }
                }

                            //Change the update the textView Minutes:Seconds
                            public void updateTextView(int secondsLeft){
                                int minutes = (int) secondsLeft/60;
                                int seconds = secondsLeft - (minutes*60);
                                String sec = String.valueOf(seconds);
                                if(seconds <=9){
                                    sec = "0" + seconds;
                                }

                                textView.setText(minutes + ":" + sec);
                            }
}
