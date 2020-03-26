package com.bangni.yzcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;

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

    //清楚缓存
    private TextView yes_hc, no_hc;

    //退出登录
    private TextView txt_hc_content;


    //网络请求 成功
    static final int SUCC_CODE = 0;


    private String title;
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
            lin_changeclose.setOnClickListener(this);
            txt_changename.setOnClickListener(this);
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