package com.bangni.yzcm.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.network.bean.CommunityInfos;
import com.bangni.yzcm.network.bean.StatisitcsInfos;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.ToastUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @BindView(R.id.ll_chart)
    LineChart ll_chart;

    @BindView(R.id.bf_chart)
    BarChart bf_chart;

    @BindView(R.id.txt_static_communityname)
    TextView txt_static_communityname;

    @BindView(R.id.txt_static_communitynamedw)
    TextView txt_static_communitynamedw;

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
        //设置默认折线图数据
        initAvgPlayData();
        //设置默认柱状图数据
        initAvgNumberData();

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

                    //成功，继续下一个请求
                    handler.sendEmptyMessage(1);

                    if(response.data != null){
                        communityNameLists = response.data;

//                        if(communityNameLists.size() > 0){
//                            if(txt_static_communityname.getText().toString().trim().equals("全部")){
//                                txt_static_communityname.setText(communityNameLists.get(0).getCommunityName());
//                            }
//                        }

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
            map.put("communityPid", selectCommunityPid(txt_static_communityname.getText().toString().trim()));
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<StatisitcsInfos>>() {

                @Override
                public void onNext(BannerBaseResponse<StatisitcsInfos> response) {
                    if(response.data != null){
                        StatisitcsInfos infos = response.data;
                        if(!TextUtils.isEmpty(infos.getCommunityName())){
                            txt_static_communityname.setText(infos.getCommunityName());
                        }

                        if(!TextUtils.isEmpty(infos.getPointNum())){
                            txt_static_communitynamedw.setText(infos.getPointNum() + "个");
                        }

                        if(infos.getCumulativeDischarge() != null){
                            //给柱状图赋值
                            List<String> keyName = new ArrayList<>();
                            List<Float> dataFloat1 = new ArrayList<>();
                            Map<String, Integer> map1 = infos.getCumulativeDischarge();
                            Set<String> keys = map1.keySet();
                            for(String key : keys){
                                System.out.println("key值：" + key + " value值：" + map1.get(key));
                                keyName.add(key);
                                dataFloat1.add((float)map1.get(key));
                            }
                            setData(keyName, dataFloat1);
                        }else{
                            //默认为0.0f
                            BannerLog.d("b_cc", "折线图为空");
                        }

                        if(infos.getCumulativePlay() != null){
                            //给柱状图赋值
                            List<String> keyName = new ArrayList<>();
                            List<Float> dataFloat1 = new ArrayList<>();
                            Map<String, Integer> map1 = infos.getCumulativePlay();
                            Set<String> keys = map1.keySet();
                            for(String key : keys){
                                System.out.println("key值：" + key + " value值：" + map1.get(key));
                                keyName.add(key);
                                dataFloat1.add((float)map1.get(key));
                            }
                            initOneChat(keyName, dataFloat1);

                        }else{
                            //默认为0.0f
                            BannerLog.d("b_cc", "柱状图为空");
                        }
                    }
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
                        parmsCommunitys.clear();
                        //修改
                        txt_static_communityname.setText(str);
//                        txt_choosesq.setText(str);
                        handler.post(getCommunityDatas);
                    }
                }
            }, 6).show();
        }
    }


    /**
     * 设置折线图默认数据
     */
    private void initAvgPlayData() {
        ll_chart.setDrawGridBackground(false);
        ll_chart.getDescription().setEnabled(false);
        ll_chart.setTouchEnabled(true);
        ll_chart.setDragEnabled(true);
        ll_chart.setScaleEnabled(true);
        ll_chart.setPinchZoom(false);
        ll_chart.getAxisLeft().setDrawGridLines(false);
        ll_chart.getAxisRight().setEnabled(false);
        ll_chart.getXAxis().setDrawGridLines(true);
        ll_chart.getXAxis().setDrawAxisLine(false);
        ll_chart.invalidate();

        //X轴
        XAxis xAxis;
        {
            xAxis = ll_chart.getXAxis();  //获取x轴
            ll_chart.getAxisLeft().setEnabled(true); //是否显示左边Y轴网格背景线
            xAxis.setLabelCount(7, true); //x轴长度，并是否自适配
            xAxis.setDrawGridLines(true);//绘制格网线
            xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
            xAxis.setAxisLineWidth(1f);//设置x轴宽度, ...其他样式
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置位置


            //设置X轴网格背景的颜色
            xAxis.setGridColor(Color.parseColor("#00000000"));
            //设置X轴字体的颜色
            xAxis.setTextColor(Color.parseColor("#798194"));

            //格式化显示数据
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    //这里是我自己的逻辑
                    return (int) value + 1 + "号";
                }
            });
        }

        //Y轴
        YAxis yAxis;
        {
            yAxis = ll_chart.getAxisLeft();  //获取Y轴
            ll_chart.getAxisRight().setEnabled(false);  //是否显示左边Y轴网格背景线
            yAxis.setLabelCount(6, true);  //设置Y轴的个数和自适应
            yAxis.setDrawGridLines(true);  //绘制y轴格网线
            yAxis.setDrawZeroLine(true);   //绘制0线
            yAxis.setTextColor(Color.parseColor("#00000000"));
            yAxis.setAxisMaximum(100f);
            yAxis.setAxisMinimum(0f);
            yAxis.setDrawLabels(false);//绘制标签  指轴上的对应数值不显示

        }
        ll_chart.animateX(1500);//x轴方向动画


        //默认为0.0f
