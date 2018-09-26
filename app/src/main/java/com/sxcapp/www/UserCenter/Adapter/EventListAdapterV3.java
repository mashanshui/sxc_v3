package com.sxcapp.www.UserCenter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.EventListByTypeBeanV3;

/**
 * Created by wenleitao on 2018/5/17.
 */

public class EventListAdapterV3 extends RecyclerView.Adapter<EventListAdapterV3.MyHolder> implements View.OnClickListener {

    private Context context;
    private EventListByTypeBeanV3 beanV3;

    public EventListAdapterV3(Context context, EventListByTypeBeanV3 beanV3) {
        this.context = context;
        this.beanV3 = beanV3;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_item_v3, parent,false);
        MyHolder holder = new MyHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tittle_tv.setText("活动名称:" + beanV3.getActivity().get(position).getTitle());
        holder.time_tv.setText("活动时间:" + beanV3.getActivity().get(position).getCreateTime() + "—" + beanV3.getActivity().get(position).getExpireTime());
        Glide.with(context).load(beanV3.getActivity().get(position).getImageUrl()).into(holder.iv);
        holder.itemView.setTag(position);
        if (position == (beanV3.getActivity().size() - 1)) {
            holder.divide_view.setVisibility(View.GONE);
        } else {
            holder.divide_view.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return beanV3.getActivity().size() > 0 ? beanV3.getActivity().size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tittle_tv, time_tv;
        private View divide_view;

        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tittle_tv = (TextView) itemView.findViewById(R.id.tittle_tv);
            time_tv = (TextView) itemView.findViewById(R.id.time_tv);
            divide_view = itemView.findViewById(R.id.divide_view);
        }
    }
}
