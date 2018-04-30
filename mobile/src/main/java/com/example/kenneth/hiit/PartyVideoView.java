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
    String ytlink, ytshortlink, templink;
    final String DEVELOPER_KEY = getString(R.string.DEVELOPER_KEY);

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
        final VideoView mVideoView = (VideoView) findViewById(R.id.vv);
        YouTubePlayerView youTubeView = (YouTubePlayerView)
                findViewById(R.id.videoView1);
        System.out.println("global.Url = " + global.Url);
        templink = global.Url;
        if (templink.contains("youtube")) {
            ytlink = templink.replace("https://www.youtube", "https://http://www.youtube");
            System.out.println("ytlink = " + ytlink);
            ytshortlink = templink.replaceAll(".*v=", "");
            System.out.println("ytshortlink = " + ytshortlink);
            youTubeView.initialize(DEVELOPER_KEY, this);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            mVideoView.setVideoPath(ytlink);
            mVideoView.requestFocus();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mVideoView.start();
                }
            });
        } else {
            vv.setMediaController(new MediaController(this));

            Uri uri = Uri.parse(global.Url);
            vv.setVideoURI(uri);
            vv.start();

        }
    }
}