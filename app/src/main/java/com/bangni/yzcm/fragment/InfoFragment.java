package com.bangni.yzcm.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.AboutActivity;
import com.bangni.yzcm.activity.FeedbackActivity;
import com.bangni.yzcm.activity.SettingActivity;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.network.bean.InfoFragmentBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import com.bangni.yzcm.utils.BannerUtils;
import com.bangni.yzcm.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 个人中心fragment
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    View view;

    private Unbinder unbinder;

    @BindView(R.id.img_info_path)
    ImageView img_info_path;

    @BindView(R.id.txt_info_name)
    TextView txt_info_name;

    @BindView(R.id.txt_info_account)
    TextView txt_info_account;

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
            Map<String, String> map = new HashMap<>();
            map.put("", "");
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
            BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<InfoFragmentBean>>() {

                @Override
                public void onNext(BannerBaseResponse<InfoFragmentBean> response) {
                    if(response.data != null);

                    //给handler
                    Message msg = getInfos.obtainMessage();
                    msg.obj = response.data;
                    getInfos.sendMessage(msg);
                }

                @Override
                public void onError(String msg) {
                    ToastUtils.error(getActivity(), msg);
                }
            };
            BannerRetrofitUtil.getInstance().userAccountInfo(body, new BannerProgressSubscriber<BannerBaseResponse<InfoFragmentBean>>(mListener, getActivity(), true));
        }
    };

    /**
     * 重置
     */
    Handler getInfos = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj != null){
                InfoFragmentBean infoFragmentBean = (InfoFragmentBean) msg.obj;
                if(!TextUtils.isEmpty(infoFragmentBean.getFaceimg())){
                    //保存头像
                    new BannerPreferenceStorage(BannerApplication.getInstance()).setInfoImg(infoFragmentBean.getFaceimg());
                    Glide
                            .with(getActivity())
                            .load(new BannerPreferenceStorage(BannerApplication.getInstance()).getInfoImg())
                            .asBitmap()
                            .centerCrop()
                            .error(R.mipmap.img_user)
                            .dontAnimate()
                            .listener(new RequestListener<String, Bitmap>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                    BannerLog.d("b_cc", "图片加载失败");
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    BannerLog.d("b_cc", "图片加载成功");
                                    return false;
                                }
                            })
                            .into(img_info_path);
                }
                if(!TextUtils.isEmpty(infoFragmentBean.getNickname())){
                    txt_info_name.setText(infoFragmentBean.getNickname());

                    //保存昵称
                    new BannerPreferenceStorage(BannerApplication.getInstance()).setNickName(infoFragmentBean.getNickname());
                }
                if(!TextUtils.isEmpty(infoFragmentBean.getLoginAccount())){
                    txt_info_account.setText(infoFragmentBean.getLoginAccount());

                    //保存手机号
                    new BannerPreferenceStorage(BannerApplication.getInstance()).setPhone(infoFragmentBean.getLoginAccount());
                }
            }
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

    @Override
    public void onResume() {
        super.onResume();
        BannerLog.d("b_cc", "infofragment的onResume()");
        if(!TextUtils.isEmpty(new BannerPreferenceStorage(BannerApplication.getInstance()).getInfoImg())){
            //保存头像
            Glide
                    .with(getActivity())
                    .load(new BannerPreferenceStorage(BannerApplication.getInstance()).getInfoImg())
                    .asBitmap()
                    .centerCrop()
                    .error(R.mipmap.img_user)
                    .dontAnimate()
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            BannerLog.d("b_cc", "图片加载失败");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            BannerLog.d("b_cc", "图片加载成功");
                            return false;
                        }
                    })
                    .into(img_info_path);
        }
        if(!TextUtils.isEmpty(new BannerPreferenceStorage(BannerApplication.getInstance()).getNickName())){
            txt_info_name.setText(new BannerPreferenceStorage(BannerApplication.getInstance()).getNickName());
        }
        if(!TextUtils.isEmpty(new BannerPreferenceStorage(BannerApplication.getInstance()).getPhone())){
            txt_info_account.setText(new BannerPreferenceStorage(BannerApplication.getInstance()).getPhone());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        BannerLog.d("b_cc", "infofragment的onStop()");
    }

    @Override
    public void onPause() {
        super.onPause();
        BannerLog.d("b_cc", "infofragment的onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BannerLog.d("b_cc", "infofragment的onDestroy()");
    }

}
