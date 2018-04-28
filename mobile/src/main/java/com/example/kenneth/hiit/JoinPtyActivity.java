package com.example.kenneth.hiit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class JoinPtyActivity extends AppCompatActivity {
Global global;
ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_pty);

        global = (Global) getApplicationContext();
        global.getPartyList();
    }
}
