package com.example.kenneth.hiit;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenneth.hiit.YouTubeContent;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.Map;



public class VideoListAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener {

    private Context mContext;
    private Map<View, YouTubeThumbnailLoader> mLoaders;

    public VideoListAdapter(final Context context) {
        mContext = context;
        mLoaders = new HashMap<>();

    }

    @Override
    public int getCount() {
        return YouTubeContent.ITEMS.size();
    }

    @Override
    public Object getItem(int position) {
        return YouTubeContent.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VideoHolder holder;

        //The item at the current position
        final YouTubeContent.YouTubeVideo item = YouTubeContent.ITEMS.get(position);

        if (convertView==null) {
        //Create the row
            System.out.println("convertview null = "+convertView);
            System.out.println("convertView nulll");
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_layout, parent, false);

        //Create the video holder
        holder = new VideoHolder();

        //Set the title
        holder.title = (TextView) convertView.findViewById(R.id.textView_title);
        holder.title.setText(item.title);

        //Initialise the thumbnail
        holder.thumb = (YouTubeThumbnailView) convertView.findViewById(R.id.imageView_thumbnail);
        holder.thumb.setTag(item.id);
        holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);

        convertView.setTag(holder);
        } else {
        //Create it again

            System.out.println("convertView not nulll");
        //holder = (VideoHolder) convertView.getTag();
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
        holder= new VideoHolder();
           System.out.println("convertview = "+convertView);
            mLoaders.remove(holder.thumb);
            mLoaders.remove(holder.title);
            final YouTubeThumbnailLoader loader = mLoaders.get(holder.thumb);
            holder.title = (TextView) convertView.findViewById(R.id.textView_title);
            holder.title.setText(item.title);

            //Initialise the thumbnail
            holder.thumb = (YouTubeThumbnailView) convertView.findViewById(R.id.imageView_thumbnail);
            holder.thumb.setTag(item.id);
            holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);
            convertView.setTag(holder);
        // if (item != null) {
        //Set the title
        //holder.title.setText(item.title);

        //Setting the video id can take a while to actually change the image
        //in the meantime the old image is shown.
        //Removing the image will cause the background color to show instead, not ideal
        //but preferable to flickering images.
        //holder.thumb.setImageBitmap(null);

         //if (loader == null) {
        //Loader is currently initialising
         //holder.thumb.setTag(item.id);
        //} else {
        //The loader is already initialised
        //Note that it's possible to get a DeadObjectException here
         //try {
        //loader.setVideo(item.id);
        //} catch (IllegalStateException exception) {
        //If the Loader has been released then remove it from the map and re-init
        //mLoaders.remove(holder.thumb);
        //holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);

        //}
        }

        //}
        //}
        return convertView;
    }


    @Override
    public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
        System.out.println("inside oninitializationsuccess");
        if(loader.hasPrevious()){
            System.out.println("youtubethumbnailloader hasprevious");
        loader.release();}
        mLoaders.put(view, loader);
        loader.setVideo((String) view.getTag());
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView thumbnailView, YouTubeInitializationResult errorReason) {
        System.out.println("inside oninitializationfailure");
        final String errorMessage = errorReason.toString();
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }


    static class VideoHolder {
        YouTubeThumbnailView thumb;
        TextView title;
    }

}
