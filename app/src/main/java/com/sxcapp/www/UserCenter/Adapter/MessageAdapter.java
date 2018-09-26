package com.sxcapp.www.UserCenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.MessageInfo;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.TimeFormat;

import java.util.List;

/**
 * 消息中心adapter
 * Created by wenleitao on 2017/8/12.
 */

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageInfo.ListBean> list;
    private static final int SYSANDMY_TYPE = 0;
    private static final int ACTIVITY_TYPE = 1;

    public MessageAdapter(Context context, List<MessageInfo.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<MessageInfo.ListBean> data) {
        list.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getAssortment() == null || "0".equals(list.get(position).getAssortment()) || list.get(position).getAssortment().isEmpty()) {
            return SYSANDMY_TYPE;
        } else {
            return ACTIVITY_TYPE;
        }
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
        ViewHolderSM viewHolderSM = null;
        ViewHolderActivity holderActivity = null;
        int type = getItemViewType(position);
        MessageInfo.ListBean bean = list.get(position);
        if (convertView == null) {
            switch (type) {
                case SYSANDMY_TYPE:
                    viewHolderSM = new ViewHolderSM();
                    convertView = LayoutInflater.from(context).inflate(R.layout.messagecenter_item, null);
                    viewHolderSM.title_tv = (TextView) convertView.findViewById(R.id.title_tv);
                    viewHolderSM.date_tv = (TextView) convertView.findViewById(R.id.date_tv);
                    viewHolderSM.hour_tv = (TextView) convertView.findViewById(R.id.hour_tv);
                    viewHolderSM.read_status_iv = (ImageView) convertView.findViewById(R.id.read_status_iv);
                    convertView.setTag(viewHolderSM);
                    break;
                case ACTIVITY_TYPE:
                    holderActivity = new ViewHolderActivity();
                    convertView = LayoutInflater.from(context).inflate(R.layout.activity_message_item, null);
                    holderActivity.activity_iv = (ImageView) convertView.findViewById(R.id.activity_iv);
                    convertView.setTag(holderActivity);
                    break;
                default:
                    break;
            }
        } else {
            switch (type) {
                case SYSANDMY_TYPE:
                    viewHolderSM = (ViewHolderSM) convertView.getTag();
                    break;
                case ACTIVITY_TYPE:
                    holderActivity = (ViewHolderActivity) convertView.getTag();
                    break;
                default:
                    break;
            }
        }
        switch (type) {
            case SYSANDMY_TYPE:
                viewHolderSM.title_tv.setText(bean.getTitle());
                viewHolderSM.date_tv.setText(TimeFormat.getDate(bean.getCreate_time() + ""));
                viewHolderSM.hour_tv.setText(TimeFormat.gethour(bean.getCreate_time() + ""));
                if (bean.getVisited() == 1) {
                    viewHolderSM.read_status_iv.setVisibility(View.GONE);
                } else {
                    viewHolderSM.read_status_iv.setVisibility(View.VISIBLE);
                }
                break;
            case ACTIVITY_TYPE:
                Glide.with(context).load(Properties.baseImageUrl + bean.getImage_path()).into(holderActivity.activity_iv);
                break;
            default:
                break;
        }
        return convertView;
    }

    class ViewHolderSM {
        TextView title_tv, date_tv, hour_tv;
        ImageView read_status_iv;
    }

    class ViewHolderActivity {
        ImageView activity_iv;
    }

}
