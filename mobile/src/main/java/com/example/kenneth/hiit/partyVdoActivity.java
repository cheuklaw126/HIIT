package com.example.kenneth.hiit;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import java.net.URL;

public class partyVdoActivity extends AppCompatActivity {
    VideoView vdo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_vdo);
        vdo = (VideoView) findViewById(R.id.videoView3);
        vdo.setVideoPath("https://http://www.youtube.com/watch?v=XSMOykMIO3c");
        int length = vdo.getDuration();

        vdo.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });

        vdo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("tes2");

            }
        });


        vdo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                System.out.println("test");
            }
        });


    }
}
