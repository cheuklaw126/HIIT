package com.example.kenneth.hiit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class testvideo extends YouTubeBaseActivity{
    private ArrayList<Video> videos;
    private ListView allvideo;
    Global global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_video);
        global = (Global) getApplicationContext();
        global.GetAllVideo();
        System.out.println(" inside testvideo global.GetAllVideo &&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        setupvideo();
        allvideo = (ListView) findViewById(R.id.allvideo);
        allvideo.setAdapter(new videoAdapter(this, videos));
        allvideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Video vl = videos.get(i);
                String videolink=(vl.getVideolink());


                Intent intent = new Intent();
                intent.setClass(testvideo.this, playvideo.class);
                intent.putExtra("videolink", videolink);
                System.out.println("onClick videolink put extra = "+videolink);
                startActivity(intent);
            }
        });




        }

public void setupvideo(){
    videos = new ArrayList<Video>();

   if(global.numvideo>0) {
       System.out.println(" global.numvideo = " + global.numvideo);
       for (int i = 0; i < global.numvideo; i++) {
           System.out.println("inside setupvideo for loop global.link1 = " + global.link1[i] + " , global.desc1 = " + global.desc1[i] + " i  = " + i+"global.createby1 = "+global.createby1[i]);
           if(global.createby1[i].equals(global.UserName)) {
               if ((global.link1[i].contains("http://")) || (global.link1[i].contains("https://")) || (global.link1[i].contains("www."))) {
                   videos.add(new Video(
                           global.link1[i], global.desc1[i]));
               } else {
                   System.out.println("this is youtube link!!!");
               }
               //store videoID , link,description
           }
       }
   }


   }
}