package com.bangni.yzcm.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.network.bean.RecordInfos;
import java.util.List;

/**
 * 监播列表adapter
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    /** 上下文 */
    Activity context;

    /** 数据源 */
    List<RecordInfos> data;

    /** 控件 */
    LayoutInflater inflater;

    /**
     * 这里的data作为数据源从activity传入
     * @param activity
     * @param datas
     */
    public RecordAdapter(Activity activity, List<RecordInfos> datas){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_rv_item, parent, false);
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
        public TextView txt_record_content, txt_record_title;
        public LinearLayout lin_record;

        public ViewHolder(View rootView) {
            super(rootView);
            this.txt_record_content = rootView.findViewById(R.id.txt_record_content);
            this.txt_record_title = rootView.findViewById(R.id.txt_record_title);
            this.lin_record = rootView.findViewById(R.id.lin_record);


            //加粗
            this.txt_record_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


            //设置item的点击事件
            this.lin_record.setOnClickListener(new View.OnClickListener() {
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
