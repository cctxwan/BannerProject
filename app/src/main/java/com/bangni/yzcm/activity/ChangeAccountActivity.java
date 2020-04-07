package com.bangni.yzcm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
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
 * 更换账号界面
 */
public class ChangeAccountActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.et_changeaccount_phone)
    EditText et_changeaccount_phone;

    @BindView(R.id.et_changeaccount_code)
    EditText et_changeaccount_code;

    @BindView(R.id.txt_changeaccount_getcode)
    TextView txt_changeaccount_getcode;

    @BindView(R.id.txt_changeaccount_comple)
    TextView txt_changeaccount_comple;

    //两次的验证码先为空
    private boolean old_getCode, new_getCode = false;

    //倒计时60s
    private int min = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        initView();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        et_changeaccount_phone.setText(new BannerPreferenceStorage(BannerApplication.getInstance()).getPhone());
    }

    @OnClick({R.id.img_changeaccount_back, R.id.txt_changeaccount_getcode, R.id.txt_changeaccount_comple})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_changeaccount_back){
            finish();
        }else if(temdId == R.id.txt_changeaccount_comple){
            //下一步
            next();
        }else if(temdId == R.id.txt_changeaccount_getcode){
            if(TextUtils.isEmpty(et_changeaccount_phone.getText().toString().trim())){
                ToastUtils.warning(this, "手机号不能为空");
                return;
            }
            getCode();
        }
    }

    /**
     * 先获取验证码
     */
    private void getCode(){
        Map<String, String> map = new HashMap<>();
        map.put("cell", et_changeaccount_phone.getText().toString().trim());
        map.put("type", "change_phone_code");
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserGetCodeBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserGetCodeBean> response) {
                //倒计时
                old_getCode = true;
                getCodeHandler.sendEmptyMessageDelayed(1, 1000);
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(ChangeAccountActivity.this, msg);
            }
        };
        BannerRetrofitUtil.getInstance().userGetCode(body, new BannerProgressSubscriber<BannerBaseResponse<UserGetCodeBean>>(mListener, this, true));

    }

    Handler getCodeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            txt_changeaccount_getcode.setText("倒计时" + min-- + "s");
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
        txt_changeaccount_getcode.setText("获取验证码");
        old_getCode = false;
    }

    private void next() {
        if(!old_getCode){
            ToastUtils.warning(this, "请先获取验证码");
            return;
        }

        if(TextUtils.isEmpty(et_changeaccount_code.getText().toString().trim())){
            ToastUtils.warning(this, "验证码不能为空");
        }

//        if()
    }
}
