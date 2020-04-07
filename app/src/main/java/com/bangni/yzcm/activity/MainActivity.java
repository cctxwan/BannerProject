package com.bangni.yzcm.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.fragment.OrderFragment;
import com.bangni.yzcm.fragment.InfoFragment;
import com.bangni.yzcm.fragment.BroadCastFragment;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


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
            img_one_bottom.setColorFilter(Color.BLUE);
            img_two_bottom.setColorFilter(Color.GRAY);
            img_three_bottom.setColorFilter(Color.GRAY);
        } else if (position == 1){
            //设置图片颜色
            img_one_bottom.setColorFilter(Color.GRAY);
            img_two_bottom.setColorFilter(Color.BLUE);
            img_three_bottom.setColorFilter(Color.GRAY);
        } else if (position == 2){
            //设置图片颜色
            img_one_bottom.setColorFilter(Color.GRAY);
            img_two_bottom.setColorFilter(Color.GRAY);
            img_three_bottom.setColorFilter(Color.BLUE);
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

}
