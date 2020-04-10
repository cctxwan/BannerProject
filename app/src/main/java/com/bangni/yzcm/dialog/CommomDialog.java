package com.bangni.yzcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import java.util.List;

/**
 * 封装的dialog
 */
public class CommomDialog extends Dialog implements View.OnClickListener{

    //切换图像
    private TextView photo, album, cancel;

    //修改昵称
    private LinearLayout lin_changeclose;
    private EditText et_changename;
    private TextView txt_changename;
    private ImageView close;

    //清楚缓存
    private TextView yes_hc, no_hc;

    //退出登录
    private TextView txt_hc_content;

    //社区和广告选择器
    private WheelView wheelview;
    private String WheelViewSelectStr;
    private TextView txt_wheelview_ok, txt_wheelview_cancal;

    //登录成功
    private TextView dialog_registersucc_login, dialog_register_title;

    //切换账号成功
    private TextView dialog_changeaccount_title;

    //网络请求 成功
    static final int SUCC_CODE = 0;


    private String title;
    private List<String> items;
    private String content;
    private Context mContext;
    private OnCloseListener listener;
    private OnCloseListenerParmes listenerParmes;
    private int num;

    public CommomDialog(Context context, int themeResId, OnCloseListener listener, int num) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.num = num;
    }

    public CommomDialog(Context context, int themeResId, String title, OnCloseListenerParmes listenerParmes, int num) {
        super(context, themeResId);
        this.title = title;
        this.mContext = context;
        this.listenerParmes = listenerParmes;
        this.num = num;
    }

    public CommomDialog(Context context, int themeResId, List<String> items, OnCloseListenerParmes listenerParmes, int num) {
        super(context, themeResId);
        this.items = items;
        this.mContext = context;
        this.listenerParmes = listenerParmes;
        this.num = num;
    }

    public CommomDialog(Context context, int themeResId, String title, String content, OnCloseListener listener, int num) {
        super(context, themeResId);
        this.title = title;
        this.mContext = context;
        this.listener = listener;
        this.content = content;
        this.num = num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(num == 1){
            setContentView(R.layout.dialogcommom);
            setCanceledOnTouchOutside(false);
            initView();
        }else if(num == 2){
            setContentView(R.layout.changename);
            setCanceledOnTouchOutside(false);
            initView();
        }else if(num == 3){
            setContentView(R.layout.clearhc);
            setCanceledOnTouchOutside(false);
            initView();
        }else if(num == 4){
            setContentView(R.layout.clearhc);
            setCanceledOnTouchOutside(false);
            initView();
        }else if(num == 5){
            setContentView(R.layout.clearhc);
            setCanceledOnTouchOutside(false);
            initView();
        }else if(num == 6){
            setContentView(R.layout.dialog_wheelview);
            setCanceledOnTouchOutside(false);
            initView();
        }else if(num == 7){
            setContentView(R.layout.dialog_registersucc);
            setCanceledOnTouchOutside(false);
            initView();
        }
    }

    private void initView(){
        if(num == 1){
            photo = findViewById(R.id.photo);
            album = findViewById(R.id.album);
            cancel = findViewById(R.id.cancel);
            photo.setOnClickListener(this);
            album.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }else if(num == 2){
            lin_changeclose = findViewById(R.id.lin_changeclose);
            et_changename = findViewById(R.id.et_changename);
            txt_changename = findViewById(R.id.txt_changename);
            close = findViewById(R.id.close);
            lin_changeclose.setOnClickListener(this);
            txt_changename.setOnClickListener(this);

            //显示之前的昵称
            et_changename.setText(new BannerPreferenceStorage(BannerApplication.getInstance()).getNickName());

            close.setColorFilter(Color.parseColor("#959595"));
        }else if(num == 3){
            yes_hc = findViewById(R.id.yes_hc);
            no_hc = findViewById(R.id.no_hc);
            txt_hc_content = findViewById(R.id.txt_hc_content);
            yes_hc.setOnClickListener(this);
            no_hc.setOnClickListener(this);

            //加粗
            txt_hc_content.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            txt_hc_content.setText("确定要清除缓存？");
        }else if(num == 4){
            yes_hc = findViewById(R.id.yes_hc);
            no_hc = findViewById(R.id.no_hc);
            txt_hc_content = findViewById(R.id.txt_hc_content);
            yes_hc.setOnClickListener(this);
            no_hc.setOnClickListener(this);

            //加粗
            txt_hc_content.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            txt_hc_content.setText("确定要退出登录？");
        }else if(num == 5){
            yes_hc = findViewById(R.id.yes_hc);
            no_hc = findViewById(R.id.no_hc);
            txt_hc_content = findViewById(R.id.txt_hc_content);
            yes_hc.setOnClickListener(this);
            no_hc.setOnClickListener(this);

            //加粗
            txt_hc_content.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            txt_hc_content.setText("修改成功后需重新登录，确定执行此操作吗？");
        }else if(num == 6){
            wheelview = findViewById(R.id.wheelview);
            txt_wheelview_cancal = findViewById(R.id.txt_wheelview_cancal);
            txt_wheelview_ok = findViewById(R.id.txt_wheelview_ok);

            txt_wheelview_ok.setOnClickListener(this);
            txt_wheelview_cancal.setOnClickListener(this);

            wheelview.setCyclic(false);
            wheelview.setItemsVisibleCount(3);
            wheelview.setLineSpacingMultiplier(2.5f);

            wheelview.setAdapter(new ArrayWheelAdapter(items));
            wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    WheelViewSelectStr = items.get(index);
                    BannerLog.d("b_cc", "当前选中的是：" + items.get(index));
                }
            });
        }else if(num == 7){
            dialog_register_title = findViewById(R.id.dialog_register_title);
            dialog_registersucc_login = findViewById(R.id.dialog_registersucc_login);

            dialog_registersucc_login.setOnClickListener(this);
            //加粗
            dialog_register_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    @Override
    public void onClick(View v) {
        if(num == 1){
            switch (v.getId()){
                case R.id.photo:
                    if(listener != null){
                        listener.onClick(this, "photo");
                    }
                    break;
                case R.id.album:
                    if(listener != null){
                        listener.onClick(this, "album");
                    }
                    break;
                case R.id.cancel:
                    if(listener != null){
                        listener.onClick(this, "cancel");
                    }
                    break;
            }
        }else if(num == 2){
            switch (v.getId()){
                case R.id.lin_changeclose:
                    if(listenerParmes != null){
                        listenerParmes.onClickParmes(this, "close", "");
                    }
                    break;
                case R.id.txt_changename:
                    if(listenerParmes != null){
                        listenerParmes.onClickParmes(this, "sub_name", et_changename.getText().toString().trim());
                    }
                    break;
            }
        }else if(num == 3){
            switch (v.getId()){
                case R.id.no_hc:
                    if(listener != null){
                        listener.onClick(this, "no_hc");
                    }
                    break;
                case R.id.yes_hc:
                    if(listener != null){
                        listener.onClick(this, "yes_hc");
                    }
                    break;
            }
        }else if(num == 4){
            switch (v.getId()){
                case R.id.no_hc:
                    if(listener != null){
                        listener.onClick(this, "no_hc");
                    }
                    break;
                case R.id.yes_hc:
                    if(listener != null){
                        listener.onClick(this, "yes_hc");
                    }
                    break;
            }
        }else if(num == 5){
            switch (v.getId()){
                case R.id.no_hc:
                    if(listener != null){
                        listener.onClick(this, "no_hc");
                    }
                    break;
                case R.id.yes_hc:
                    if(listener != null){
                        listener.onClick(this, "yes_hc");
                    }
                    break;
            }
        }else if(num == 6){
            switch (v.getId()){
                case R.id.txt_wheelview_cancal:
                    if(listenerParmes != null){
                        dismiss();
                    }
                    break;
                case R.id.txt_wheelview_ok:
                    if(listenerParmes != null){
                        if(!TextUtils.isEmpty(WheelViewSelectStr)){
                            listenerParmes.onClickParmes(this, "ok", WheelViewSelectStr);
                        }else{
                            listenerParmes.onClickParmes(this, "ok", items.get(0));
                        }
                    }
                    break;
            }
        }else if(num == 7){
            switch (v.getId()){
                case R.id.dialog_registersucc_login:
                    if(listener != null){
                        listener.onClick(this, "tologin");
                    }
                    break;
            }
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, String content);
    }

    public interface OnCloseListenerParmes{
        void onClickParmes(Dialog dialog, String content, String str);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}