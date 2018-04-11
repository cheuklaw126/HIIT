package com.example.kenneth.hiit;

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
}
