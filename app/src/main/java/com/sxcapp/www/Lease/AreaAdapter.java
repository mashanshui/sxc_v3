package com.sxcapp.www.Lease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sxcapp.www.Lease.Bean.Area;
import com.sxcapp.www.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenleitao on 2017/7/10.
 */

public class AreaAdapter extends BaseAdapter {
    private Context context;
    private List<Area> list;
    private int selectedPosition = 0;

    public AreaAdapter(Context context, List<Area> list) {
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
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.area_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(i).getName());
        if (selectedPosition == i) {
            holder.tv.setBackgroundResource(R.color.white);
            holder.tv.setTextColor(context.getResources().getColor(R.color.darkBlack));
        } else if (selectedPosition != i) {
            holder.tv.setBackgroundResource(R.color.lightWhite1);
            holder.tv.setTextColor(context.getResources().getColor(R.color.lightGray3));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.area_tv)
        TextView tv;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
