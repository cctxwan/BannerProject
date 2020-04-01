package com.bangni.yzcm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.OrderDetailActivity;
import com.bangni.yzcm.adapter.OrderAdapter;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
