/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kenneth.hiit;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class CameraActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    Global global;
    String ytshortlink, templink;
    YouTubePlayer u2;
    Camera2VideoFragment frag;
    FragmentManager fm;
    @Override
    protected void onDestroy() {
        global.curHandler = null;
        global.client.Send("|qp|" + global.CurrentParty.HostUname);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //     super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ((FrameLayout)findViewById(R.id.container1)).setVisibility(View.GONE);
         fm = getFragmentManager();
      if (null == savedInstanceState) {
    fm.beginTransaction()
            .replace(R.id.container1, Camera2VideoFragment.newInstance(),"sos")
            .commit();
    }

        VideoView vv = (VideoView) findViewById(R.id.vv);

        YouTubePlayerView youTubeView = (YouTubePlayerView)
                findViewById(R.id.videoView1);
        final String DEVELOPER_KEY = "AIzaSyAsJkJqZZ6zW1_hswItJup7FQP3UVNoaM4";
        global = (Global) getApplicationContext();
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //all ready
                    case 20:
                    //    frag.startRecordingVideo();
                        u2.play();

                        break;
                    case 21:
                        //some one not ready
                        Toast.makeText(getApplicationContext(), "Waiting others", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

        global.curHandler = mHandler;
        System.out.println("global.CurrentParty.Url = " + global.CurrentParty.Url);
        templink = global.CurrentParty.Url;
        if (templink.contains("youtube")) {

            ytshortlink = templink.replaceAll(".*v=", "");
            System.out.println("ytshortlink = " + ytshortlink);
            youTubeView.initialize(DEVELOPER_KEY, this);
            youTubeView.setEnabled(false);
            //    youTubeView.set

        } else {
            vv.setMediaController(new MediaController(this));
            Uri uri = Uri.parse(global.Url);
            vv.setVideoURI(uri);
            vv.start();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.cueVideo(ytshortlink);
        u2 = youTubePlayer;
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {
                Log.d("CheckPoint", "CheckPoint onLoading");
                u2.setFullscreen(true);
            }

            @Override
            public void onLoaded(String s) {
                Log.d("CheckPoint", "CheckPoint onLoaded");
                u2.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                global.client.Send("|vdoOK|" + global.CurrentParty.HostUname);
                frag = (Camera2VideoFragment)fm.findFragmentByTag("sos");
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
                global.curHandler = null;
                global.client.Send("|qp|" + global.CurrentParty.HostUname);
                frag.stopRecordingVideo();

                CameraActivity.this.finish();
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
