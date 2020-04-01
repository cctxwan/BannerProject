package com.bangni.yzcm.activity;

import android.os.Bundle;
import android.view.View;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.adapter.CommunityAdapter;
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
 * 社区列表
 */
public class CommunityActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.rv_community_list)
    RecyclerView rv_community_list;

    @BindView(R.id.community_swipeRefreshLayout)
    RefreshLayout community_swipeRefreshLayout;

    CommunityAdapter communityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        initView();
    }

    private void initView() {
        rv_community_list.setLayoutManager(new LinearLayoutManager(mContext));
        communityAdapter = new CommunityAdapter(mContext, null);
        rv_community_list.setAdapter(communityAdapter);


        community_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "下拉刷新更多");
                community_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
            }
        });

        community_swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "上拉加载更多");
                //如果有更多数据

            }
        });
    }

    @OnClick({R.id.img_community_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_community_back){
            finish();
        }
    }

}
