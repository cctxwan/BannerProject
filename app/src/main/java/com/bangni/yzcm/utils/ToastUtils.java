package com.bangni.yzcm.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Toast;

import com.bangni.yzcm.R;

import es.dmoral.toasty.Toasty;

/**
 * Toast类
 */
public class ToastUtils {

    public ToastUtils(){
        Toasty.Config.getInstance()
                .tintIcon(false) // optional (apply textColor also to the icon)
                .setToastTypeface(Typeface.DEFAULT) // optional
                .setTextSize(R.dimen.dimen_130_dip) // optional
                .allowQueue(false) // optional (prevents several Toastys from queuing)
                .apply(); // required

        Toasty.Config.reset();
    }

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
