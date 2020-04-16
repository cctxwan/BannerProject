package com.bangni.yzcm.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.dialog.CommomDialog;
import com.bangni.yzcm.fragment.InfoFragment;
import com.bangni.yzcm.network.bean.BannerQNiuYModel;
import com.bangni.yzcm.network.bean.InfoFragmentBean;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.network.util.BannerConstants;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import com.bangni.yzcm.utils.ClearDataUtils;
import com.bangni.yzcm.utils.LQRPhotoSelectUtils;
import com.bangni.yzcm.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 设置界面
 */
public class SettingActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_loginout)
    TextView txt_loginout;

    @BindView(R.id.img_userimg)
    ImageView img_userimg;

    @BindView(R.id.txt_getHc)
    TextView txt_getHc;

    @BindView(R.id.txt_change_phone)
    TextView txt_change_phone;

    @BindView(R.id.txt_set_nickname)
    TextView txt_set_nickname;

    //上传图片
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    String upImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() throws Exception {
        txt_getHc.setText(ClearDataUtils.getTotalCacheSize(mContext));
        //加粗
        txt_loginout.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        //给头像、昵称和账号赋值
        if(!TextUtils.isEmpty(new BannerPreferenceStorage(BannerApplication.getInstance()).getInfoImg())){
            Glide.with(SettingActivity.this).load(new BannerPreferenceStorage(BannerApplication.getInstance()).getInfoImg()).error(R.mipmap.img_user).into(img_userimg);
        }
        if(!TextUtils.isEmpty(new BannerPreferenceStorage(BannerApplication.getInstance()).getNickName())){
            txt_set_nickname.setText(new BannerPreferenceStorage(BannerApplication.getInstance()).getNickName());
        }else{
            txt_set_nickname.setText("请设置昵称");
        }
        txt_change_phone.setText(new BannerPreferenceStorage(BannerApplication.getInstance()).getPhone());
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                String nickname = (String) msg.obj;
                txt_set_nickname.setText(nickname);
                new BannerPreferenceStorage(BannerApplication.getInstance()).setNickName(nickname);
//                ToastUtils.success(mContext, "昵称修改成功");
            }else if(msg.what == 2){
                String path = (String) msg.obj;
                // 4、当拍照或从图库选取图片成功后回调
                Glide
                        .with(SettingActivity.this)
                        .load(path)
                        .error(R.mipmap.img_user)
                        .into(img_userimg);

                new BannerPreferenceStorage(BannerApplication.getInstance()).setInfoImg(path.toString());
            }
        }
    };


    @OnClick({R.id.rel_change_img, R.id.rel_change_name, R.id.rel_change_account, R.id.rel_change_pass, R.id.rel_clear_hc, R.id.txt_loginout, R.id.img_setting_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.rel_change_img){
            changeImg();
        }else if(temdId == R.id.rel_change_name){
            changename();
        }else if(temdId == R.id.rel_change_account){
            startActivity(new Intent(mContext, ChangeAccountActivity.class));
        }else if(temdId == R.id.rel_change_pass){
            startActivity(new Intent(mContext, ChangePsdActivity.class));
        }else if(temdId == R.id.rel_clear_hc){
//            if(txt_getHc.getText().toString().trim().equals("0.00K")) return;
            clearHC();
        }else if(temdId == R.id.txt_loginout){
            loginout();
        }else if(temdId == R.id.img_setting_back){
            finish();
        }
    }

    /**
     * 退出登录
     */
    private void loginout() {
        new CommomDialog(mContext, R.style.dialog, new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, String content) {
                if(content.equals("yes_hc")){
                    dialog.dismiss();
                    new BannerPreferenceStorage(BannerApplication.getInstance()).setToken("");
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //退出登录
                    finishAllActivity();
                }else if(content.equals("no_hc")){
                    dialog.dismiss();
                }
            }
        }, 4).show();
    }

    /**
     * 清除缓存
     */
    private void clearHC() {
        new CommomDialog(mContext, R.style.dialog, new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, String content) {
                if(content.equals("yes_hc")){
                    dialog.dismiss();
                    //清楚缓存
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ClearDataUtils.clearAllCache(mContext);
                                txt_getHc.setText(ClearDataUtils.getTotalCacheSize(mContext));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else if(content.equals("no_hc")){
                    dialog.dismiss();
                }
            }
        }, 3).show();
    }

    /**
     * 修改昵称
     */
    private void changename() {
        new CommomDialog(mContext, R.style.dialog, "更改昵称", new CommomDialog.OnCloseListenerParmes() {

            @Override
            public void onClickParmes(Dialog dialog, String content, String str) {
                if(content.equals("sub_name")){
                    dialog.dismiss();
                    BannerLog.d("b_cc", "更改昵称所返回的昵称为：" + str);
                    //修改
                    Map<String, String> map = new HashMap<>();
                    map.put("nickname", str);
                    map.put("faceimg", new BannerPreferenceStorage(mContext).getInfoImg());
                    Gson gson = new Gson();
                    String entity = gson.toJson(map);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
                    BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<InfoFragmentBean>>() {

                        @Override
                        public void onNext(BannerBaseResponse<InfoFragmentBean> response) {
                            Message msg = handler.obtainMessage();
                            msg.what = 1;
                            msg.obj = str;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onError(String msg) {
                            ToastUtils.error(mContext, msg);
                        }
                    };
                    BannerRetrofitUtil.getInstance().changeUserAccountInfo(body, new BannerProgressSubscriber<BannerBaseResponse<InfoFragmentBean>>(mListener, mContext, true));
                }else if(content.equals("close")){
                    dialog.dismiss();
                }
            }
        }, 2).show();
    }

    /**
     * 切换圆形img
     */
    private void changeImg() {
        new CommomDialog(mContext, R.style.dialog, new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, String content) {
                if(content.equals("photo")){
                    dialog.dismiss();
                    //请求打开权限
                    // 3、调用拍照方法
                    PermissionGen.with(SettingActivity.this)
                            .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            ).request();
                }else if(content.equals("album")){
                    dialog.dismiss();
                    // 3、调用从图库选取图片方法
                    PermissionGen.needPermission(SettingActivity.this,
                            LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    );
                }else if(content.equals("cancel")){
                    dialog.dismiss();
                }
            }
        }, 1).show();
    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 321);
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                //修改
                BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<BannerQNiuYModel>>() {

                    @Override
                    public void onNext(BannerBaseResponse<BannerQNiuYModel> beanBaseResponse) {
                        BannerQNiuYModel bean = beanBaseResponse.data;
                        if (bean != null) {
                            uploadImageToQiniu(outputFile, bean.getUploadToken(), bean.getUploadKey());
                        }
                    }

                    @Override
                    public void onError(String msg) {

                    }
                };
                BannerRetrofitUtil.getInstance().qNiuYtoken(new BannerProgressSubscriber<BannerBaseResponse<BannerQNiuYModel>>(mListener, mContext, true));
            }
        }, false);//true裁剪，false不裁剪

        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                //上传
                BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<BannerQNiuYModel>>() {

                    @Override
                    public void onNext(BannerBaseResponse<BannerQNiuYModel> beanBaseResponse) {
                        BannerQNiuYModel bean = beanBaseResponse.data;
                        if (bean != null) {
                            uploadImageToQiniu(outputFile, bean.getUploadToken(), bean.getUploadKey());
                        }
                    }

                    @Override
                    public void onError(String msg) {

                    }
                };
                BannerRetrofitUtil.getInstance().qNiuYtoken(new BannerProgressSubscriber<BannerBaseResponse<BannerQNiuYModel>>(mListener, mContext, true));
            }
        }, false);//true裁剪，false不裁剪
        mLqrPhotoSelectUtils.selectPhoto();
    }

    /**
     * 上传图片到七牛
     *
     * @param filePath 要上传的图片路径
     * @param token    在七牛官网上注册的token
     */
    private void uploadImageToQiniu(File filePath, String token, String key) {
        //上传到七牛云
        BannerLog.d("b_cc", "上传到七牛云");

        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                BannerLog.d("b_cc", "七牛云的k" + key);
                upImgUrl = BannerConstants.BASE_IMAGE_URL + key;
                // 4、当拍照或从图库选取图片成功后回调
                Glide
                        .with(SettingActivity.this)
                        .load(upImgUrl)
                        .error(R.mipmap.img_user)
                        .into(img_userimg);
                userRaceimgModify();
            }
        }, null);
    }

    /**
     * 修改
     */
    private void userRaceimgModify() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", new BannerPreferenceStorage(mContext).getNickName());
        map.put("faceimg", upImgUrl);
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<InfoFragmentBean>>() {

            @Override
            public void onNext(BannerBaseResponse<InfoFragmentBean> response) {
                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = upImgUrl;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(mContext, msg);
            }
        };
        BannerRetrofitUtil.getInstance().changeUserAccountInfo(body, new BannerProgressSubscriber<BannerBaseResponse<InfoFragmentBean>>(mListener, mContext, true));
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        goToAppSetting();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        goToAppSetting();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        if(mLqrPhotoSelectUtils != null){
            mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

}
