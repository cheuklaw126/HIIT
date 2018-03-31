package com.example.kenneth.hiit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GetAllVideoList {

        SQLiteDatabase db;
        public String[] LINK;
        public String[] DESC;
        public int getcount, GV;



        public  String[] getDESC() {
            return DESC;
        }



        public String[] getLINK() {
            return LINK;
        }

        public int getcount() {
            return getcount;
        }

        public void setLINK(String[] LINK) {
            this.LINK = LINK;
        }

        public void setDESC(String[] DESC) {
            this.DESC = DESC;
        }

        public void setCount(int getcount) {
            this.getcount = getcount;
        }

        public GetAllVideoList() {

            System.out.println("Enter to get db getallvideolist!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
            System.out.println("inside VIDEOLIST SELECT *  FROM videolist;");
            try {
                Cursor cursor = db.rawQuery("SELECT * FROM videolist;", null);
                GV= cursor.getCount();
                LINK=new String[GV];
                DESC=new String[GV];
                setCount(GV);
                int i=0;
                while(cursor.moveToNext()) {

                    if(i<GV){
                        //LINK[1]="this is test array";
                       // System.out.println("printout array "+LINK[1]);
                        System.out.println("inside videolist  get lengtt "+LINK.length+DESC.length);
                        System.out.println("inside videolist vlink"+cursor.getString(cursor.getColumnIndex("vlink")));
                        System.out.println("inside videolist vdesc"+cursor.getString(cursor.getColumnIndex("vdesc")));
                        LINK[i] = cursor.getString(cursor.getColumnIndex("vlink"));
                        DESC[i] = cursor.getString(cursor.getColumnIndex("vdesc"));


                        System.out.println("inside videolist " + LINK[i] + "  " + DESC[i]);
                        i++;
                    }
                    setLINK(LINK);
                    setDESC(DESC);
                }
            }catch(Exception e) {
                e.printStackTrace();

            }
        }
    }
