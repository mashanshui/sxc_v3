package com.sxcapp.www.Lease;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Bean.BannerBean;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.Properties;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/18.
 */

public class BannerPageAdapter extends PagerAdapter {
    private Context context ;
    private List<BannerBean> list;

    public BannerPageAdapter(Context context, List<BannerBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        Glide.with(context).load(Properties.baseImageUrl+list.get(position).getImage_path()).into(iv);
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);
    }

}
