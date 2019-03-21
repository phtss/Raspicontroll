package com.example.phtms.raspicontroll;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.net.UnknownHostException;
import java.security.KeyStore;


public class configSend extends IntentService {
    private Socket socket;
    private ObjectOutputStream outputnet;

    public configSend() {
        super("Hello");
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


            try {
                TrustManagerFactory TMF = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                TMF.init(AccesKey);
                TrustManager[] TM = TMF.getTrustManagers();

                SSLContext Context = SSLContext.getInstance("SSL");
                Context.init(null, TM , null);
                SSLSocketFactory socketFactory = Context.getSocketFactory();

                socket = socketFactory.createSocket(configLoad.IPServer, 9007);
                outputnet = new ObjectOutputStream(socket.getOutputStream());
                outputnet.writeObject(configLoad.ustawienia);
                socket.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e){
        }
    }

}
