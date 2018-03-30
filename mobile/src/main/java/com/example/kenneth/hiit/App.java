package com.example.kenneth.hiit;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/2/24.
 */

public class App extends Application {
    private static Application sApplication;
    public static Application getApplication(){
        return sApplication;
    }
    public static Context getContext(){
        return getApplication().getApplicationContext();
    }
    @Override
    public void onCreate(){
        super.onCreate();
        sApplication=this;
    }
}
