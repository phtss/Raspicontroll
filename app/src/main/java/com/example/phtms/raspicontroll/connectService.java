package com.example.phtms.raspicontroll;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


public class connectService extends IntentService {
    Socket socket; //Network Socket
    DataOutputStream outputnet; //Network Output Stream
    Context context;

    public connectService() {
        super("Hello");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    public Socket connect(String host, int port, Intent intent){
        try {
            KeyStore AccesKey = KeyStore.getInstance("BKS");
            InputStream inpufile = this.getResources().openRawResource(R.raw.client);
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
                Context.init(null, TM, null);
                SSLSocketFactory socketFactory = Context.getSocketFactory();

                socket = socketFactory.createSocket("host", port);
                return socket;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                        outputnet.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return new Socket();
    }
}
