package com.example.kenneth.hiit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class videoAdapter extends BaseAdapter  {


    private LayoutInflater contextView;
    private ArrayList<Video> videos;

    Global global;
    String ytshortlink, templink;


    private class ViewHolder {
        TextView desc;
        ImageView videov;


        public ViewHolder(TextView desc, ImageView videov) {
            this.desc = desc;
            this.videov = videov;

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
        Drawable drawable;
        if (view == null) {
            view = contextView.inflate(R.layout.video_playback, null);
            holder = new ViewHolder((TextView) view.findViewById(R.id.desc),
                    (ImageView) view.findViewById(R.id.videov));

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        Video video = videos.get(i);
        holder.desc.setText(video.getVideodesc());
        templink = video.getVideolink();
        System.out.println("templink = " + templink);
        if (templink.contains("youtube")) {



            System.out.println("ytshortlink = " + ytshortlink);



        } else if ((templink.contains("http://")) || (templink.contains("https://"))||(templink.contains("www."))) {
            //holder.videov.setVisibility(view.VISIBLE);
            //holder.videov.setVisibility(view.VISIBLE);
            //Bitmap thumb = ThumbnailUtils.createVideoThumbnail(templink,MediaStore.Images.Thumbnails.MINI_KIND);
            Glide.with(contextView.getContext())
                    .load(templink)

                    .thumbnail(0.2f)
                    .into(holder.videov);
            /*holder.videov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videolink= null;
                Intent intent = new Intent();
                intent.setClass(contextView.getContext(), playvideo.class);
                intent.putExtra("videolink", templink);
                System.out.println("onClick templink put extra = "+templink);
                contextView.getContext().startActivity(intent);
            }
            });   */

            //Bitmap thumb = ThumbnailUtils.createVideoThumbnail(templink,
                    // MediaStore.Images.Thumbnails.MINI_KIND);
           //drawable = contextView.getContext().
            // drawable = new BitmapDrawable(contextView.getContext().getResources(), thumb);
            //holder.videov.setImageDrawable(drawable);
            //holder.videov.setImageBitmap();
            //holder.videov.setImageBitmap(R.drawable.cal);
            //holder.videov.setImageBitmap(ThumbnailUtils.createVideoThumbnail(templink,
                   // MediaStore.Video.Thumbnails.MICRO_KIND));
            //Bitmap thumb = ThumbnailUtils.createVideoThumbnail(templink,
                   // MediaStore.Images.Thumbnails.MINI_KIND);
            //holder.videov.setImageBitmap(thumb);
            //holder.videov.setMediaController(new MediaController(view.getContext()));
            //Uri uri = Uri.parse(video.getVideolink());
            //holder.videov.setVideoURI(uri);
            //holder.videov.start();

        }else{
            System.out.println("templink = " + templink);
        }
            return view;
        }
    }


