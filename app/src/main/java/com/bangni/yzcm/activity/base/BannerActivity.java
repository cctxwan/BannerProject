package com.bangni.yzcm.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.bangni.yzcm.utils.BannerUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Activity基类
 */
public class BannerActivity extends SwipeBackActivity {

    public static String sdPath;

    public Activity mContext = BannerActivity.this;

    /** 管理activity */
    private List<Activity> allActivity = new ArrayList<>();
    private WeakReference<Activity> sCurrentActivityWeakRef;

    /** 是否允许全屏，默认全屏 **/
    private boolean mAllowFullScreen = false;

    /** 是否允许滑动退出，不允许 **/
    private boolean isSwback = false;

    /**
     * 设置是否为滑动退出
     * @param Swback
     */
    public void setSwback(boolean Swback) {
        this.isSwback = Swback;
    }

    /**
     * 设置是否为全屏
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    public SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        addActivity(mContext);




        //是否全屏
        if (mAllowFullScreen) {
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        if(isSwback){
            // 可以调用该方法，设置是否允许滑动退出
            setSwipeBackEnable(isSwback);
            mSwipeBackLayout = getSwipeBackLayout();
            // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
            // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
            mSwipeBackLayout.setEdgeSize(200);
        }

        sdPath = getExternalFilesDir(null).toString() + "/";

    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (BannerUtils.isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod(){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }

    /**
     * 防止快速点击
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 添加activity
     * @param activity
     */
    protected void addActivity(Activity activity) {
        allActivity.add(activity);
    }

    /**
     * 移除
     * @param activity
     */
    protected void removeActivity(final Activity activity) {
        if (getCurrentActivity() == activity) {
            setCurrentActivity(null);
        }
        allActivity.remove(activity);
    }

    /**
     * 当前选中的activity
     * @return
     */
    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    //当前选中
    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

    /**
     * 移除所有的activity
     */
    public void finishAllActivity() {
        for (Activity activity : allActivity) {
            if (activity != null) {
                activity.finish();
            }
        }
        allActivity.clear();
    }

    /**
     * 普通跳转
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除activity
        removeActivity(this);
    }
}
