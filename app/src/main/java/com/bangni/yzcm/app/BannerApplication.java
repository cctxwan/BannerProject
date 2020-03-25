package com.bangni.yzcm.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * application
 */
public class BannerApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //budly(测试环境为true，正式环境为false)
        CrashReport.initCrashReport(getApplicationContext(), "f0503b4ca0", true);
    }
}
