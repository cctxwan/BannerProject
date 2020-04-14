package com.bangni.yzcm.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bangni.yzcm.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 闲杂工具类
 */
public class BannerUtils {

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    //验证手机号是否正确
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 更换账号成功的Toast
     * dialog_changeaccount_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
     */
    public static void showImageToas(Context context, String message){
        View toastview = LayoutInflater.from(context).inflate(R.layout.dialog_changeaccountsucc,null);

        LinearLayout lin_changeaccountsucc = toastview.findViewById(R.id.lin_changeaccountsucc);
        //动态设置toast控件的宽高度，宽高分别是130dp
        //这里用了一个将dp转换为px的工具类PxUtil
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) PxUtil.dpToPx(context, 300), (int)PxUtil.dpToPx(context, 153));
        lin_changeaccountsucc.setLayoutParams(layoutParams);


        TextView text = toastview.findViewById(R.id.dialog_changeaccount_title);
        text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        text.setText(message);    //要提示的文本
        Toast toast = new Toast(context);   //上下文
        toast.setGravity(Gravity.CENTER,0,0);   //位置居中
        toast.setDuration(Toast.LENGTH_SHORT);  //设置短暂提示

        toast.setView(toastview);   //把定义好的View布局设置到Toast里面
        toast.show();
    }

    /**
     * 修改密码错误的Toast
     * dialog_changeaccount_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
     */
    public static void showImageToasPsdError(Context context, String message){
        View toastview = LayoutInflater.from(context).inflate(R.layout.dialog_psderror,null);

        LinearLayout lin_changeaccountsucc = toastview.findViewById(R.id.lin_psderror);
        //动态设置toast控件的宽高度，宽高分别是130dp
        //这里用了一个将dp转换为px的工具类PxUtil
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) PxUtil.dpToPx(context, 280), (int)PxUtil.dpToPx(context, 165));
        lin_changeaccountsucc.setLayoutParams(layoutParams);


        TextView text = toastview.findViewById(R.id.dialog_psderror_title);
        text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        text.setText(message);    //要提示的文本
        Toast toast = new Toast(context);   //上下文
        toast.setGravity(Gravity.CENTER,0,0);   //位置居中
        toast.setDuration(Toast.LENGTH_LONG);  //设置短暂提示

        toast.setView(toastview);   //把定义好的View布局设置到Toast里面
        toast.show();
    }

    /**
     * 验证码登录提示没有账号的Toast
     * dialog_changeaccount_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
     */
    public static void showToLoginNoAccount(Context context, String message, int second){
        View toastview = LayoutInflater.from(context).inflate(R.layout.dialog_codeloginaccount,null);

        LinearLayout lin_changeaccountsucc = toastview.findViewById(R.id.lin_codeloginnoaccount);
        //动态设置toast控件的宽高度，宽高分别是130dp
        //这里用了一个将dp转换为px的工具类PxUtil
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) PxUtil.dpToPx(context, 214), (int)PxUtil.dpToPx(context, 39));
        lin_changeaccountsucc.setLayoutParams(layoutParams);


        TextView text = toastview.findViewById(R.id.dialog_codeloginnoaccount_title);
        text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        text.setText(message);    //要提示的文本
        Toast toast = new Toast(context);   //上下文
        toast.setGravity(Gravity.CENTER,0,0);   //位置居中
        toast.setDuration(second);  //设置短暂提示

        toast.setView(toastview);   //把定义好的View布局设置到Toast里面
        toast.show();

        if(second <= 1000){
            Message message1 = timeHandler.obtainMessage();
            message1.obj = toast;
            timeHandler.sendMessageDelayed(message1, second);
        }
    }

    /**
     * 干掉toast
     */
    static Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj != null){
                Toast toast = (Toast) msg.obj;
                toast.cancel();
            }
        }
    };

    /*
     * 将时间戳转换为时间（只要年月日）
     */
    public static String stampToDateOnlyymd(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String stampToDatesss(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String stampToDatessssss(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将日期转换为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

}
