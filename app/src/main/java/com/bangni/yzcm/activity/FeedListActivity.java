package com.bangni.yzcm.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.adapter.FeedListAdapter;
import com.bangni.yzcm.adapter.OrderAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @BindView(R.id.feedlist_lin_nodata)
    LinearLayout feedlist_lin_nodata;

    FeedListAdapter feedListAdapter;

    List<FeedBookListModel.ListBean> listBeanList = new ArrayList<>();

    /** 自定义刷新和加载的标识，默认为false */
    boolean isRef, isLoad = false;

    /** 网络请求返回码 */
    static final int SUCC_CODE = 10000;

    private int pageNo = 1, pageSize = 5, total = 0;//当前页数、数据总数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);
        
        initView();
        addActivity(this);
        getfeedlistsHandler.post(getDatas);
    }

    Handler getfeedlistsHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            feedlist_swipeRefreshLayout.finishRefresh(true);
            feedlist_swipeRefreshLayout.finishLoadMore(true);
            //如果是刷新和加载的请求标识，直接刷新adapter加载数据
            if(msg.arg1 == SUCC_CODE && isLoad || isRef){
                feedListAdapter.notifyDataSetChanged();
            }
            //否则的话就相当于首次进入加载，先关闭动画，然后把数据加载到RV上
            else if(msg.arg1 == SUCC_CODE){
                rv_feedlist.setLayoutManager(new LinearLayoutManager(mContext));
                feedListAdapter = new FeedListAdapter(mContext, listBeanList);
                rv_feedlist.setAdapter(feedListAdapter);
                //当rv的item点击之后进入此方法，并在openWindow处理逻辑
                feedListAdapter.setLinster(new FeedListAdapter.ItemOnClickLinster() {
                    @Override
                    public void textItemOnClick(View view, int position) {
                        BannerLog.d("b_cc", "----->position=" + position);
                        startActivity(new Intent(mContext, OrderDetailActivity.class));
                    }
                });
            }
        }
    };

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pageNum", pageNo + "");
            map.put("pageSize", pageSize + "");
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<FeedBookListModel>>() {

                @Override
                public void onNext(BannerBaseResponse<FeedBookListModel> response) {
                    if (response.data != null) {
                        if(response.data.getList() != null){
                            Message message = getfeedlistsHandler.obtainMessage();
                            total = response.data.getTotal();
                            if(response.data.getList().size() > 0){
                                rv_feedlist.setVisibility(View.VISIBLE);
                                feedlist_lin_nodata.setVisibility(View.GONE);
                                feedlist_swipeRefreshLayout.setEnableLoadMore(true);
                            }else{
                                feedlist_lin_nodata.setVisibility(View.VISIBLE);
                                feedlist_swipeRefreshLayout.setEnableLoadMore(false);
                            }
                            if(isRef){
                                List<FeedBookListModel.ListBean> json = new ArrayList<>();
                                json.addAll(response.data.getList());
                                listBeanList.clear();
                                for(int i = 0 ; i < json.size() ; i++) {
                                    listBeanList.add(json.get(i));
                                }
                                isRef = false;
                            }else {
                                listBeanList.addAll(response.data.getList());
                            }
                            message.arg1 = 10000;
                            getfeedlistsHandler.sendMessage(message);
                            if(listBeanList.size() == total && pageNo == 1){
                                if(total == 0) return;
                                feedlist_swipeRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
                                feedlist_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
                                feedlist_swipeRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        }else{
                            rv_feedlist.setVisibility(View.GONE);
                            feedlist_lin_nodata.setVisibility(View.VISIBLE);
                            feedlist_swipeRefreshLayout.finishRefresh(true);
                            feedlist_swipeRefreshLayout.finishLoadMore(true);
                        }
                    }else{
                        rv_feedlist.setVisibility(View.GONE);
                        feedlist_lin_nodata.setVisibility(View.VISIBLE);
                        feedlist_swipeRefreshLayout.finishRefresh(true);
                        feedlist_swipeRefreshLayout.finishLoadMore(true);
                    }
                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(mContext, msg);
                    feedlist_swipeRefreshLayout.finishRefresh(true);
                    feedlist_swipeRefreshLayout.finishLoadMore(true);
                    if (msg.equals("暂无数据")) {
                        rv_feedlist.setVisibility(View.GONE);
                        feedlist_lin_nodata.setVisibility(View.VISIBLE);
                    }else{
                        feedlist_lin_nodata.setVisibility(View.GONE);
                    }
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


        feedlist_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "下拉刷新更多");
                feedlist_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
                pageNo = 1;
                isRef = true;
                getfeedlistsHandler.post(getDatas);
            }
        });

        feedlist_swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "上拉加载更多");
                //如果有更多数据
                if (listBeanList.size() < total) {
                    isLoad = true;
                    pageNo++;
                    isRef = false;
                    getfeedlistsHandler.post(getDatas);
                }else{
                    feedlist_swipeRefreshLayout.finishLoadMoreWithNoMoreData();
                }
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
