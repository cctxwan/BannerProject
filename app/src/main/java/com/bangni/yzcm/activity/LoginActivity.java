package com.bangni.yzcm.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.bean.UserGetCodeLoginBean;
import com.bangni.yzcm.network.bean.UserLoginBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarCompat;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import com.bangni.yzcm.utils.BannerUtils;
import com.bangni.yzcm.utils.ToastUtils;
import com.bangni.yzcm.view.CheckEditForButton;
import com.bangni.yzcm.view.EditTextChangeListener;
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
public class LoginActivity extends BannerActivity implements View.OnClickListener {

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

    @BindView(R.id.txt_login_title)
    TextView txt_login_title;

    @BindView(R.id.img_login_loogpsd)
    ImageView img_login_loogpsd;

    @BindView(R.id.txt_login_ysxy)
    TextView txt_login_ysxy;

    @BindView(R.id.txt_login_forgetPsd)
    TextView txt_login_forgetPsd;

    //是否是账号登录
    private boolean ISUSERLOGIN = true;

    //是否获取过验证码
    private boolean ISGETCODE = false;

    //查看密码
    private boolean isLookPsd = false;

    //倒计时60s
    private int min = 60;

    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关闭侧滑
        setSwback(false);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        StatusBarCompat.translucentStatusBar(this, false);
        StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT);
        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        initView();
    }



    private void initView() {

        txt_login.setClickable(false);

        //1.创建工具类对象 设置监听空间
        CheckEditForButton checkEditForButton = new CheckEditForButton(txt_login);

        //2.把所有被监听的EditText设置进去
        checkEditForButton.addEditText(et_username, et_password);

        //3.根据接口回调的方法,分别进行操作
        checkEditForButton.setListener(new EditTextChangeListener() {
            @Override
            public void allHasContent(boolean isHasContent) {
                BannerLog.d("b_cc", "isHasContent=" + isHasContent);
                if (isHasContent) {
                    txt_login.setClickable(true);
                    txt_login.setBackgroundResource(R.drawable.slategrey_bg);
                } else {
                    txt_login.setClickable(false);
                    txt_login.setBackgroundResource(R.drawable.slategrey_helftrs_bg);
                }
            }
        });

        if(!TextUtils.isEmpty(new BannerPreferenceStorage(BannerApplication.getInstance()).getToken())){
            //去主界面
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        Intent intent = getIntent();
        if(intent != null){
            username = intent.getStringExtra("username");
            password = intent.getStringExtra("password");

            BannerLog.d("b_cc", "注册传过来的账号：" + username + "和密码：" + password);
        }

        //加粗
        txt_login_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_getcodelogin.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_getcode.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_login.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


        txt_register.setClickable(true);
        String textSource = "还没有账号，立即<font color='#1D65FF'>注册</font>";
        txt_register.setText(Html.fromHtml(textSource));

        String textYsxy = "登录即同意<font color='#1D65FF'>《用户协议》</font>首次登录自动注册";
        txt_login_ysxy.setText(Html.fromHtml(textYsxy));

        //密码
        et_password.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        if(ISUSERLOGIN){
            //否则隐藏密码
            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }


        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                BannerLog.d("b_cc", "111");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BannerLog.d("b_cc", "222");
            }

            @Override
            public void afterTextChanged(Editable s) {
                BannerLog.d("b_cc", "333---" + s.length());
                if(ISUSERLOGIN){
                    if(s.length() > 0){
                        img_login_loogpsd.setVisibility(View.VISIBLE);
                    }else{
                        img_login_loogpsd.setVisibility(View.GONE);
                    }
                    if(s.length() == 0) {
                        isLookPsd = false;
                        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        img_login_loogpsd.setImageResource(R.mipmap.hide_pass);
                        et_password.setSelection(et_password.getText().length());
                    }
                }
            }
        });
    }


    private void login(){
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserLoginBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserLoginBean> response) {
                //非空
                if(response.data == null) return;

                //存入token
                new BannerPreferenceStorage(BannerApplication.getInstance()).setToken(response.data.getToken());
                //清空img和name
                new BannerPreferenceStorage(BannerApplication.getInstance()).setInfoImg("");
                new BannerPreferenceStorage(BannerApplication.getInstance()).setNickName("");

                //去主界面
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(LoginActivity.this, msg);
            }
        };
        BannerRetrofitUtil.getInstance().userLogin(body, new BannerProgressSubscriber<BannerBaseResponse<UserLoginBean>>(mListener, this, true));
    }

    /**
     * 点击事件
     * @param v
     */
    @OnClick({R.id.txt_login, R.id.txt_getcode, R.id.txt_register, R.id.txt_getcodelogin, R.id.img_login_loogpsd, R.id.txt_login_ysxy, R.id.txt_login_forgetPsd})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.txt_getcode){
            //获取验证码

            //非空处理
            if(TextUtils.isEmpty(et_username.getText().toString().trim())){
                ToastUtils.warning(this, "手机号不能为空");
                return;
            }
            if(!BannerUtils.isMobileNO(et_username.getText().toString().trim())){
                ToastUtils.warning(this, "请输入正确格式的手机号");
                return;
            }

            getCode(et_username.getText().toString().trim());

        }else if(temdId == R.id.txt_login_forgetPsd){
            //忘记密码
            startActivity(new Intent(LoginActivity.this, FindPsdActivity.class));
        }else if(temdId == R.id.txt_login){
            //登录
            if(ISUSERLOGIN){
                //账号密码登录
                userLoginCheck();
            }else{
                //验证码登录
                getCodeLoginCheck();
            }
        }else if(temdId == R.id.txt_login_ysxy){
            //隐私协议
            startActivity(new Intent(LoginActivity.this, AgreementActivity.class));
        }else if(temdId == R.id.txt_register){
            initGetCode();
            //注册
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }else if(temdId == R.id.txt_getcodelogin){
            //验证码登录
            if(txt_getcodelogin.getText().toString().trim().equals("验证码登录")){
                ISUSERLOGIN = false;
                txt_login_title.setText("手机快捷登录");
                txt_register.setText("");
                txt_register.setClickable(false);
                txt_login_ysxy.setVisibility(View.VISIBLE);
                txt_login_forgetPsd.setVisibility(View.GONE);
                txt_getcode.setVisibility(View.VISIBLE);
                img_login_loogpsd.setVisibility(View.GONE);
                txt_getcodelogin.setText("账号密码登录");
                et_password.setHint("请输入验证码");
                et_password.setInputType(InputType.TYPE_CLASS_PHONE);
                et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                et_username.setText("");
                et_password.setText("");
            }else{
                initGetCode();
                ISUSERLOGIN = true;
                txt_login_title.setText("账号密码登录");
                txt_register.setText((Html.fromHtml("还没有账号，立即<font color='#1D65FF'>注册</font>")));
                txt_login_ysxy.setVisibility(View.GONE);
                txt_register.setClickable(true);
                txt_login_forgetPsd.setVisibility(View.VISIBLE);
                txt_getcode.setVisibility(View.GONE);
                img_login_loogpsd.setVisibility(View.GONE);
                txt_getcodelogin.setText("验证码登录");
                et_password.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
                et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                //否则隐藏密码
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et_password.setSelection(et_password.getText().length());

                et_password.setHint("请输入密码");
//                et_username.setText("");
                et_password.setText("");
            }
        }else if(temdId == R.id.img_login_loogpsd){
            if(isLookPsd){
                isLookPsd = false;
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                img_login_loogpsd.setImageResource(R.mipmap.hide_pass);
                et_password.setSelection(et_password.getText().length());
            }else{
                isLookPsd = true;
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                img_login_loogpsd.setImageResource(R.mipmap.show_pass);
                et_password.setSelection(et_password.getText().length());
            }
        }
    }

    /**
     * 获取验证码
     * @param et_username
     */
    private void getCode(String et_username) {
        Map<String, String> map = new HashMap<>();
        map.put("cell", et_username);
        map.put("type", "login_code_");
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserGetCodeBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserGetCodeBean> response) {
                //倒计时
                ISGETCODE = true;
                txt_getcode.setClickable(false);
                getCodeHandler.sendEmptyMessageDelayed(1, 1000);
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(LoginActivity.this, msg);
            }
        };
        BannerRetrofitUtil.getInstance().userGetCode(body, new BannerProgressSubscriber<BannerBaseResponse<UserGetCodeBean>>(mListener, this, true));
    }

    /**
     * 获取验证码登录
     */
    private void getCodeLoginCheck() {
        //标记标识设置是否点击过获取验证码

        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //账号判断
        if(TextUtils.isEmpty(username)){
            ToastUtils.warning(this, "手机号不能为空");
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
            ToastUtils.warning(this, "验证码不能为空");
            return;
        }
        
        codeLogin(username, password);
    }

    /**
     * 验证码登录
     * @param username
     * @param password
     */
    private void codeLogin(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("code", password);
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserGetCodeLoginBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserGetCodeLoginBean> response) {

                //非空
                if(response.data == null) return;

                //存入token
                new BannerPreferenceStorage(BannerApplication.getInstance()).setToken(response.data.getToken());
                //清空img和name
                new BannerPreferenceStorage(BannerApplication.getInstance()).setInfoImg("");
                new BannerPreferenceStorage(BannerApplication.getInstance()).setNickName("");

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(String msg) {
                if(msg.equals("账号不存在")){
                    BannerUtils.showToLoginNoAccount(LoginActivity.this, "该手机号码暂未注册任何用户", 1500);
                }else{
                    ToastUtils.error(LoginActivity.this, msg);
                }
            }
        };
        BannerRetrofitUtil.getInstance().userGetCodeLogin(body, new BannerProgressSubscriber<BannerBaseResponse<UserGetCodeLoginBean>>(mListener, this, true));
    }

    /**
     * 账号密码登录
     */
    private void userLoginCheck() {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        //账号判断
        if(TextUtils.isEmpty(username)){
            ToastUtils.warning(this, "手机号不能为空");
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

        //账号密码登录
        login();
    }

    Handler getCodeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            txt_getcode.setText(min-- + "s");
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
        txt_getcode.setClickable(true);
    }


    long temptime;

    /**
     * 点击两次返回才退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN)) {
            if(System.currentTimeMillis() - temptime > 2000){
                System.out.println(Toast.LENGTH_LONG);
                Toast.makeText(this, "请在按一次返回退出", Toast.LENGTH_LONG).show();
                temptime = System.currentTimeMillis();
            } else {
                // 仿返回键退出界面,但不销毁，程序仍在后台运行
//                moveTaskToBack(false); // 关键的一行代码
                finishAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                //退出整个APP，记得加权限
                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                manager.restartPackage(getPackageName());
            }
            return true;
        }
        return false;
    }

}
