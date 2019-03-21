package com.example.phtms.raspicontroll;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


public class SensorAlert extends IntentService {
    private Socket socket;
    private DataInputStream inputnet;
    static public boolean check = false;
    static public int mode;

    public SensorAlert() {
        super("Hello");
        mode = 0;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            KeyStore AccesKey = KeyStore.getInstance("BKS");
            InputStream inpufile = getResources().openRawResource(R.raw.client);

            try {
                AccesKey.load(inpufile, "pg2017".toCharArray());
            } finally {
                inpufile.close();
            }

            while (true && !check) {
                try {
                    TrustManagerFactory TMF = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    TMF.init(AccesKey);
                    TrustManager[] TM = TMF.getTrustManagers();

                    SSLContext Context = SSLContext.getInstance("SSL");
                    Context.init(null, TM , null);
                    SSLSocketFactory socketFactory = Context.getSocketFactory();

                    socket = socketFactory.createSocket(configLoad.IPServer, 9005);
                    inputnet = new DataInputStream(socket.getInputStream());
                    mode = inputnet.readInt();
                    check = true;

                    socket.close();
                    inputnet.close();
                    Intent dialogIntent = new Intent(this, AlarmSensor.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dialogIntent);

                } catch (UnknownHostException e) {

                } catch (IOException e) {
                }
            }
        }catch (Exception e){

        }
    }

}
