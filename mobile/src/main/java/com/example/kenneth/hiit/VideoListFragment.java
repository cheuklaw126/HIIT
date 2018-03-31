package com.example.kenneth.hiit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

//import com.mynetgear.cheuklaw126.hiit.YouTubeFragment;


public class VideoListFragment extends ListFragment {
//VideoListAdapter adapter;
    /**
     * Empty constructor
     */

    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("In onCreate !!!!!!!");
        // getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();


        // adapter=new VideoListAdapter(getActivity());
        //setListAdapter(adapter);

        //adapter.notifyDataSetChanged();
        VideoListAdapter adapter = new VideoListAdapter(getActivity());
        setListAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        // View listView = getActivity().findViewById(R.id.video_list);
        // System.out.println("listview =  "+listView);
        //FragmentManager fragmentManager = getFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //fragmentTransaction.re
        //YouTubeFragment fragment = new YouTubeFragment();
        // fragmentTransaction.add(R.id.video_list, fragment);
        //fragmentTransaction.commit();
        //Fragment newFragment = new YouTubeFragment();
        // FragmentManager fragmentManager = getFragmentManager();
        // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentTransaction.replace(R.id.video_list, newFragment);
        // fragmentTransaction.addToBackStack(null);
        // fragmentTransaction.commit();

        System.out.println("in VideoListFragment onResume");
        //adapter=new VideoListAdapter(getActivity());
        //setListAdapter(adapter);

        //adapter.notifyDataSetChanged();
        // setListAdapter(new VideoListAdapter(getActivity()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("In onDestroyView !!!!!!!");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("In onPause !!!!!!!");
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        System.out.println("in VideoListFragment onResume");
        setListAdapter(new VideoListAdapter(getActivity()));

    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("In onStop !!!!!!!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("In onDestroy !!!!!!!");
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

}

