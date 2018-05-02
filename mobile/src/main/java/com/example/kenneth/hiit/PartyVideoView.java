package com.example.kenneth.hiit;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PartyVideoView extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    Global global;
    String ytshortlink, templink;


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) player.cueVideo(ytshortlink); // your video to play
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_videovideo);
        VideoView vv = (VideoView) findViewById(R.id.vv);

        YouTubePlayerView youTubeView = (YouTubePlayerView)
                findViewById(R.id.videoView1);
        final String DEVELOPER_KEY = "AIzaSyAsJkJqZZ6zW1_hswItJup7FQP3UVNoaM4";
        global = (Global) getApplicationContext();
        System.out.println("global.CurrentParty.Url = " + global.CurrentParty.Url);
        templink = global.CurrentParty.Url;
        if (templink.contains("youtube")) {

            ytshortlink = templink.replaceAll(".*v=", "");
            System.out.println("ytshortlink = " + ytshortlink);
            youTubeView.initialize(DEVELOPER_KEY, this);

        } else if ((templink.contains("http://"))||(templink.contains("https://"))||(templink.contains("www."))){
            vv.setMediaController(new MediaController(this));

            Uri uri = Uri.parse(global.Url);
            vv.setVideoURI(uri);
            vv.start();

        }else{
            ytshortlink=templink;
            System.out.println("ytshortlink = " + ytshortlink);
            youTubeView.initialize(DEVELOPER_KEY, this);
        }

    }
}