//        List<Float> dataFloat = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            if(i == 2){
//                dataFloat.add(24.0f);
//            }else if (i == 4){
//                dataFloat.add(36.0f);
//            }else if (i == 6){
//                dataFloat.add(17.0f);
//            }else if (i == 8){
//                dataFloat.add(50.0f);
//            }else if (i == 10){
//                dataFloat.add(82.0f);
//            }else if (i == 12){
//                dataFloat.add(12.0f);
//            }else if (i == 19){
//                dataFloat.add(23.0f);
//            }else if (i == 21){
//                dataFloat.add(20.0f);
//            }else if (i == 24){
//                dataFloat.add(40.0f);
//            }else{
//                dataFloat.add(0.0f);
//            }
//        }
//        setData(100, dataFloat);
    }

    int i = 0;
    /**
     * 设置折线图的数据
     * @param count
     * @param range
     */
    private void setData(List<String> count, List<Float> range) {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < count.size(); i++) {
            values.add(new Entry(i, range.get(i), null));
        }

        //只执行一次
        if(i==0){
            i = 1;
            //设置一页最大显示个数为6，超出部分就滑动
            float ratio = (float) range.size()/(float) 7;
            //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
            ll_chart.zoom(ratio,1f,0,0);
        }


        LineDataSet set1 = new LineDataSet(values, "折线图");
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawIcons(false);
        set1.setLineWidth(2f);
        set1.setColor(Color.parseColor("#4876FD"));// 链接线颜色
        set1.setDrawFilled(true);
        set1.setDrawValues(true);
        set1.setCircleHoleRadius(0f);
        set1.setDrawCircleHole(true);//设置小圆点是否空心
        set1.setDrawCircles(true);//显示小圆心
        set1.setCircleHoleColor(Color.parseColor("#DFE93C"));  //设置节点圆心的颜色
        set1.setCircleColor(Color.parseColor("#ffffff"));  //可以设置Entry节点的颜色
        set1.setHighLightColor(Color.parseColor("#00000000")); // 设置点击某个点时，横竖两条线的颜色
        set1.setValueTextSize(9f);
        set1.setValueTextColor(Color.parseColor("#798194"));
        set1.setDrawValues(false);//不显示当前数值

        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return ll_chart.getAxisLeft().getAxisMinimum();
            }
        });
        set1.setFillColor(Color.WHITE);

        LineData mBarData = new LineData(set1);
        mBarData.setDrawValues(true);//显示柱子当前数值
        ll_chart.setNoDataText("暂无数据");
        ll_chart.setData(mBarData);
        // 设置 是否可以缩放
        ll_chart.setScaleEnabled(false);
        ll_chart.setTouchEnabled(true);// 设置是否可以触摸
//        barChartTow.setDrawValueAboveBar(true);//柱状图上面的数值显示在柱子上面还是柱子里面
        ll_chart.animateXY(2000, 3000);
        ll_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                e.getX();       //X轴坐标 记得转 int
                e.getY();       //当前柱状图Y轴值
                e.getIcon();    //对应 BarEntry(float x, float y, Drawable icon)
                e.getData();    //对应 BarEntry(float x, float y, Object data)
            }

            @Override
            public void onNothingSelected() {
            }
        });

        // 获取 x 轴
        XAxis xAxis = ll_chart.getXAxis();
        // 设置 x 轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 网格线
        xAxis.setDrawGridLines(true);
        // 设置 x 轴 坐标旋转角度
