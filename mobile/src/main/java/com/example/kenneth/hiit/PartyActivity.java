package com.example.kenneth.hiit;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PartyActivity extends AppCompatActivity {
Global global;
Party ptyObj;
Button btn;
Thread thread;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
this.btn = (Button)findViewById(R.id.btn_ready);
        global = (Global) getApplicationContext();

        ptyObj = global.CurrentParty;

        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case 1:

                        break;
                }
            }
        };
        global.curHandler=mHandler;

        if(global.UserName==ptyObj.HostUname){
//btn.setTag("Start");
//btn.setEnabled(false);
        }
this.btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        global.client.Send("/rdypty/"+global.UserName);
    }
});

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        global.curHandler=null;
    }
}
