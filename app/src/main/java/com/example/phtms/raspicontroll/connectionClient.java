package com.example.phtms.raspicontroll;

/**
 * Created by phtms on 10.02.2017.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


public class connectionClient extends AsyncTask<String, Void, Void> {
    Socket socket; //Network Socket
    DataOutputStream  outputnet; //Network Output Stream
    Context context;

    void setContext(Context mycontext) { context = mycontext;}
    @Override
    protected Void doInBackground(String... params) {
        try {
            KeyStore AccesKey = KeyStore.getInstance("BKS");
            InputStream inpufile = context.getResources().openRawResource(R.raw.client);

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

                socket = socketFactory.createSocket(params[0], Integer.parseInt(params[1]));
                outputnet = new DataOutputStream(socket.getOutputStream());
                outputnet.writeInt(Integer.valueOf(params[2]));
                configLoad.connect = true;
                outputnet.flush();

                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
