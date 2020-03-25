package com.bangni.yzcm.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Toast类
 */
public class ToastUtils {

    public static void error(Context context, String msg){
        Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void success(Context context, String msg){
        Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(Context context, String msg){
        Toasty.info(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void warning(Context context, String msg){
        Toasty.warning(context, msg, Toast.LENGTH_SHORT, true).show();
    }

}
