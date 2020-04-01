package com.bangni.yzcm.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.adapter.FeedListAdapter;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 反馈记录
 */
public class FeedListActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_feedbooklist_title)
    TextView txt_feedbooklist_title;

    @BindView(R.id.rv_feedlist)
    RecyclerView rv_feedlist;

    @BindView(R.id.feedlist_swipeRefreshLayout)
    RefreshLayout feedlist_swipeRefreshLayout;

    FeedListAdapter feedListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        
        initView();
    }

    /**
     * 初始化设置
     */
    private void initView() {
        //加粗
        txt_feedbooklist_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        rv_feedlist.setLayoutManager(new LinearLayoutManager(mContext));
        feedListAdapter = new FeedListAdapter(mContext, null);
        rv_feedlist.setAdapter(feedListAdapter);


        feedlist_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "下拉刷新更多");
                feedlist_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
            }
        });

        feedlist_swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "上拉加载更多");
                //如果有更多数据

            }
        });
    }

    @OnClick({R.id.img_feedlist_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_feedlist_back){
            finish();
        }
    }
}
