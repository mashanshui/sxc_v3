package com.sxcapp.www.Share.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.LuxuryCarListByStoreBeanV3;
import com.sxcapp.www.Util.AndroidTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenleitao on 2018/4/27.
 */

public class LuxuryCarListAdapterV3 extends BaseAdapter {
    private Context context;
    private List<LuxuryCarListByStoreBeanV3.CarListBean> list;

    public LuxuryCarListAdapterV3(Context context, List<LuxuryCarListByStoreBeanV3.CarListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.luxury_car_vp_item, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        LuxuryCarListByStoreBeanV3.CarListBean bean = list.get(position);
        holder.car_vp.setPageMargin(AndroidTool.dip2px(context, 20));
        LuxuryCarImagePageAdapterV3 adapterV3 = new LuxuryCarImagePageAdapterV3(context, list.get(position).getCar_image(), position);
        holder.car_vp.setAdapter(adapterV3);
        holder.car_info_tv.setText(bean.getCar_name() + " " + bean.getSettings());
        return convertView;
    }

    static class MyViewHolder {
        @BindView(R.id.car_vp)
        ViewPager car_vp;
        @BindView(R.id.car_info_tv)
        TextView car_info_tv;

        public MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
