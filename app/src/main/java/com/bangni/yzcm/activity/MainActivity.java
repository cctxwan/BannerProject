package com.bangni.yzcm.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bangni.yzcm.R;
import com.bangni.yzcm.activity.base.BannerActivity;
import com.bangni.yzcm.fragment.OrderFragment;
import com.bangni.yzcm.fragment.InfoFragment;
import com.bangni.yzcm.fragment.BroadCastFragment;
import com.bangni.yzcm.utils.BannerLog;
import java.util.HashMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends BannerActivity implements View.OnClickListener {

    /**
     * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    //获取底部导航栏的ImageView
    @BindView(R.id.img_one_bottom)
    ImageView img_one_bottom;

    @BindView(R.id.img_two_bottom)
    ImageView img_two_bottom;

    @BindView(R.id.img_three_bottom)
    ImageView img_three_bottom;

    OrderFragment orderFragment = new OrderFragment();
    BroadCastFragment broadCastFragment = new BroadCastFragment();
    InfoFragment infoFragment = new InfoFragment();

    /** 第一个fragment */
    public static final int PAGE_COMMON = 0;
    /** 第二个fragment */
    public static final int PAGE_TRANSLUCENT = 1;
    /** 第三个fragment */
    public static final int PAGE_COORDINATOR = 2;

    /** 管理fragment */
    private HashMap<Integer, Fragment> fragments = new HashMap<>();

    //当前activity的fragment控件
    private int fragmentContentId = R.id.fragment_content;

    /** 设置默认的fragment */
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initFrag();
        // 设置默认的Fragment
        defaultFragment();
        SelectColor(0);

        //关闭侧滑
        setSwback(false);
    }

    private void initFrag() {
        fragments.put(PAGE_COMMON, orderFragment);
        fragments.put(PAGE_TRANSLUCENT, broadCastFragment);
        fragments.put(PAGE_COORDINATOR, infoFragment);
    }

    private void defaultFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId,fragments.get(PAGE_COMMON));
        currentTab = PAGE_COMMON;
        ft.commit();
    }

    /**
     * 当页面选中时改变当前的导航栏蓝色和图片的状态
     * @param position 当前页面
     */
    public void SelectColor(int position) {
        if(position == 0){
            //设置图片颜色
            img_one_bottom.setImageResource(R.mipmap.broadcast_selected);
//            img_two_bottom.setColorFilter(Color.GRAY);
            img_three_bottom.setImageResource(R.mipmap.wode_select);
        } else if (position == 1){
            //设置图片颜色
            img_one_bottom.setColorFilter(Color.GRAY);
            img_two_bottom.setColorFilter(Color.BLUE);
            img_three_bottom.setColorFilter(Color.GRAY);
        } else if (position == 2){
            //设置图片颜色
            img_one_bottom.setImageResource(R.mipmap.broadcast_select);
//            img_two_bottom.setColorFilter(Color.GRAY);
            img_three_bottom.setImageResource(R.mipmap.wode_selected);
        }
    }

    /**
     * 点击切换下部按钮
     * @param page
     */
    private void changeTab(int page) {
        //默认的currentTab == 当前的页码，不做任何处理
        if (currentTab == page) {
            return;
        }

        //获取fragment的页码
        Fragment fragment = fragments.get(page);
        //fragment事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false。
        //当前activity中添加的不是这个fragment
        if(!fragment.isAdded()){
            //所以将他加进去
            ft.add(fragmentContentId,fragment);
        }
        //隐藏当前currentTab的
        ft.hide(fragments.get(currentTab));
        //显示现在page的
        ft.show(fragments.get(page));
        //设置当前currentTab底部的状态
        SelectColor(currentTab);
        //当前显示的赋值给currentTab
        currentTab = page;
        //设置当前currentTab底部的状态
        SelectColor(currentTab);
        //activity被销毁？  ！否
        if (!this.isFinishing()) {
            //允许状态丢失
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 所有的控件在这里进行点击（单击）事件
     * @param v
     */
    @OnClick({
            R.id.txt_one_bottom, R.id.txt_two_bottom, R.id.txt_three_bottom,
            R.id.img_one_bottom, R.id.img_two_bottom, R.id.img_three_bottom,
            R.id.Lin_one, R.id.Lin_two, R.id.Lin_three
    })
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.Lin_one || temdId == R.id.txt_one_bottom || temdId == R.id.img_one_bottom){
            changeTab(PAGE_COMMON);
            orderFragment.getData();
        }
//        else if (temdId == R.id.Lin_two || temdId == R.id.txt_two_bottom || temdId == R.id.img_two_bottom){
//            changeTab(PAGE_TRANSLUCENT);
//        }
        else if (temdId == R.id.Lin_three || temdId == R.id.txt_three_bottom || temdId == R.id.img_three_bottom){
            changeTab(PAGE_COORDINATOR);
            infoFragment.getData();
        }
    }

    long temptime;

    /**
     * 点击两次返回才退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN)) {
            if(System.currentTimeMillis() - temptime > 2000){
                System.out.println(Toast.LENGTH_LONG);
                Toast.makeText(this, "请在按一次返回退出", Toast.LENGTH_LONG).show();
                temptime = System.currentTimeMillis();
            } else {
                // 仿返回键退出界面,但不销毁，程序仍在后台运行
//                moveTaskToBack(false); // 关键的一行代码
                finishAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BannerLog.d("b_cc", "onDestroy");
//        finishAllActivity();
    }

}