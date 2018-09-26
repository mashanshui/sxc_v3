package com.sxcapp.www.Share.ShareActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.StoreByCity;
import com.sxcapp.www.Share.Bean.TTLottoTypeBean;
import com.sxcapp.www.Share.Bean.TTStoreBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 共享车选择门店adapter
 * Created by wenleitao on 2017/7/31.
 */

public class StoreAdapter extends BaseAdapter {
    private Context context;
    private List<TTLottoTypeBean.ListBean.StoreListBean> list;

    public StoreAdapter(Context context, List<TTLottoTypeBean.ListBean.StoreListBean> list) {
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
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.indaycity_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.store_tv.setText(list.get(position).getStore_name());

        return convertView;
    }

    static class Holder {
        @BindView(R.id.city_tv)
        TextView store_tv;

        public Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
