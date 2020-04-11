package com.bangni.yzcm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.network.bean.ChangepsdModel;
import com.bangni.yzcm.network.bean.FeedBookListModel;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
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
 * 修改密码
 */
public class ChangePsdActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.et_oldpsd)
    EditText et_oldpsd;

    @BindView(R.id.et_newpsd)
    EditText et_newpsd;

    @BindView(R.id.et_newpsd_r)
    EditText et_newpsd_r;

    @BindView(R.id.txt_changepsd_comple)
    TextView txt_changepsd_comple;

    @BindView(R.id.img_old_lookpsd)
    ImageView img_old_lookpsd;

    @BindView(R.id.img_new_lookpsd)
    ImageView img_new_lookpsd;

    @BindView(R.id.img_new_r_lookpsd)
    ImageView img_new_r_lookpsd;


    //查看密码
    private boolean isLookOldPsd, isLookNewPsd, isLookNewRPsd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psd);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        initView();
    }

    private void initView() {

        txt_changepsd_comple.setClickable(false);

        //1.创建工具类对象 设置监听空间
        CheckEditForButton checkEditForButton = new CheckEditForButton(txt_changepsd_comple);

        //2.把所有被监听的EditText设置进去
        checkEditForButton.addEditText(et_oldpsd, et_newpsd, et_newpsd_r);

        //3.根据接口回调的方法,分别进行操作
        checkEditForButton.setListener(new EditTextChangeListener() {
            @Override
            public void allHasContent(boolean isHasContent) {
                BannerLog.d("b_cc", "isHasContent=" + isHasContent);
                if (isHasContent) {
                    txt_changepsd_comple.setClickable(true);
                    txt_changepsd_comple.setBackgroundResource(R.drawable.slategrey_bg);
                } else {
                    txt_changepsd_comple.setClickable(false);
                    txt_changepsd_comple.setBackgroundResource(R.drawable.slategrey_helftrs_bg);
                }
            }
        });


        //加粗
        txt_changepsd_comple.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        et_oldpsd.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_oldpsd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //否则隐藏密码
        et_oldpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_newpsd.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_newpsd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //否则隐藏密码
        et_newpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_newpsd_r.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_newpsd_r.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //否则隐藏密码
        et_newpsd_r.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_oldpsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    img_old_lookpsd.setVisibility(View.VISIBLE);
                }else{
                    img_old_lookpsd.setVisibility(View.GONE);
                }

                if(s.length() == 0) {
                    isLookOldPsd = false;
                    et_oldpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_old_lookpsd.setImageResource(R.mipmap.hide_pass);
                    et_oldpsd.setSelection(et_oldpsd.getText().length());
                }
            }
        });



        img_new_lookpsd.setVisibility(View.GONE);
        et_newpsd.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_newpsd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //否则隐藏密码
        et_newpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_newpsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    img_new_lookpsd.setVisibility(View.VISIBLE);
                }else{
                    img_new_lookpsd.setVisibility(View.GONE);
                }
                if(s.length() == 0) {
                    isLookNewPsd = false;
                    et_newpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_new_lookpsd.setImageResource(R.mipmap.hide_pass);
                    et_newpsd.setSelection(et_newpsd.getText().length());
                }
            }
        });




        img_new_r_lookpsd.setVisibility(View.GONE);
        et_newpsd_r.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        et_newpsd_r.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //否则隐藏密码
        et_newpsd_r.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_newpsd_r.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    img_new_r_lookpsd.setVisibility(View.VISIBLE);
                }else{
                    img_new_r_lookpsd.setVisibility(View.GONE);
                }
                if(s.length() == 0) {
                    isLookNewRPsd = false;
                    et_newpsd_r.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_new_r_lookpsd.setImageResource(R.mipmap.hide_pass);
                    et_newpsd_r.setSelection(et_newpsd_r.getText().length());
                }
            }
        });
    }

    @OnClick({R.id.img_changepsd_back, R.id.txt_changepsd_comple, R.id.img_old_lookpsd, R.id.img_new_lookpsd, R.id.img_new_r_lookpsd})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_changepsd_back){
            finish();
        }else if(temdId == R.id.txt_changepsd_comple){
            //修改密码
            changePsd();
        }else if(temdId == R.id.img_old_lookpsd){
            //查看密码
            if(isLookOldPsd){
                isLookOldPsd = false;
                et_oldpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                img_old_lookpsd.setImageResource(R.mipmap.hide_pass);
                et_oldpsd.setSelection(et_oldpsd.getText().length());
            }else{
                isLookOldPsd = true;
                et_oldpsd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                img_old_lookpsd.setImageResource(R.mipmap.show_pass);
                et_oldpsd.setSelection(et_oldpsd.getText().length());
            }
        }else if(temdId == R.id.img_new_lookpsd){
            //查看密码
            if(isLookNewPsd){
                isLookNewPsd = false;
                et_newpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                img_new_lookpsd.setImageResource(R.mipmap.hide_pass);
                et_newpsd.setSelection(et_newpsd.getText().length());
            }else{
                isLookNewPsd = true;
                et_newpsd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                img_new_lookpsd.setImageResource(R.mipmap.show_pass);
                et_newpsd.setSelection(et_newpsd.getText().length());
            }
        }else if(temdId == R.id.img_new_r_lookpsd){
            //查看密码
            if(isLookNewRPsd){
                isLookNewRPsd = false;
                et_newpsd_r.setTransformationMethod(PasswordTransformationMethod.getInstance());
                img_new_r_lookpsd.setImageResource(R.mipmap.hide_pass);
                et_newpsd_r.setSelection(et_newpsd_r.getText().length());
            }else{
                isLookNewRPsd = true;
                et_newpsd_r.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                img_new_r_lookpsd.setImageResource(R.mipmap.show_pass);
                et_newpsd_r.setSelection(et_newpsd_r.getText().length());
            }
        }
    }

    /**
     * 修改密码
     */
    private void changePsd() {
        //旧密码
        if(TextUtils.isEmpty(et_oldpsd.getText().toString().trim())){
            ToastUtils.warning(mContext, "旧密码不能为空");
            return;
        }

        //新密码
        if(TextUtils.isEmpty(et_newpsd.getText().toString().trim()) || et_newpsd.getText().toString().trim().length() < 6){
            ToastUtils.warning(mContext, "请输入6-20位大小写字母和数字组合");
            return;
        }

        //确认密码
        if(TextUtils.isEmpty(et_newpsd_r.getText().toString().trim()) || et_newpsd_r.getText().toString().trim().length() < 6){
            ToastUtils.warning(mContext, "请输入6-20位大小写字母和数字组合");
            return;
        }

        //两次输入的密码不一致
        if(!et_newpsd_r.getText().toString().trim().equals(et_newpsd.getText().toString().trim())){
            ToastUtils.warning(mContext, "两次输入的密码不一致");
            return;
        }

        new CommomDialog(mContext, R.style.dialog, new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, String content) {
                if(content.equals("yes_hc")){
                    dialog.dismiss();
                    //提交数据到服务器
                    changepsd.post(getDatas);
                }else if(content.equals("no_hc")){
                    dialog.dismiss();
                }
            }
        }, 5).show();

    }

    Handler changepsd = new Handler();

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("oldPassword", et_oldpsd.getText().toString().trim());
            map.put("newPassword", et_newpsd.getText().toString().trim());
            map.put("newPassword2", et_newpsd_r.getText().toString().trim());
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<ChangepsdModel>>() {

                @Override
                public void onNext(BannerBaseResponse<ChangepsdModel> response) {
                    new BannerPreferenceStorage(BannerApplication.getInstance()).setToken("");
                    startActivity(new Intent(mContext, LoginActivity.class));
                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(mContext, msg);
                }
            };
            BannerRetrofitUtil.getInstance().changePsd(body, new BannerProgressSubscriber<BannerBaseResponse<ChangepsdModel>>(mListener, mContext, true));
        }
    };

}
