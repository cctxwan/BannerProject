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




    /**
     * 保存token
     * @param token
     */
    public void setToken(String token) {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);// 实例化SharedPreferences对象；Constant.APP_NAME是一个常量，是创建的数据库表的名字
        SharedPreferences.Editor editor = sp.edit();// 实例化SharedPreferences.Editor对象
        editor.putString("token", token);// 保存数据
        editor.commit();// 提交数据
    }

    /**
     * 获取token
     * @return
     */
    public String getToken() {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);
        return sp.getString("token", "");// 获取token
    }




    /**
     * 保存手机号
     * @param phone
     */
    public void setPhone(String phone) {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);// 实例化SharedPreferences对象；Constant.APP_NAME是一个常量，是创建的数据库表的名字
        SharedPreferences.Editor editor = sp.edit();// 实例化SharedPreferences.Editor对象
        editor.putString("phone", phone);// 保存数据
        editor.commit();// 提交数据
    }

    /**
     * 获取手机号
     * @return
     */
    public String getPhone() {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);
        return sp.getString("phone", "");// 获取手机号
    }



    /**
     * 保存昵称
     * @param nickname
     */
    public void setNickName(String nickname) {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);// 实例化SharedPreferences对象；Constant.APP_NAME是一个常量，是创建的数据库表的名字
        SharedPreferences.Editor editor = sp.edit();// 实例化SharedPreferences.Editor对象
        editor.putString("nickname", nickname);// 保存数据
        editor.commit();// 提交数据
    }

    /**
     * 获取昵称
     * @return
     */
    public String getNickName() {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);
        return sp.getString("nickname", "");// 获取昵称
    }



    /**
     * 保存头像
     * @param infoimg
     */
    public void setInfoImg(String infoimg) {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);// 实例化SharedPreferences对象；Constant.APP_NAME是一个常量，是创建的数据库表的名字
        SharedPreferences.Editor editor = sp.edit();// 实例化SharedPreferences.Editor对象
        editor.putString("infoimg", infoimg);// 保存数据
        editor.commit();// 提交数据
    }

    /**
     * 获取头像
     * @return
     */
    public String getInfoImg() {
        SharedPreferences sp = context.getSharedPreferences(BannerConstants.APP_NAME,
                Activity.MODE_PRIVATE);
        return sp.getString("infoimg", "");// 获取头像
    }

}
