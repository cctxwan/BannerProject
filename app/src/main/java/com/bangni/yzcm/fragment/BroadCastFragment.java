package com.bangni.yzcm.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.BroadcastRecordActivity;
import com.bangni.yzcm.adapter.BroadcastAdapter;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 监播fragment
 */
public class BroadCastFragment extends Fragment implements View.OnClickListener {

    View view;

    private Unbinder unbinder;

    @BindView(R.id.rv_broadcast_list)
    RecyclerView rv_broadcast_list;

    @BindView(R.id.broadcast_swipeRefreshLayout)
    RefreshLayout broadcast_swipeRefreshLayout;

    BroadcastAdapter broadcastAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_broadcast, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(getActivity(), true);

        initView();
    }

    private void initView() {
        rv_broadcast_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        broadcastAdapter = new BroadcastAdapter(getActivity(), null);
        rv_broadcast_list.setAdapter(broadcastAdapter);

        /**
         * 点击事件
         */
        broadcastAdapter.setLinster(new BroadcastAdapter.ItemOnClickLinster() {
            @Override
            public void textItemOnClick(View view, int position) {
                startActivity(new Intent(getActivity(), BroadcastRecordActivity.class));
            }
        });


        broadcast_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "下拉刷新更多");
                broadcast_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
            }
        });

        broadcast_swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
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
            BannerLog.d("b_cc", "离开了监播界面");
        }else{
            BannerLog.d("b_cc", "进入了监播界面");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_ad_list})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.txt_ad_list){
            //弹出广告选择列表框
            List<String> parms = new ArrayList<>();
            parms.add("馨灵瑜伽");
            parms.add("黑耀堂");
            parms.add("天才小神功");
            parms.add("唐延阿里扎");
            parms.add("秦地勒布朗");
            parms.add("锦园字母歌");
            parms.add("棕榈哈登");

            //全部监播统计
            new CommomDialog(getActivity(), R.style.dialog, parms, new CommomDialog.OnCloseListenerParmes() {

                @Override
                public void onClickParmes(Dialog dialog, String content, String str) {
                    if(content.equals("ok")){
                        dialog.dismiss();
                        BannerLog.d("b_cc", "选择的是" + str);
                        //修改
                    }
                }
            }, 6).show();
        }
    }
}
