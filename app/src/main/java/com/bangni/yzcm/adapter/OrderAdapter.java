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
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 订单列表adapter
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    /** 上下文 */
    Activity context;

    /** 数据源 */
    List<OrderInfos> data;

    /** 控件 */
    LayoutInflater inflater;

    /**
     * 这里的data作为数据源从activity传入
     * @param activity
     * @param datas
     */
    public OrderAdapter(Activity activity, List<OrderInfos> datas){
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
        public TextView order_item_state, order_item_name, order_item_tfdw, order_item_ljll, order_item_ljbf, order_item_kssj;
        public LinearLayout lin_order_rv;

        public ViewHolder(View rootView) {
            super(rootView);
            this.order_item_state = rootView.findViewById(R.id.order_item_state);
            this.order_item_name = rootView.findViewById(R.id.order_item_name);
            this.order_item_tfdw = rootView.findViewById(R.id.order_item_tfdw);
            this.order_item_ljll = rootView.findViewById(R.id.order_item_ljll);
            this.order_item_ljbf = rootView.findViewById(R.id.order_item_ljbf);
            this.order_item_kssj = rootView.findViewById(R.id.order_item_kssj);

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
