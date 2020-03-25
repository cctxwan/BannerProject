package com.bangni.yzcm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.AboutActivity;
import com.bangni.yzcm.activity.FeedbackActivity;
import com.bangni.yzcm.activity.SettingActivity;
import com.bangni.yzcm.systemstatusbar.StatusBarCompat;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by admin on 2018/4/10.
 */

public class InfoFragment extends Fragment implements View.OnClickListener {

    View view;

    @BindView(R.id.rel_setting)
    RelativeLayout rel_setting;

    @BindView(R.id.rel_feedbook)
    RelativeLayout rel_feedbook;

    @BindView(R.id.rel_about)
    RelativeLayout rel_about;

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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
            StatusBarUtil.setImmersiveStatusBar(getActivity(), true);
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
}
