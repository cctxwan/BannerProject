package com.bangni.yzcm.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 监播详情
 */
public class BroadcastActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_broadcase_name)
    TextView txt_broadcase_name;

    @BindView(R.id.txt_broadcase_wz)
    TextView txt_broadcase_wz;

    @BindView(R.id.txt_broadcase_time)
    TextView txt_broadcase_time;

    @BindView(R.id.txt_broadcase_takephoto)
    TextView txt_broadcase_takephoto;

    @BindView(R.id.txt_broadcase_photosb)
    TextView txt_broadcase_photosb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        initView();
    }

    private void initView() {
        //加粗
        txt_broadcase_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_broadcase_wz.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_broadcase_time.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_broadcase_takephoto.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_broadcase_photosb.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @OnClick({R.id.img_broadcast_back, })
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_broadcast_back){
            finish();
        }
    }
}
