package com.bangni.yzcm.activity;

import android.os.Bundle;
import android.view.View;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutActivity extends BannerActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
    }

    @OnClick({R.id.img_about_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_about_back){
            finish();
        }
    }
}
