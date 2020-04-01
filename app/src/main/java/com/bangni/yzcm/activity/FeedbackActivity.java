package com.bangni.yzcm.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.network.bean.UserFeedBookBean;
import com.bangni.yzcm.network.retrofit.BannerBaseResponse;
import com.bangni.yzcm.network.retrofit.BannerProgressSubscriber;
import com.bangni.yzcm.network.retrofit.BannerRetrofitUtil;
import com.bangni.yzcm.network.retrofit.BannerSubscriberOnNextListener;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import com.bangni.yzcm.utils.ToastUtils;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.txt_feedbook_submit)
    TextView txt_feedbook_submit;

    @BindView(R.id.et_feedbook_content)
    EditText et_feedbook_content;

    @BindView(R.id.img_feedbook_back)
    LinearLayout img_feedbook_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        initView();
    }

    private void initView() {
        //加粗
        txt_feedbook_submit.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @OnClick({R.id.txt_feedbook_submit, R.id.img_feedbook_back, R.id.txt_feedbook_list})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_feedbook_back){
            finish();
        }else if(temdId == R.id.txt_feedbook_submit){
            if(TextUtils.isEmpty(et_feedbook_content.getText().toString().trim())){
                ToastUtils.warning(mContext, "内容不能为空");
                return;
            }

            //提交
            feedBook(et_feedbook_content.getText().toString().trim());
        }else if(temdId == R.id.txt_feedbook_list){
            //反馈记录
            startActivity(new Intent(mContext, FeedListActivity.class));
        }
    }

    /**
     * 反馈内容
     * @param trim
     */
    private void feedBook(String trim) {
        Map<String, String> map = new HashMap<>();
        map.put("feedbackContent", trim);
        Gson gson = new Gson();
        String entity = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), entity);
        BannerSubscriberOnNextListener mListener = new BannerSubscriberOnNextListener<BannerBaseResponse<UserFeedBookBean>>() {

            @Override
            public void onNext(BannerBaseResponse<UserFeedBookBean> response) {
                finish();
            }

            @Override
            public void onError(String msg) {
                ToastUtils.error(mContext, msg);
            }
        };
        BannerRetrofitUtil.getInstance().userFeedBook(body, new BannerProgressSubscriber<BannerBaseResponse<UserFeedBookBean>>(mListener, this, true));
    }
}
