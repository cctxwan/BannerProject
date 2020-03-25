package com.bangni.yzcm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;

public class OrderDetailActivity extends BannerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
    }
}
