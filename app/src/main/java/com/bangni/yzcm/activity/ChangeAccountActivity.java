package com.bangni.yzcm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
    }

    @OnClick({R.id.img_changeaccount_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_changeaccount_back){
            finish();
        }else if(temdId == R.id.txt_changeaccount_comple){
            //下一步
            next();
        }
    }

    private void next() {
    }
}
