package com.bangni.yzcm.activity;

import android.app.Dialog;
import android.content.Intent;
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
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.network.bean.ChangeAccountModel;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import com.bangni.yzcm.utils.BannerUtils;
import com.bangni.yzcm.utils.ClearDataUtils;
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

    //验证码先为空
    private boolean getCode = false;

    //倒计时60s
    private int min = 60;

    //存储旧验证码
    private String oldCodeStr;

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
            if(txt_changeaccount_comple.getText().toString().trim().equals("下一步")){
                //下一步
                next();
            }else if(txt_changeaccount_comple.getText().toString().trim().equals("更换手机号码")){
                //更换
                comple();
            }
        }else if(temdId == R.id.txt_changeaccount_getcode){

            //固定格式的手机号码
//            if(TextUtils.isEmpty(et_changeaccount_phone.getText().toString().trim())){
//                ToastUtils.warning(this, "手机号不能为空");
//                return;
//            }
//            if(!BannerUtils.isMobileNO(et_changeaccount_phone.getText().toString().trim())){
//                ToastUtils.warning(this, "请输入正确格式的手机号");
//                return;
//            }
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
                getCode = true;
                txt_changeaccount_getcode.setClickable(false);
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
            txt_changeaccount_getcode.setText(min-- + "s");
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
        getCode = false;
        txt_changeaccount_getcode.setClickable(true);
    }

    /**
     * 确认更换手机号
     */
    private void comple() {
        if(!TextUtils.isEmpty(et_changeaccount_phone.getText().toString().trim())){
            ToastUtils.warning(this, "手机号不能为空");
            return;
        }

        if(!BannerUtils.isMobileNO(et_changeaccount_phone.getText().toString().trim())){
            ToastUtils.warning(this, "请输入正确格式的手机号");
            return;
        }

        if(!getCode){
            ToastUtils.warning(this, "请先获取验证码");
            return;
        }

        if(TextUtils.isEmpty(et_changeaccount_code.getText().toString().trim())){
            ToastUtils.warning(this, "验证码不能为空");
            return;
        }


        new CommomDialog(mContext, R.style.dialog, new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, String content) {
                if(content.equals("yes_hc")){
                    dialog.dismiss();
                    //修改
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", et_changeaccount_phone.getText().toString().trim());
                    map.put("code", et_changeaccount_code.getText().toString().trim());
                    map.put("oldCode", oldCodeStr);
                    Gson gson = new Gson();
                    String entity = gson.toJson(map);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
                    BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<ChangeAccountModel>>() {

                        @Override
                        public void onNext(BannerBaseResponse<ChangeAccountModel> response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new BannerPreferenceStorage(BannerApplication.getInstance()).setToken("");
                                    BannerUtils.showImageToas(mContext, "更换成功,请重新登录");
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                }
                            });
                        }

                        @Override
                        public void onError(String msg) {
                            ToastUtils.error(mContext, msg);
                        }
                    };
                    BannerRetrofitUtil.getInstance().changeAccount(body, new BannerProgressSubscriber<BannerBaseResponse<ChangeAccountModel>>(mListener, mContext, true));
                }else if(content.equals("no_hc")){
                    dialog.dismiss();
                }
            }
        }, 5).show();
    }

    /**
     * 下一步
     */
    private void next() {
        if(!getCode){
            ToastUtils.warning(this, "请先获取验证码");
            return;
        }

        if(TextUtils.isEmpty(et_changeaccount_code.getText().toString().trim())){
            ToastUtils.warning(this, "验证码不能为空");
            return;
        }

        //旧验证码
        oldCodeStr = et_changeaccount_code.getText().toString().trim();


        initGetCode();
        txt_changeaccount_comple.setText("更换手机号码");
        getCode = false;

        //手机号可输入并默认获取焦点
        et_changeaccount_phone.setFocusableInTouchMode(true);
        et_changeaccount_phone.setFocusable(true);
        et_changeaccount_phone.requestFocus();

        et_changeaccount_phone.setText("");
        et_changeaccount_code.setText("");
    }
}
