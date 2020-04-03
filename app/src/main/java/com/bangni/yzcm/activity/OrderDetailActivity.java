package com.bangni.yzcm.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单详情界面
 */
public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.txt_orderdetail_title)
    TextView txt_orderdetail_title;

    @BindView(R.id.txt_orderdetail_info)
    TextView txt_orderdetail_info;

    @BindView(R.id.txt_broadcasenumber)
    TextView txt_broadcasenumber;

    private DatePickerDialog dpd;

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

    @OnClick({R.id.img_orderdetail_back, R.id.rel_orderdetail_sqname, R.id.rel_orderdetail_ggdw, R.id.rel_orderdetail_jctime, R.id.orderdetail_txt_detail})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_orderdetail_back){
            finish();
        }else if(temdId == R.id.rel_orderdetail_sqname){
            //社区名称
            List<String> parms = new ArrayList<>();
            parms.add("大兴裕苑");
            parms.add("大兴景苑");
            parms.add("大兴家苑");
            parms.add("唐延中心城");
            parms.add("秦地雅仕");
            parms.add("锦园新世纪");
            parms.add("棕榈阳光");

            //全部监播统计
            new CommomDialog(OrderDetailActivity.this, R.style.dialog, parms, new CommomDialog.OnCloseListenerParmes() {

                @Override
                public void onClickParmes(Dialog dialog, String content, String str) {
                    if(content.equals("ok")){
                        dialog.dismiss();
                        BannerLog.d("b_cc", "选择的是" + str);
                        //修改
                    }
                }
            }, 6).show();
        }else if(temdId == R.id.rel_orderdetail_ggdw){
            //广告点位
            List<String> parms = new ArrayList<>();
            parms.add("1号楼中梯");
            parms.add("1号楼东梯");
            parms.add("1号楼西梯");
            parms.add("2号楼中梯");
            parms.add("2号楼东梯");
            parms.add("2号楼西梯");
            parms.add("3号楼西梯");

            //全部监播统计
            new CommomDialog(OrderDetailActivity.this, R.style.dialog, parms, new CommomDialog.OnCloseListenerParmes() {

                @Override
                public void onClickParmes(Dialog dialog, String content, String str) {
                    if(content.equals("ok")){
                        dialog.dismiss();
                        BannerLog.d("b_cc", "选择的是" + str);
                        //修改
                    }
                }
            }, 6).show();
        }else if(temdId == R.id.rel_orderdetail_jctime){
            //监测时间
            chooseTime();
        }else if(temdId == R.id.orderdetail_txt_detail){
            //进入统计详情
            startActivity(new Intent(OrderDetailActivity.this, StatisticDetailActivity.class));
        }
    }

    /**
     * 选择日期
     */
    private void chooseTime() {
        Calendar now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    OrderDetailActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    OrderDetailActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }


        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.showYearPickerFirst(true);

        dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.HORIZONTAL);

        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d("DatePickerDialog", "Dialog was cancelled");
                dpd = null;
            }
        });
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dpd = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "你选择的是：" + +year + "年" + (++monthOfYear) + "月" + dayOfMonth +"日";
        BannerLog.d("b_cc", date);
        ToastUtils.success(this, date);
        dpd = null;
    }

}
