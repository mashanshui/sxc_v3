package com.sxcapp.www.Share.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wenleitao on 2017/9/7.
 */

public class ShareInDayNeedInfoBean implements Parcelable{
    private String fetch_store_name;
    private String g_store_name;
    private String fetch_store_id;
    private String g_store_id;
    private String share_in_day_type;
    private String deduction_info;
    private String cost_info;
    private String day_type;

    public ShareInDayNeedInfoBean(String fetch_store_name, String g_store_name, String fetch_store_id, String g_store_id, String share_in_day_type, String deduction_info, String cost_info, String day_type) {
        this.fetch_store_name = fetch_store_name;
        this.g_store_name = g_store_name;
        this.fetch_store_id = fetch_store_id;
        this.g_store_id = g_store_id;
        this.share_in_day_type = share_in_day_type;
        this.deduction_info = deduction_info;
        this.cost_info = cost_info;
        this.day_type = day_type;
    }

    protected ShareInDayNeedInfoBean(Parcel in) {
        fetch_store_name = in.readString();
        g_store_name = in.readString();
        fetch_store_id = in.readString();
        g_store_id = in.readString();
        share_in_day_type = in.readString();
        deduction_info = in.readString();
        cost_info = in.readString();
        day_type = in.readString();
    }

    public static final Creator<ShareInDayNeedInfoBean> CREATOR = new Creator<ShareInDayNeedInfoBean>() {
        @Override
        public ShareInDayNeedInfoBean createFromParcel(Parcel in) {
            return new ShareInDayNeedInfoBean(in);
        }

        @Override
        public ShareInDayNeedInfoBean[] newArray(int size) {
            return new ShareInDayNeedInfoBean[size];
        }
    };

    public String getFetch_store_name() {
        return fetch_store_name;
    }

    public void setFetch_store_name(String fetch_store_name) {
        this.fetch_store_name = fetch_store_name;
    }

    public String getG_store_name() {
        return g_store_name;
    }

    public void setG_store_name(String g_store_name) {
        this.g_store_name = g_store_name;
    }

    public String getFetch_store_id() {
        return fetch_store_id;
    }

    public void setFetch_store_id(String fetch_store_id) {
        this.fetch_store_id = fetch_store_id;
    }

    public String getG_store_id() {
        return g_store_id;
    }

    public void setG_store_id(String g_store_id) {
        this.g_store_id = g_store_id;
    }

    public String getShare_in_day_type() {
        return share_in_day_type;
    }

    public void setShare_in_day_type(String share_in_day_type) {
        this.share_in_day_type = share_in_day_type;
    }

    public String getDeduction_info() {
        return deduction_info;
    }

    public void setDeduction_info(String deduction_info) {
        this.deduction_info = deduction_info;
    }

    public String getCost_info() {
        return cost_info;
    }

    public void setCost_info(String cost_info) {
        this.cost_info = cost_info;
    }

    public String getDay_type() {
        return day_type;
    }

    public void setDay_type(String day_type) {
        this.day_type = day_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fetch_store_name);
        dest.writeString(g_store_name);
        dest.writeString(fetch_store_id);
        dest.writeString(g_store_id);
        dest.writeString(share_in_day_type);
        dest.writeString(deduction_info);
        dest.writeString(cost_info);
        dest.writeString(day_type);
    }
}
