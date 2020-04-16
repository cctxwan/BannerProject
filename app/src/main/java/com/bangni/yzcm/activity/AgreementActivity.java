package com.bangni.yzcm.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.network.util.BannerConstants;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;

/**
 * 隐私协议
 */
public class AgreementActivity extends BannerActivity implements View.OnClickListener {

    @BindView(R.id.web_agreement)
    WebView web_agreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        web_agreement.getSettings().setSupportZoom(true);
        web_agreement.getSettings().setBuiltInZoomControls(true);

        web_agreement.getSettings().setBuiltInZoomControls(false);
        web_agreement.getSettings().setSupportZoom(false);
        web_agreement.getSettings().setDisplayZoomControls(false);

        web_agreement.getSettings().setJavaScriptEnabled(true);
        web_agreement.loadUrl(BannerConstants.BASE_URL + "/img/agreement.html");

        web_agreement.setWebChromeClient(new WebChromeClient() {});
        web_agreement.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

    @OnClick({R.id.img_agreement_back})
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_agreement_back){
            finish();
        }
    }

}
