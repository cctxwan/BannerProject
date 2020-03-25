package com.bangni.yzcm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.bangni.yzcm.network.bean.UserLoginBean;
import com.bangni.yzcm.network.util.BannerConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * 保存数据到内存
 * cc
 */
public class BannerPreferenceStorage {

    /** 上下文 */
    private Context context;

    //gz方法
    public BannerPreferenceStorage(Context context) {
        this.context = context;
    }

    public void setToken(String token) {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);// 实例化SharedPreferences对象；Constant.APP_NAME是一个常量，是创建的数据库表的名字
        SharedPreferences.Editor editor = sp.edit();// 实例化SharedPreferences.Editor对象
        editor.putString("token", token);// 保存数据
        editor.commit();// 提交数据
    }

    public String getToken() {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);
        return sp.getString("token", "");// 获取用户名
    }

    /**
     * 保存登录信息
     * @param userInfo
     */
    public void setUserInfo(UserLoginBean userInfo) {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);// 实例化SharedPreferences对象；Constant.APP_NAME是一个常量，是创建的数据库表的名字
        SharedPreferences.Editor editor = sp.edit();// 实例化SharedPreferences.Editor对象
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        editor.putString("userInfo", json);// 保存数据
        editor.commit();// 提交数据
    }

    /**
     * 获取登录信息
     * @return
     */
    public UserLoginBean getUserInfo() {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("userInfo", null);
        Type type = new TypeToken<UserLoginBean>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
