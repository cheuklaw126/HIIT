package com.example.kenneth.hiit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreatePartyActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button btn_create;
    Spinner videolist;
    EditText edittext2;
    String vname, vlink;

    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        global = (Global) getApplicationContext();
        btn_create = (Button) findViewById(R.id.btn_create);
        videolist = (Spinner) findViewById(R.id.videolist);
        edittext2 = (EditText) findViewById(R.id.editText2);
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery("SELECT * from allvideo;", null);
            if (cursor.getCount() > 0) {
                System.out.println("inside createpartyactivity get data from videolist!!!!! INSIDE videoLIST!!!!");
                System.out.println("inside createpartyactivity Count = " + cursor.getCount());
                final ArrayList<VL> vl = new ArrayList<>();
                ArrayList<VN> vn = new ArrayList<>();
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    vname = cursor.getString(cursor.getColumnIndex("vname"));
                    System.out.println("vname = " + vname);
                    vlink = cursor.getString(cursor.getColumnIndex("vlink"));
                    System.out.println("vlink = " + vlink);
                    if((vlink.contains("https://"))||(vlink.contains("http://"))||(vlink.contains("youtube.com"))){
                        vl.add(new VL(vlink));
                        vn.add(new VN(vname));
                    }else {
                        vl.add(new VL("https://www.youtube.com/watch?v=" + vlink));
                        vn.add(new VN(vname));
                    }
                }
                ArrayAdapter<VN> adapter = new ArrayAdapter<VN>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, vn);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                videolist.setAdapter(adapter);
                videolist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final String item = parent.getItemAtPosition(position).toString();

                        System.out.println("spinner item = " + item + "position = " + position);
                        System.out.println("videolink = " + vl.get(position).toString());
                        edittext2.setText(vl.get(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RoomName = ((EditText) findViewById(R.id.editText1)).getText().toString();
                System.out.println(" RoomName = " + RoomName);
                String URL = ((EditText) findViewById(R.id.editText2)).getText().toString();
                System.out.println(" URL = " + URL);

                global.CreateParty(RoomName, URL);
                Intent intent = new Intent(getApplicationContext(), PartyActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private class VN {
        private String video_name;

        public VN() {

        }

        public VN(String video_name) {
            this.video_name = video_name;

        }

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        @Override
        public String toString() {
            return video_name;
        }
    }

    private class VL {
        private String video_link;

        public VL() {
        }

        public VL(String video_link) {
            this.video_link = video_link;
        }

        public String getVideo_link() {
            return video_link;
        }

        public void setVideo_link(String video_link) {
            this.video_link = video_link;
        }

        @Override
        public String toString() {
            return video_link;
        }
    }
}
