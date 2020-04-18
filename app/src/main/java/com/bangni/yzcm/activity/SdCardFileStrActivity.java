package com.bangni.yzcm.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.os.Bundle;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.systemstatusbar.StatusBarUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 读取sd卡日志
 */
public class SdCardFileStrActivity extends BannerActivity {

    @BindView(R.id.txt_fileStr)
    TextView txt_fileStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd_card_file_str);
        ButterKnife.bind(this);

        //修改状态栏字体颜色
        StatusBarUtil.setImmersiveStatusBar(this, true);

        getSdCardStr();
    }

    private void getSdCardStr() {
        File file = new File(BannerActivity.sdPath + "log/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt");
        String str = null;
        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(input);
            while ((str = reader.readLine()) != null) {
                txt_fileStr.append(str);
                txt_fileStr.append("\n");
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
