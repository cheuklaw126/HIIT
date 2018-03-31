package com.example.kenneth.hiit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/24.
 */

public class HistoryList extends  AppCompatActivity {

    private ArrayList<History> historys;
    private ListView lvHistory;
    public String staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);
        //staff=getIntent().getStringExtra("staffname");
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setTitle(staff);
        setupData();

        lvHistory = (ListView) findViewById(R.id.hList);
        lvHistory.setAdapter(new HistoryListAdapter(this, historys));
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                History m = historys.get(i);
                String eid = Integer.toString(m.getEid());
                setvid(eid);
                System.out.println("getEid = " + eid);
                Intent intent = new Intent();
                intent.putExtra("eid", eid);
                intent.setClass(HistoryList.this, HistoryPage.class);
                startActivity(intent);
            }
        });
    }


    public void setupData() {
        historys = new ArrayList<History>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //open DB file
            Cursor cursor = db.rawQuery("select e.*, v.vname as vvname from exlist as e join videolist as v on e.vid=v.vid;", null);

            while (cursor.moveToNext()) {
                historys.add(new History(
                        cursor.getInt(cursor.getColumnIndex("elid")),
                        cursor.getInt(cursor.getColumnIndex("uid")),
                        cursor.getInt(cursor.getColumnIndex("vid")),
                        cursor.getString(cursor.getColumnIndex("lastD")),
                        cursor.getString(cursor.getColumnIndex("eg")),
                        cursor.getString(cursor.getColumnIndex("com")),
                        cursor.getString(cursor.getColumnIndex("vvname"))

                ));
            }

            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void setvid(String eid) {
        String thiseid = eid;

        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //open DB file
            System.out.println("INSIDE HISTORYLIST INSERT INTO noex VALUES (" + thiseid + ");");
            db.execSQL("DELETE FROM noex");
            db.execSQL("INSERT INTO noex VALUES (" + thiseid + ");");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }
}
