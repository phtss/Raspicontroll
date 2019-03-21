package com.example.phtms.raspicontroll;

import android.app.IntentService;
import android.content.Intent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


public class configLoad extends IntentService {
    static public Properties ustawienia;
    static public String IPServer = "192.168.0.18";
    static public boolean connect = false;

    private Socket socket;
    private ObjectInputStream inputnet;

    public configLoad() {
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

                socket = socketFactory.createSocket(IPServer, 9008);
                inputnet = new ObjectInputStream(socket.getInputStream());
                ustawienia = (Properties) inputnet.readObject();
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
