package com.bangni.yzcm.network.retrofit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.bangni.yzcm.network.util.ErrorCodeUtils;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.LoadsUtils;
import com.bangni.yzcm.utils.ToastUtils;

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

    public BannerProgressSubscriber(BannerSubscriberOnNextListener<T> listener, Context context, boolean isShowDialog) {
        this.mListener = listener;
        this.mContext = context;
        this.mIsShowDialog = isShowDialog;
    }


    /**
     * 无参方法，表示事件正常结束
     * 当没有onNext()方法发出的时候
     * 需要触发onCompleted()方法标志事件正常完成。
     */
    @Override
    public void onCompleted() {
        LoadsUtils.getInstance((Activity) mContext).stopLoad();
    }

    /**
     * 一个参数方法
     * 事件执行遇到异常
     * 同时剩余的onNext不再执行。
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        LoadsUtils.getInstance((Activity) mContext).stopLoad();
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
        BannerBaseResponse response = (BannerBaseResponse) t;
        if (response.code.equals("200")) {
            if (mListener != null) {
                mListener.onNext(t);
            }
        } else if (response.code.equals("404")) {

        }
    }
}
