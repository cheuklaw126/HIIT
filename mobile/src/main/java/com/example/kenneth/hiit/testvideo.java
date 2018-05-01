package com.example.kenneth.hiit;

import android.net.Uri;
import android.os.Bundle;
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




        }

public void setupvideo(){
    videos = new ArrayList<Video>();

   if(global.numvideo>0) {
       System.out.println(" global.numvideo = " + global.numvideo);
       for (int i = 0; i < global.numvideo; i++) {
           System.out.println("inside setupvideo for loop global.link1 = " + global.link1[i] + " , global.desc1 = " + global.desc1[i] + " i  = " + i);
           videos.add(new Video(
                   global.link1[i], global.desc1[i]));
           //store videoID , link,description
       }
   }


   }
}