package com.example.kenneth.hiit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AnalysisPage extends AppCompatActivity {
    int  uid;
    String lastD, lastT, cc, eg, com, hr, vn, eid;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.analysis_page);
        getExData();


    }

    private void getExData() {
        float TotTime = 0, TEG = 0, THR = 0, TTC = 0;
        int count = 0;
        System.out.println("inside AnalysisPage in getEXDATA");
        String g7d = getCalculatedDate("yyyy-MM-dd", -7);
        System.out.println("Enter to get db exlist!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
        final Calendar c = Calendar.getInstance();


        try {
            TextView GV = (TextView) findViewById(R.id.gainvalue);
            TextView NH = (TextView) findViewById(R.id.numhiit);
            TextView TT = (TextView) findViewById(R.id.TTime);
            TextView HR = (TextView) findViewById(R.id.AHrate);
            TextView TC = (TextView) findViewById(R.id.Tcal);
            LinearLayout LGV = (LinearLayout) findViewById(R.id.TGI);
            LinearLayout LNH = (LinearLayout) findViewById(R.id.HT);
            LinearLayout LTT = (LinearLayout) findViewById(R.id.TT);
            LinearLayout LHR = (LinearLayout) findViewById(R.id.AHR);
            LinearLayout LTC = (LinearLayout) findViewById(R.id.CB);
            System.out.println("inside AnalysisPag SELECT * from exlist WHERE lastD>'" + g7d + "';");
            Cursor cursor1 = db.rawQuery("SELECT * from exlist WHERE lastD>'" + g7d + "';", null);
            count = cursor1.getCount();
            if (cursor1.getCount() > 0) {
                System.out.println("inside AnalysisPag Sucess get data from exlist!!!!! INSIDE exLIST!!!!");
                System.out.println("inside AnalysisPag Count = " + cursor1.getCount());

                while (cursor1.moveToNext()) {

                    uid = cursor1.getInt(cursor1.getColumnIndex("uid"));
                    lastD = cursor1.getString(cursor1.getColumnIndex("lastD"));
                    lastT = cursor1.getString(cursor1.getColumnIndex("lastT"));
                    String[] h1 = lastT.split(":");

                    //int hour=Integer.parseInt(h1[0]);
                    int minute = Integer.parseInt(h1[0]);
                    int second = Integer.parseInt(h1[1]);

                    int temp;
                    temp = minute + (second / 60);
                    TotTime = (float) temp + TotTime;
                    cc = cursor1.getString(cursor1.getColumnIndex("cc"));
                    TTC = Float.parseFloat(cc) + TTC;
                    com = cursor1.getString(cursor1.getColumnIndex("com"));
                    eg = cursor1.getString(cursor1.getColumnIndex("eg"));
                    TEG = Float.parseFloat(eg) + TEG;
                    hr = cursor1.getString(cursor1.getColumnIndex("hr"));
                    THR = Float.parseFloat(hr);
                    System.out.println("TotTime, TTC, TEG, THR, count = " + TotTime + " , " + TTC + " , " + TEG + " , " + THR + " , " + cursor1.getCount());
                }
                GV.setText(Float.toString(TEG));
                NH.setText(Integer.toString(count));
                TT.setText(Float.toString(TotTime));
                HR.setText(Float.toString(THR));
                TC.setText(Float.toString(TTC));
            } else {
                Toast.makeText(getApplicationContext(), "You havn't exercise recond !", Toast.LENGTH_LONG).show();
                finish();
                onBackPressed();
            }

            LGV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent picture_intent = new Intent(AnalysisPage.this, GainIndex.class);
                    startActivity(picture_intent);
                }
            });

            LNH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent picture_intent = new Intent(AnalysisPage.this, HistoryList.class);
                    startActivity(picture_intent);
                }
            });

            LTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent picture_intent = new Intent(AnalysisPage.this, TotalTime.class);
                    startActivity(picture_intent);
                }
            });

            LHR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent picture_intent = new Intent(AnalysisPage.this, HeartRate.class);
                    startActivity(picture_intent);
                }
            });

            LTC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent picture_intent = new Intent(AnalysisPage.this, CaloriesBurn.class);
                    startActivity(picture_intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
}