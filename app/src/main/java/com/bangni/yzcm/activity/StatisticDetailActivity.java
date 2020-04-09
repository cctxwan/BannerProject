package com.bangni.yzcm.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.network.bean.CommunityInfos;
import com.bangni.yzcm.network.bean.StatisitcsInfos;
import com.bangni.yzcm.network.bean.UserFeedBookBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计详情
 */
public class StatisticDetailActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_statistic_title)
    TextView txt_statistic_title;

    @BindView(R.id.txt_statistic_info)
    TextView txt_statistic_info;

    @BindView(R.id.txt_choosesq)
    TextView txt_choosesq;

    //定义str
    private String pid;

    //社区名称
    List<CommunityInfos> communityNameLists = new ArrayList<>();
    List<String> parmsCommunitys = new ArrayList<>();

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

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        //非空验证
        if(!TextUtils.isEmpty(pid)){
            BannerLog.d("b_cc", "pid=" + pid);
            handler.post(getCommunityDatas);
        }
    }

    Runnable getCommunityDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pid", pid);
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<List<CommunityInfos>>>() {

                @Override
                public void onNext(BannerBaseResponse<List<CommunityInfos>> response) {
                    if(response.data != null){

                        //成功，继续下一个请求
                        handler.sendEmptyMessage(1);

                        communityNameLists = response.data;
                        if(communityNameLists.size() > 0){
                            txt_choosesq.setText(communityNameLists.get(0).getCommunityName());
                        }

                        for (int i = 0; i < communityNameLists.size(); i ++){
                            parmsCommunitys.add(communityNameLists.get(i).getCommunityName());
                        }
                    }
                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(mContext, msg);
                }
            };
            BannerRetrofitUtil.getInstance().getCommunityLists(body, new BannerProgressSubscriber<BannerBaseResponse<List<CommunityInfos>>>(mListener, mContext, true));
        }
    };

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pid", pid);
            map.put("communityPid", selectCommunityPid(txt_choosesq.getText().toString().trim()));
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<StatisitcsInfos>>() {

                @Override
                public void onNext(BannerBaseResponse<StatisitcsInfos> response) {

                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(mContext, msg);
                }
            };
            BannerRetrofitUtil.getInstance().statisitcsDetail(body, new BannerProgressSubscriber<BannerBaseResponse<StatisitcsInfos>>(mListener, mContext, true));
        }
    };

    /**
     * 通过名称得到社区id
     * @param trim
     * @return
     */
    private String selectCommunityPid(String trim) {
        for (int i = 0; i < communityNameLists.size(); i ++){
            if(communityNameLists.get(i).getCommunityName().equals(trim)){
                return communityNameLists.get(i).getPid() + "";
            }
        }
        return "";
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                handler.post(getDatas);
            }
        }
    };

    @OnClick({R.id.img_statistic_back, R.id.txt_choosesq})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_statistic_back){
            finish();
        }else if(temdId == R.id.txt_choosesq){
            //选择社区

            //全部监播统计
            new CommomDialog(StatisticDetailActivity.this, R.style.dialog, parmsCommunitys, new CommomDialog.OnCloseListenerParmes() {

                @Override
                public void onClickParmes(Dialog dialog, String content, String str) {
                    if(content.equals("ok")){
                        dialog.dismiss();
                        BannerLog.d("b_cc", "选择的是" + str);
                        //修改
                        txt_choosesq.setText(str);
                    }
                }
            }, 6).show();
        }
    }
}
