package com.example.timers;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView text;SeekBar timerseek; Boolean counter=false;
    Button b;CountDownTimer count; long milliLeft;
    public void reset()  // we create this function to reset the timer to 4:30 by just pressing the button instead of using the seekbar to do so once the timer is finished
    {
        text.setText("10:00");
        counter=false;
        timerseek.setEnabled(true);
        timerseek.setProgress(600);
        count.cancel();
        b.setText("START");
    }
    public void timerStart(long timelength)
    {
        count = new CountDownTimer(timerseek.getProgress()* 1000 +100, 1000) // getProgress will give seconds so we multiply by 1000 for milliseconds
        {                                              // we add 100 milliseconds as the timer takes fraction of seconds to end and execute the onFinish()
            @Override
            public void onTick(long millisUntilFinished) {
                milliLeft=millisUntilFinished;
               updateTimer ((int) millisUntilFinished / 1000); // updateTimer method is called

            }

            @Override
            public void onFinish() {
                //Log.d("finished", "done");
                // MediaPlayer media= MediaPlayer.create(getApplicationContext(), R.raw.song);  "we don't use this as it would refer to the timer so instead getApplication"
                reset();      // reset method is called
            }
        }.start();
    }

    public void timerButton(View v) {
        if (b.getText().equals("START"))
        {
            Log.i("Started", b.getText().toString());
            b.setText("Pause");
            timerseek.setEnabled(false);   //the seekbar is disabled when the timer is running
            timerStart(  timerseek.getProgress()* 1000);
        }
        else if (b.getText().equals("Pause"))
        {
            Log.i("Paused", b.getText().toString());
            b.setText("Resume");
            timerseek.setEnabled(true);
            count.cancel();
        }
        else if (b.getText().equals("Resume"))
        {
            b.setText("Pause");
            timerseek.setEnabled(false);
            timerStart(milliLeft);
        }

       /* if (counter) {
            counter = false;
            b.setText("START");
            timerseek.setEnabled(true);
            count.cancel();           // this will stop the timer
        }
        else
        {
            counter=true;
            timerseek.setEnabled(false);
            b.setText("STOP");
            timerStart(400);
        } */
    }

   public void updateTimer(int secondsleft) {

       int min = secondsleft / 60;
       int sec = secondsleft - (min * 60);
       if (min < 10) {
           if (sec < 10)
               text.setText("0" + Integer.toString(min) + ":" + "0" + Integer.toString(sec));
           else
               text.setText("0" + Integer.toString(min) + ":" + Integer.toString(sec));
       } else {
           if (sec < 10)
               text.setText(Integer.toString(min) + ":" + "0" + Integer.toString(sec));
           else
               text.setText(Integer.toString(min) + ":" + Integer.toString(sec));
       }

   }


    @Override
    protected void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         timerseek= findViewById(R.id.seekBar);
         text=findViewById(R.id.textView);
         b=findViewById(R.id.button);

        timerseek.setMax(900); //time in seconds
        timerseek.setProgress(600);
       timerseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser)
           {

               updateTimer(i);

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