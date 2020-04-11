package com.bangni.yzcm.activity;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.bangni.yzcm.network.bean.UserFeedBookBean;
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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
        addActivity(this);
//        initData();
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
                    if(response.data != null){
                        if(!TextUtils.isEmpty(response.data.getCommunityName())){
                            txt_static_communityname.setText(response.data.getCommunityName());
                        }

                        if(!TextUtils.isEmpty(response.data.getPointNum())){
                            txt_static_communitynamedw.setText(response.data.getPointNum() + "个");
                        }

                        if(response.data.getCumulativeDischarge() != null){
                            List<Float> dataFloat1 = new ArrayList<>();
                            Map<String, Integer> map1 = new HashMap<>();
//                            map1.put("1", response.data.getCumulativeDischarge().get_$11());
//                            map1.put("2", response.data.getElevatorExceptionStatistics().get_$2());
//                            map1.put("3", response.data.getElevatorExceptionStatistics().get_$3());
//                            map1.put("4", response.data.getElevatorExceptionStatistics().get_$4());
//                            map1.put("5", response.data.getElevatorExceptionStatistics().get_$5());
//                            map1.put("6", response.data.getElevatorExceptionStatistics().get_$6());
//                            map1.put("7", response.data.getElevatorExceptionStatistics().get_$7());
//                            map1.put("8", response.data.getElevatorExceptionStatistics().get_$8());
//                            map1.put("9", response.data.getElevatorExceptionStatistics().get_$9());
//                            map1.put("10", response.data.getElevatorExceptionStatistics().get_$10());
//                            map1.put("11", response.data.getElevatorExceptionStatistics().get_$11());
//                            map1.put("12", response.data.getElevatorExceptionStatistics().get_$12());
//                            for (int i = 0; i < map1.size(); i++) {
//                                dataFloat1.add(new BigDecimal(map1.get(String.valueOf(i + 1))).floatValue());
//                            }
//                            initOneChat(12, dataFloat1);
//                        }else {
//                            List<Float> dataFloat1 = new ArrayList<>();
//                            for (int i = 0; i < 12; i++) {
//                                dataFloat1.add((float) (i * 1) * 12 - i);
//                            }
//                            initOneChat(12, dataFloat1);
                        }else{
                            //默认为0.0f
                            List<Float> dataFloat = new ArrayList<>();
                            for (int i = 0; i < 24; i++) {
                                dataFloat.add(0.0f);
                            }
                            setData(24, dataFloat);
                        }

                        if(!TextUtils.isEmpty(response.data.getCumulativePlay().toString())){
                            //给柱状图赋值


                        }else{
                            List<Float> dataFloat1 = new ArrayList<>();
                            for (int i = 0; i < 12; i++) {
                                dataFloat1.add((float) (i * 1) * 12-i);
                            }
                            initOneChat(12, dataFloat1);
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
                        //修改
                        txt_choosesq.setText(str);
                        handler.post(getCommunityDatas);
                    }
                }
            }, 6).show();
        }
    }


    //默认数据
    private void initData() {



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
            xAxis.setLabelCount(13, true); //x轴长度，并是否自适配
            xAxis.setDrawGridLines(true);//绘制格网线
            xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
            xAxis.setLabelCount(13);//设置x轴显示的标签个数
            xAxis.setAxisLineWidth(1f);//设置x轴宽度, ...其他样式


            //设置X轴网格背景的颜色
            xAxis.setGridColor(Color.parseColor("#00000000"));
            //设置X轴字体的颜色
            xAxis.setTextColor(Color.parseColor("#798194"));

            //格式化显示数据
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    //这里是我自己的逻辑
                    BannerLog.d("b_cc", "=====" + value);
                    int time;
                    time = (int) value;
                    if(time/2  != 0){
                        time += 1;
                    }
                    if(time == 1) time = 2;
                    if(time == 23) time = 24;
                    return time+ "点";
                }
            });
        }



        //Y轴
        YAxis yAxis;
        {
            yAxis = ll_chart.getAxisLeft();  //获取Y轴

            ll_chart.getAxisRight().setEnabled(false);  //是否显示左边Y轴网格背景线
            yAxis.setLabelCount(5, true);  //设置Y轴的个数和自适应
            yAxis.setDrawGridLines(true);  //绘制y轴格网线
            yAxis.setDrawZeroLine(true);   //绘制0线


            yAxis.setTextColor(Color.parseColor("#ffffff"));
            yAxis.setAxisMaximum(100f);
            yAxis.setAxisMinimum(0f);
            yAxis.setDrawLabels(false);//绘制标签  指轴上的对应数值不显示

        }


        ll_chart.animateX(1500);//x轴方向动画
    }

    private void setData(int count, List<Float> range) {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            values.add(new Entry(i, range.get(i), null));
        }


        ll_chart.setTouchEnabled(false);
        XAxis xAxis = ll_chart.getXAxis();


        //设置X轴的位置（默认在上方）：
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setLabelCount(13, true);
        //启用X轴
        xAxis.setEnabled(true);
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Color.RED);
        xAxis.setDrawGridLines(false);
        //设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        xAxis.setAvoidFirstLastClipping(true);


        final YAxis yAxis = ll_chart.getAxisLeft();

