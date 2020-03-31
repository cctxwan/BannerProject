//package com.bangni.yzcm.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//
//import com.zyao89.view.zloading.ZLoadingDialog;
//import com.zyao89.view.zloading.Z_TYPE;
//
///**
// * 动画工具类
// */
//public class LoadsUtils {
//
//    private static ZLoadingDialog dialog;
//
//    public LoadsUtils(Activity activity) {
//        if(dialog == null){
//            dialog = new ZLoadingDialog(activity);
//        }
//    }
//
//    public static void startLoad(){
//        dialog.setLoadingBuilder(Z_TYPE.SNAKE_CIRCLE)//设置类型
//                .setLoadingColor(Color.BLUE)//颜色
//                .setHintText("")
//                .setHintTextSize(16) // 设置字体大小 dp
//                .setHintTextColor(Color.BLACK)  // 设置字体颜色
//                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
//                .setCanceledOnTouchOutside(false)
//                .setDialogBackgroundColor(Color.TRANSPARENT) // 设置背景色，默认白色
//                .show();
//    }
//
//    public static void stopLoad(){
//        if (dialog != null){
//            dialog.dismiss();
//        }
//    }
//
//}
