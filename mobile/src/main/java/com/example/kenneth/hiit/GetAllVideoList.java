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
            System.out.println("inside getALLVIDEO SELECT *  FROM allvideo;");
            try {
                Cursor cursor = db.rawQuery("select * from allvideo where vlink not like '%http://%' and vlink not like '%https://%' and vlink not like '%www.%';", null);
                GV= cursor.getCount();
                LINK=new String[GV];
                DESC=new String[GV];
                setCount(GV);
                int i=0;
                while(cursor.moveToNext()) {

                    if(i<GV){
                        //LINK[1]="this is test array";
                       // System.out.println("printout array "+LINK[1]);
                        System.out.println("inside allvideo  get lengtt "+LINK.length+DESC.length);
                        System.out.println("inside allvideo vlink"+cursor.getString(cursor.getColumnIndex("vlink")));
                        System.out.println("inside allvideo description"+cursor.getString(cursor.getColumnIndex("description")));
                        if (cursor.getString(cursor.getColumnIndex("vlink")).contains("youtube")) {
                            LINK[i] = cursor.getString(cursor.getColumnIndex("vlink"));
                            DESC[i] = cursor.getString(cursor.getColumnIndex("description"));
                        }else if((cursor.getString(cursor.getColumnIndex("vlink")).contains("http://"))||(cursor.getString(cursor.getColumnIndex("vlink")).contains("https://"))||(cursor.getString(cursor.getColumnIndex("vlink")).contains("www."))){
                            i--;
                        }else{
                            LINK[i] = cursor.getString(cursor.getColumnIndex("vlink"));
                            DESC[i] = cursor.getString(cursor.getColumnIndex("description"));
                        }


                        System.out.println("inside allvideo " + LINK[i] + "  " + DESC[i]);
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
