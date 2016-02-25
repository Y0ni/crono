package com.example.t3ll0.cronometro;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView hrText;
    TextView minText;
    TextView segText;
    TextView miliText;
    ListView list;
    Button startButton;
    Button stopButton;
    long hr;
    long min;
    long sec;
    long mil;
    long startTime;
    long currentTime;
    boolean onStop=false;
    boolean isStarted;
    List<String> listTimes= new ArrayList<>();
    ArrayAdapter<String> AA;
Handler handler= new Handler();

    Runnable  myRunnable=new Runnable() {
        @Override
        public void run() {
            currentTime=System.currentTimeMillis()-startTime;
            UpdateTimer(currentTime);
            handler.postDelayed(this,1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         startButton=(Button)findViewById(R.id.button);
         stopButton=(Button)findViewById(R.id.button2);
         hrText =(TextView)findViewById(R.id.HrtextView);
         minText=(TextView)findViewById(R.id.MinTextView);
         segText=(TextView)findViewById(R.id.SegTextView);
         miliText=(TextView)findViewById(R.id.MiliTextView);
        list=(ListView)findViewById(R.id.listView);
        AA= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listTimes);
        list.setAdapter(AA);
    }

    public void StartTimer(View view){
        if(!isStarted) {
            startTime = System.currentTimeMillis();
            handler.post(myRunnable);
            isStarted=true;
            startButton.setText("Reset");
        }else {
            isStarted=false;
            Stop();
            ClearTimer();
            AddTime();
            startButton.setText("Start");

        }

    }
    public void StopTimer(View view){
        if(isStarted) {
            if (!onStop) {
                Stop();
                stopButton.setText("Resume");

                onStop = true;
            } else {
                stopButton.setText("Stop");
                handler.post(myRunnable);
                onStop = false;
            }
        }
    }
    public void Stop(){
        handler.removeCallbacks(myRunnable);
    }
    public void UpdateTimer(long time) {
     mil= (int)(time%1000);
        sec= (int)TimeUnit.MILLISECONDS.toSeconds(time);
         min=(int)TimeUnit.MILLISECONDS.toMinutes(time);
         hr=(int)TimeUnit.MILLISECONDS.toHours(time);
        hrText.setText(String.valueOf(hr));
        segText.setText(String.valueOf(sec%60));
        minText.setText(String.valueOf(min%60));
        miliText.setText(String.valueOf(mil));
    }
    public void ClearTimer(){
        hrText.setText("00");
        segText.setText("00");
        minText.setText("00");
        miliText.setText("00");
    }
    public void AddTime(){
listTimes.add(hr + ":" + min + ":" + sec + ":" + mil);
        AA.notifyDataSetChanged();
    }


}
