package com.autotoll.jmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView videoView = findViewById(R.id.vv);
        String url="http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
        videoView.setVideoPath(url);
        MediaController mediaController=new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
//        videoView.loadUrl(url);
    }
    public void onClick(View view){

    }
}
