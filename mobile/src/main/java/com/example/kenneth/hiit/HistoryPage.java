package com.example.kenneth.hiit;


        import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//  import com.mynetgear.cheuklaw126.hiit.YouTubeFragment;


public class HistoryPage extends AppCompatActivity {

    int vid,uid;
    String lastD,lastT,cc,eg,com,hr,vn,eid;

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eid = getIntent().getStringExtra("eid");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);
        getExData(eid);
    }



    private void getExData(String eid) {

        String EID=eid;
        System.out.println(" in getEXDATA noex  ");

        System.out.println("Enter to get db exlist!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        db = SQLiteDatabase.openDatabase("/data/data/com.mynetgear.cheuklaw126.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
        try{

        System.out.println("SELECT * from exlist WHERE elid="+EID+";");
        Cursor cursor1= db.rawQuery("SELECT * from exlist WHERE elid="+EID+";", null);

        if (cursor1.getCount() > 0) {
            System.out.println("Sucess get data from exlist!!!!! INSIDE exLIST!!!!");
            System.out.println("Count = " + cursor1.getCount());

        while (cursor1.moveToNext()) {
            vid = cursor1.getInt(cursor1.getColumnIndex("vid"));
            uid = cursor1.getInt(cursor1.getColumnIndex("uid"));
            lastD = cursor1.getString(cursor1.getColumnIndex("lastD"));
            lastT = cursor1.getString(cursor1.getColumnIndex("lastT"));
            cc = cursor1.getString(cursor1.getColumnIndex("cc"));
            com = cursor1.getString(cursor1.getColumnIndex("com"));
            eg = cursor1.getString(cursor1.getColumnIndex("eg"));
            hr = cursor1.getString(cursor1.getColumnIndex("hr"));
            //System.out.println("inside videolist " + LINK+"  "+DESC);
            db = SQLiteDatabase.openDatabase("/data/data/com.mynetgear.cheuklaw126.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //Create DB file
            System.out.println("SELECT * from videolist where vid="+vid+";");
            Cursor cursor2 = db.rawQuery("SELECT * from videolist where vid="+vid+";", null);
            while(cursor2.moveToNext()){
                vn=cursor2.getString(cursor2.getColumnIndex("vname"));
            }
            TextView showTEXT = (TextView) findViewById(R.id.lastDT);
            showTEXT.setText(lastD);

            TextView complete = (TextView) findViewById(R.id.comYN);
            complete.setText(com);

            TextView exN = (TextView) findViewById(R.id.exName);
            exN.setText(vn);

            TextView doT = (TextView) findViewById(R.id.dotime);
            doT.setText(lastT);

            TextView ccal = (TextView) findViewById(R.id.CalBurn);
            ccal.setText(cc);

            TextView shr = (TextView) findViewById(R.id.heartRate);
            shr.setText(hr);

            TextView exG = (TextView) findViewById(R.id.exGain);
            exG.setText(eg);

        }}else{
            Toast.makeText(getApplicationContext(), "You havn't exercise recond !", Toast.LENGTH_LONG).show();
           finish();
            onBackPressed();
        }
          }catch(Exception e) {
        e.printStackTrace();
    }


}}

