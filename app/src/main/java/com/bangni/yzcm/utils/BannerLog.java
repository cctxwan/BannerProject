package com.bangni.yzcm.utils;

import android.util.Log;
import com.bangni.yzcm.activity.base.BannerActivity;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局Log管理
 */
public class BannerLog {

    private static Boolean MYLOG_SWITCH = true; // 日志文件总开关

    private static Boolean MYLOG_OTHER_OPEN = true;// 其他日志是否打印

    private static String MYLOGFILEName = ".txt";// 本类输出的日志文件名称

    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    /**
     * 自定义日志
     * @param tag
     * @param text
     */
    public static void d(String tag, String text) {
        log(tag, text);
    }

    /**
     * 根据tag, msg，输出日志
     * @param tag
     * @param msg
     */
    private static void log(String tag, String msg) {

        //日志文件总开关
        if (MYLOG_SWITCH) {
            if(tag.equals("b_cc")){
                Log.d(tag, msg);
                writeLogtoFile(tag, msg);
            }else{
                if(MYLOG_OTHER_OPEN){
                    Log.d(tag, msg);
                }
            }
        }else{
            Log.d(tag, msg);
        }
    }

    /**
     * 打开日志文件并写入日志
     * @param tag
     * @param text
     */
    private static void writeLogtoFile(String tag, String text) {// 新建或打开日志文件
        Date nowtime;
        nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = "时间：" + new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(System.currentTimeMillis())) + tag + "：" + text;

        File dirsFile = new File(BannerActivity.sdPath + "log/");
        if (!dirsFile.exists()){
            dirsFile.mkdirs();
        }
        File file = new File(dirsFile.toString(), needWriteFiel + MYLOGFILEName);// MYLOG_PATH_SDCARD_DIR
        if (!file.exists()) {
            try {
                //在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
