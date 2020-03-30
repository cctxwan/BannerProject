package com.bangni.yzcm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_orderdetail_title)
    TextView txt_orderdetail_title;

    @BindView(R.id.txt_orderdetail_info)
    TextView txt_orderdetail_info;

    @BindView(R.id.txt_broadcasenumber)
    TextView txt_broadcasenumber;

    @BindView(R.id.img_orderdetail_back)
    LinearLayout img_orderdetail_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        
        initView();
    }

    private void initView() {
        //加粗
        txt_orderdetail_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_orderdetail_info.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_broadcasenumber.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @OnClick({R.id.img_orderdetail_back, R.id.rel_order_community, R.id.orderdetail_txt_allbroadcast})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_orderdetail_back){
            finish();
        }else if(temdId == R.id.rel_order_community){
            //社区列表
            startActivity(new Intent(mContext, CommunityActivity.class));
        }else if(temdId == R.id.orderdetail_txt_allbroadcast){
            List<String> parms = new ArrayList<>();
            parms.add("大兴裕苑");
            parms.add("大兴景苑");
            parms.add("大兴家苑");
            parms.add("唐延中心城");
            parms.add("秦地雅仕");
            parms.add("锦园新世纪");
            parms.add("棕榈阳光");

            //全部监播统计
            new CommomDialog(mContext, R.style.dialog, parms, new CommomDialog.OnCloseListenerParmes() {

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
