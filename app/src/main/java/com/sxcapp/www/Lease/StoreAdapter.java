package com.sxcapp.www.Lease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sxcapp.www.Lease.Bean.Store;
import com.sxcapp.www.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 门店列表adapter
 * Created by wenleitao on 2017/7/11.
 */

public class StoreAdapter extends BaseAdapter {
    private Context context;
    private List<Store> list;

    public StoreAdapter(Context context, List<Store> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.store_item, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(list.get(i).getStore_name());
        holder.tv_address.setText("地址:" + list.get(i).getAddress_details());
        holder.tv_phone.setText("热线电话:" + list.get(i).getPhone());
        return convertView;
    }

    static class MyViewHolder {
        @BindView(R.id.store_name_tv)
        TextView tv_name;
        @BindView(R.id.address_tv)
        TextView tv_address;
        @BindView(R.id.phone_tv)
        TextView tv_phone;

        public MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
