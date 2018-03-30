package com.example.kenneth.hiit;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {
    Button btn1;
    EditText ac, pw, pw2, fname, lname, height, weight;
    TextView acMsg,pwdMsg;
    RadioButton rm, rf;
    RadioGroup rgp;
ArrayList errorList;
    String acc,pwd;
public enum ChkNum{
    account,password;

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ac = (EditText) findViewById(R.id.uid);
        pw = (EditText) findViewById(R.id.pwd);
        pw2 = (EditText) findViewById(R.id.pwd2);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        height = (EditText) findViewById(R.id.edit_height);
        weight = (EditText) findViewById(R.id.edit_weight);
        RadioGroup rgp = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton rm = (RadioButton) findViewById(R.id.rm);
        final RadioButton rf = (RadioButton) findViewById(R.id.rf);
        acMsg = (TextView) findViewById(R.id.acMsg);
        pwdMsg = (TextView) findViewById(R.id.pwdMsg);
        btn1 = (Button)findViewById(R.id.btn1);
        errorList = new ArrayList();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(errorList.isEmpty()){


String w = weight.getText().toString();
                    String h = height.getText().toString();
String Sex="";
if(rf.isChecked()){
    Sex="F";
}else{
    Sex ="M";
}

String First = fname.getText().toString();
String Last = lname.getText().toString();
acc= acc.toLowerCase();
String query =String.format("INSERT INTO [dbo].[pData] ([uname],[firstName],[lastName],[password],[height],[weight],[sex],[createDate] ,[level]) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s')",acc,First,Last,pwd,h,w,Sex, "","1");

                    final ArrayList<String> querys = new ArrayList<String>();
                    querys.add(query);

                    try {
IOObject io = new IOObject("ExecuteNonQuery",querys);
io.Start();
                        JSONObject jsonObject= io.getReturnObject();
                     int effectRows=   jsonObject.getInt("EffectRows");

                        if(effectRows==1){
                            AlertDialog ad = new AlertDialog.Builder(RegisterActivity.this).create();
                            ad.setCancelable(false); // This blocks the 'BACK' button
                            ad.setMessage("Successful");
                            ad.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    RegisterActivity.this.finish();
                                }
                            });
                            ad.show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Server Busy, Please try again!", Toast.LENGTH_SHORT).show();
                        }


System.out.println(effectRows);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

               }
                else{
                    return;
                }
            }
        });

        ac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                     acc = ac.getText().toString();
                    Global globalobj = new Global();
                    if (globalobj.ChkAccExit(acc)) {
acMsg.setText(acc +" Already Exit!");
                        errorList.add(ChkNum.account);
//ac.requestFocus();
                    } else {
                        acMsg.setText("");
                        acc=ac.getText().toString();
                        errorList.remove(ChkNum.account);
                    }
                }
            }
        });

pw2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                String pwString1  = pw.getText().toString();
                String pwString2  = pw2.getText().toString();
if(!pwString1.equals(pwString2) || pwString2.equals("")){
    pwdMsg.setText("Password Invaid");
    errorList.add(ChkNum.password);
}else{
    errorList.remove(ChkNum.password);
    pwd = pw2.getText().toString();
    pwdMsg.setText("");
}
                }
            }
        });
    }




}
