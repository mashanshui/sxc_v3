package com.sxcapp.www.CustomerView.SortListView;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by wenleitao on 2017/7/5.
 */

public class PinyinComparator implements Comparator<SortModel> ,Serializable{
    @Override
    public int compare(SortModel o1, SortModel o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if ("#".equals(o2.getSortLetters())) {
            return -1;
        } else if ("#".equals(o1.getSortLetters())) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }


}
