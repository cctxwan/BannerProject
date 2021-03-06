package com.bangni.yzcm.app;

import android.app.Application;
import android.content.Context;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.multidex.MultiDex;

/**
 * 程序入口
 */
public class BannerApplication extends Application {

    private static BannerApplication instance;

    public static BannerApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //budly(测试环境为true，正式环境为false)
        CrashReport.initCrashReport(getApplicationContext(), "f0503b4ca0", true);
    }
}
