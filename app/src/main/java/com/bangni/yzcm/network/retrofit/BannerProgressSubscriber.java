package com.bangni.yzcm.network.retrofit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.bangni.yzcm.activity.LoginActivity;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.network.util.ErrorCodeUtils;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import com.bangni.yzcm.utils.ToastUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import rx.Subscriber;

/**
 * 来到重头戏，我们为Subscriber写一个子类，让其实现对话框的退出方法。因为Subscriber比Observer
 * （正常情况下都会被转换为Subscriber，详情请看源代码）会多一个onStart方法，
 * 我们可以在onStart中调用对话框显示，在onComplete方法中调用对话框的隐藏方法。
 * Created by Fsh on 2017/3/16.
 */
public class BannerProgressSubscriber<T> extends Subscriber<T> {
    
    private BannerSubscriberOnNextListener<T> mListener;
    private Context mContext;
    private boolean mIsShowDialog;
    private ZLoadingDialog dialog;

    public BannerProgressSubscriber(BannerSubscriberOnNextListener<T> listener, Context context, boolean isShowDialog) {
        this.mListener = listener;
        this.mContext = context;
        this.mIsShowDialog = isShowDialog;
        if(isShowDialog){
            if(dialog == null){
                dialog = new ZLoadingDialog(mContext);
                dialog.setLoadingBuilder(Z_TYPE.SNAKE_CIRCLE)//设置类型
                        .setLoadingColor(Color.BLUE)//颜色
                        .setHintText("")
                        .setHintTextSize(16) // 设置字体大小 dp
                        .setHintTextColor(Color.BLACK)  // 设置字体颜色
                        .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                        .setCanceledOnTouchOutside(false)
                        .setDialogBackgroundColor(Color.TRANSPARENT) // 设置背景色，默认白色
                        .show();
            }
        }
    }

    /**
     * 无参方法，表示事件正常结束
     * 当没有onNext()方法发出的时候
     * 需要触发onCompleted()方法标志事件正常完成。
     */
    @Override
    public void onCompleted() {
        if(mIsShowDialog){
            if (dialog != null){
                dialog.dismiss();
            }
        }
    }

    /**
     * 一个参数方法
     * 事件执行遇到异常
     * 同时剩余的onNext不再执行。
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if(mIsShowDialog){
            if (dialog != null){
                dialog.dismiss();
            }
        }
        //失败时取消所有请求
        //遇到取消订阅的情况可以直接调用
        e.printStackTrace();
        this.unsubscribe();
        //全局code处理
        if(mListener != null) {
            String message = ErrorCodeUtils.onError(mContext, e);
            mListener.onError(message);
        }
    }

    /**
     * 一个参数方法
     * 所有的事件都在此方法中执行
     * Rxjava中需要对每个事件单独执行
     * 并且以队列的形式依次执行。
     * @param t
     */
    @Override
    public void onNext(T t) {
        BannerLog.d("b_cc", "onNext");
        if(mIsShowDialog){
            if (dialog != null){
                dialog.dismiss();
            }
        }
        BannerBaseResponse response = (BannerBaseResponse) t;
        if (response.code.equals("10000")) {
            if (mListener != null) {
                mListener.onNext(t);
            }
        } else {
            if (mListener != null) {
                if(response.code.equals("403")){
                    //账号在其他设备登录
                    ToastUtils.warning(mContext, response.info);
                    new BannerPreferenceStorage(BannerApplication.getInstance()).setToken("");
                    mContext.startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }else if(response.code.equals("401")){
                    //登录失效或登录token为空！
                    ToastUtils.warning(mContext, response.info);
                    new BannerPreferenceStorage(BannerApplication.getInstance()).setToken("");
                    mContext.startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }else{
                    mListener.onError(response.info);
                }
            }
        }
    }
}
