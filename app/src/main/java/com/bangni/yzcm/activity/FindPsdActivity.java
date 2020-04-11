package com.bangni.yzcm.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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
import com.bangni.yzcm.network.bean.FindPsdBean;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.bean.UserRegisterBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerUtils;
import com.bangni.yzcm.utils.ToastUtils;
import com.bangni.yzcm.view.CheckEditForButton;
import com.bangni.yzcm.view.EditTextChangeListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 忘记密码
 */
public class FindPsdActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_findpsd_title)
    TextView txt_findpsd_title;

    @BindView(R.id.et_findpsd_username)
    EditText et_findpsd_username;

    @BindView(R.id.et_findpsd_code)
    EditText et_findpsd_code;

    @BindView(R.id.txt_findpsd_getcode)
    TextView txt_findpsd_getcode;

    @BindView(R.id.et_findpsd_password)
    EditText et_findpsd_password;

    @BindView(R.id.img_findpsd_lookpsd)
    ImageView img_findpsd_lookpsd;

    @BindView(R.id.txt_findpsd)
    TextView txt_findpsd;

    //查看密码
    private boolean isLookPsd = false;

    //验证码先为空
    private boolean getCode = false;

    //倒计时60s
    private int min = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psd);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        txt_findpsd.setClickable(false);
        //1.创建工具类对象 设置监听空间
        CheckEditForButton checkEditForButton = new CheckEditForButton(txt_findpsd);

        //2.把所有被监听的EditText设置进去
        checkEditForButton.addEditText(et_findpsd_username, et_findpsd_code, et_findpsd_password);

        //3.根据接口回调的方法,分别进行操作
        checkEditForButton.setListener(new EditTextChangeListener() {
            @Override
            public void allHasContent(boolean isHasContent) {
                BannerLog.d("b_cc", "isHasContent=" + isHasContent);
                if (isHasContent) {
                    txt_findpsd.setClickable(true);
                    txt_findpsd.setBackgroundResource(R.drawable.slategrey_bg);
                } else {
                    txt_findpsd.setClickable(false);
                    txt_findpsd.setBackgroundResource(R.drawable.slategrey_helftrs_bg);
                }
            }
        });

        img_findpsd_lookpsd.setVisibility(View.GONE);
        et_findpsd_password.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_findpsd_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //否则隐藏密码
        et_findpsd_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_findpsd_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    img_findpsd_lookpsd.setVisibility(View.VISIBLE);
                }else{
                    img_findpsd_lookpsd.setVisibility(View.GONE);
                }
                if(s.length() == 0) {
                    isLookPsd = false;
                    et_findpsd_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_findpsd_lookpsd.setImageResource(R.mipmap.hide_pass);
                    et_findpsd_password.setSelection(et_findpsd_password.getText().length());
                }
            }
        });
    }

    @OnClick({R.id.img_findpsd_lookpsd, R.id.txt_findpsd_getcode, R.id.txt_findpsd, R.id.img_findpsd_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_findpsd_lookpsd){
            if(isLookPsd){
                isLookPsd = false;
                et_findpsd_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                img_findpsd_lookpsd.setImageResource(R.mipmap.hide_pass);
                et_findpsd_password.setSelection(et_findpsd_password.getText().length());
            }else{
                isLookPsd = true;
                et_findpsd_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                img_findpsd_lookpsd.setImageResource(R.mipmap.show_pass);
                et_findpsd_password.setSelection(et_findpsd_password.getText().length());
            }
        }else if(temdId == R.id.txt_findpsd_getcode){
            //获取验证码
            getCode();
        }else if(temdId == R.id.txt_findpsd){
            //确认修改
            findPsd();
        }else if(temdId == R.id.img_findpsd_back){
            //返回
            finish();
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        if(TextUtils.isEmpty(et_findpsd_username.getText().toString().trim())){
            ToastUtils.warning(mContext, "手机号不能为空");
            return;
        }
        if(!BannerUtils.isMobileNO(et_findpsd_username.getText().toString().trim())){
            ToastUtils.warning(this, "请输入正确格式的手机号");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("cell", et_findpsd_username.getText().toString().trim());
        map.put("type", "forget_pwd_code_");
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserGetCodeBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserGetCodeBean> response) {
                //倒计时
                getCode = true;
                txt_findpsd_getcode.setClickable(false);
                getCodeHandler.sendEmptyMessageDelayed(1, 1000);
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(mContext, msg);
            }
        };
        BannerRetrofitUtil.getInstance().userGetCode(body, new BannerProgressSubscriber<BannerBaseResponse<UserGetCodeBean>>(mListener, this, true));
    }

    Handler getCodeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            txt_findpsd_getcode.setText(min-- + "s");
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
        txt_findpsd_getcode.setText("获取验证码");
        getCode = false;
        txt_findpsd_getcode.setClickable(true);
    }

    /**
     * 找回密码
     */
    private void findPsd() {
        if(!getCode){
            ToastUtils.warning(this, "请先获取验证码");
            return;
        }

        if(TextUtils.isEmpty(et_findpsd_code.getText().toString().trim())){
            ToastUtils.warning(this, "验证码不能为空");
            return;
        }

        if(TextUtils.isEmpty(et_findpsd_password.getText().toString().trim())
                || et_findpsd_password.getText().toString().trim().length() < 6){
            BannerUtils.showImageToasPsdError(mContext, "请输入6-20位大小写字母和数字组合");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("phone", et_findpsd_username.getText().toString().trim());
        map.put("password", et_findpsd_password.getText().toString().trim());
        map.put("code", et_findpsd_code.getText().toString().trim());
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<FindPsdBean>>() {

            @Override
            public void onNext(BannerBaseResponse<FindPsdBean> response) {
                BannerUtils.showToLoginNoAccount(mContext, "新密码设置成功");
                finish();
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(mContext, msg);
            }
        };
        BannerRetrofitUtil.getInstance().findPsd(body, new BannerProgressSubscriber<BannerBaseResponse<FindPsdBean>>(mListener, this, true));


    }
}
