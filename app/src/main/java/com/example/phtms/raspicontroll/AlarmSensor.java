package com.example.phtms.raspicontroll;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class AlarmSensor extends AppCompatActivity {
    String mediaURL;
    WebView webView;
    MediaPlayer BuzzerAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_sensor);


        TextView textSensor;
        textSensor = (TextView) findViewById(R.id.textView3);


        if(SensorAlert.mode==1) textSensor.setText("Czujnik ruchu zareagował!!");
        if(SensorAlert.mode==2) textSensor.setText("Czujnik metanu zareagował!!");
        
        BuzzerAlarm = MediaPlayer.create(this, R.raw.buzzer);

        BuzzerAlarm.setLooping(true);
        BuzzerAlarm.start();

        webView = (WebView)findViewById(R.id.vision);

        mediaURL = "http://user:myp4ssw0rd@" + configLoad.IPServer + ":9002/stream";
        webView.loadUrl(mediaURL);

        stopService(new Intent(getBaseContext(), SensorAlert.class));
    }


    public void stopalarm(View view){
        BuzzerAlarm.stop();
    }

    public void back(View view){
        SensorAlert.check = false;
        startService(new Intent(getBaseContext(), SensorAlert.class));

        BuzzerAlarm.stop();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
