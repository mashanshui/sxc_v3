package com.sxcapp.www.UserCenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.EventListBean;
import com.sxcapp.www.Util.Properties;

/**
 * Created by wenleitao on 2017/12/15.
 */

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private EventListBean listBean;

    public EventListAdapter(Context context, EventListBean listBean) {
        this.context = context;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        return listBean.getList().size() > 0 ? listBean.getList().size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listBean.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.eventlist_item, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.activity_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Properties.baseImageUrl+listBean.getList().get(position).getImage_url()).into(holder.iv);
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
    }
}
