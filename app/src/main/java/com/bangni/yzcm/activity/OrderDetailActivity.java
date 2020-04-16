package com.bangni.yzcm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.network.bean.CommunityDWInfos;
import com.bangni.yzcm.network.bean.CommunityInfos;
import com.bangni.yzcm.network.bean.OrderDetailInfo;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerUtils;
import com.bangni.yzcm.utils.ToastUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @BindView(R.id.lin_orderdetail_titlebg)
    LinearLayout lin_orderdetail_titlebg;

    @BindView(R.id.txt_orderdetail_titletv)
    TextView txt_orderdetail_titletv;

    @BindView(R.id.lin_gpuresult)
    LinearLayout lin_gpuresult;

    @BindView(R.id.orderdetail_txt_detail)
    TextView orderdetail_txt_detail;

    //时间控件
    private TimePickerView pvTime;

    //订单id， 开始时间， 结束时间
    private String pid, startTime, endTime;
    //订单状态
    private int state;

    //设置时间控件的开始和结束时间
    private Calendar startDate = Calendar.getInstance(), endDate = Calendar.getInstance();

    //社区名称
    List<CommunityInfos> communityNameLists = new ArrayList<>();
    List<String> parmsCommunitys = new ArrayList<>();

    //社区点位
    List<CommunityDWInfos> communityNameDwLists = new ArrayList<>();
    List<String> parmsCommunityDws = new ArrayList<>();


    //默认选择当前日期的时间戳
    String stringToDate = System.currentTimeMillis() + "";

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
                    //成功，继续下一个请求
                    getDatasHandler.sendEmptyMessage(1);

                    if(response.data != null){



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
                    if(msg.equals("接口出现问题")){
                        setResult(111);
                        finish();
                    }else{
                        ToastUtils.error(mContext, msg);
                    }
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
            map.put("monitorTime", stringToDate);//txt_orderdetail_time.getText().toString().trim()
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<OrderDetailInfo>>() {

                @Override
                public void onNext(BannerBaseResponse<OrderDetailInfo> response) {
//                    if(response.data.getStatus() == 2){
//                        lin_orderdetail_titlebg.setBackgroundResource(R.drawable.blue_orderdetail_bg);
//                        txt_orderdetail_title.setTextColor(Color.parseColor("#1D65FF"));
//                        txt_orderdetail_titletv.setText("正在进行中...");
//                    }else if(response.data.getStatus() == 3){
//                        lin_orderdetail_titlebg.setBackgroundResource(R.drawable.grey_orderdetail_bg);
//                        txt_orderdetail_title.setTextColor(Color.parseColor("#919194"));
//                        txt_orderdetail_titletv.setText("已结束");
//                    }else{
//                        lin_orderdetail_titlebg.setBackgroundResource(R.drawable.yellew_orderdetail_bg);
//                        txt_orderdetail_title.setTextColor(Color.parseColor("#FFB944"));
//                        txt_orderdetail_titletv.setText("未开始...");
//                    }

                    //图片
                    if(!TextUtils.isEmpty(response.data.getMonitorImage())){
                        Glide.with(mContext).load(response.data.getMonitorImage()).error(R.mipmap.orderdetail_url).into(img_orderdetail_url);
                    }else{
                        Glide.with(mContext).load(R.mipmap.orderdetail_url).error(R.mipmap.orderdetail_url).into(img_orderdetail_url);
                    }

                    //显示gpu识别
                    if(response.data.getGpuDiscernResult() != null){
                        //显示
                        lin_gpuresult.setVisibility(View.GONE);
                    }else if(response.data.getGpuDiscernResult() == null){
                        lin_gpuresult.setVisibility(View.GONE);
                    }else{
                        lin_gpuresult.setVisibility(View.GONE);
                    }


                    if(!TextUtils.isEmpty(response.data.getMonitorImage())){
                        //加载图片
                    }
//                    if(!TextUtils.isEmpty(response.data.getGpuDiscernResult().toString())){
//                        //GPU识别结果
//                    }
                    if(response.data.getAvgPlay() > 0){
                        //日均播放量
                        txt_orderdetail_bfl.setText(response.data.getAvgPlay() + "次");
                    }else{
                        //日均曝光量
                        txt_orderdetail_bgl.setText("0次");
                    }

                    if(response.data.getAvgNumber() > 0){
                        //日均曝光量
                        txt_orderdetail_bgl.setText(response.data.getAvgNumber() + "次");
                    }else{
                        //日均曝光量
                        txt_orderdetail_bgl.setText("0次");
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

                    getDatasHandler.sendEmptyMessage(2);

                    if(response.data != null){

                        communityNameDwLists = response.data;
                        if(communityNameDwLists.size() > 0){
                            txt_orderdetail_communitydw.setText(communityNameDwLists.get(0).getDevicePosition());
                        }

                        for (int i = 0; i < communityNameDwLists.size(); i ++){
                            parmsCommunityDws.add(communityNameDwLists.get(i).getDevicePosition());
                        }


                    }
                }

                @Override
                public void onError(String msg) {
                    if(msg.equals("接口出现问题")){
                        setResult(111);
                        finish();
                    }else{
                        ToastUtils.error(mContext, msg);
                    }
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
                if(txt_orderdetail_community.getText().toString().trim().equals("请选择社区")) {
                    getDatasHandler.post(getOrderDetailDatas);
                }else{
                    getDatasHandler.post(getCommunityDwDatas);
                }
            }else if(msg.what == 2){
                getDatasHandler.post(getOrderDetailDatas);
            }
        }
    };

    private void initView() {

//        if(!BannerUtils.isApkInDebug(mContext, getPackageName())){
//            orderdetail_txt_detail.setVisibility(View.GONE);
//        }

        //接收传值
        Intent intent = getIntent();
        pid = intent.getExtras().getInt("pid") + "";
        state = intent.getExtras().getInt("state");
        startTime = intent.getExtras().getLong("startTime") + "";
        endTime = intent.getExtras().getLong("endTime") + "";


        //只要pid不为空，便开始请求
        if(!TextUtils.isEmpty(pid)){
            getDatasHandler.post(getCommunityDatas);
        }

        //
        if(!TextUtils.isEmpty(startTime)){
            BannerLog.d("b_cc", "开始时间是：" + BannerUtils.stampToDatesss(startTime));

            //设置控件开始时间
            startDate.set(Integer.valueOf(BannerUtils.stampToDatesss(startTime).substring(0, 4))
                    , Integer.valueOf(BannerUtils.stampToDatesss(startTime).substring(5, 7)) - 1
                    , Integer.valueOf(BannerUtils.stampToDatesss(startTime).substring(8, 10)));
        }
        if(!TextUtils.isEmpty(endTime)){
            BannerLog.d("b_cc", "结束时间是：" + BannerUtils.stampToDatesss(endTime));

            //设置控件结束时间
            endDate.set(Integer.valueOf(BannerUtils.stampToDatesss(endTime).substring(0, 4))
                    , Integer.valueOf(BannerUtils.stampToDatesss(endTime).substring(5, 7)) - 1
                    , Integer.valueOf(BannerUtils.stampToDatesss(endTime).substring(8, 10)));
        }

        //根据状态处理不同的数据
        if(state == 3){
            lin_orderdetail_titlebg.setBackgroundResource(R.drawable.grey_orderdetail_bg);
            txt_orderdetail_title.setTextColor(Color.parseColor("#919194"));
            txt_orderdetail_titletv.setText("已结束");
        }else{
            lin_orderdetail_titlebg.setBackgroundResource(R.drawable.blue_orderdetail_bg);
            txt_orderdetail_title.setTextColor(Color.parseColor("#1D65FF"));
            txt_orderdetail_titletv.setText("正在进行中...");
        }

        //实例化时间控件
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //时间戳
                stringToDate = String.valueOf(date.getTime());
                //拿到时间
                txt_orderdetail_time.setText(BannerUtils.stampToDateOnlyymd(String.valueOf(date.getTime())));
                //请求数据
                getDatasHandler.post(getOrderDetailDatas);
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {

            }
        }).setType(new boolean[]{
                true, true, true, false, false, false
        }).isDialog(true)
                .setLabel("年", "月", "日", "", "", "")
                .setRangDate(startDate == null ? Calendar.getInstance() : startDate, endDate == null ? Calendar.getInstance() : endDate)//起始终止年月日设定
                .setDate(Calendar.getInstance())
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }

        //给时间赋值当前时间
        txt_orderdetail_time.setText(BannerUtils.stampToDateOnlyymd(String.valueOf(System.currentTimeMillis())));

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
                        parmsCommunityDws.clear();
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
            if (pvTime != null) {
                pvTime.show(v);
            }
        }else if(temdId == R.id.orderdetail_txt_detail){
            //进入统计详情
            Intent intent = new Intent(mContext, StatisticDetailActivity.class);
            intent.putExtra("pid", pid + "");
            intent.putExtra("communityPid", selectCommunityPid(txt_orderdetail_community.getText().toString().trim()) + "");
            startActivity(intent);
        }
    }

}