//        xAxis.setLabelRotationAngle(10f);
        // 设置 x 轴 坐标字体大小
        xAxis.setTextSize(10f);
        xAxis.setTextColor(getResources().getColor(R.color.aaaa));
        // 设置 x 坐标轴 颜色
        xAxis.setAxisLineColor(getResources().getColor(R.color.aaaa));
        // 设置 x 坐标轴 宽度
        xAxis.setAxisLineWidth(1f);
        // 设置 x轴 的刻度数量
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return count.get((int)value);
            }
        });

        // 获取 左边 Y轴
        YAxis mLAxis = ll_chart.getAxisLeft();
        // 显示  左边 Y轴 坐标线
        mLAxis.setDrawAxisLine(true);
        // 设置 x 坐标轴 宽度
        mLAxis.setAxisLineWidth(0.5f);
        // 显示 横向 网格线
        mLAxis.setDrawGridLines(true);

        // 设置 Y 坐标轴 颜色
        mLAxis.setAxisLineColor(getResources().getColor(R.color.aaaa));
        mLAxis.setTextColor(getResources().getColor(R.color.aaaa));
//        mLAxis.setAxisMaximum(Collections.max(range) > 0 ? Collections.max(range) : 10f);
        mLAxis.setAxisMaxValue(maxXValue(range));//计算Y轴最大值
        mLAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                if(isSafety){
                return itemXValue(value, true);
//                }else{
//                    return itemXValue(value, false);
//                }
            }
        });

        mLAxis.setAxisMinimum(0f);
        // 设置 x 坐标轴 宽度
        mLAxis.setAxisLineWidth(1f);
        // 设置 Y轴 的刻度数量
        mLAxis.setLabelCount(6, true);


    }


    /**
     * 设置柱状图默认数据
     */
    private void initAvgNumberData() {
        bf_chart.setDrawGridBackground(false);
        bf_chart.getDescription().setEnabled(false);
        bf_chart.setTouchEnabled(true);
        bf_chart.setDragEnabled(true);
        bf_chart.setScaleEnabled(true);
        bf_chart.setPinchZoom(false);
        bf_chart.getAxisLeft().setDrawGridLines(false);
        bf_chart.getAxisRight().setEnabled(false);
        bf_chart.getXAxis().setDrawGridLines(true);
        bf_chart.getXAxis().setDrawAxisLine(false);
        bf_chart.invalidate();
        //设置一页最大显示个数为6，超出部分就滑动

        //X轴
        XAxis xAxis;
        {
            xAxis = bf_chart.getXAxis();  //获取x轴
            bf_chart.getAxisLeft().setEnabled(true); //是否显示左边Y轴网格背景线
            xAxis.setLabelCount(7, true); //x轴长度，并是否自适配
            xAxis.setDrawGridLines(true);//绘制格网线
            xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
            xAxis.setLabelCount(7);//设置x轴显示的标签个数
            xAxis.setAxisLineWidth(1f);//设置x轴宽度, ...其他样式


            //设置X轴网格背景的颜色
            xAxis.setGridColor(Color.parseColor("#00000000"));
            //设置X轴字体的颜色
            xAxis.setTextColor(Color.parseColor("#798194"));

            //格式化显示数据
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return "";
                }
            });
        }

        //Y轴
        YAxis yAxis;
        {
            yAxis = bf_chart.getAxisLeft();  //获取Y轴
            bf_chart.getAxisRight().setEnabled(false);  //是否显示左边Y轴网格背景线
            yAxis.setLabelCount(6, true);  //设置Y轴的个数和自适应
            yAxis.setDrawGridLines(true);  //绘制y轴格网线
            yAxis.setDrawZeroLine(true);   //绘制0线
            yAxis.setTextColor(Color.parseColor("#00000000"));
            yAxis.setAxisMaximum(100f);
            yAxis.setAxisMinimum(0f);
            yAxis.setDrawLabels(false);//绘制标签  指轴上的对应数值不显示

        }
        bf_chart.animateX(1500);//x轴方向动画


        List<Float> dataFloat1 = new ArrayList<>();
//        dataFloat1.add((float) 12);
//        dataFloat1.add((float) 32);
//        dataFloat1.add((float) 45);
//        dataFloat1.add((float) 12);
//        dataFloat1.add((float) 81);
//        dataFloat1.add((float) 21);
//        dataFloat1.add((float) 15);
//        dataFloat1.add((float) 0);
//        dataFloat1.add((float) 0);
//        dataFloat1.add((float) 54);
//        dataFloat1.add((float) 12);
//        dataFloat1.add((float) 2);
        for (int i = 0; i < 100; i ++){
            dataFloat1.add((float) 0);
        }
    }

    /**
     * 动态设置柱状图数据
     * @param count
     * @param range
     */
    private void initOneChat(List<String> count, List<Float> range) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count.size(); i++) {
            yValues.add(new BarEntry(i, range.get(i)));
        }
        // y 轴数据集
        BarDataSet barDataSet = new BarDataSet(yValues, "柱状图");
        barDataSet.setColor(Color.parseColor("#1D65FF"));//设置柱子颜色
        barDataSet.setValueTextColor(getResources().getColor(R.color.aaaa));//设置柱子上值得颜色


        BarData mBarData = new BarData(barDataSet);
        mBarData.setDrawValues(true);//显示柱子当前数值
        bf_chart.setNoDataText("暂无数据");
        bf_chart.setData(mBarData);
        // 设置 是否可以缩放
        bf_chart.setScaleEnabled(false);
        bf_chart.setTouchEnabled(true);// 设置是否可以触摸
