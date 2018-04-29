package com.example.kenneth.hiit;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PartyActivity extends AppCompatActivity {
    Global global;
    Party ptyObj;
    Button btn;
    Thread thread;

    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        this.btn = (Button) findViewById(R.id.btn_ready);
        global = (Global) getApplicationContext();

        ptyObj = global.CurrentParty;



        list = (ListView) findViewById(R.id.ptyMemberList);

        final ListAdapter adapter = new ArrayAdapter<Party.PartyUser>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1,
                ((ArrayList<Party.PartyUser>) global.CurrentParty.MemberList)) {

            @Override
            public View getView(int pos, View convert, ViewGroup group) {
                View v = super.getView(pos, convert, group);
                TextView t1 = (TextView) v.findViewById(android.R.id.text1);
                TextView t2 = (TextView) v.findViewById(android.R.id.text2);

                t1.setText(getItem(pos).uname);
                if (getItem(pos).isReady) {
                    t2.setText("Ready");

                } else {
                    t2.setText("Not Ready");

                }
                return v;
            }
        };
        list.setAdapter(adapter);
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(getApplicationContext(), "Ready Start :" + msg.obj + "sec left ", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        list.invalidateViews();

                        break;
                }
            }
        };
        global.curHandler = mHandler;
        if (global.UserName == ptyObj.HostUname) {
            btn.setTag("Start");

            if (ptyObj.MemberList.size() != 1)
                btn.setEnabled(false);
        }
        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.currentContext = getApplicationContext();
                global.client.Send("/rdypty/" + global.UserName);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        global.curHandler = null;
    }
}
