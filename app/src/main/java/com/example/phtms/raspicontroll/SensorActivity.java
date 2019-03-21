package com.example.phtms.raspicontroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class SensorActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private CheckBox checkbox1, checkbox2, checkbox3;
    private boolean change;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        change = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        try {
            change = true;
            startService(new Intent(getBaseContext(), configLoad.class));
            checkbox1 = (CheckBox) findViewById(R.id.sensorMove);
            checkbox2 = (CheckBox) findViewById(R.id.sensorMetan);
            checkbox3 = (CheckBox) findViewById(R.id.smartplug);

            checkbox1.setChecked(Boolean.parseBoolean(configLoad.ustawienia.getProperty("Sensor1")));
            checkbox2.setChecked(Boolean.parseBoolean(configLoad.ustawienia.getProperty("Sensor2")));
            checkbox3.setChecked(Boolean.parseBoolean(configLoad.ustawienia.getProperty("SmartPlug")));

        } catch (Exception e){
            change = false;
        }
    }

    public void returnMain(View view){
            if(change){
                configLoad.ustawienia.setProperty("Sensor1", String.valueOf(checkbox1.isChecked()));
                configLoad.ustawienia.setProperty("Sensor2", String.valueOf(checkbox2.isChecked()));
                configLoad.ustawienia.setProperty("SmartPlug", String.valueOf(checkbox3.isChecked()));
            }
            startService(new Intent(getBaseContext(), configSend.class));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
