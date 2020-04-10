package com.bangni.yzcm.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.network.bean.CommunityDWInfos;
import com.bangni.yzcm.network.bean.CommunityInfos;
import com.bangni.yzcm.network.bean.OrderDetailInfo;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.network.bean.StatisitcsInfos;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.ToastUtils;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 订单详情界面
 */
public class OrderDetailActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_orderdetail_title)
    TextView txt_orderdetail_title;

    @BindView(R.id.txt_orderdetail_info)
    TextView txt_orderdetail_info;

    @BindView(R.id.txt_broadcasenumber)
    TextView txt_broadcasenumber;

    @BindView(R.id.txt_orderdetail_community)
    TextView txt_orderdetail_community;

    @BindView(R.id.txt_orderdetail_communitydw)
    TextView txt_orderdetail_communitydw;

    @BindView(R.id.txt_orderdetail_time)
    TextView txt_orderdetail_time;

    @BindView(R.id.txt_orderdetail_bgl)
    TextView txt_orderdetail_bgl;

    @BindView(R.id.img_orderdetail_url)
    ImageView img_orderdetail_url;

    @BindView(R.id.txt_orderdetail_bfl)
    TextView txt_orderdetail_bfl;

    //订单id
    private String pid, putCommunityPid, pointPid, monitorTime;

    //社区名称
    List<CommunityInfos> communityNameLists = new ArrayList<>();
    List<String> parmsCommunitys = new ArrayList<>();

    //社区点位
    List<CommunityDWInfos> communityNameDwLists = new ArrayList<>();
    List<String> parmsCommunityDws = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        initView();
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
                        getDatasHandler.sendEmptyMessage(1);

                        communityNameLists = response.data;
                        if(communityNameLists.size() > 0){
                            txt_orderdetail_community.setText(communityNameLists.get(0).getCommunityName());
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

    Runnable getOrderDetailDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pid", pid);
            map.put("putCommunityPid", selectCommunityPid(txt_orderdetail_community.getText().toString().trim()));
            map.put("pointPid", selectCommunityDwPid(txt_orderdetail_communitydw.getText().toString().trim()));
            map.put("monitorTime", System.currentTimeMillis() + "");//txt_orderdetail_time.getText().toString().trim()
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<OrderDetailInfo>>() {

                @Override
                public void onNext(BannerBaseResponse<OrderDetailInfo> response) {
                    if(!TextUtils.isEmpty(response.data.getMonitorImage())){
                        //加载图片
                    }
//                    if(!TextUtils.isEmpty(response.data.getGpuDiscernResult().toString())){
//                        //GPU识别结果
//                    }
                    if(!TextUtils.isEmpty(response.data.getAvgPlay() + "")){
                        //日均播放量
                        txt_orderdetail_bfl.setText(response.data.getAvgPlay() + "");
                    }
                    if(!TextUtils.isEmpty(response.data.getAvgNumber() + "")){
                        //日均曝光量
                        txt_orderdetail_bgl.setText(response.data.getAvgNumber() + "");
                    }
                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(mContext, msg);
                }
            };
            BannerRetrofitUtil.getInstance().getOrderDetail(body, new BannerProgressSubscriber<BannerBaseResponse<OrderDetailInfo>>(mListener, mContext, true));
        }
    };

    Runnable getCommunityDwDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pid", pid);
            map.put("communityPid", selectCommunityPid(txt_orderdetail_community.getText().toString().trim()));
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<List<CommunityDWInfos>>>() {

                @Override
                public void onNext(BannerBaseResponse<List<CommunityDWInfos>> response) {
                    if(response.data != null){

                        communityNameDwLists = response.data;
                        if(communityNameDwLists.size() > 0){
                            txt_orderdetail_communitydw.setText(communityNameDwLists.get(0).getDevicePosition());
                        }

                        for (int i = 0; i < communityNameDwLists.size(); i ++){
                            parmsCommunityDws.add(communityNameDwLists.get(i).getDevicePosition());
                        }

                        getDatasHandler.sendEmptyMessage(2);
                    }
                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(mContext, msg);
                }
            };
            BannerRetrofitUtil.getInstance().getDwLists(body, new BannerProgressSubscriber<BannerBaseResponse<List<CommunityDWInfos>>>(mListener, mContext, true));
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

    /**
     * 通过名称得到社区点位id
     * @param trim
     * @return
     */
    private String selectCommunityDwPid(String trim) {
        for (int i = 0; i < communityNameDwLists.size(); i ++){
            if(communityNameDwLists.get(i).getDevicePosition().equals(trim)){
                return communityNameDwLists.get(i).getPid() + "";
            }
        }
        return "";
    }

    Handler getDatasHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                //下一步
                getDatasHandler.post(getCommunityDwDatas);
            }else if(msg.what == 2){
                getDatasHandler.post(getOrderDetailDatas);
            }
        }
    };

    private void initView() {
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        if(!TextUtils.isEmpty(pid)){
            getDatasHandler.post(getCommunityDatas);
        }

        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        txt_orderdetail_time.setText(year + "-" + month + "-" + day);

        //加粗
        txt_orderdetail_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_orderdetail_info.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        txt_broadcasenumber.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @OnClick({R.id.img_orderdetail_back, R.id.rel_orderdetail_sqname, R.id.rel_orderdetail_ggdw, R.id.rel_orderdetail_jctime, R.id.orderdetail_txt_detail})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_orderdetail_back){
            finish();
        }else if(temdId == R.id.rel_orderdetail_sqname){
            if(parmsCommunitys.size() == 0) return;
            //全部监播统计
            new CommomDialog(OrderDetailActivity.this, R.style.dialog, parmsCommunitys, new CommomDialog.OnCloseListenerParmes() {

                @Override
                public void onClickParmes(Dialog dialog, String content, String str) {
                    if(content.equals("ok")){
                        dialog.dismiss();
                        BannerLog.d("b_cc", "选择的是" + str);
                        txt_orderdetail_community.setText(str);
                        //修改
                        getDatasHandler.post(getCommunityDwDatas);
                    }
                }
            }, 6).show();
        }else if(temdId == R.id.rel_orderdetail_ggdw){
            //广告点位
            if(parmsCommunityDws.size() == 0) return;
            //全部监播统计
            new CommomDialog(OrderDetailActivity.this, R.style.dialog, parmsCommunityDws, new CommomDialog.OnCloseListenerParmes() {

                @Override
                public void onClickParmes(Dialog dialog, String content, String str) {
                    if(content.equals("ok")){
                        dialog.dismiss();
                        BannerLog.d("b_cc", "选择的是" + str);

                        //修改
                        txt_orderdetail_communitydw.setText(str);

                        //获取详情
                        getDatasHandler.post(getOrderDetailDatas);
                    }
                }
            }, 6).show();
        }else if(temdId == R.id.rel_orderdetail_jctime){
            //监测时间
            chooseTime();
        }else if(temdId == R.id.orderdetail_txt_detail){
            //进入统计详情
            Intent intent = new Intent(mContext, StatisticDetailActivity.class);
            intent.putExtra("pid", pid + "");
            intent.putExtra("communityPid", selectCommunityPid(txt_orderdetail_community.getText().toString().trim()) + "");
            startActivity(intent);
        }
    }

    /**
     * 选择日期
     */
    private void chooseTime() {
        Calendar now = Calendar.getInstance();
        new android.app.DatePickerDialog(mContext, new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "你选择的是：" + +year + "年" + (++month) + "月" + dayOfMonth +"日";
                txt_orderdetail_time.setText(year + "-" + (++month) + "-" + dayOfMonth);
                BannerLog.d("b_cc", date);
                getDatasHandler.post(getOrderDetailDatas);
            }
        },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        ).show();
    }


}
