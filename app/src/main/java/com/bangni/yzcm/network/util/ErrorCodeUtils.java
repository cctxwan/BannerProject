package com.bangni.yzcm.network.util;

import android.content.Context;
import android.content.Intent;
import com.bangni.yzcm.activity.LoginActivity;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 全局code处理
 */
public class ErrorCodeUtils {

    //异常msg
    private static String ERROR_MSG;

    /**
     * 统一管理code
     * @param mContext
     * @param e
     */
    public static String onError(Context mContext, Throwable e) {
        ERROR_MSG = "暂无数据";
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            ERROR_MSG = "网络中断，请检查您的网络状态";
        } else {
            if (e.getMessage().contains("401")) {
                ERROR_MSG = "登录失效，请重新登录";
                mContext.startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
        return ERROR_MSG;
    }
}