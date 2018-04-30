package com.example.kenneth.hiit;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

//  import com.mynetgear.cheuklaw126.hiit.YouTubeFragment;


public class HistoryPage extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    int vid, uid;
    String lastD, lastT, cc, eg, com, hr, vn, eid,vl;

    SQLiteDatabase db;

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        System.out.println("vl = "+vl);
        if (!wasRestored) player.cueVideo(vl); // your video to play


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        eid = getIntent().getStringExtra("eid");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);

        YouTubePlayerView youTubeView = (YouTubePlayerView)
                findViewById(R.id.hpyt);
        getExData(eid);
        final String DEVELOPER_KEY = "AIzaSyAsJkJqZZ6zW1_hswItJup7FQP3UVNoaM4";
        youTubeView.initialize(DEVELOPER_KEY, this);


       // getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.video_list)).commit();



    }


    private void getExData(String eid) {

        String EID = eid;
        System.out.println("inside HistoryPage in getEXDATA noex  ");

        System.out.println("Enter to get db exlist!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
        try {

            System.out.println("inside HistoryPage ELECT * from exlist WHERE elid=" + EID + ";");
            Cursor cursor1 = db.rawQuery("SELECT * from exlist WHERE elid=" + EID + ";", null);

            if (cursor1.getCount() > 0) {
                System.out.println("inside HistoryPage Sucess get data from exlist!!!!! INSIDE exLIST!!!!");
                System.out.println("inside HistoryPage Count = " + cursor1.getCount());

                while (cursor1.moveToNext()) {
                    vid = cursor1.getInt(cursor1.getColumnIndex("vid"));
                    uid = cursor1.getInt(cursor1.getColumnIndex("uid"));
                    lastD = cursor1.getString(cursor1.getColumnIndex("lastD"));
                    lastT = cursor1.getString(cursor1.getColumnIndex("lastT"));
                    cc = cursor1.getString(cursor1.getColumnIndex("cc"));
                    com = cursor1.getString(cursor1.getColumnIndex("com"));
                    eg = cursor1.getString(cursor1.getColumnIndex("eg"));
                    hr = cursor1.getString(cursor1.getColumnIndex("hr"));
                    //System.out.println("inside videolist " + LINK+"  "+DESC);
                    db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
                    System.out.println("inside HistoryPage SELECT * from videolist where vid=" + vid + ";");
                    Cursor cursor2 = db.rawQuery("SELECT * from videolist where vid=" + vid + ";", null);
                    while (cursor2.moveToNext()) {
                        vn = cursor2.getString(cursor2.getColumnIndex("vname"));
                        vl= cursor2.getString(cursor2.getColumnIndex("vlink"));
                    }
                    TextView showTEXT = (TextView) findViewById(R.id.lastDT);
                    showTEXT.setText(lastD);

                    TextView complete = (TextView) findViewById(R.id.comYN);
                    complete.setText(com);

                    TextView exN = (TextView) findViewById(R.id.exName);
                    exN.setText(vn);

                    TextView doT = (TextView) findViewById(R.id.dotime);
                    doT.setText(lastT);

                    TextView ccal = (TextView) findViewById(R.id.CalBurn);
                    ccal.setText(cc);

                    TextView shr = (TextView) findViewById(R.id.heartRate);
                    shr.setText(hr);

                    TextView exG = (TextView) findViewById(R.id.exGain);
                    exG.setText(eg);

                }
            } else {
                Toast.makeText(getApplicationContext(), "You havn't exercise recond !", Toast.LENGTH_LONG).show();
                finish();
                onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*public static final class VideoListFragment extends ListFragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            System.out.println("inside historypage ~ videolistfragment count = ");
            List<Fragment> fragment = getFragmentManager().getFragments();
            if (fragment != null) {

                System.out.println(" in HistoryList onclick fragment not null");
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.video_list)).commit();
            }

            VideoListAdapter adapter = new VideoListAdapter(getActivity());

            if (adapter.getCount() > 0) {
                System.out.println("adapter.getCount() "+adapter.getCount());

                adapter.notifyDataSetChanged();
                setListAdapter(adapter);
            }else {
                setListAdapter(adapter);
            }


        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);

        }
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {

            final Context context = getActivity();
            final String DEVELOPER_KEY = getString(R.string.DEVELOPER_KEY);
            final YouTubeContent.YouTubeVideo video = YouTubeContent.ITEMS.get(position);


            //Issue #3 - Need to resolve StandalonePlayer as well
            if (YouTubeIntents.canResolvePlayVideoIntent(context)) {
                //Opens in the StandAlonePlayer, defaults to fullscreen
                startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                        DEVELOPER_KEY, video.id));
            }

        }


    }*/

}