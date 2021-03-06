package com.example.kenneth.hiit;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Created by Kenneth on 27/2/2018.
 */

public class Global extends Application implements Serializable {

    public String UserName, pw, FirstName, LastName, src;
    public int Uid, vid, compEx;
    public String lastD, lastT, cc, hr, eg, com, Url;
    public String[] vn1, link1, desc1, createby1;
    public int numvideo;
    public static String vn, link, desc;
    public static Context contextOfApplication;
    public Client client;
    public Party CurrentParty;
    public Thread SocketListener;
    public Context currentContext;
    public TextView CurTv;
    public Handler curHandler, GlobalHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //all ready
                case 100:
                    //   uploadVdo

                    //    upload(msg.obj.toString());
                    break;
            }

        }
    };
    public Party.PartyUser[] partyUsers;
    ArrayList<Party> partyList;


    IOObject io;
    ArrayList<JSONObject> fdList, fdRequestList, nearlyByList, partyMemberList;

    public void Reset() {
        this.UserName = null;
        this.pw = null;
        this.FirstName = null;
        this.LastName = null;
        this.src = null;
        this.Uid = 0;
        this.vid = 0;
        this.compEx = 0;
        this.lastD = null;
        this.lastT = null;
        this.cc = null;
        this.hr = null;
        this.eg = null;
        this.com = null;
        this.client = null;
        this.CurrentParty = null;
        this.SocketListener = null;
        this.Url = null;
        this.vn = null;
        this.desc = null;
        this.link = null;
        this.numvideo = 0;
    }

    public Global() {
    }

    public String LastLoginTIme;


    public String GetUname() {
        return this.UserName;
    }

    public void SetImage(ImageView bmImage, String url) {
        new DownloadImageTask(bmImage).execute(url);
    }


    public Global(int uid, String userName, String pw, String firstName, String lastName, String lastLoginTIme) {
        Uid = uid;
        UserName = userName;
        this.pw = pw;
        FirstName = firstName;
        LastName = lastName;
        LastLoginTIme = lastLoginTIme;


    }

    public boolean SQLhelper(String query) {
        ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteNonQuery", querys);
            io.Start();
            JSONObject jsonObject = io.getReturnObject();
            int effectRows = jsonObject.getInt("effectRows");
            if (effectRows == 1) {
                return true;
            } else {


            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }


    public void UpdateCurrentData() {

        String query = String.format("SELECT * from pData where uname='%s'", UserName);
        final ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");
            JSONObject data = jsonArray.getJSONObject(0);
            if (jsonArray.length() > 0) {
                this.Uid = data.getInt("uid");
                this.UserName = data.getString("uname");
                this.FirstName = data.getString("firstName");
                this.LastName = data.getString("lastName");
                this.pw = data.getString("password");
                this.src = data.getString("src");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void getPartyList() {
        this.client.Send("|getptys|");
    }


    public void SetPartyMember() {
        ArrayList members = null;
        if (CurrentParty != null) {
            members = CurrentParty.MemberList;
            if (partyMemberList == null) {
                partyMemberList = new ArrayList<JSONObject>();
            } else {
                partyMemberList.clear();
            }
            for (int i = 0; i < members.size(); i++) {
                String query = String.format("SELECT * FROM pData where uname='%s'", ((Party.PartyUser) members.get(i)).uname);
            }
        }
    }

    public void SetUrl() {
        if (CurrentParty != null) {
            Url = CurrentParty.getUrl().toString();
        }
    }

    public void SetNearlybyList(String uname) {
        if (nearlyByList != null) {
            nearlyByList.clear();
        } else {
            nearlyByList = new ArrayList<JSONObject>();
        }

        String query = String.format("SELECT pData.* ,fdList.funame FROM fdList ,pData where fdList.funame= pData.uname and fdList.uname='%s'", uname);
        final ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            //     JSONObject jobj = io.getReturnObject();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int a = 0; a < jsonArray.length(); a++) {
                    JSONObject data = jsonArray.getJSONObject(a);
                    nearlyByList.add(data);
                }

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public class MyRunnable implements Runnable {
        String path;

        public MyRunnable(String path) {
            // store parameter for later user
            this.path = path;
        }

        public void run() {
            try {
                Comfirmed(200);
                File file = new File(this.path);
                byte[] fileByte = loadFile(file);

           //     String enc64=""; = Base64.encodeToString(fileByte, Base64.DEFAULT);
                FileInputStream fileInputStream = new FileInputStream(file);

                IOObject ioObj = new IOObject("obj", new ArrayList<String>());
            //    client.Send("|mp4|"+Base64.encodeToString(fileByte, Base64.DEFAULT));

                ioObj.obj =  Base64.encodeToString(fileByte, Base64.DEFAULT);
                //ioObj.obj = enc64;
                if (path.toLowerCase().endsWith("jpg")) {
                    ioObj.FileType = "jpg";
                } else if (path.toLowerCase().endsWith("png")) {
                    ioObj.FileType = "png";
                } else if (path.toLowerCase().endsWith("gif")) {
                    ioObj.FileType = "gif";
                } else {
                    ioObj.FileType = "mp4";
                }

                ioObj.CreateUser = UserName;
                ioObj.Start();
                Comfirmed(201);

            } catch (Exception ex) {
                System.out.println(ex.toString() + "________________________________________________________________");
            }
        }
    }

    public void upload(String path) {
        Runnable r = new MyRunnable(path);
        Thread thread = new Thread(r);
        thread.start();
    }


    public byte[] loadFile(File file) throws IOException {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (Exception e) {

            System.out.println(e.toString());
        }
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    public boolean Login(String acc, String pwd) {
        String query = String.format("select * from pData where uname ='%s' and password='%s'", acc, pwd);
        ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");
            JSONObject data = jsonArray.getJSONObject(0);
            if (jsonArray.length() > 0) {
                this.Uid = data.getInt("uid");
                this.UserName = acc;
                this.FirstName = data.getString("firstName");
                this.LastName = data.getString("lastName");
                this.pw = data.getString("password");
                this.src = data.getString("src");
                StartSocket();
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    public void StartSocket() {
        this.client = new Client(this.UserName.toLowerCase());
        this.SocketListener = new serverListener(this.client);
        this.SocketListener.start();
    }

    public void Comfirmed() {
        if (curHandler != null) {
            Message curMsg = Message.obtain();
            curMsg.what = 0;
            curMsg.obj = "";
            curHandler.sendMessage(curMsg);
        }
    }

    public void Comfirmed(int what) {
        if (curHandler != null) {
            Message curMsg = Message.obtain();
            curMsg.what = what;
            curMsg.obj = "";
            curHandler.sendMessage(curMsg);
        }
    }

    public void Comfirmed(int what, Object obj) {
        if (curHandler != null) {
            Message curMsg = Message.obtain();
            curMsg.what = what;
            curMsg.obj = obj;
            curHandler.sendMessage(curMsg);
        }
    }

    public class serverListener extends Thread {
        Client client;

        public serverListener(Client client) {
            this.client = client;
        }

        @Override
        public void run() {
            while (true) {
                String msg = client.Listener();
                if (msg.isEmpty())
                    return;
                String[] pair = msg.split(Pattern.quote("|"));
                String Action = "", Value = "";
                try {
                    //     Action = pair[0].toLowerCase();
                    Action = pair[1].toLowerCase();
                    Value = pair[2];


                } catch (Exception ex) {
                }
                System.out.println(msg);
                switch (Action) {
                    case "strvdo":
                        Comfirmed(20);
                        break;
                    case "waitpp":
                        //  Comfirmed(21);
                        break;
                    case "ptys":
                        Gson tmpGson = new Gson();
                        String test = Value;
                        if (Value != null) {
                            partyList = tmpGson.fromJson(Value, new TypeToken<ArrayList<Party>>() {
                            }.getType());
                            Comfirmed();
                        }
                        break;
                    case "rchat":
                        if (curHandler != null) {
                            Message curMsg = Message.obtain();
                            curMsg.what = 1;
                            curMsg.obj = CurTv.getText() + "\n" + Value;
                            curHandler.sendMessage(curMsg);
                        }
                        break;
                    case "msg":
                        NoticeMsg(Value);
                        break;
                    case "updatepty":
                        tmpGson = new Gson();
                        if (Value.equals("_")) {
                            if (curHandler != null) {
                                //                   CurrentParty.MemberList = (ArrayList<Party.PartyUser>) tmpPty.MemberList.clone();
                                Message curMsg = Message.obtain();
                                curMsg.what = 3;
                                curMsg.obj = "";
                                curHandler.sendMessage(curMsg);
                            }
                        } else {
                            Party tmpPty = tmpGson.fromJson(Value, Party.class);
                            if (CurrentParty != null) {
                                if (CurrentParty.RoomName.equals(tmpPty.RoomName)
                                        && CurrentParty.HostUname.equals(tmpPty.HostUname)) {
                                    CurrentParty.MemberList.clear();
                                    for (int a = 0; a < tmpPty.MemberList.size(); a++) {
                                        CurrentParty.MemberList.add(tmpPty.MemberList.get(a));
                                        //CurrentParty.MemberList.set(a, tmpPty.MemberList.get(a));
                                    }
                                }
                                while (curHandler == null) {
                                }
                                if (curHandler != null) {
                                    //                   CurrentParty.MemberList = (ArrayList<Party.PartyUser>) tmpPty.MemberList.clone();
                                    Message curMsg = Message.obtain();
                                    curMsg.what = 2;
                                    curMsg.obj = "";
                                    curHandler.sendMessage(curMsg);
                                }
                            }

                        }
                        break;
                    case "strpty":
                        if (CurrentParty != null) {
                            if (CurrentParty.HostUname.equals(Value)) {
                                PlayVideo();
                            }
                        }
                        break;
                }
                if (Action.startsWith("kill")) {
                    if (!Action.equals("kill")) {
                        if (Action.split("kill")[1].toLowerCase().equals(UserName.toLowerCase())) {
                            NoticeMsg("You got server Kicked");
                            //   this.interrupt();
                            System.exit(0);
                        }
                    } else {
                        NoticeMsg("You got server Kicked");
                        //MainActivity.this.finish();
                        System.exit(0);
                    }
                }
            }
        }
    }

    public void PlayVideo() {
        Comfirmed(10, 1);
        Comfirmed(11);
        return;
//        if (currentContext != null) {
//
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    CountDownTimer cdt5 = new CountDownTimer(5000, 1000) {
//
//                        @Override
//                        public void onFinish() {
//                            Comfirmed(11);
//                        }
//
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            final long remainLong = millisUntilFinished;
//                            double remain = Math.ceil(((double) remainLong) / 1000);
//                            Comfirmed(10, remain);
//                            System.out.println(millisUntilFinished + "..............");
//                            //  mTextView.setText("seconds remaining:"+millisUntilFinished/1000);
//                        }
//                    }.start();
//                }
//            });}


//            long t = System.currentTimeMillis();
//            long end = t + 5000;
//            int a = 5;
//            try {
//                while (System.currentTimeMillis() < end) {
//                    Message curMsg = Message.obtain();
//                    curMsg.what = 1;
//                    curMsg.obj = a;
//                    this.curHandler.sendMessage(curMsg);
//                    a = a - 1;
//                    Thread.sleep(1000);
//                }
//            } catch (Exception ex) {
//
//            }
//
//
//            Intent intent = new Intent(currentContext, CameraActivity.class);
//            startActivity(intent);
    }
//        CountDownTimer cc = new CountDownTimer(5000, 1000) {
//            int count = 5;
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//                client.Send("test:" + millisUntilFinished);
//                //  Toast.makeText(getApplicationContext(), "Ready Start :" +(count-(millisUntilFinished/1000)) +"sec left ", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFinish() {
//                Intent intent = new Intent(currentContext, IndexActivity.class);
//                startActivity(intent);
//
//            }
//        };
//        try {
//            cc.start();
//        } catch (Exception ex) {
//            client.Send("test: fail" + ex);
//        }


    public void CreateParty(String roomName, String url) {
        CurrentParty = new Party(roomName, this.UserName, url);
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
        String jsonString = gson.toJson(CurrentParty);

//this.client.Send("0123456789");
        this.client.Send("|cp|" + jsonString);

    }

    public void PartyEnd() {
        if (CurrentParty.HostUname.equals(UserName)) {
            client.Send("|killpty|" + UserName);


            CurrentParty = null;
        }


    }


    public void NoticeMsg(String msg) {

        int notificationId = 0x1234;
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            NotificationChannel channel = new NotificationChannel("1", "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(this, "1");
            // builder.setSmallIcon(android.R.drawable.stat_notify_chat)
            builder.setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setContentTitle("HIIT")
                    .setContentText(msg)
                    .setNumber(99);
            // notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.drawable.icon).setContentTitle("").setContentText("").setSound(soundUri).setChannelId("1").build();

            notificationManager.notify(notificationId, builder.build());

        } else {
            //notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.drawable.icon).setContentTitle("HIIT").setContentText(msg).setSound(soundUri).build();
            notification = new Notification.Builder(getApplicationContext()).setSmallIcon(android.R.drawable.stat_notify_chat).setContentTitle("HIIT").setContentText(msg).setSound(soundUri).build();
            notificationManager.notify(notificationId, notification);
        }
    }


    public void AcceptFrd(String frdName) {
        ArrayList<String> querys = new ArrayList<String>();
        String query = String.format("delete fdRequestList where uname='%s';", this.UserName);

        querys.add(query);
        try {
            io = new IOObject("ExecuteNonQuery", querys);
            io.Start();
            JSONObject jsonObject = io.getReturnObject();
            int effectRows = jsonObject.getInt("effectRows");
            if (effectRows == 1) {
            } else {
            }
        } catch (Exception ex) {

        }

        querys = new ArrayList<String>();

        query = String.format("insert into fdList  values('%s','%s',GETDATE());", this.UserName.toLowerCase(), frdName.toLowerCase());
        querys.add(query);
        query = String.format("insert into fdList  values('%s','%s',GETDATE());",  frdName.toLowerCase(),this.UserName.toLowerCase());
        querys.add(query);
        try {
            io = new IOObject("ExecuteNonQuery", querys);
            io.Start();
            JSONObject jsonObject = io.getReturnObject();
            int effectRows = jsonObject.getInt("effectRows");
            if (effectRows == 1) {
            } else {
            }
        } catch (Exception ex) {

        }

    }

    public boolean RemoveFrd(String uname, String funame) {
        String query = String.format("delete fdList where (uname='%s'  and funame='%s') or (uname='%s'  and funame='%s');", uname.toLowerCase(), funame.toLowerCase(), funame.toLowerCase(), uname.toLowerCase());
        ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteNonQuery", querys);
            io.Start();
            JSONObject jsonObject = io.getReturnObject();
            int effectRows = jsonObject.getInt("effectRows");
            if (effectRows == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;

        }
    }

    public boolean AddFrd(String frdUname) {

        String myUserName = this.UserName;
        String frdUserName = frdUname;

        if (!this.ChkAccExit(frdUname)) {
            return false;
        }
        String query = "";
        ArrayList<String> querys = new ArrayList<String>();

        try {

            //     JSONObject jobj = io.getReturnObject();
            //        JSONArray jsonArray = io.getReturnObject().getJSONArray("data");


            querys = new ArrayList<String>();

            query = String.format("delete fdRequestList where  uname='%s' and funame ='%s' ", frdUserName.toLowerCase(), myUserName.toLowerCase());
            querys.add(query);

            query = String.format("  insert into fdRequestList values('%s','%s')  ;", frdUname, this.UserName);


            querys.add(query);

            io = new IOObject("ExecuteNonQuery", querys);
            io.Start();
            JSONObject jsonObject = io.getReturnObject();
            int effectRows = jsonObject.getInt("effectRows");
            if (effectRows > 0) {
                return true;
            } else {
                return false;
            }


        } catch (Exception ex) {
            return false;

        }


    }

    public void SetFrdList() {
        if (fdList != null) {
            fdList.clear();
        } else {
            fdList = new ArrayList<JSONObject>();
        }

        String query = String.format("SELECT pData.* ,fdList.funame  FROM fdList ,pData where fdList.funame= pData.uname and (fdList.uname='%s');", this.UserName.toLowerCase());
        final ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            //     JSONObject jobj = io.getReturnObject();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int a = 0; a < jsonArray.length(); a++) {
                    JSONObject data = jsonArray.getJSONObject(a);
                    fdList.add(data);
                }

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void SetFrdRequestList() {
        if (fdRequestList != null) {
            fdRequestList.clear();
        } else {
            fdRequestList = new ArrayList<JSONObject>();
        }

        String query = String.format("SELECT pData.*  FROM fdRequestList ,pData where fdRequestList.funame= pData.uname and fdRequestList.uname='%s'", this.UserName);
        final ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            //     JSONObject jobj = io.getReturnObject();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int a = 0; a < jsonArray.length(); a++) {
                    JSONObject data = jsonArray.getJSONObject(a);
                    fdRequestList.add(data);
                }

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public boolean ChkFrdExit(String id) {

        String query = String.format("select * from fdlist where (uname ='%s' and funame='%s')   or (uname ='%s' and funame='%s')  ", this.UserName, id.toLowerCase(), id.toLowerCase(), this.UserName);
        ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            JSONObject jobj = io.getReturnObject();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");

            if (jsonArray.length() > 0) {
                return true;
            } else {
                return false;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean ChkAccExit(String id) {

        String query = String.format("select * from pData where uname ='%s'", id.toLowerCase());
        final ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            JSONObject jobj = io.getReturnObject();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");

            if (jsonArray.length() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void GetExerciseHistory(int uid, int x) {
        int xindex;
        String query = String.format("select * from exeriseHistory where uID =%s ", uid);
        final ArrayList<String> querys = new ArrayList<String>();
        querys.add(query);
        compEx = 0;
        try {
            io = new IOObject("ExecuteReader", querys);
            io.Start();
            JSONObject jobj = io.getReturnObject();
            JSONArray jsonArray = io.getReturnObject().getJSONArray("data");

            if (jsonArray.length() > 0) {
                compEx = jsonArray.length();
                if (x != 0) {
                    xindex = x;
                } else {
                    xindex = compEx - 1;
                }
                JSONObject eh = jsonArray.getJSONObject(x);
                lastD = eh.getString("createDate");
                lastT = eh.getString("totTime");
                cc = eh.getString("caloriesCal");
                hr = eh.getString("heartRate");
                eg = eh.getString("exGain");
                com = eh.getString("isComplete");
                vid = eh.getInt("vid");

            } else {
                compEx = 0;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Upload(String picPath, Intent data) {


    }


    public void GetAllVideo() {

        String queryV = String.format("select * from movie");

        final ArrayList<String> queryvs = new ArrayList<String>();
        queryvs.add(queryV);
        try {
            io = new IOObject("ExecuteReader", queryvs);
            io.Start();
            JSONObject vjobj = io.getReturnObject();
            JSONArray vjsonArray = io.getReturnObject().getJSONArray("data");
            System.out.println(" vjsonArray.length = " + vjsonArray.length());
            if (vjsonArray.length() > 0) {
                numvideo = vjsonArray.length();
                System.out.println(" numvideo = " + numvideo);
                vn1 = new String[numvideo];
                link1 = new String[numvideo];
                desc1 = new String[numvideo];
                createby1 = new String[numvideo];
                for (int i = 0; i < numvideo; i++) {
                    JSONObject veh = vjsonArray.getJSONObject(i);
                    System.out.println(" veh " + i + " link1 = " + veh.getString("link") + " desc1 = " + veh.getString("description")+"createby = "+ veh.getString("createBy"));
                    //vn1[i] = "ieyzL5OaPZk";
                    vn1[i] = veh.getString("vname");
                    link1[i] = veh.getString("link");
                    desc1[i] = veh.getString("description");
                    createby1[i] = veh.getString("createBy");
                    System.out.println(" Global getALLVIDEO vn1 = " + vn1[i] + " link1 = " + link1[i] + " desc1 = " + desc1[i]+"createby1 = "+ createby1[i]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void GetVideo(int vid) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.kenneth.hiit/hiitDB", null, SQLiteDatabase.OPEN_READWRITE); //open DB file
        db.execSQL("DELETE FROM videoList");
        String queryV = String.format("select * from movie where vid =%s ", vid);

        final ArrayList<String> queryvs = new ArrayList<String>();
        queryvs.add(queryV);
        try {
            io = new IOObject("ExecuteReader", queryvs);
            io.Start();
            JSONObject vjobj = io.getReturnObject();
            JSONArray vjsonArray = io.getReturnObject().getJSONArray("data");
            JSONObject veh = vjsonArray.getJSONObject(0);

            vn = veh.getString("vname");
            link = veh.getString("link");
            desc = veh.getString("description");
            db.execSQL("INSERT INTO videolist VALUES (" + vid + " , '" + vn + "', '" + link + "', '" + desc + "');");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

