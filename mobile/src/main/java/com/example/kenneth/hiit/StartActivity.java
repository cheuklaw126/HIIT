package com.example.kenneth.hiit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {
    int PermissionCount, denyCount;
boolean isStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        PermissionCount = 0;
        denyCount = 0;
        ArrayList<Integer> tmpList =new ArrayList<Integer>();
        String [] permissions={ Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};

        isStart=false;



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            PermissionCount++;
        } else {
            tmpList.add(1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            PermissionCount++;
        } else {
            tmpList.add(2);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            PermissionCount++;
        } else {
            tmpList.add(3);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            PermissionCount++;
        } else {
            tmpList.add(4);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            PermissionCount++;
        } else {
            tmpList.add(5);
        }

        if(PermissionCount!=5) {
            String[] reqPerMis = new String[tmpList.size()];
            for (int a = 0; a < tmpList.size(); a++) {
                reqPerMis[a] = permissions[tmpList.get(a) - 1];
            }

            ActivityCompat.requestPermissions(this, reqPerMis, 0);
        }
        else {
            isStart=true;
        }

        Thread tmp = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (isStart) {
                        GoNextScreen();
                        break;
                    }
                    }


            }
        });
        tmp.start();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
switch (requestCode){
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
int x =0;
            for(int a=0;a<grantResults.length;a++){

                if(grantResults[a] == PackageManager.PERMISSION_GRANTED){
                    PermissionCount++;
                    x++;
                }
            }
            if(x!=grantResults.length){
                this.finish();
                System.exit(0);
            }
            else {
                isStart=true;
            }
        }
        return;


}


      //  PermissionCount=0;


    public void GoNextScreen() {
        Timer tm = new Timer();
        tm.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, IndexActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        }, 2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();


        }
    }
}
