package com.example.kenneth.hiit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
                String acc = ac.getText().toString();
                acc = acc.toLowerCase();
                String pwd = pw.getText().toString();
                String query = String.format("select * from pData where uname ='%s' and password='%s'", acc, pwd);
                final ArrayList<String> querys = new ArrayList<String>();
                querys.add(query);
                IOObject io = null;
                try {
                    io = new IOObject("ExecuteReader", querys);
                    io.Start();
                    //JSONObject jobj = io.getReturnObject();
                    JSONArray jsonArray = io.getReturnObject().getJSONArray("data");
                    JSONObject data = jsonArray.getJSONObject(0);

                    if (jsonArray.length() > 0) {
                        Toast.makeText(getApplicationContext(), "Log in Success", Toast.LENGTH_SHORT).show();
                        Global global = (Global) getApplicationContext();
                        global.Uid = data.getInt("uid");
                        global.UserName = data.getString("uname");
                        global.FirstName = data.getString("firstName");
                        global.LastName = data.getString("lastName");
                        global.pw = data.getString("password");
                        global.src = data.getString("src");

                        Intent intent = new Intent();
//                            intent.putExtra("global",new Global(data.getInt("uid"), acc,pwd,data.getString("firstName"),data.getString("lastName"), LocalDateTime.now().toString()));

                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        LoginActivity.this.finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Username or Password Invalid!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
