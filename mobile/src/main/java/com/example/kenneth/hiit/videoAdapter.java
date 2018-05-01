package com.example.kenneth.hiit;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import static android.view.View.GONE;

public class videoAdapter extends BaseAdapter implements YouTubePlayer.OnInitializedListener {

    YouTubePlayer Player;
    private LayoutInflater contextView;
    private ArrayList<Video> videos;
    final String DEVELOPER_KEY = "AIzaSyAsJkJqZZ6zW1_hswItJup7FQP3UVNoaM4";
    Global global;
    String ytshortlink, templink;

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored){
            player.cueVideo(ytshortlink); // your video to play
            player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                @Override
                public void onFullscreen(boolean b) {
                    player.loadVideo(ytshortlink);
                }
            });
        }

        //if(!wasRestored)
        //Player=player;
        //Player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
           /// @Override
            //public void onFullscreen(boolean wasRestored) {

            //}
        //});

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
    }

    private class ViewHolder {
        TextView desc;
        VideoView videov;
        YouTubePlayerView ytview;

        public ViewHolder(TextView desc, VideoView videov, YouTubePlayerView ytview) {
            this.desc = desc;
            this.videov = videov;
            this.ytview = ytview;
        }
    }

    public videoAdapter(Context context, ArrayList<Video> videos) {
        contextView = LayoutInflater.from(context);
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int i) {
        return videos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = contextView.inflate(R.layout.video_playback, null);
            holder = new ViewHolder((TextView) view.findViewById(R.id.desc),
                    (VideoView) view.findViewById(R.id.videov),
                    (YouTubePlayerView) view.findViewById(R.id.ytview));
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        Video video = videos.get(i);
        holder.desc.setText(video.getVideodesc());
        templink = video.getVideolink();
        System.out.println("templink = " + templink);
        if (templink.contains("youtube")) {
            //holder.ytview.setVisibility(view.VISIBLE);
            holder.ytview.setVisibility(view.VISIBLE);
            ytshortlink = templink.replaceAll(".*v=", "");
            System.out.println("ytshortlink = " + ytshortlink);
            holder.ytview.initialize(DEVELOPER_KEY, this);


        } else if ((templink.contains("http://")) || (templink.contains("https://"))) {
            //holder.videov.setVisibility(view.VISIBLE);
            holder.ytview.setVisibility(view.VISIBLE);
            holder.videov.setMediaController(new MediaController(view.getContext()));
            Uri uri = Uri.parse(video.getVideolink());
            holder.videov.setVideoURI(uri);
            holder.videov.start();
        } else {
            ytshortlink = templink;
            holder.ytview.setVisibility(view.VISIBLE);
            holder.ytview.initialize(DEVELOPER_KEY, this);

        }
            return view;
        }
    }


