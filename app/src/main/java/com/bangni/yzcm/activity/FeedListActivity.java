package com.bangni.yzcm.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.adapter.FeedListAdapter;
import com.bangni.yzcm.network.bean.FeedBookListModel;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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

        getfeedlists.post(getDatas);
    }

    Handler getfeedlists = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pageNum", 1 + "");
            map.put("pageSize", 10 + "");
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<FeedBookListModel>>() {

                @Override
                public void onNext(BannerBaseResponse<FeedBookListModel> response) {

                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(mContext, msg);
                }
            };
            BannerRetrofitUtil.getInstance().getFeedbookLists(body, new BannerProgressSubscriber<BannerBaseResponse<FeedBookListModel>>(mListener, mContext, true));
        }
    };

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
