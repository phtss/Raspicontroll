package com.example.phtms.raspicontroll;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.security.MessageDigest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public connectionClient client;
    TextView textView;
    Button button1, button2,button3;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //password: pg2017
        password = "970fd4baa344c85a3126f8a490b01c39a76747f77874b9f15f9ba80702db674b";



        if(configLoad.connect){
            EditText editText = (EditText) findViewById(R.id.passwd);
            textView = (TextView) findViewById(R.id.textView);
            textView.setText("Połączono");

            button1 = (Button) findViewById(R.id.SensorButton);
            button2 = (Button) findViewById(R.id.RotatorButton);
            button3 = (Button) findViewById(R.id.CamerButton);

            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            editText.setEnabled(false);
        }

    }

    public void sensorGo(View view){
        Intent intent = new Intent(this, SensorActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    public void cameraGo(View view){
        Intent intent = new Intent(this, cameraActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    public void rotatorGo(View view){
        Intent intent = new Intent(this, rotatorActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private String getPasswordHash(){
        EditText editText = (EditText) findViewById(R.id.passwd);
        String passwd = "";
        try {
            MessageDigest dg = MessageDigest.getInstance("SHA-256");
            dg.update(editText.getText().toString().getBytes("UTF-8"));
            byte[] digest = dg.digest();
            passwd = String.format("%064x", new java.math.BigInteger(1, digest));

        } catch (Exception e){
        }

        return passwd;
    }

    public void checkConnection(View view){
        EditText editText = (EditText) findViewById(R.id.passwd);
        if(password.equals(getPasswordHash())){
            Log.e("taga",getPasswordHash()+ "  |  " + password);
            if(configLoad.connect){
                startService(new Intent(getBaseContext(), configLoad.class));
                textView = (TextView) findViewById(R.id.textView);
                textView.setText("Połączono");

                button1 = (Button) findViewById(R.id.SensorButton);
                button2 = (Button) findViewById(R.id.RotatorButton);
                button3 = (Button) findViewById(R.id.CamerButton);

                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                editText.setEnabled(false);
            }
            else {
                client = new connectionClient();
                client.setContext(this);

                client.execute(configLoad.IPServer, "9004", "0");
                startService(new Intent(getBaseContext(), SensorAlert.class));
            }
        }
    }


}
