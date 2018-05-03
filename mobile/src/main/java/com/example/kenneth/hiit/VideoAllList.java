package com.example.kenneth.hiit;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/25.
 */

public class VideoAllList extends AppCompatActivity {
Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_alllist);
        global = (Global) getApplicationContext();
        MainActivity ma = new MainActivity();
        ma.GetExerciseHistory(global.Uid);
    }

    public static final class VideoListFragment extends ListFragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.video_list)).commit();
            //FragmentTransaction transaction = getFragmentManager().beginTransaction();
            //transaction.remove()
            //FragmentManager fragmentManager = getFragmentManager();
            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //int count = fragmentManager.getBackStackEntryCount();
            System.out.println("inside historypage ~ videolistfragment count = ");
            List<Fragment> fragment = getFragmentManager().getFragments();

            //fragmentManager.popBackStack();
            //fragmentManager.findFragmentById(R.id.video_list);
            //fragmentTransaction.commit();
            //VideoListAdapter adapter = new VideoListAdapter(getActivity());
            //setListAdapter(adapter);
            VideoAllList.VideoListAdapter adapter= new VideoAllList.VideoListAdapter(getActivity());
            setListAdapter(adapter);
            //setListAdapter(new VideoListAdapter(getActivity()));


        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);

        }
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {

            final Context context = getActivity();
            final String DEVELOPER_KEY = getString(R.string.DEVELOPER_KEY);
            final YTContent.YTV video = YTContent.ITEMS.get(position);


            //Issue #3 - Need to resolve StandalonePlayer as well
            if (YouTubeIntents.canResolvePlayVideoIntent(context)) {
                //Opens in the StandAlonePlayer, defaults to fullscreen
                startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                        DEVELOPER_KEY, video.id));
            }

        }


    }
    //public void onBackPressed(){
        //System.out.println("onBackPressed @ videoalllist");
    //}
    public static class YTContent {
        //SQLiteDatabase db;
        Global global;
        public  static String[] LINK;
        public  static String[] DESC;
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READONLY); //Create DB file

        /**
         * An array of YouTube videos
         */
        public static List<YTContent.YTV> ITEMS = new ArrayList<>();


        /**
         * A map of YouTube videos, by ID.
         */
        public static Map<String, YTContent.YTV> ITEM_MAP = new HashMap<>();
        static {
            GetAllVideoList vl= new GetAllVideoList();

            LINK=vl.getLINK();
            DESC=vl.getDESC();
            for(int i=0; i<vl.getcount(); i++) {
                String[] slink=LINK;
                System.out.println("inside videocontent getallVideolist.getLINK = " + LINK[i] + "  vl.getDESC =" + DESC[i] + " v1.getcount = " + vl.getcount()+"slink = "+slink[i]);
                //if ((slink[i].contains("http://")) || (slink[i].contains("https://"))) {
                //System.out.println("inside videoAllList  link have http:// or https//");
                //addItem(new YTContent.YTV(LINK[i], DESC[i]));
                //} else {
                    addItem(new YTContent.YTV(LINK[i], DESC[i]));
               // }
            }
        }




        private static void addItem(final YTContent.YTV item) {
            ITEMS.add(item);
            ITEM_MAP.put(item.id, item);
        }

        /**
         * A POJO representing a YouTube video
         */
        public static class YTV {
            public String id;
            public String title;

            public YTV(String id, String content) {
                this.id = id;
                this.title = content;
            }

            @Override
            public String toString() {
                return title;
            }
        }



    }

    public static class VideoListAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener {

        private Context mContext;
        private Map<View, YouTubeThumbnailLoader> mLoaders;
        String ytshortlink, templink;

        public VideoListAdapter(final Context context) {
            mContext = context;
            mLoaders = new HashMap<>();
        }

        @Override
        public int getCount() {
            return YTContent.ITEMS.size();
        }

        @Override
        public Object getItem(int position) {
            return YTContent.ITEMS.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            VideoListAdapter.VideoHolder holder;

            //The item at the current position
            final YTContent.YTV item = YTContent.ITEMS.get(position);

            if (convertView == null) {
                //Create the row
                final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_layout, parent, false);

                //Create the video holder
                holder = new VideoListAdapter.VideoHolder();

                //Set the title
                templink= item.id;
                if (templink.contains("youtube")) {
                    holder.title = (TextView) convertView.findViewById(R.id.textView_title);
                    holder.title.setText(item.title);
                    System.out.println("inside holder.title templink  = " + templink);
                }else if((templink.contains("http://"))||(templink.contains("https://"))||(templink.contains("www."))) {
                    System.out.println("inside holder.title else if templink  = " + templink);
                }else {
                    holder.title = (TextView) convertView.findViewById(R.id.textView_title);
                    holder.title.setText(item.title);
                    System.out.println("inside holder.title templink  = " + templink);
                }

                //templink= item.id;
                System.out.println(" videoalllist templink = "+item.id);
                if (templink.contains("youtube")) {
                    //Initialise the thumbnail
                    ytshortlink = templink.replaceAll(".*v=", "");
                    holder.thumb = (YouTubeThumbnailView) convertView.findViewById(R.id.imageView_thumbnail);
                    holder.thumb.setVisibility(convertView.VISIBLE);
                    System.out.println("ytshortlink = " + ytshortlink);
                    holder.thumb.setTag(ytshortlink);
                    holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);
                }else if((templink.contains("http://"))||(templink.contains("https://"))||(templink.contains("www."))) {
                    //Initialise videoview
                    System.out.println("inside else if templink  = " + templink);

                    //holder.vv = (ImageView) convertView.findViewById(R.id.vvv);
                    //holder.thumb.setVisibility(convertView.INVISIBLE);
                    //holder.vv.setVisibility(convertView.VISIBLE);
                    //Glide.with(mContext)
                            //.load(templink)

                            //.thumbnail(0.2f)
                            //.into(holder.vv);
                    //holder.vv.setImageBitmap(ThumbnailUtils.createVideoThumbnail(templink,MediaStore.Video.Thumbnails.MICRO_KIND));
                    //holder.vv.setOnClickListener(new View.OnClickListener(){
                        /*@Override
                        public void onClick(View v){
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(templink), null);
                        //startActivity(intent);
                        }
                    });  */
                    //BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(),thumb);
                    //holder.vv.setBackground(thumb);
                    //holder.vv.setMediaController(new MediaController(mContext));
                    //Uri uri = Uri.parse(templink);
                    //holder.vv.setVideoURI(uri);
                    //holder.vv.start();
                }else{
                    ytshortlink=templink;
                    holder.thumb = (YouTubeThumbnailView) convertView.findViewById(R.id.imageView_thumbnail);
                    holder.thumb.setVisibility(convertView.VISIBLE);
                    System.out.println("ytshortlink = " + ytshortlink);
                    holder.thumb.setTag(ytshortlink);
                    holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);
                }

                convertView.setTag(holder);
            } else {
                //Create it again
                holder = (VideoListAdapter.VideoHolder) convertView.getTag();
                final YouTubeThumbnailLoader loader = mLoaders.get(holder.thumb);

                if (item != null) {
                    //Set the title
                    holder.title.setText(item.title);

                    //Setting the video id can take a while to actually change the image
                    //in the meantime the old image is shown.
                    //Removing the image will cause the background color to show instead, not ideal
                    //but preferable to flickering images.
                    holder.thumb.setImageBitmap(null);

                    if (loader == null) {
                        //Loader is currently initialising
                        holder.thumb.setTag(item.id);
                    } else {
                        //The loader is already initialised
                        //Note that it's possible to get a DeadObjectException here
                        try {
                            loader.setVideo(item.id);
                        } catch (IllegalStateException exception) {
                            //If the Loader has been released then remove it from the map and re-init
                            mLoaders.remove(holder.thumb);
                            holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);

                        }
                    }

                }
            }
            return convertView;
        }


        @Override
        public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            mLoaders.put(view, loader);
            loader.setVideo((String) view.getTag());
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView thumbnailView, YouTubeInitializationResult errorReason) {
            final String errorMessage = errorReason.toString();
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
        }


        static class VideoHolder {
            YouTubeThumbnailView thumb;
            TextView title;
            ImageView vv;
        }

    }


}
