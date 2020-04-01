package com.bangni.yzcm.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.network.bean.BroadcastInfos;
import java.util.List;

/**
 * 监播列表adapter
 */
public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.ViewHolder> {

    /** 上下文 */
    Activity context;

    /** 数据源 */
    List<BroadcastInfos> data;

    /** 控件 */
    LayoutInflater inflater;

    /**
     * 这里的data作为数据源从activity传入
     * @param activity
     * @param datas
     */
    public BroadcastAdapter(Activity activity, List<BroadcastInfos> datas){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.broadcast_rv_item, parent, false);
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
        public TextView broadcast_rv_txt_name;
        public LinearLayout lin_broadcast_item;

        public ViewHolder(View rootView) {
            super(rootView);
            this.lin_broadcast_item = rootView.findViewById(R.id.lin_broadcast_item);

            this.broadcast_rv_txt_name = rootView.findViewById(R.id.broadcast_rv_txt_name);



            //设置item的点击事件
            this.lin_broadcast_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Linster.textItemOnClick(v, getPosition());
                }
            });
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