//        //是否启用绘制零线:设置为true后才有后面的操作
//        yAxis.setDrawZeroLine(true);
//        //设置绘制零线宽度
//        yAxis.setZeroLineWidth(1f);
//        //绘制零线颜色
//        yAxis.setZeroLineColor(Color.parseColor("#4876FD"));

        if (Collections.max(range) == 0) {
            yAxis.setAxisMaximum(100f);
        } else {
            yAxis.setAxisMaximum(100f);
        }



        LineDataSet set1;
        if (ll_chart.getData() != null &&
                ll_chart.getData().getDataSetCount() > 0) {
//            chart.clearValues();

            ll_chart.setScaleMinima(1.0f, 1.0f);

            ll_chart.getViewPortHandler().refresh(new Matrix(), ll_chart, true);


            set1 = (LineDataSet) ll_chart.getData().getDataSetByIndex(0);


            set1.setValues(values);
            set1.notifyDataSetChanged();


            // redraw
            ll_chart.invalidate();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "02");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            set1.setDrawIcons(false);

            // black lines and points
            set1.setLineWidth(2f);
            set1.setColor(Color.parseColor("#4876FD"));// 链接线颜色
            set1.setDrawFilled(true);
            set1.setDrawValues(true);
            set1.setCircleHoleRadius(0f);
            set1.setDrawCircleHole(false);//设置小圆点是否空心
            set1.setDrawCircles(false);//显示小圆心
            set1.setHighLightColor(Color.parseColor("#00000000")); // 设置点击某个点时，横竖两条线的颜色
            set1.setValueTextSize(9f);
            set1.setValueTextColor(Color.parseColor("#798194"));
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setDrawValues(false);//不显示当前数值

            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return ll_chart.getAxisLeft().getAxisMinimum();
                }
            });


            set1.setFillColor(Color.WHITE);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            // create a data object with the data sets
            LineData data = new LineData(dataSets);
            // set data
            ll_chart.setData(data);//设置数据
        }
    }

    private void initOneChat(final int count, List<Float> range) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, range.get(i)));
        }
        // y 轴数据集
        final BarDataSet barDataSet = new BarDataSet(yValues, "柱状图");



        ////设置柱子的颜色
        barDataSet.setColor(Color.parseColor("#1D65FF"));

        barDataSet.setValueTextColor(getResources().getColor(R.color.transparent));//设置柱子上值 de颜色

//        int startColor2 = ContextCompat.getColor(parent, R.color.start);
//        int endColor2 = ContextCompat.getColor(parent, R.color.end);
//
//        List<GradientColor> gradientColors2 = new ArrayList<>();
//        gradientColors2.add(new GradientColor(startColor2, endColor2));

        //点击之后的颜色
