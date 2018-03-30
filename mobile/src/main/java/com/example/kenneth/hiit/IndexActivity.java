package com.example.kenneth.hiit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class IndexActivity extends AppCompatActivity {
    Button btn_login, btn_register;
    VideoView vdo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        vdo = (VideoView)findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bg);
        vdo.setVideoURI(uri);
      vdo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override
          public void onPrepared(MediaPlayer mp) {
              mp.setLooping(true);
          }
      });
        vdo.start();



        btn_login = (Button) findViewById(R.id.button);
        btn_register = (Button) findViewById(R.id.button2);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IndexActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(IndexActivity.this, LoginActivity.class);
                startActivity(intent);



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vdo.start();
    }
}
