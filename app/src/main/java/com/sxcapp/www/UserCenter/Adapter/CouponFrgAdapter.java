package com.sxcapp.www.UserCenter.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.sxcapp.www.UserCenter.MessageFragment;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/12.
 */

public class CouponFrgAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public CouponFrgAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
