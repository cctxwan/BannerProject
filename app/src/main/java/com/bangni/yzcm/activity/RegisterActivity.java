package com.bangni.yzcm.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.systemstatusbar.StatusBarCompat;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerUtils;
import com.bangni.yzcm.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.et_rg_username)
    EditText et_rg_username;

    @BindView(R.id.et_rg_code)
    EditText et_rg_code;

    @BindView(R.id.txt_rg_getcode)
    TextView txt_rg_getcode;

    @BindView(R.id.et_rg_password)
    EditText et_rg_password;

    @BindView(R.id.txt_register)
    TextView txt_register;

    @BindView(R.id.txt_yhxy)
    TextView txt_yhxy;

    @BindView(R.id.txt_tologin)
    TextView txt_tologin;

    //是否获取过验证码
    private boolean ISGETCODE = false;

    //倒计时60s
    private int min = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        StatusBarCompat.translucentStatusBar(this, false);
        StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT);
        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
    }


    /**
     * 点击事件
     * @param v
     */
    @OnClick({R.id.txt_rg_getcode, R.id.txt_register, R.id.txt_yhxy, R.id.txt_tologin})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.txt_rg_getcode){
            //获取验证码
            //账号判断
            if(TextUtils.isEmpty(et_rg_username.getText().toString().trim())){
                ToastUtils.warning(this, "手机不能为空");
                return;
            }
            if(!BannerUtils.isMobileNO(et_rg_username.getText().toString().trim())){
                ToastUtils.warning(this, "请输入正确格式的手机号");
                return;
            }

            ISGETCODE = true;
            getCodeHandler.sendEmptyMessageDelayed(1, 1000);


        }else if(temdId == R.id.txt_register){
            //注册
            register();
        }else if(temdId == R.id.txt_yhxy){
            initGetCode();
            //用户协议
        }else if(temdId == R.id.txt_tologin){
            //去登录界面
            initGetCode();
            finish();
        }
    }

    /**
     * 注册
     */
    private void register() {
        String username = et_rg_username.getText().toString().trim();
        String password = et_rg_password.getText().toString().trim();
        String code = et_rg_code.getText().toString().trim();

        //账号判断
        if(TextUtils.isEmpty(username)){
            ToastUtils.warning(this, "手机不能为空");
            return;
        }
        if(!BannerUtils.isMobileNO(username)){
            ToastUtils.warning(this, "请输入正确格式的手机号");
            return;
        }

        if(!ISGETCODE){
            ToastUtils.warning(this, "请获取验证码");
            return;
        }
        if(TextUtils.isEmpty(code)){
            ToastUtils.warning(this, "验证码不能为空");
            return;
        }


        //密码判断
        if(TextUtils.isEmpty(password)){
            ToastUtils.warning(this, "密码不能为空");
            return;
        }
        if(password.length() < 6){
            ToastUtils.warning(this, "密码不能小于6位");
            return;
        }
    }

    Handler getCodeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            txt_rg_getcode.setText("倒计时" + min-- + "s");
            if(min == -1) {
                initGetCode();
            }else{
                getCodeHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    /**
     * 重置验证码
     */
    private void initGetCode() {
        min = 60;
        getCodeHandler.removeMessages(1);
        txt_rg_getcode.setText("获取验证码");
        ISGETCODE = false;
    }

}
