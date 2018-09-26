package com.sxcapp.www.Lease;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/12.
 */

public class CarFrgAdapter extends FragmentPagerAdapter {
    private List<CarListFragment>list;
    CarListFragment currentFragment;

    public CarFrgAdapter(FragmentManager fm, List<CarListFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragment = (CarListFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public CarListFragment getCurrentFragment() {
        return currentFragment;
    }
}
