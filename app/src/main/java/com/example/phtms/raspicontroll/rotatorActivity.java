package com.example.phtms.raspicontroll;

import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class rotatorActivity extends AppCompatActivity {
    private int positionRotator = 0;
    private int move = 5;
    public connectionClient stepperClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotator);
        startService(new Intent(getBaseContext(), configLoad.class));
        positionRotator = Integer.parseInt(configLoad.ustawienia.getProperty("position"));

        TextView newtext = (TextView) findViewById(R.id.pozycja);
        newtext.setText("Pozycja: "+configLoad.ustawienia.getProperty("position")+"%");
    }

    private void update(){
        TextView newtext = (TextView) findViewById(R.id.pozycja);
        newtext.setText("Pozycja: "+configLoad.ustawienia.getProperty("position")+"%");

        stepperClient = new connectionClient();
        stepperClient.setContext(this);
        stepperClient.execute(configLoad.IPServer, "9004", String.valueOf(positionRotator));
        startService(new Intent(getBaseContext(), configSend.class));


    }

    public void upADD(View v){
        if(positionRotator < 100){
            positionRotator += move;
            configLoad.ustawienia.setProperty("position", String.valueOf(positionRotator));
        }
        update();
    }

    public void downADD(View v){
       if(positionRotator > 0){
           positionRotator -= move;
           configLoad.ustawienia.setProperty("position", String.valueOf(positionRotator));
       }
       update();
    }



    public void returnMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
