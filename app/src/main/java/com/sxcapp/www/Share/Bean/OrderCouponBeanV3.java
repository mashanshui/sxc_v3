package com.sxcapp.www.Share.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class OrderCouponBeanV3 {

    private String coupon_title;
    private String coupon_cut;
    private String coupon_termtime;
    private String coupon_type_remark;
    private int coupon_type;
    private String coupon_id;

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public String getCoupon_cut() {
        return coupon_cut;
    }

    public void setCoupon_cut(String coupon_cut) {
        this.coupon_cut = coupon_cut;
    }


    public String getCoupon_termtime() {
        return coupon_termtime;
    }

    public void setCoupon_termtime(String coupon_termtime) {
        this.coupon_termtime = coupon_termtime;
    }

    public String getCoupon_type_remark() {
        return coupon_type_remark;
    }

    public void setCoupon_type_remark(String coupon_type_remark) {
        this.coupon_type_remark = coupon_type_remark;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }
}
