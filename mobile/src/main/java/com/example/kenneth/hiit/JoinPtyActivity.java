package com.example.kenneth.hiit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class JoinPtyActivity extends AppCompatActivity {
    Global global;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_pty);

        global = (Global) getApplicationContext();
        global.getPartyList();
        while (global.partyList == null) {

        }

        list = (ListView) findViewById(R.id.listView_partyList);
        ListAdapter adapter = new ArrayAdapter<Party>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1,
                ((ArrayList<Party>) global.partyList)) {
            @Override
            public View getView(int pos, View convert, ViewGroup group) {
                View v = super.getView(pos, convert, group);
                TextView t1 = (TextView) v.findViewById(android.R.id.text1);
                TextView t2 = (TextView) v.findViewById(android.R.id.text2);

                t1.setText(getItem(pos).RoomName);
                t2.setText(getItem(pos).HostUname);
                return v;
            }
        };
        list.setAdapter(adapter);
      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              global.client.Send("/jp/" + global.partyList.get(position).HostUname);
          }
      });

    }
}
