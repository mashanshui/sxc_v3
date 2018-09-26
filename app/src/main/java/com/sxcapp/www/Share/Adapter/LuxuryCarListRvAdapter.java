package com.sxcapp.www.Share.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.LuxuryCarListByStoreBeanV3;
import com.sxcapp.www.Util.AndroidTool;

import java.util.List;


/**
 * Created by wenleitao on 2018/5/7.
 */

public class LuxuryCarListRvAdapter extends RecyclerView.Adapter<LuxuryCarListRvAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<LuxuryCarListByStoreBeanV3.CarListBean> list;

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

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public LuxuryCarListRvAdapter(Context context, List<LuxuryCarListByStoreBeanV3.CarListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.luxury_car_vp_item, null);
        MyViewHolder viewHolder = new MyViewHolder(v);
        v.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int width = AndroidTool.getDeviceWidth((Activity) context) - AndroidTool.dip2px(context, 82);
        LuxuryCarImagePageAdapterV3 adapterV3 = new LuxuryCarImagePageAdapterV3(context, list.get(position).getCar_image(), position);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width / 2);
        layoutParams.setMargins(AndroidTool.dip2px(context, 12), AndroidTool.dip2px(context, 10), AndroidTool.dip2px(context, 50), 0);
        holder.car_vp.setClipChildren(false);
        holder.car_vp.setLayoutParams(layoutParams);
        holder.car_vp.setAdapter(adapterV3);
        holder.car_info_tv.setText(list.get(position).getCar_name() + " " + list.get(position).getSettings());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ViewPager car_vp;
        private TextView car_info_tv;


        public MyViewHolder(View itemView) {
            super(itemView);
            car_info_tv =  itemView.findViewById(R.id.car_info_tv);
            car_vp =  itemView.findViewById(R.id.car_vp);
        }
    }
}
