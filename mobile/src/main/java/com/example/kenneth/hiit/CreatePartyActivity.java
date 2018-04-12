package com.example.kenneth.hiit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePartyActivity extends AppCompatActivity {
Button btn_create;
Global global;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        global = (Global) getApplicationContext();
        btn_create = (Button)findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RoomNmae = ((EditText)findViewById(R.id.editText1)).getText().toString();
                String URL = ((EditText)findViewById(R.id.editText2)).getText().toString();

              global.CreateParty(RoomNmae,URL);
                Intent intent = new Intent(getApplicationContext(),PartyActivity.class);
                startActivity(intent);
              finish();
            }
        });
    }
}
