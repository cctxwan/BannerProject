package com.bangni.yzcm.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.utils.BannerUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 订单列表adapter
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    /** 上下文 */
    Activity context;

    /** 数据源 */
    List<OrderInfos.ListBean> data;

    /** 控件 */
    LayoutInflater inflater;

    /**
     * 这里的data作为数据源从activity传入
     * @param activity
     * @param datas
     */
    public OrderAdapter(Activity activity, List<OrderInfos.ListBean> datas){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_rv_item, parent, false);
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
        if(data.get(position).getStatus() == 1){
            viewHolder.order_item_state.bringToFront();
            viewHolder.order_item_state.setText("未开始...");
            viewHolder.order_item_state.setBackgroundResource(R.drawable.order_item_bg_yellow);

            viewHolder.order_item_name.setText(data.get(position).getAdName());
//            viewHolder.order_item_tfdw.setText("投放点位：暂无");
//            viewHolder.order_item_bgl.setText("曝光量：暂无");
//            viewHolder.order_item_ljbf.setText("播放量：暂无");
//            viewHolder.order_item_kssj.setText("开始时间：暂无");
//            viewHolder.order_item_jssj.setText("结束时间：暂无");

            viewHolder.order_item_tfdw.setText("投放点位：共" + data.get(position).getPutNum() + "个点位");
            viewHolder.order_item_bgl.setText("曝光量：" + data.get(position).getCumulativeNumber() + "（人次）");
            viewHolder.order_item_ljbf.setText("播放量：" + data.get(position).getCumulativePlay() + "(次)");
            viewHolder.order_item_kssj.setText("开始时间：" + BannerUtils.stampToDatessssss(String.valueOf(data.get(position).getStartTime())));
            viewHolder.order_item_jssj.setText("结束时间：" + BannerUtils.stampToDatessssss(String.valueOf(data.get(position).getEndTime())));
        }else {
            if(data.get(position).getStatus() == 2){
                viewHolder.order_item_state.setText("进行中...");

                viewHolder.order_item_state.setBackgroundResource(R.drawable.order_item_bg_blue);
            }else if(data.get(position).getStatus() == 3){
                viewHolder.order_item_state.setText("已结束");

                viewHolder.order_item_state.setBackgroundResource(R.drawable.order_item_bg_grey);
            }

            viewHolder.order_item_name.setText(data.get(position).getAdName());
            viewHolder.order_item_tfdw.setText("投放点位：共" + data.get(position).getPutNum() + "个点位");
            viewHolder.order_item_bgl.setText("曝光量：" + data.get(position).getCumulativeNumber() + "（人次）");
            viewHolder.order_item_ljbf.setText("播放量：" + data.get(position).getCumulativePlay() + "(次)");



            viewHolder.order_item_kssj.setText("开始时间：" + BannerUtils.stampToDatessssss(String.valueOf(data.get(position).getStartTime())));
            viewHolder.order_item_jssj.setText("结束时间：" + BannerUtils.stampToDatessssss(String.valueOf(data.get(position).getEndTime())));
        }



        //设置tag
        viewHolder.itemView.setTag(position);
    }

    /**
     * 数据源的内容大小
     * @return
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /** 获取item的控件 */
        public TextView order_item_state, order_item_name, order_item_tfdw, order_item_ljbf, order_item_kssj, order_item_jssj, order_item_bgl;
        public LinearLayout lin_order_rv;

        public ViewHolder(View rootView) {
            super(rootView);
            this.order_item_state = rootView.findViewById(R.id.order_item_state);
            this.order_item_name = rootView.findViewById(R.id.order_item_name);
            this.order_item_tfdw = rootView.findViewById(R.id.order_item_tfdw);
            this.order_item_bgl = rootView.findViewById(R.id.order_item_bgl);
            this.order_item_ljbf = rootView.findViewById(R.id.order_item_ljbf);
            this.order_item_kssj = rootView.findViewById(R.id.order_item_kssj);
            this.order_item_jssj = rootView.findViewById(R.id.order_item_jssj);

            this.lin_order_rv = rootView.findViewById(R.id.lin_order_rv);

            //加粗
            this.order_item_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


            //设置item的点击事件
            this.lin_order_rv.setOnClickListener(new View.OnClickListener() {
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
