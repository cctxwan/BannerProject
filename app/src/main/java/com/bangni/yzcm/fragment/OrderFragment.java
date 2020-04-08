package com.bangni.yzcm.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.OrderDetailActivity;
import com.bangni.yzcm.adapter.OrderAdapter;
import com.bangni.yzcm.network.bean.InfoFragmentBean;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 订单fragment
 */
public class OrderFragment extends Fragment {

    View view;

    private Unbinder unbinder;

    @BindView(R.id.rv_order_list)
    RecyclerView rv_order_list;

    @BindView(R.id.order_swipeRefreshLayout)
    RefreshLayout order_swipeRefreshLayout;

    OrderAdapter orderAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BannerLog.d("b_cc", "onAttach()");
        orderHandler.post(getOrderLists);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(getActivity(), true);
        
        initView();
    }

    private void initView() {
        rv_order_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter = new OrderAdapter(getActivity(), null);
        rv_order_list.setAdapter(orderAdapter);
        /**
         * 点击事件
         */
        orderAdapter.setLinster(new OrderAdapter.ItemOnClickLinster() {
            @Override
            public void textItemOnClick(View view, int position) {
                startActivity(new Intent(getActivity(), OrderDetailActivity.class));
            }
        });

        order_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "下拉刷新更多");
                order_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
            }
        });

        order_swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "上拉加载更多");
                //如果有更多数据

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(getActivity(), true);

        if(hidden){
            BannerLog.d("b_cc", "离开了订单界面");
        }else{
            BannerLog.d("b_cc", "进入了订单界面");
        }
    }

    /**
     * 实现runnable接口处理异步操作
     */
    Runnable getOrderLists = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pageNum", 1 + "");
            map.put("pageSize", 10 + "");
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<OrderInfos>>() {

                @Override
                public void onNext(BannerBaseResponse<OrderInfos> response) {

                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(getActivity(), msg);
                }
            };
            BannerRetrofitUtil.getInstance().userOrderLists(body, new BannerProgressSubscriber<BannerBaseResponse<OrderInfos>>(mListener, getActivity(), true));

        }
    };

    /**
     * 订单列表handler
     */
    Handler orderHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 进入此fragment执行此方法
     */
    public void getData() {
        orderHandler.post(getOrderLists);
    }

    @Override
    public void onResume() {
        super.onResume();
        BannerLog.d("b_cc", "orderfragment的onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        BannerLog.d("b_cc", "orderfragment的onStop()");
    }

    @Override
    public void onPause() {
        super.onPause();
        BannerLog.d("b_cc", "orderfragment的onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BannerLog.d("b_cc", "orderfragment的onDestroy()");
    }


}
