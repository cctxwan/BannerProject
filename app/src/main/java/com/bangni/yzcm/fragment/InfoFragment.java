package com.bangni.yzcm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.AboutActivity;
import com.bangni.yzcm.activity.FeedbackActivity;
import com.bangni.yzcm.activity.SettingActivity;
import com.bangni.yzcm.network.bean.InfoFragmentBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 个人中心fragment
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    View view;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(getActivity(), true);
    }

    Runnable getRunnable = new Runnable() {
        @Override
        public void run() {
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<InfoFragmentBean>>() {

                @Override
                public void onNext(BannerBaseResponse<InfoFragmentBean> response) {

                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(getActivity(), msg);
                }
            };
            BannerRetrofitUtil.getInstance().userAccountInfo(new BannerProgressSubscriber<BannerBaseResponse<InfoFragmentBean>>(mListener, getActivity(), true));
        }
    };

    /**
     * 重置
     */
    Handler getInfos = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtil.setImmersiveStatusBar(getActivity(), true);

        if(hidden){
            BannerLog.d("b_cc", "离开了我的界面");
        }else{
            BannerLog.d("b_cc", "进入了我的界面");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rel_setting, R.id.rel_feedbook, R.id.rel_about})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.rel_setting){
            //设置
            startActivity(new Intent(getActivity(), SettingActivity.class));
        }else if(temdId == R.id.rel_feedbook){
            //反馈
            startActivity(new Intent(getActivity(), FeedbackActivity.class));
        }else if(temdId == R.id.rel_about){
            //关于我们
            startActivity(new Intent(getActivity(), AboutActivity.class));
        }
    }

    /**
     * 进入此fragment调用方法
     */
    public void getData() {
        getInfos.post(getRunnable);
    }
}
