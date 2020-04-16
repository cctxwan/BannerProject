package com.bangni.yzcm.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.LoginActivity;
import com.bangni.yzcm.activity.OrderDetailActivity;
import com.bangni.yzcm.adapter.MultipleTypesAdapter;
import com.bangni.yzcm.adapter.OrderAdapter;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.network.bean.FindPsdBean;
import com.bangni.yzcm.network.bean.InfoFragmentBean;
import com.bangni.yzcm.network.bean.OrderBannerModel;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import com.bangni.yzcm.utils.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    List<OrderInfos.ListBean> orderInfos = new ArrayList<>();

    @BindView(R.id.order_lin_nodata)
    LinearLayout order_lin_nodata;

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.indicator)
    RoundLinesIndicator indicator;

    @BindView(R.id.lin_banner)
    LinearLayout lin_banner;

    /** 自定义刷新和加载的标识，默认为false */
    boolean isRef, isLoad = false;

    /** 网络请求返回码 */
    static final int SUCC_CODE = 10000;

    private int pageNo = 1, pageSize = 5, total = 0;//当前页数、数据总数量

    /** 刷新方式 */
    public boolean isRefreshHead = false;

    //轮播图集合
    List<OrderBannerModel.ListBean> bannerLists;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BannerLog.d("b_cc", "onAttach()");
        orderHandler.post(getBannerLists);
        isRefreshHead = true;
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

