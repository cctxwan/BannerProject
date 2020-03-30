package com.bangni.yzcm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.adapter.CommunityAdapter;
import com.bangni.yzcm.adapter.RecordAdapter;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 监播记录界面
 */
public class BroadcastRecordActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.rv_record_list)
    RecyclerView rv_record_list;

    @BindView(R.id.record_swipeRefreshLayout)
    RefreshLayout record_swipeRefreshLayout;

    RecordAdapter recordAdapter;

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
        rv_record_list.setLayoutManager(new LinearLayoutManager(mContext));
        recordAdapter = new RecordAdapter(mContext, null);
        rv_record_list.setAdapter(recordAdapter);

        /**
         * 点击事件
         */
        recordAdapter.setLinster(new RecordAdapter.ItemOnClickLinster() {
            @Override
            public void textItemOnClick(View view, int position) {
                startActivity(new Intent(mContext, BroadcastActivity.class));
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

    @OnClick({R.id.img_record_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_record_back){
            finish();
        }
    }
}
