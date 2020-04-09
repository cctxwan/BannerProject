package com.bangni.yzcm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.bean.UserRegisterBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarCompat;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerUtils;
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

    @BindView(R.id.txt_tologin)
    TextView txt_tologin;

    @BindView(R.id.txt_register_title)
    TextView txt_register_title;

    @BindView(R.id.img_register_lookpsd)
    ImageView img_register_lookpsd;

    @BindView(R.id.txt_yhxy)
    TextView txt_yhxy;

    //是否获取过验证码
    private boolean ISGETCODE = false;

    //倒计时60s
    private int min = 60;

    //查看密码
    private boolean isLookPsd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        StatusBarCompat.translucentStatusBar(this, false);
        StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT);
        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        
        initView();
    }

    private void initView() {
        String textSource = "点击立即注册表示您已阅读并同意<font color='#1D65FF'>《用户协议》</font>";
        txt_yhxy.setText(Html.fromHtml(textSource));
        //加粗
        txt_register_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_rg_getcode.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_tologin.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_register.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        img_register_lookpsd.setVisibility(View.GONE);
        et_rg_password.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_rg_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //否则隐藏密码
        et_rg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_rg_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    img_register_lookpsd.setVisibility(View.VISIBLE);
                }else{
                    img_register_lookpsd.setVisibility(View.GONE);
                }
                if(s.length() == 0) {
                    isLookPsd = false;
                    et_rg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_register_lookpsd.setImageResource(R.mipmap.hide_pass);
                    et_rg_password.setSelection(et_rg_password.getText().length());
                }
            }
        });
    }


    /**
     * 点击事件
     * @param v
     */
    @OnClick({R.id.txt_rg_getcode, R.id.txt_register, R.id.txt_yhxy, R.id.txt_tologin, R.id.img_register_lookpsd})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.txt_rg_getcode){
            //获取验证码
            //账号判断
            if(TextUtils.isEmpty(et_rg_username.getText().toString().trim())){
                ToastUtils.warning(this, "手机号不能为空");
                return;
            }
            if(!BannerUtils.isMobileNO(et_rg_username.getText().toString().trim())){
                ToastUtils.warning(this, "请输入正确格式的手机号");
                return;
            }

            getCode(et_rg_username.getText().toString().trim());


        }else if(temdId == R.id.txt_register){
            //注册
            registerCheck();
        }else if(temdId == R.id.txt_yhxy){
            initGetCode();
            //用户协议
            startActivity(new Intent(mContext, AgreementActivity.class));
        }else if(temdId == R.id.txt_tologin){
            //去登录界面
            initGetCode();
            finish();
        }else if(temdId == R.id.img_register_lookpsd){
            //查看密码
            if(isLookPsd){
                isLookPsd = false;
                et_rg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                img_register_lookpsd.setImageResource(R.mipmap.hide_pass);
                et_rg_password.setSelection(et_rg_password.getText().length());
            }else{
                isLookPsd = true;
                et_rg_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                img_register_lookpsd.setImageResource(R.mipmap.show_pass);
                et_rg_password.setSelection(et_rg_password.getText().length());
            }
        }
    }

    /**
     * 获取验证码
     * @param trim
     */
    private void getCode(String trim) {
        Map<String, String> map = new HashMap<>();
        map.put("cell", trim);
        map.put("type", "reg_code_");
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserGetCodeBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserGetCodeBean> response) {
                BannerLog.d("b_cc", "注册验证码获取成功返回参数为：" + response.toString());

                //倒计时
                ISGETCODE = true;
                txt_rg_getcode.setClickable(false);
                getCodeHandler.sendEmptyMessageDelayed(1, 1000);
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(mContext, msg);
            }
        };
        BannerRetrofitUtil.getInstance().userGetCode(body, new BannerProgressSubscriber<BannerBaseResponse<UserGetCodeBean>>(mListener, this, true));
    }

    /**
     * 注册
     */
    private void registerCheck() {
        String username = et_rg_username.getText().toString().trim();
        String password = et_rg_password.getText().toString().trim();
        String code = et_rg_code.getText().toString().trim();

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
        if(TextUtils.isEmpty(code)){
            ToastUtils.warning(this, "验证码不能为空");
            return;
        }


        //密码判断
        if(TextUtils.isEmpty(password) || password.length() < 6){
//            ToastUtils.warning(this, "请输入6-20位大小写字母和数字组合");
            BannerUtils.showImageToasPsdError(mContext, "请输入6-20位大小写字母和数字组合");
            return;
        }

        register(username, password, code);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @param code
     */
    private void register(String username, String password, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("code", code);
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserRegisterBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserRegisterBean> response) {
                new CommomDialog(mContext, R.style.dialog, new CommomDialog.OnCloseListener() {

                    @Override
                    public void onClick(Dialog dialog, String content) {
                        if(content.equals("tologin")){
                            dialog.dismiss();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, 7).show();
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(mContext, msg);
            }
        };
        BannerRetrofitUtil.getInstance().userRegister(body, new BannerProgressSubscriber<BannerBaseResponse<UserRegisterBean>>(mListener, this, true));
    }

    Handler getCodeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            txt_rg_getcode.setText(min-- + "s");
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
        txt_rg_getcode.setClickable(true);
    }

}