//        barDataSet.setHighLightColor(Color.RED);
        barDataSet.setHighLightAlpha(0);

        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "";
            }
        });

        BarData mBarData = new BarData(barDataSet);
        mBarData.setDrawValues(true);//显示柱子当前数值

        bf_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("CCCC_CCCC", "=============" + h);
                Log.d("CCCC_CCCC", "=============" + e);
//                h.getY()
                for (int j = 1; j < count; j ++){

                }
//                if(count)
            }

            @Override
            public void onNothingSelected() {

            }
        });

        bf_chart.setNoDataText("暂无数据");
        bf_chart.setData(mBarData);
        // 设置 是否可以缩放
        bf_chart.setScaleEnabled(false);
        //设置阴影
        bf_chart.setDrawBarShadow(false);

        bf_chart.setTouchEnabled(true);// 设置是否可以触摸
//        mBarChart.setDrawValueAboveBar(true);//柱状图上面的数值显示在柱子上面还是柱子里面
        bf_chart.animateXY(2000, 3000);
        // 设置 柱子的宽度
        mBarData.setBarWidth(0.5f);
        // 获取 x 轴
        XAxis xAxis = bf_chart.getXAxis();
        // 隐藏下边X轴
        xAxis.setDrawAxisLine(false);
        // 设置 x 轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 取消 垂直 网格线
        xAxis.setDrawGridLines(false);
        // 设置 x 轴 坐标旋转角度
//        xAxis.setLabelRotationAngle(10f);
        // 设置 x 轴 坐标字体大小
        xAxis.setTextSize(10f);
        xAxis.setTextColor(getResources().getColor(R.color.colorAccent));
        // 设置 x 坐标轴 颜色
//        xAxis.setAxisLineColor(0xFFBA7858);
        // 设置 x 坐标轴 宽度
        xAxis.setAxisLineWidth(1f);
        // 设置 x轴 的刻度数量
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((int) value + 1) + "月";
            }
        });

        // 获取 右边 y 轴
        YAxis mRAxis = bf_chart.getAxisRight();
        // 隐藏 右边 Y 轴
        mRAxis.setEnabled(false);

        // 获取 左边 Y轴
        YAxis mLAxis = bf_chart.getAxisLeft();
//        // 显示  左边 Y轴 坐标线
        mLAxis.setDrawAxisLine(false);
        // 设置 Y 坐标轴 颜色
//        mLAxis.setAxisLineColor(0xFFBA7858);
        mLAxis.setTextColor(getResources().getColor(R.color.white));
//        mLAxis.setAxisMaximum(Collections.max(range) > 0 ? Collections.max(range) : 10f);
        mLAxis.setAxisMaxValue(maxXValue(range));//计算Y轴最大值
        mLAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("Y轴数据为：", String.valueOf(value));
                return itemXValue(value, true);
            }
        });
        mLAxis.setAxisMinimum(0f);
        // 设置 x 坐标轴 宽度
        mLAxis.setAxisLineWidth(1f);
        // 显示 横向 网格线
        mLAxis.setDrawGridLines(true);
        //网格颜色
        mLAxis.setGridColor(0x1AFFFFFF);
        // 设置 Y轴 的刻度数量
        mLAxis.setLabelCount(6, true);
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
            BannerLog.d("b_cc", e.toString());
            maxFloat = 50;
        }
        return maxFloat;
    }

    private String itemXValue(float value, boolean reall) {
        String valueStr = "";
        if (value > 999 && value < 10000) {
            valueStr = (value / 1000.f) + "k";
        } else {
            if(reall){
                valueStr = ((int) value) + "";
            }else {
                valueStr = value + "";
            }
        }
        if (value > 9999 && value < 100000) {
            valueStr = value / 10000.f + "w";
        }
        return valueStr;
    }

}
