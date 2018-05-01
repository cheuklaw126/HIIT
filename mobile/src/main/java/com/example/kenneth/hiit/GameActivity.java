package com.example.kenneth.hiit;

import android.app.FragmentManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class GameActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    Global global;
    String ytshortlink, templink;
    YouTubePlayer u2;
    Camera2VideoFragment frag;
    FragmentManager fm;
    boolean a = false;
    VideoView vv;
    boolean isYoutble;

    @Override
    protected void onDestroy() {
        // global.curHandler = null;
        // global.client.Send("|qp|" + global.CurrentParty.HostUname);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        fm = getFragmentManager();
        if (null == savedInstanceState) {
            fm.beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance(), "sos")
                    .commit();
        }
        //((FrameLayout)findViewById(R.id.container1)).setVisibility(View.GONE);
        final String DEVELOPER_KEY = "AIzaSyAsJkJqZZ6zW1_hswItJup7FQP3UVNoaM4";
        global = (Global) getApplicationContext();
        vv = (VideoView) findViewById(R.id.vv000);
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //all ready
                    case 20:
                        //    frag.startRecordingVideo();
                        if (isYoutble) {
                            u2.play();
                        } else {
                            frag = (Camera2VideoFragment) fm.findFragmentByTag("sos");
                            frag.mButtonVideo.callOnClick();
                            vv.start();

                        }
                        break;
                    case 21:
                        //some one not ready
                        Toast.makeText(getApplicationContext(), "Waiting others", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        YouTubePlayerView youTubeView = (YouTubePlayerView)
                findViewById(R.id.videoView000);
        global.curHandler = mHandler;
        // templink = global.CurrentParty.Url;

        templink = "https://www.youtube.com/watch?v=wg8ezm5MXs4";
        templink = "http://cheuklaw126.mynetgear.com/share/vdo/bg.mp4";

        if (templink.contains("youtube")) {
            isYoutble = true;
            vv.setVisibility(View.GONE);
            youTubeView.setVisibility(View.VISIBLE);
            ytshortlink = templink.replaceAll(".*v=", "");
            System.out.println("ytshortlink = " + ytshortlink);
            youTubeView.initialize(DEVELOPER_KEY, this);
            youTubeView.setEnabled(false);
            //    youTubeView.set

        } else {
            isYoutble = false;
            vv.setVisibility(View.VISIBLE);
            vv.setClickable(false);
            int lenght = vv.getDuration();

            vv.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });

            vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    System.out.println("tes2");
                   //u2.setFullscreen(false);
                    global.curHandler = null;
                    global.client.Send("|qp|" + global.CurrentParty.HostUname);
                    frag.mButtonVideo.callOnClick();

                    GameActivity.this.finish();
                }
            });


            vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("CheckPoint", "CheckPoint onLoaded");
                    //       if(isFulledScreen){
                    global.client.Send("|vdoOK|" + global.CurrentParty.HostUname);

                }
            });


            youTubeView.setVisibility(View.GONE);
            vv.setMediaController(new MediaController(this));
            Uri uri = Uri.parse(templink);
            vv.setVideoURI(uri);
            vv.start();
        }
        Button btn = (Button) findViewById(R.id.button5);
        btn.setVisibility(View.GONE);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {

        u2 = youTubePlayer;
        u2.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        youTubePlayer.cueVideo(ytshortlink);
        //    u2.setFullscreen(true);

        u2.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                Log.d("CheckPoint", "CheckPoint 77777777777777777777777777");
                //isFulledScreen=b;

            }
        });

        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {

            @Override
            public void onLoading() {
                Log.d("CheckPoint", "CheckPoint onLoading");

            }

            @Override
            public void onLoaded(String s) {
                Log.d("CheckPoint", "CheckPoint onLoaded");
                //       if(isFulledScreen){
                global.client.Send("|vdoOK|" + global.CurrentParty.HostUname);
                frag = (Camera2VideoFragment) fm.findFragmentByTag("sos");
                //     }
            }

            @Override
            public void onAdStarted() {
                Log.d("CheckPoint", "CheckPoint onAdStarted");
            }

            @Override
            public void onVideoStarted() {
                Log.d("CheckPoint", "CheckPoint onVideoStarted");
                frag.mButtonVideo.callOnClick();

            }

            @Override
            public void onVideoEnded() {
                u2.setFullscreen(false);
                global.curHandler = null;
                global.client.Send("|qp|" + global.CurrentParty.HostUname);
                frag.mButtonVideo.callOnClick();

                GameActivity.this.finish();
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {
                Log.d("CheckPoint", "CheckPoint onError = " + errorReason);
            }
        });


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}