package com.bangni.yzcm.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计详情
 */
public class StatisticDetailActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_statistic_title)
    TextView txt_statistic_title;

    @BindView(R.id.txt_statistic_info)
    TextView txt_statistic_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_detail);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        //加粗
        txt_statistic_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_statistic_info.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @OnClick({R.id.img_statistic_back, R.id.txt_choosesq})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_statistic_back){
            finish();
        }else if(temdId == R.id.txt_choosesq){
            //选择社区
            List<String> parms = new ArrayList<>();
            parms.add("大兴裕苑");
            parms.add("大兴景苑");
            parms.add("大兴家苑");
            parms.add("唐延中心城");
            parms.add("秦地雅仕");
            parms.add("锦园新世纪");
            parms.add("棕榈阳光");

            //全部监播统计
            new CommomDialog(StatisticDetailActivity.this, R.style.dialog, parms, new CommomDialog.OnCloseListenerParmes() {

                @Override
                public void onClickParmes(Dialog dialog, String content, String str) {
                    if(content.equals("ok")){
                        dialog.dismiss();
                        BannerLog.d("b_cc", "选择的是" + str);
                        //修改
                    }
                }
            }, 6).show();
        }
    }
}
