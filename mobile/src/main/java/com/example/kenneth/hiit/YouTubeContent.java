package com.example.kenneth.hiit;

import com.example.kenneth.hiit.Global;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.SharedPreferences;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.content.Intent;

import static android.content.Context.MODE_PRIVATE;


public class YouTubeContent {
    //SQLiteDatabase db;
    Global global;
    public  String LINK;
    public  String DESC;
    SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READONLY); //Create DB file

    /**
     * An array of YouTube videos
     */
    public static List<YouTubeVideo> ITEMS = new ArrayList<>();


    /**
     * A map of YouTube videos, by ID.
     */
    public static Map<String, YouTubeVideo> ITEM_MAP = new HashMap<>();
    static {
        VideoList vl = new VideoList();



        System.out.println("inside videocontent v1.getLINK = " + vl.getLINK() + "  vl.getDESC =" + vl.getDESC() +" v1.getcount = "+vl.getcount());
        addItem(new YouTubeVideo(vl.getLINK(), vl.getDESC()));
    }




    private static void addItem(final YouTubeVideo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    /**
     * A POJO representing a YouTube video
     */
    public static class YouTubeVideo {
        public String id;
        public String title;

        public YouTubeVideo(String id, String content) {
            this.id = id;
            this.title = content;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public static void getvideolist() {
        SQLiteDatabase db;
        System.out.println("inside videocontent Enter to get db videolist!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READONLY); //Create DB file


        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM videolist;", null);
        if (cursor.getCount() > 0) {
            System.out.println("inside videocontent Sucess get data from videolist!!!!!!!!!!!!!!");
            System.out.println("inside videocontent Count = " + cursor.getCount());
        } else {
            System.out.println("inside videocontent No data get from videolist!!!!!!!!!!!!!!!");
            System.out.println("inside videocontent Count = " + cursor.getCount());
        }
        while (cursor.moveToNext()) {
            String LINK = cursor.getString(cursor.getColumnIndex("link"));
            String DESC = cursor.getString(cursor.getColumnIndex("desc"));
            addItem(new YouTubeVideo(LINK, DESC));

        }
    }

}