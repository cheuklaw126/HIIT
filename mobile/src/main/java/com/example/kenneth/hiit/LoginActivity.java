package com.example.kenneth.hiit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText ac, pw;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ac = (EditText) findViewById(R.id.editText);
        pw = (EditText) findViewById(R.id.editText2);

        btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global global = (Global) getApplicationContext();
                global.Reset();
                global.NoticeMsg("");
                String acc = ac.getText().toString();
                acc = acc.toLowerCase();
                String pwd = pw.getText().toString();
                boolean chk = global.Login(acc, pwd);
                if (chk) {
                    Toast.makeText(getApplicationContext(), "Log in Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Username or Password Invalid!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
