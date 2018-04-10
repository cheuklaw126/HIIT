package com.example.kenneth.hiit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


public class TotalTime extends AppCompatActivity {
    SQLiteDatabase db;
    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_time);
        chart = (BarChart) findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        Bardataset = new BarDataSet(BARENTRY, "Exercise Time (minute)");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        Bardataset.setValueTextColor(Color.WHITE);
        Bardataset.setValueTextSize(10);
        chart.getAxisLeft().setDrawLabels(true);
        chart.getAxisLeft().setAxisMinValue(0);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        //chart.getAxisLeft().setTextSize(10);
        //chart.getAxisLeft().setLabelCount(5,false);
        chart.getXAxis().setTextColor(Color.WHITE);
        //chart.getXAxis().setTextSize(10);
        chart.getLegend().setTextColor(Color.WHITE);
        //chart.getLegend().setTextSize(10);
        chart.setData(BARDATA);

        chart.animateY(3000);

    }

    public void AddValuesToBARENTRY(){
        float MINUTE;
        String HDATE, ET;
        int count=0, gindex=0;

        System.out.println("inside GainIndex in AddValuesToBARENTRY");
        String g7d = getCalculatedDate("yyyy-MM-dd", -7);
        System.out.println("Enter to get db exlist!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
        final Calendar c = Calendar.getInstance();
        System.out.println("inside GainIndex SELECT * from exlist WHERE lastD>'"+g7d+"';");
        Cursor cursor1 = db.rawQuery("SELECT * from exlist WHERE lastD>'"+g7d+"';", null);
        count = cursor1.getCount();
        if (cursor1.getCount() > 0) {
            System.out.println("inside GainIndex Sucess get data from exlist!!!!! INSIDE exLIST!!!!");
            System.out.println("inside GainIndex Count = " + cursor1.getCount());

            while (cursor1.moveToNext()) {

                HDATE = cursor1.getString(cursor1.getColumnIndex("lastD"));

                BarEntryLabels.add("'"+HDATE+"'");
                ET = cursor1.getString(cursor1.getColumnIndex("lastT"));
                String[] h1 = ET.split(":");
                //int hour=Integer.parseInt(h1[0]);
                int minute = Integer.parseInt(h1[0]);
                int second = Integer.parseInt(h1[1]);


                MINUTE = minute + (second / 60);
                BARENTRY.add(new BarEntry(MINUTE, gindex));
                System.out.println("HDATE, ET, MINUTE, count , gindex = " + HDATE + " , " + ET+ " , " +MINUTE+ " , " + cursor1.getCount()+ " , " +gindex);
                gindex++;
            }
        }
    }


    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
}
