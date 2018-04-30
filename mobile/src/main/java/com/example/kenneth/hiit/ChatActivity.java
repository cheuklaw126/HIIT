package com.example.kenneth.hiit;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {
    TextView tv;
    EditText et;
    Global global;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        global.curHandler=null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       setContentView(R.layout.activity_chat);
        global = (Global) getApplicationContext();
        tv = (TextView) findViewById(R.id.textView2);
    //    btn = (Button) findViewById(R.id.button4);

        et = (EditText) findViewById(R.id.editText5);
        global.CurTv = tv;
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case 1:
tv.setText(msg.obj.toString());
                        break;
                }
            }
        };
        global.curHandler=mHandler;








    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == 66) {
            global.client.Send("|chat|" + global.LastName + " : " + et.getText());
            et.setText("");

        }
        return super.onKeyUp(keyCode, event);
    }
}