//        barChartTow.setDrawValueAboveBar(true);//柱状图上面的数值显示在柱子上面还是柱子里面
        bf_chart.animateXY(2000, 3000);
        bf_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                e.getX();       //X轴坐标 记得转 int
                e.getY();       //当前柱状图Y轴值
                e.getIcon();    //对应 BarEntry(float x, float y, Drawable icon)
                e.getData();    //对应 BarEntry(float x, float y, Object data)
            }

            @Override
            public void onNothingSelected() {
            }
        });
        // 设置 柱子的宽度
        mBarData.setBarWidth(0.5f);
        // 获取 x 轴
        XAxis xAxis = bf_chart.getXAxis();
        // 设置 x 轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 网格线
        xAxis.setDrawGridLines(true);
        // 设置 x 轴 坐标旋转角度
//        xAxis.setLabelRotationAngle(10f);
        // 设置 x 轴 坐标字体大小
        xAxis.setTextSize(10f);
        xAxis.setTextColor(getResources().getColor(R.color.aaaa));
        // 设置 x 坐标轴 颜色
        xAxis.setAxisLineColor(getResources().getColor(R.color.aaaa));
        // 设置 x 坐标轴 宽度
        xAxis.setAxisLineWidth(1f);
        // 设置 x轴 的刻度数量
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return count.get((int)value);
            }
        });

        // 获取 右边 y 轴
        YAxis mRAxis = bf_chart.getAxisRight();
        // 隐藏 右边 Y 轴
        mRAxis.setEnabled(false);

        // 获取 左边 Y轴
        YAxis mLAxis = bf_chart.getAxisLeft();
        // 显示  左边 Y轴 坐标线
        mLAxis.setDrawAxisLine(true);
        // 设置 x 坐标轴 宽度
        mLAxis.setAxisLineWidth(0.5f);
        // 显示 横向 网格线
        mLAxis.setDrawGridLines(true);

        // 设置 Y 坐标轴 颜色
        mLAxis.setAxisLineColor(getResources().getColor(R.color.aaaa));
        mLAxis.setTextColor(getResources().getColor(R.color.aaaa));
//        mLAxis.setAxisMaximum(Collections.max(range) > 0 ? Collections.max(range) : 10f);
        mLAxis.setAxisMaxValue(maxXValue(range));//计算Y轴最大值
        mLAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                if(isSafety){
                    return itemXValue(value, true);
//                }else{
//                    return itemXValue(value, false);
//                }
            }
        });

        mLAxis.setAxisMinimum(0f);
        // 设置 x 坐标轴 宽度
        mLAxis.setAxisLineWidth(1f);
        // 设置 Y轴 的刻度数量
        mLAxis.setLabelCount(6, true);
    }




    private String itemXValue(float value, boolean reall) {
        String valueStr = "";
        if (value > 999 && value < 10000) {
            valueStr = (value / 1000.f) + "k";
        } else {
            if(reall){
                valueStr = ((int) value) + "";
            }else {
                valueStr = (int) value + "";
            }
        }
        if (value > 9999 && value < 100000) {
            valueStr = value / 10000.f + "w";
        }
        return valueStr;
    }

    private float maxXValue(List<Float> range) {
        float maxFloat = 0;
        try {
            if (Collections.max(range) < 1000) {
                maxFloat = (((int) (Collections.max(range) / 50) + 1) * 50);
            } else if (Collections.max(range) >= 1000 && Collections.max(range) < 9999) {
                maxFloat = (((int) (Collections.max(range) / 500) + 1) * 500);
            } else if (Collections.max(range) >= 10000 && Collections.max(range) < 99999) {
                maxFloat = (((int) (Collections.max(range) / 5000) + 1) * 5000);
            } else {
                maxFloat = (((int) (Collections.max(range) / 50) + 1) * 50);
            }
        } catch (Exception e) {
            BannerLog.d("chart", e.toString());
            maxFloat = 50;
        }
        return maxFloat;
    }

}
