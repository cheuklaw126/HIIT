package com.example.kenneth.hiit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {
    TextView tv;
    Button btn;
    EditText et;
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       setContentView(R.layout.activity_chat);
        global = (Global) getApplicationContext();
        tv = (TextView) findViewById(R.id.textView2);
        btn = (Button) findViewById(R.id.button4);

        et = (EditText) findViewById(R.id.editText5);
        global.CurTv = tv;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //  tv.setText(tv.getText()+"\n"+et.getText());
                global.client.Send("/chat/" + global.LastName + " : " + et.getText());
                et.setText("");


            }
        });


    }
}
