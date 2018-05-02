package com.example.kenneth.hiit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class playvideo extends AppCompatActivity {
    VideoView vv;
    String videolink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video);
        VideoView vv = (VideoView) findViewById(R.id.vv);
        Bundle getlink = getIntent().getExtras();
        if (getlink!=null){
            videolink = getlink.getString("videolink");
        }
        System.out.println(" videolink = "+videolink);
        vv.setMediaController(new MediaController(this));
        Uri uri = Uri.parse(videolink);
        vv.setVideoURI(uri);
        vv.start();
    }
}
