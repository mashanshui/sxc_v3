package com.sxcapp.www.Share.ShareActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.TTLottoTypeBean;
import com.sxcapp.www.Share.Bean.TTTypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenleitao on 2017/11/18.
 */

public class TypeAdapter extends BaseAdapter {
    private Context context;
    private List<TTLottoTypeBean.ListBean.StoreListBean.CouponListBean> list;
    private int selectedPosition = -1;

    public TypeAdapter(Context context, List<TTLottoTypeBean.ListBean.StoreListBean.CouponListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.indaycity_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.city_tv.setText(list.get(position).getType_name());
        if (selectedPosition == position) {
            holder.city_tv.setBackgroundResource(R.color.lightWhite1);
            holder.city_tv.setTextColor(context.getResources().getColor(R.color.darkBlack));
        } else if (selectedPosition != position) {
            holder.city_tv.setBackgroundResource(R.color.white);
            holder.city_tv.setTextColor(context.getResources().getColor(R.color.lightGray3));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.city_tv)
        TextView city_tv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
