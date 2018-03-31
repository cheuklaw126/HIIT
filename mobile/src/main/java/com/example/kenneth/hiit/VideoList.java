package com.example.kenneth.hiit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2018/3/15.
 */

public class VideoList {
    SQLiteDatabase db;
    public String LINK;
    public String DESC;
    public int getcount, GVID;
    public  String getDESC() {
        return DESC;
    }



    public String getLINK() {
        return LINK;
    }

    public int getcount() {
        return getcount;
    }

    public void setLINK(String LINK) {
        this.LINK = LINK;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }

    public void setCount(int getcount) {
        this.getcount = getcount;
    }

    public VideoList() {

        System.out.println("Enter to get db videolist!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
        System.out.println("inside VIDEOLIST SELECT getvid from noex;");
        try {
            Cursor cursor = db.rawQuery("SELECT getvid from noex;", null);
            while(cursor.moveToNext()) {
                GVID = cursor.getInt(cursor.getColumnIndex("getvid"));
            }
            System.out.println("inside VIDEOLIST SELECT * from videolist WHERE vid=" + GVID + ";");
            Cursor cursor1 = db.rawQuery("SELECT * from videolist WHERE vid=" + GVID + ";", null);

            if (cursor1.getCount() > 0) {
                System.out.println("Sucess get data from videolist!!!!!!!!!!!!!! INSIDE VIDEOLIST!!!!");
                System.out.println("inside VIDEOLIST Count = " + cursor1.getCount());
                getcount=cursor1.getCount();
                setCount(getcount);

                while (cursor1.moveToNext()) {
                    System.out.println("inside VIDEOLIST in while getdata ");
                    LINK = cursor1.getString(cursor1.getColumnIndex("vlink"));
                    DESC = cursor1.getString(cursor1.getColumnIndex("vdesc"));

                    setLINK(LINK);
                    setDESC(DESC);

                    System.out.println("inside videolist " + LINK + "  " + DESC);
                }
            } else {
                System.out.println("No data get from videolist!!!!!!!!!!!!!!INSIDE VIDEOLIST!!!!");
                System.out.println("Count = " + cursor.getCount());
            }
        }catch(Exception e) {
            e.printStackTrace();

        }
    }
}
