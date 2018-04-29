package com.example.kenneth.hiit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class PartyVideoView extends AppCompatActivity {
    Global global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_videovideo);
        VideoView vv = (VideoView) findViewById(R.id.vv);
        vv.setMediaController(new MediaController(this));

        Uri uri = Uri.parse(global.Url);
        vv.setVideoURI(uri);
        vv.start();

    }
}