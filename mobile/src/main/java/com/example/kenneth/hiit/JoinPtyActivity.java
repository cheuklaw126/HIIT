package com.example.kenneth.hiit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JoinPtyActivity extends AppCompatActivity {
    Global global;
    ListView list;
    ListAdapter adapter;
    ArrayList <Party>al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_pty);

        global = (Global) getApplicationContext();
        if(global.partyList!=null) {
            global.partyList.clear();
        }
        global.partyList=null;
        global.getPartyList();
         al= global.partyList;
        list = (ListView) findViewById(R.id.listView_partyList);
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        adapter = new ArrayAdapter<Party>(getApplicationContext(), android.R.layout.simple_list_item_2,android.R.id.text1,((ArrayList<Party>) global.partyList)) {
                            @Override
                            public View getView(int pos, View convert, ViewGroup group) {
                                View v = super.getView(pos, convert, group);
                                TextView t1 = (TextView) v.findViewById(android.R.id.text1);
                                TextView t2 = (TextView) v.findViewById(android.R.id.text2);
                                t1.setText(getItem(pos).RoomName);
                                t1.setTextColor(Color.parseColor("#FFFFFF"));
                                t2.setText(getItem(pos).HostUname);
                                t2.setTextColor(Color.parseColor("#FFFFFF"));
                                return v;
                            }
                        };

                        list.setAdapter(adapter);
                        break;
                }
            }
        };

        global.curHandler=mHandler;



      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              Intent intent = new Intent(getApplicationContext(),PartyActivity.class);
              startActivity(intent);
              JoinPtyActivity.this.finish();
              global.curHandler=null;
              global.CurrentParty=global.partyList.get(position);
              global.client.Send("|jp|" + global.partyList.get(position).HostUname);

          }
      });

    }
}
