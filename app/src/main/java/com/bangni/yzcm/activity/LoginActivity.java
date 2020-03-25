package com.bangni.yzcm.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.network.bean.UserLoginBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarCompat;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerUtils;
import com.bangni.yzcm.utils.SystemUtil;
import com.bangni.yzcm.utils.ToastUtils;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 登录界面
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.txt_login)
    TextView txt_login;

    @BindView(R.id.txt_getcode)
    TextView txt_getcode;

    @BindView(R.id.txt_register)
    TextView txt_register;

    @BindView(R.id.txt_getcodelogin)
    TextView txt_getcodelogin;

    //是否是账号登录
    private boolean ISUSERLOGIN = true;

    //是否获取过验证码
    private boolean ISGETCODE = false;

    //倒计时60s
    private int min = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        StatusBarCompat.translucentStatusBar(this, false);
        StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT);
        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        initView();
    }



    private void initView() {
        String textSource = "还没有账号，立即<font color='#1D65FF'>注册</font>";
        txt_register.setText(Html.fromHtml(textSource));
        //密码
        et_password.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
    }


    private void login(){
        Map<String, String> map = new HashMap<>();
        map.put("userAccount", "18588400509");
        map.put("userPassword", "123456");
        map.put("deviceType", "1");
        map.put("deviceToken", "24544h56gfs1h56gf1jdsggdsa56151");
        map.put("systemManufacturer", SystemUtil.getDeviceBrand());
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserLoginBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserLoginBean> response) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(String msg) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        };
        BannerRetrofitUtil.getInstance().userLogin(body, new BannerProgressSubscriber<BannerBaseResponse<UserLoginBean>>(mListener, this, true));
    }

    /**
     * 点击事件
     * @param v
     */
    @OnClick({R.id.txt_login, R.id.txt_getcode, R.id.txt_register, R.id.txt_getcodelogin})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.txt_getcode){
            //获取验证码

            //非空处理
            if(TextUtils.isEmpty(et_username.getText().toString().trim())){
                ToastUtils.warning(this, "手机不能为空");
                return;
            }
            if(!BannerUtils.isMobileNO(et_username.getText().toString().trim())){
                ToastUtils.warning(this, "请输入正确格式的手机号");
                return;
            }

            //倒计时
            ISGETCODE = true;
            getCodeHandler.sendEmptyMessageDelayed(1, 1000);

        }else if(temdId == R.id.txt_login){
            //登录
            if(ISUSERLOGIN){
                //账号密码登录
//                userLogin();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{
                //验证码登录
                getCodeLogin();
            }
        }else if(temdId == R.id.txt_register){
            initGetCode();
            //注册
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }else if(temdId == R.id.txt_getcodelogin){
            //验证码登录
            if(txt_getcodelogin.getText().toString().trim().equals("验证码登录")){
                ISUSERLOGIN = false;
                txt_getcode.setVisibility(View.VISIBLE);
                txt_getcodelogin.setText("账号密码登录");
                et_password.setHint("请输入验证码");
                et_password.setInputType(InputType.TYPE_CLASS_PHONE);
                et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                et_username.setText("");
                et_password.setText("");
            }else{
                initGetCode();
                ISUSERLOGIN = true;
                txt_getcode.setVisibility(View.GONE);
                txt_getcodelogin.setText("验证码登录");
                et_password.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
                et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                et_password.setHint("请输入密码");
                et_username.setText("");
                et_password.setText("");
            }
        }
    }

    /**
     * 获取验证码登录
     */
    private void getCodeLogin() {
        //标记标识设置是否点击过获取验证码

        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
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

    /**
     * 账号密码登录
     */
    private void userLogin() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //账号判断
        if(TextUtils.isEmpty(username)){
            ToastUtils.warning(this, "手机不能为空");
            return;
        }
        if(!BannerUtils.isMobileNO(username)){
            ToastUtils.warning(this, "请输入正确格式的手机号");
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

        login();
    }

    Handler getCodeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            txt_getcode.setText("倒计时" + min-- + "s");
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
        txt_getcode.setText("获取验证码");
        ISGETCODE = false;
    }


}
