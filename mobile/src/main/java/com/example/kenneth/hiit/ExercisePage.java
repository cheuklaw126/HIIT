package com.example.kenneth.hiit;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenneth.hiit.LoginActivity;
import com.example.kenneth.hiit.YouTubeContent;
import com.example.kenneth.hiit.YouTubeFragment;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ExercisePage extends AppCompatActivity {
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_page);
        //Button lastRedo = (Button) findViewById(R.id.lastRedo);
        //Button previousVideo = (Button) findViewById(R.id.previousVideo);
        //Button nextVideo = (Button) findViewById(R.id.nextVideo);
        global = (Global) getIntent().getSerializableExtra("global");
        getExData();
    }



    private void getExData() {
        String userID = getSharedPreferences("user", MODE_PRIVATE)
                .getString("global.Uid", "");

        // global.GetExerciseHistory(global.Uid);
        global.GetVideo(global.vid);
        //String userName = getSharedPreferences("user", MODE_PRIVATE)
        // .getString("NAME", "");
        //String PW = getSharedPreferences("user", MODE_PRIVATE)
        //.getString("PASSWORD","");
        //String userID = String.valueOf(1);

        //Toast.makeText(getApplicationContext(), "data got " + userID + " " + userName + " " + PW , Toast.LENGTH_LONG).show();
        //String query = String.format("select * from exeriseHistory where uID =%s ", userID);
        //final ArrayList<String> querys = new ArrayList<String>();
        //querys.add(query);
        // IOObject io = null;
        // try {
        // io = new IOObject("ExecuteReader", querys);
        // io.Start();
        // JSONObject jobj = io.getReturnObject();
        //  JSONArray jsonArray =io.getReturnObject().getJSONArray("data");
        //  JSONObject eh=jsonArray.getJSONObject(0);
        /// String lastD = eh.getString("createDate");
        //String lastT= eh.getString("totTime");
        //String cc = eh.getString("caloriesCal");
        // String hr = eh.getString("heartRate");
        // String eg = eh.getString("exGain");
        //  String com = eh.getString("isComplete");
        //  String vid = eh.getString("vid");
        // String queryV = String.format("select * from movie where vid =%s ",vid);

        //   final ArrayList<String> queryvs = new ArrayList<String>();
        //  queryvs.add(queryV);
        //  io = new IOObject("ExecuteReader", queryvs);
        //  io.Start();
        //  JSONObject vjobj = io.getReturnObject();
        //  JSONArray vjsonArray =io.getReturnObject().getJSONArray("data");
        //  JSONObject veh=vjsonArray.getJSONObject(0);
        // String vn = veh.getString("vname");
        //  String link = veh.getString("link");
        // String desc= veh.getString("description");

        //YouTubeContent ytc= new YouTubeContent();
        //ytc.setLink(link);
        //YouTubeVideo ytv= new YouTubeVideo(link, desc);
        //ytv.YouTubeVideo(link,desc);
        if (global.compEx>0) {
            SharedPreferences storeUN = getApplicationContext().getSharedPreferences("user", 0); // 0 - for private mode
            SharedPreferences.Editor editor = storeUN.edit();
            editor.putString("VID", String.valueOf(global.vid))
                    .putString("LINK", global.link)
                    .putString("DESC", global.desc)
                    .commit(); //store videoID , link,description
            String storeLink = getSharedPreferences("user", MODE_PRIVATE)
                    .getString("LINK", "");
            System.out.println(" storelink = " + storeLink);
            // storeVL=storeLink;
            //// System.out.println("storeVL = "+storeVL);
            // putLink();
            TextView showTEXT = (TextView) findViewById(R.id.lastDT);
            showTEXT.setText(global.lastD);

            TextView complete = (TextView) findViewById(R.id.comYN);
            complete.setText(global.com);

            TextView exN = (TextView) findViewById(R.id.exName);
            exN.setText(global.vn);

            TextView doT = (TextView) findViewById(R.id.dotime);
            doT.setText(global.lastT);

            TextView ccal = (TextView) findViewById(R.id.CalBurn);
            ccal.setText(global.cc);

            TextView shr = (TextView) findViewById(R.id.heartRate);
            shr.setText(global.hr);

            TextView exG = (TextView) findViewById(R.id.exGain);
            exG.setText(global.eg);
        }else{

        }
        //Toast.makeText(getApplicationContext(), "link =" + link, Toast.LENGTH_LONG).show();

        //Toast.makeText(getApplicationContext(), "content link =" + vl, Toast.LENGTH_LONG).show();
        //if (jsonArray.length() > 0) {

        //String exdata=jsonArray.getString(1);
        // Toast.makeText(getApplicationContext(), "data got " + exdata, Toast.LENGTH_LONG).show();
        //Intent intent = new Intent();
        //intent.setClass(LoginActivity.this, HiitMain.class);
        //startActivity(intent);

        //} else {
        //Toast.makeText(getApplicationContext(), "no data get", Toast.LENGTH_LONG).show();
        //Intent intent = new Intent();
        //intent.setClass(LoginActivity.this, LoginActivity.class);
        //startActivity(intent);

        //}
        //} catch (Exception e) {
        //e.printStackTrace();
        //}



    }
    //public void putLink() {
    //String userID = getSharedPreferences("user", MODE_PRIVATE)
    //.getString("UID", "");
    // System.out.println("inside putlink link = " + storeVL);
    //String storeLink = getSharedPreferences("user", MODE_PRIVATE)
    //.getString("LINK", "");
    //setLink(storeLink);
    //}

    //public void setLink(String videolink) {
    //Toast.makeText(getApplicationContext(), "setlink =" + videolink, Toast.LENGTH_LONG).show();
    //this.vl = videolink;
    //getLink();
    //}

    //public String getLink() {
    // putLink();
    // System.out.println("getLink = " + this.vl);
    //Toast.makeText(getApplicationContext(), "getlink =" + vl, Toast.LENGTH_LONG).show();
    // return this.vl;
    // }

}




