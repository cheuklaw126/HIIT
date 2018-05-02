package com.example.kenneth.hiit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    Global global;
    EditText pw1, pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        global = (Global) getApplicationContext();

        Button btn = (Button) findViewById(R.id.button4);
        pw1 = (EditText) findViewById(R.id.editText3);
        pw2 = (EditText) findViewById(R.id.editText4);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pw1.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Password can not be black!.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pw1.getText().toString().equals(pw2.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password not match!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(global.SQLhelper(String.format("update pData set password='%s' where uname='%s'",pw1.getText().toString(),global.UserName))){
                    global.pw=pw1.toString();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
