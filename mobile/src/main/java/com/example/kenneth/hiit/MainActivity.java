package com.example.kenneth.hiit;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView ac, lastname;
    IOObject io,io1;
    Global global;
    VideoView vdo;
    private static int IMG_RESULT = 1;
    private String picPath = null;
    SQLiteDatabase db;

    ImageView pIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideSoftKeyboard();


        db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.CREATE_IF_NECESSARY); //Create DB file
        try{
            db.execSQL("DROP TABLE if exists videolist;");
            db.execSQL("DROP TABLE if exists exlist;");
            db.execSQL("DROP TABLE if exists noex;");
            db.execSQL("DROP TABLE if exists novideo;");
            db.execSQL("DROP TABLE if exists allvideo;");

            db.execSQL("CREATE TABLE IF NOT EXISTS videolist(vid int PRIMARY KEY , vname text, vlink text,vdesc text);");    //Create tables
            db.execSQL("CREATE TABLE IF NOT EXISTS exlist(elid INTEGER PRIMARY KEY AUTOINCREMENT, uid int, vid int, lastD text, lastT text, cc text, hr text, eg text, com text);");
            db.execSQL("CREATE TABLE IF NOT EXISTS noex(getvid int);");
            db.execSQL("CREATE TABLE IF NOT EXISTS novideo(totalvideo int);");
            db.execSQL("CREATE TABLE IF NOT EXISTS allvideo(vid int PRIMARY KEY , vname text,vlevel text,vlength text, description text, createBy text, createDate text, vlink text);");
            db.close();

        }catch (SQLException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_main);
        global = (Global) getApplicationContext();

        vdo = (VideoView) findViewById(R.id.videoView2);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg2);
        vdo.setVideoURI(uri);
        vdo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        vdo.start();
        GetExerciseHistory(global.Uid);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        TextView ac = (TextView) findViewById(R.id.textView_ac);
        TextView lastname = (TextView) findViewById(R.id.textView_lastName);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ac.setText(global.UserName);
        lastname.setText(global.LastName);
        pIcon = (ImageView) findViewById(R.id.pIcon);

        pIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, IMG_RESULT);
            }
        });


        global.SetImage(pIcon, global.src);

        return true;
    }


    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            /**
             * 当选择的图片不为空的话，在获取到图片的途径
             */
            Uri uri = data.getData();
            Log.e("uploadImage", "uri = " + uri);
            try {
                String[] pojo = {MediaStore.Images.Media.DATA};

                CursorLoader cursorLoader = new CursorLoader(this, uri, null, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        picPath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        File file = new File(picPath);

                        byte[] fileByte = global.loadFile(file);
                        String enc64 = android.util.Base64.encodeToString(fileByte, android.util.Base64.DEFAULT);
                        FileInputStream fileInputStream = new FileInputStream(file);
                        io = new IOObject("obj", new ArrayList<String>());
                        io.obj = enc64;
                        if (path.endsWith("jpg")) {

                            io.FileType = "jpg";
                        } else {
                            io.FileType = "png";
                        }





                        io.CreateUser = global.UserName;
                        io.Start();


                        // imageView.setImageBitmap(bitmap);
                    } else {

                        //??

                    }
                } else {

                    ///???

                }

            } catch (Exception e) {
                System.out.println(e.toString());

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.logout) {
            global.client.Send("/logout");
            global = null;
            MainActivity.this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        try {
            int id = item.getItemId();

            Fragment fragment = null;
            Class currentClass;
      /* View fd  = (View)findViewById(R.id.frd);
        View indexView  = (View)findViewById(R.id.index);
        fd.setVisibility(View.INVISIBLE);
       indexView.setVisibility(View.INVISIBLE);*/

            Intent intent = new Intent();


            switch (id) {

        case R.id.frd:
            //intent.putExtra("global", global);
            intent.setClass(MainActivity.this, frdActivity.class);
            break;
        case R.id.nav_gallery:
          intent.setClass(MainActivity.this, HistoryList.class);
            break;
        case R.id.nav_slideshow:
            System.out.println(" in intent nav_slideshow");
            intent.setClass(MainActivity.this, VideoAllList.class);
            break;
        case R.id.nav_manage:
            System.out.println("in intent nav_manage");
            intent.setClass(MainActivity.this, AnalysisPage.class);
            break;
        default:
            break;
    }
    startActivity(intent);
} catch (Exception e) {
    e.printStackTrace();
}




//        try {
//            fragment = (Fragment) currentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//    FragmentManager fragmentManager = getSupportFragmentManager();
//    fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
/*
        if (id == R.id.nav_camera) {
       //     indexView.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.frd) {

        } else if (id == R.id.nav_send) {

        }
        else if(id ==R.id.frd){

        }
*/
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        vdo.start();
    }

    public void setAllVideo(){

    }

    public void GetExerciseHistory(int uid){
        int vid,getvid;
        int compEx,compEx1;
        String lastD, lastT, cc, hr, eg, com;
        String allvid, allvname, allvlevel, allvlength, alldescription, allcreateby, allcreatedate, alllink;
        String query = String.format("select * from exeriseHistory where uID =%s ",uid);
        String query1 = String.format("select * from movie");
        final ArrayList<String> querys = new ArrayList<String>();
        final ArrayList<String> querys1 = new ArrayList<String>();
        querys.add(query);
        querys1.add(query1);
        compEx=0;
        compEx1=0;
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //open DB file


        try {
            io = new IOObject("ExecuteReader", querys);
            io1= new IOObject("ExecuteReader", querys1);
            io.Start();
            io1.Start();
            JSONObject jobj = io.getReturnObject();
            JSONObject jobj1 = io1.getReturnObject();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");
            JSONArray jsonArray1 = io1.getReturnObject().getJSONArray("data");
            System.out.println("jsonArray = "+jsonArray.length()+" jsonArray1 =  "+jsonArray1);
            db.execSQL("DELETE FROM exlist");
            db.execSQL("DELETE FROM videoList");
            db.execSQL("DELETE FROM noex");
            db.execSQL("DELETE FROM novideo");
            db.execSQL("DELETE FROM allvideo");
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'exlist'");
            if (jsonArray1.length() >0) {
                compEx1=jsonArray1.length();
                System.out.println("in mainActivity compEX1 = "+compEx1);
                for(int j=0; j<compEx1; j++) {
                    JSONObject eh1 = jsonArray1.getJSONObject(j);
                    allvid=eh1.getString("vid");
                    allvname=eh1.getString("vname");
                    allvlevel=eh1.getString("vlevel");
                    allvlength=eh1.getString("vlength");
                    alldescription=eh1.getString("description");
                    allcreateby=eh1.getString("createBy");
                    allcreatedate=eh1.getString("createDate");
                    alllink=eh1.getString("link");
                    System.out.println("in mainactivity insert allvideo INSERT INTO allvideo VALUES ("+allvid+", '"+allvname+"', '"+allvlevel+"', '"+allvlength+"', '"+alldescription+"', '"+allcreateby+"', '"+allcreatedate+"', '"+alllink+"'); ");
                    db.execSQL("INSERT INTO allvideo VALUES ("+allvid+", '"+allvname+"', '"+allvlevel+"', '"+allvlength+"', '"+alldescription+"', '"+allcreateby+"', '"+allcreatedate+"', '"+alllink+"');");

                }
            }
            if (jsonArray.length() > 0) {
                compEx=jsonArray.length();
                System.out.println("in mainActivity compEX = "+compEx);
                JSONObject gvid=jsonArray.getJSONObject(compEx-1);
                getvid= gvid.getInt("vid");
                //System.out.println("INSERT INTO noex VALUES ("+compEx+", "+getvid+");");
                //db.execSQL("INSERT INTO noex VALUES ("+compEx+", "+getvid+");");
                System.out.println("compEx = "+compEx);
                for(int i=0; i<compEx; i++) {
                    JSONObject eh = jsonArray.getJSONObject(i);
                    lastD = eh.getString("createDate");
                    lastT = eh.getString("totTime");
                    cc = eh.getString("caloriesCal");
                    hr = eh.getString("heartRate");
                    eg = eh.getString("exGain");
                    com = eh.getString("isComplete");
                    vid = eh.getInt("vid");
                    String query111=("INSERT INTO exlist (uid, vid, lastD, lastT, cc, hr, eg, com) VALUES ("+uid+ ", "+vid+", '"+lastD+"', '"+lastT+"', '"+cc+"', '"+hr+"', '"+eg+"', '"+com+"');");
                    System.out.println("query111 = "+query111);

                    db.execSQL("INSERT INTO exlist (uid, vid, lastD, lastT, cc, hr, eg, com) VALUES ("+uid+ ", "+vid+", '"+lastD+"', '"+lastT+"', '"+cc+"', '"+hr+"', '"+eg+"', '"+com+"');");
                    System.out.println("compEx = "+compEx);
                    GetVideo(vid);
                }
            }else{
                compEx=0;
            }
            System.out.println("inside mainactivity INSERT INTO novideo VALUES ("+jsonArray1.length()+");");
            db.execSQL("INSERT INTO novideo VALUES ("+jsonArray1.length()+");");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void GetVideo(int vid){
        String vn,link,desc;
        int videoid=vid;
        int allvideo=0;
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //open DB file

        String queryV = String.format("select * from movie where vid =%s ",videoid);
        System.out.println("queryV = "+queryV);
        final ArrayList<String> queryvs = new ArrayList<String>();
        queryvs.add(queryV);
        try{
            io = new IOObject("ExecuteReader", queryvs);
            io.Start();
            JSONObject vjobj = io.getReturnObject();
            JSONArray vjsonArray =io.getReturnObject().getJSONArray("data");

            System.out.println(" getvideo allvideo = "+allvideo);
            if (vjsonArray.length() > 0) {
                allvideo=vjsonArray.length();
            }
            System.out.println(" getvideo allvideo = "+allvideo);
            for(int i=0; i<allvideo; i++) {
                JSONObject veh=vjsonArray.getJSONObject(i);

                vn = veh.getString("vname");
                link = veh.getString("link");
                desc= veh.getString("description");
                System.out.println("INSERT INTO videolist VALUES ("+vid+" , '"+vn+"', '"+link+"', '"+desc+"');");
                db.execSQL("INSERT INTO videolist VALUES ("+vid+" , '"+vn+"', '"+link+"', '"+desc+"');");
            }}
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
