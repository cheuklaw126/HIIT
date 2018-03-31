package com.example.kenneth.hiit;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_alllist);


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
                System.out.println("inside videocontent getallVideolist.getLINK = " + LINK[i] + "  vl.getDESC =" + DESC[i] + " v1.getcount = " + vl.getcount());
                addItem(new YTContent.YTV(LINK[i], DESC[i]));
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
                holder.title = (TextView) convertView.findViewById(R.id.textView_title);
                holder.title.setText(item.title);

                //Initialise the thumbnail
                holder.thumb = (YouTubeThumbnailView) convertView.findViewById(R.id.imageView_thumbnail);
                holder.thumb.setTag(item.id);
                holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);

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
        }

    }


}
