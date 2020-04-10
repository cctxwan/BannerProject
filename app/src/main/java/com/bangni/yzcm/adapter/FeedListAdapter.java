package com.bangni.yzcm.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bangni.yzcm.R;
import com.bangni.yzcm.network.bean.FeedBookListModel;
import com.bangni.yzcm.utils.BannerUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 反馈记录列表adapter
 */
public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    /** 上下文 */
    Activity context;

    /** 数据源 */
    List<FeedBookListModel.ListBean> data;

    /** 控件 */
    LayoutInflater inflater;

    /**
     * 这里的data作为数据源从activity传入
     * @param activity
     * @param datas
     */
    public FeedListAdapter(Activity activity, List<FeedBookListModel.ListBean> datas){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_feedlist_item, parent, false);
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
        viewHolder.txt_feedlist_time.setText(BannerUtils.stampToDate(data.get(position).getFeedbackTime() + ""));
        viewHolder.txt_feedlist_content.setText(data.get(position).getFeedbackContent());

        if(TextUtils.isEmpty(data.get(position).getReplyContent())){
            viewHolder.txt_feedlist_kf_content.setText("暂无");
        }else{
            viewHolder.txt_feedlist_kf_content.setText(data.get(position).getReplyContent());
        }



        if(TextUtils.isEmpty(data.get(position).getReplyTime() + "")){
            viewHolder.txt_feedlist_kf_time.setVisibility(View.GONE);
        }else{
            viewHolder.txt_feedlist_kf_time.setText(BannerUtils.stampToDate(data.get(position).getReplyTime() + ""));
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
        public TextView broadcast_rv_txt_name, txt_feedlist_time, txt_feedlist_content, txt_feedlist_kf_time, txt_feedlist_kf_content;

        public ViewHolder(View rootView) {
            super(rootView);
            this.txt_feedlist_time = rootView.findViewById(R.id.txt_feedlist_time);
            this.txt_feedlist_content = rootView.findViewById(R.id.txt_feedlist_content);
            this.txt_feedlist_kf_time = rootView.findViewById(R.id.txt_feedlist_kf_time);
            this.txt_feedlist_kf_content = rootView.findViewById(R.id.txt_feedlist_kf_content);

            this.broadcast_rv_txt_name = rootView.findViewById(R.id.broadcast_rv_txt_name);



            //设置item的点击事件
//            this.lin_broadcast_item.setOnClickListener(new View.OnClickListener() {
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
