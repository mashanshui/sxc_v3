package com.sxcapp.www.UserCenter.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class CouponBeanV3 implements Parcelable {


    /**
     * coupon_title : 电车分时满20减5
     * coupon_cut : 5.00
     * coupon_isuse : 0
     * coupon_termtime : 无期限
     * coupon_type_remark : 任意类型车型都可使用
     * coupon_isouttime : 0
     * coupon_type : 0
     */

    private String coupon_title;
    private String coupon_cut;
    private String coupon_isuse;
    private String coupon_termtime;
    private String coupon_type_remark;
    private String coupon_isouttime;
    private int coupon_type;

    protected CouponBeanV3(Parcel in) {
        coupon_title = in.readString();
        coupon_cut = in.readString();
        coupon_isuse = in.readString();
        coupon_termtime = in.readString();
        coupon_type_remark = in.readString();
        coupon_isouttime = in.readString();
        coupon_type = in.readInt();
    }

    public static final Creator<CouponBeanV3> CREATOR = new Creator<CouponBeanV3>() {
        @Override
        public CouponBeanV3 createFromParcel(Parcel in) {
            return new CouponBeanV3(in);
        }

        @Override
        public CouponBeanV3[] newArray(int size) {
            return new CouponBeanV3[size];
        }
    };

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

    public String getCoupon_isuse() {
        return coupon_isuse;
    }

    public void setCoupon_isuse(String coupon_isuse) {
        this.coupon_isuse = coupon_isuse;
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

    public String getCoupon_isouttime() {
        return coupon_isouttime;
    }

    public void setCoupon_isouttime(String coupon_isouttime) {
        this.coupon_isouttime = coupon_isouttime;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coupon_title);
        dest.writeString(coupon_cut);
        dest.writeString(coupon_isuse);
        dest.writeString(coupon_termtime);
        dest.writeString(coupon_type_remark);
        dest.writeString(coupon_isouttime);
        dest.writeInt(coupon_type);
    }
}
