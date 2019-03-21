package com.example.phtms.raspicontroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.webkit.WebView;
import android.net.Uri;
import android.os.Bundle;

public class cameraActivity extends AppCompatActivity {
    VideoView videoView;
    Button button;
    MediaController mediaController;
    String mediaURL;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        webView = (WebView)findViewById(R.id.webView);

        int default_zoom_level=100;
        webView.setInitialScale(default_zoom_level);
        mediaURL = "http://user:myp4ssw0rd@192.168.43.43:9002/stream";
        int width = webView.getWidth()-50;
        int height = webView.getHeight()-50;
        webView.loadUrl(mediaURL + "?width=" + width + "&height=" + height);

       /* mediaURL = "http://user:myp4ssw0rd@192.168.0.18:9000/stream/video.h264";

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath(mediaURL);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();*/

    }

    public void returnMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
