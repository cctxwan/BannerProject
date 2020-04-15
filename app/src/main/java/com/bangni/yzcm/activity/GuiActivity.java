package com.bangni.yzcm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.utils.BannerLog;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 启动页面
 */
public class GuiActivity extends BannerActivity {

    @BindView(R.id.iv_start)
    ImageView img_guidance_close;

    /**save*/
    SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gui);


        ButterKnife.bind(this);

        //关闭侧滑
        setSwback(false);

        //请求打开权限
        PermissionGen.with(GuiActivity.this)
                .addRequestCode(123)
                .permissions(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).request();
    }

    @PermissionSuccess(requestCode = 123)
    void succ(){
        try {
            if(isFirstInAPPZoom()){
                initView();
            }else{
                //跳往登录界面
                toMainActivity();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PermissionFail(requestCode = 123)
    void fail() {
        goToAppSetting();
    }

    /**
     * 用来获取xml中的控件
     */
    private void initView() {
        //进行缩放动画（参数）
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(4000);
        //动画播放完成后保持形状
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        img_guidance_close.startAnimation(scaleAnimation);
    }

    /**
     * 跳往主页面
     */
    void toMainActivity(){
        BannerLog.d("b_cc", "启动页结束，开始登录。");
        Intent intent = new Intent(GuiActivity.this, LoginActivity.class);
        startActivity(intent);
        //前往主界面的同时结束掉这个act
        finish();
    }

    /**
     * 是否第一次进入app
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public boolean isFirstInAPPZoom() throws PackageManager.NameNotFoundException {
        //获取包信息
        PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
        //当前app包的版本号（XML配置中的version）
        int currentVersion = info.versionCode;
        //本地存储文件
        sf = PreferenceManager.getDefaultSharedPreferences(GuiActivity.this);
        //将当前获取到的版本号存储起来，因为第一次运行，所以存入0
        int lastVersion = sf.getInt("/data/xml/isOne.xml", 0);
        //当前版本号大于之前版本号说明该版本号第一次进入，故加载welcome页面启动动画效果
        if(currentVersion > lastVersion){
            //因为第一次进入，所以肯定会执行这段代码，执行之后，下次进入就应该将将0改为当前版本存储
            sf.edit().putInt("/data/xml/isOne.xml", currentVersion).commit();
            return true;
        }
        return false;
    }




    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //请求打开权限
        PermissionGen.with(GuiActivity.this)
                .addRequestCode(123)
                .permissions(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).request();
    }
}
