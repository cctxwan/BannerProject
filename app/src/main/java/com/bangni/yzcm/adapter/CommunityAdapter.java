package com.bangni.yzcm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.network.bean.CommunityInfos;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 社区列表adapter
 */
public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    /** 上下文 */
    Activity context;

    /** 数据源 */
    List<CommunityInfos> data;

    /** 控件 */
    LayoutInflater inflater;

    /**
     * 这里的data作为数据源从activity传入
     * @param activity
     * @param datas
     */
    public CommunityAdapter(Activity activity, List<CommunityInfos> datas){
        this.context = activity;
        this.data = datas;

        //获取布局
        inflater = LayoutInflater.from(activity);


    }


    /**
     * 加载布局，相当于activity的onCreate方法
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_rv_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 绑定数据
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        //赋值

        //设置tag
        viewHolder.itemView.setTag(position);
    }

    /**
     * 数据源的内容大小
     * @return
     */
    @Override
    public int getItemCount() {
        return 20;
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /** 获取item的控件 */
        public TextView community_rv_txt_name, community_rv_txt_wz;
        public LinearLayout lin_community_item;

        public ViewHolder(View rootView) {
            super(rootView);
            this.lin_community_item = rootView.findViewById(R.id.lin_community_item);

            this.community_rv_txt_name = rootView.findViewById(R.id.community_rv_txt_name);
            this.community_rv_txt_wz = rootView.findViewById(R.id.community_rv_txt_wz);



            //设置item的点击事件
//            this.lin_community_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Linster.textItemOnClick(v, getPosition());
//                }
//            });
        }
    }

    public ItemOnClickLinster Linster;

    public void setLinster(ItemOnClickLinster linster) {
        Linster = linster;
    }

    public interface ItemOnClickLinster{
        void textItemOnClick(View view, int position);
    }

}
