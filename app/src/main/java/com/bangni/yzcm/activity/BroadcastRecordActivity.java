package com.bangni.yzcm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.adapter.RecordAdapter;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.ToastUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 监播记录界面
 */
public class BroadcastRecordActivity extends BannerActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.rv_record_list)
    RecyclerView rv_record_list;

    @BindView(R.id.record_swipeRefreshLayout)
    RefreshLayout record_swipeRefreshLayout;

    RecordAdapter recordAdapter;


    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_record);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        initView();
    }

    private void initView() {
        rv_record_list.setLayoutManager(new LinearLayoutManager(BroadcastRecordActivity.this));
        recordAdapter = new RecordAdapter(BroadcastRecordActivity.this, null);
        rv_record_list.setAdapter(recordAdapter);

        /**
         * 点击事件
         */
        recordAdapter.setLinster(new RecordAdapter.ItemOnClickLinster() {
            @Override
            public void textItemOnClick(View view, int position) {
                startActivity(new Intent(BroadcastRecordActivity.this, BroadcastActivity.class));
            }
        });

        record_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "下拉刷新更多");
                record_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
            }
        });

        record_swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "上拉加载更多");
                //如果有更多数据

            }
        });
    }

    @OnClick({R.id.img_record_back, R.id.txt_choosetime})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_record_back){
            finish();
        }else if(temdId == R.id.txt_choosetime){
            //选择日期
            chooseTime();
        }
    }

    /**
     * 选择日期
     */
    private void chooseTime() {
        Calendar now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    BroadcastRecordActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    BroadcastRecordActivity.this,
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
