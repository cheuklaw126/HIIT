package com.example.kenneth.hiit;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kenneth on 6/2/2018.
 */

public class IOObject extends Application {
    private Context context;
    private String action;
    private ArrayList<String> querys;
    private JSONObject sendObject;
    private JSONObject ReturnObject;
    public Object obj;
    public String FileType;
    public String CreateUser;
    private AsyncTask.Status IOStatus;

    public IOObject() {
    }

    public AsyncTask.Status getIOStatus() {
        return this.IOStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<String> getQuerys() {
        return querys;
    }

    public void setQuerys(ArrayList<String> querys) {
        this.querys = querys;
    }

    public JSONObject getSendObject() {
        return sendObject;
    }

    public void setSendObject(JSONObject sendObject) {
        this.sendObject = sendObject;
    }

    public JSONObject getReturnObject() {
        return ReturnObject;
    }

    public void setReturnObject(JSONObject returnObject) {
        ReturnObject = returnObject;
    }

    public String urlString = "http://cheuklaw126.mynetgear.com/api/io";

    public IOObject(String action, ArrayList<String> querys) throws JSONException {
        this.context = context;
        this.action = action;
        this.querys = querys;
        sendObject = new JSONObject();
        sendObject.put("action", this.action);
        sendObject.put("querys", new JSONArray(this.querys));
    }

    public IOObject(String action, ArrayList<String> querys, Context context)
            throws JSONException {
        this.context = context;
        this.action = action;
        this.querys = querys;
        sendObject = new JSONObject();
        sendObject.put("action", this.action);
        sendObject.put("querys", new JSONArray(this.querys));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void Start() {
//        IOAdapter adapter = (IOAdapter) new IOAdapter();
        try {
            this.setReturnObject(new IOAdapter().execute(this).get());
        } catch (Exception ex) {
            System.out.println(ex.toString() + "____________");
        }
    }



    private class IOAdapter extends AsyncTask<IOObject, Void, JSONObject> {
        private Context context;
        private JSONObject ReturnObject;

        public JSONObject getReturnObject() {
            return ReturnObject;
        }

        public void setReturnObject(JSONObject returnObject) {
            ReturnObject = returnObject;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(IOObject... obj) {
            this.context = obj[0].getContext();
            Boolean chk = false;
            JSONObject jobj;
            BufferedReader reader = null;
            publishProgress();
            try {
                URL url = new URL("http://cheuklaw126.mynetgear.com/api/io");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                sendObject = new JSONObject();
                sendObject.put("action", obj[0].getAction());

                if (obj[0].getAction() != "obj") {
                    sendObject.put("querys", new JSONArray(obj[0].getQuerys()));
                } else {
                    sendObject.put("obj", obj[0].obj);
                    sendObject.put("CreateUser", obj[0].CreateUser);
                    sendObject.put("FileType", obj[0].FileType);
                }
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(999999999);
                OutputStream os = conn.getOutputStream();
if(obj[0].FileType!=null){
    if( obj[0].FileType.toString().equals("mp4")){
        os.write(sendObject.toString().getBytes("utf-8"));
    }else {
        os.write(sendObject.toString().getBytes("utf-8"));
    }
}else {
    os.write(sendObject.toString().getBytes("utf-8"));

}

                os.flush();
                os.close();
                reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String lines;
                StringBuffer sb = new StringBuffer("");
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sb.append(lines);
                }

                jobj = new JSONObject(sb.toString());
                //  this.setReturnObject(new JSONObject(sb.toString()));
                conn.disconnect();
                reader.close();
                chk = true;

            } catch (Exception e) {
                chk = false;
                jobj = new JSONObject();

                e.printStackTrace();
            }
            return jobj;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject chk) {
            super.onPostExecute(chk);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
