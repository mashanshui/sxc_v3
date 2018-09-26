package com.sxcapp.www.CustomerView.SortListView;

/**
 * Created by wenleitao on 2017/7/5.
 */

public class SortModel {
//    显示的数据
    private String name ;
//    显示数据拼音的首字母
    private String sortLetters;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