//        list.add(new OrderBannerModel(R.drawable.image1, "相信自己,你努力的样子真的很美", 1));
//        list.add(new OrderBannerModel(R.drawable.image2, "极致简约,梦幻小屋", 3));
//        list.add(new OrderBannerModel(R.drawable.image3, "超级卖梦人", 3));
//        list.add(new OrderBannerModel(R.drawable.image4, "夏季新搭配", 1));
//        list.add(new OrderBannerModel(R.drawable.image5, "绝美风格搭配", 1));
//        list.add(new OrderBannerModel(R.drawable.image6, "微微一笑 很倾城", 3));
//
        //最大显示下拉高度/Header标准高度
        order_swipeRefreshLayout.setHeaderMaxDragRate(2);

        order_swipeRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        order_swipeRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作

        order_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "下拉刷新更多");
                isRefreshHead = false;
                banner.stop();
                order_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
                pageNo = 1;
                isRef = true;
                orderHandler.post(getBannerLists);
            }
        });

        order_swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                BannerLog.d("b_cc", "上拉加载更多");
                //如果有更多数据
                if (orderInfos.size() < total) {
                    isLoad = true;
                    pageNo++;
                    isRef = false;
                    orderHandler.post(getOrderLists);
                }else{
                    order_swipeRefreshLayout.finishLoadMoreWithNoMoreData();
                }
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
            isRefreshHead = false;
        }else{
            BannerLog.d("b_cc", "进入了订单界面");
        }
    }

    Runnable getBannerLists = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pageNum", 1 + "");
            map.put("pageSize", 20 + "");
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<OrderBannerModel>>() {

                @Override
                public void onNext(BannerBaseResponse<OrderBannerModel> response) {
                    banner.start();
                    orderHandler.post(getOrderLists);

                    if(response.data != null){
                        OrderBannerModel orderBannerModel = response.data;
                        if(orderBannerModel.getList().size() > 0){
                            banner.setVisibility(View.VISIBLE);
                            bannerLists = new ArrayList<>(orderBannerModel.getList());
                            banner.setAdapter(new MultipleTypesAdapter(getActivity(), bannerLists));
                            banner.setIndicator(new RectangleIndicator(getActivity()));
                            banner.setIndicatorNormalWidth((int) BannerUtils.dp2px(12));
                            banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
                            banner.setIndicatorRadius(0);
                            //设置点击事件
                            banner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(Object data, int position) {

                                }
                            });

                            //圆角
                            banner.setBannerRound(BannerUtils.dp2px(5));

                        }else{
                            lin_banner.setBackgroundResource(R.mipmap.order_bg);
                            banner.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onError(String msg) {
                    banner.start();
                    ToastUtils.error(getActivity(), msg);
                    order_swipeRefreshLayout.finishRefresh(true);
                    order_swipeRefreshLayout.finishLoadMore(true);
                }
            };
            BannerRetrofitUtil.getInstance().getBannerLists(body, new BannerProgressSubscriber<BannerBaseResponse<OrderBannerModel>>(mListener, getActivity(), isRefreshHead));

        }
    };

    /**
     * 实现runnable接口处理异步操作
     */
    Runnable getOrderLists = new Runnable() {
        @Override
        public void run() {
            Map<String, String> map = new HashMap<>();
            map.put("pageNum", pageNo + "");
            map.put("pageSize", pageSize + "");
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<OrderInfos>>() {

                @Override
                public void onNext(BannerBaseResponse<OrderInfos> response) {
                    if (response.data != null) {
                        if(response.data.getList() != null){
                            Message message = orderHandler.obtainMessage();
                            total = response.data.getTotal();
                            if(response.data.getList().size() > 0){
                                rv_order_list.setVisibility(View.VISIBLE);
                                order_lin_nodata.setVisibility(View.GONE);
                                order_swipeRefreshLayout.setEnableLoadMore(true);
                            }else{
                                order_lin_nodata.setVisibility(View.VISIBLE);
                                order_swipeRefreshLayout.setEnableLoadMore(false);
                            }
                            if(isRef){
                                List<OrderInfos.ListBean> json = new ArrayList<>();
                                json.addAll(response.data.getList());
                                orderInfos.clear();
                                for(int i = 0 ; i < json.size() ; i++) {
                                    orderInfos.add(json.get(i));
                                }
                                isRef = false;
                            }else {
                                orderInfos.addAll(response.data.getList());
                            }
                            message.arg1 = 10000;
                            orderHandler.sendMessage(message);
                            if(orderInfos.size() == total && pageNo == 1){
                                if(total == 0) return;
                                order_swipeRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
                                order_swipeRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
                                order_swipeRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        }else{
                            rv_order_list.setVisibility(View.GONE);
                            order_lin_nodata.setVisibility(View.VISIBLE);
                            order_swipeRefreshLayout.finishRefresh(true);
                            order_swipeRefreshLayout.finishLoadMore(true);
                        }

                    }else{
                        rv_order_list.setVisibility(View.GONE);
                        order_lin_nodata.setVisibility(View.VISIBLE);
                        order_swipeRefreshLayout.finishRefresh(true);
                        order_swipeRefreshLayout.finishLoadMore(true);
                    }
                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(getActivity(), msg);
                    order_swipeRefreshLayout.finishRefresh(true);
                    order_swipeRefreshLayout.finishLoadMore(true);
                    if (msg.equals("暂无数据")) {
                        rv_order_list.setVisibility(View.GONE);
                        order_lin_nodata.setVisibility(View.VISIBLE);
                    }else{
                        order_lin_nodata.setVisibility(View.VISIBLE);
                    }
                }
            };
            BannerRetrofitUtil.getInstance().userOrderLists(body, new BannerProgressSubscriber<BannerBaseResponse<OrderInfos>>(mListener, getActivity(), isRefreshHead));

        }
    };

    /**
     * 订单列表handler
     */
    Handler orderHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            order_swipeRefreshLayout.finishRefresh(true);
            order_swipeRefreshLayout.finishLoadMore(true);
            //如果是刷新和加载的请求标识，直接刷新adapter加载数据
            if(msg.arg1 == SUCC_CODE && isLoad || isRef){
                orderAdapter.notifyDataSetChanged();
            }
            //否则的话就相当于首次进入加载，先关闭动画，然后把数据加载到RV上
            else if(msg.arg1 == SUCC_CODE){
                rv_order_list.setLayoutManager(new LinearLayoutManager(getActivity()));
                orderAdapter = new OrderAdapter(getActivity(), orderInfos);
                rv_order_list.setAdapter(orderAdapter);
                //当rv的item点击之后进入此方法，并在openWindow处理逻辑
                orderAdapter.setLinster(new OrderAdapter.ItemOnClickLinster() {
                    @Override
                    public void textItemOnClick(View view, int position) {
                        if(orderInfos.get(position).getStatus() != 1){
                            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("pid", orderInfos.get(position).getPid());
                            bundle.putInt("state", orderInfos.get(position).getStatus());
                            bundle.putLong("startTime", orderInfos.get(position).getStartTime());
                            bundle.putLong("endTime", orderInfos.get(position).getEndTime());
                            intent.putExtras(bundle);
                            startActivityForResult(intent, 222);
                        }else{
                            ToastUtils.warning(getActivity(), "广告暂未投放，请稍后再试。");
                        }
                    }
                });
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 判断请求码和返回码是不是正确的，这两个码都是我们自己设置的
        if (requestCode == 222 && resultCode == 111){
            BannerLog.d("b_cc", "接口出现问题");
            ToastUtils.warning(getActivity(), "账号已在其他设备登录");
            new BannerPreferenceStorage(BannerApplication.getInstance()).setToken("");
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            order_swipeRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 进入此fragment执行此方法
     */
    public void getData() {
        order_swipeRefreshLayout.autoRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始轮播
        banner.start();
        BannerLog.d("b_cc", "orderfragment的onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stop();
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
