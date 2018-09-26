package com.sxcapp.www.UserCenter.Bean;

/**
 * Created by wenleitao on 2017/12/14.
 */

public class CouponBean {

    /**
     * order_no :
     * term_time : 2017-12-31
     * coupon_title : 满150元减15元
     * cut_cost : 15
     * use_type : 1
     * use_time :
     * is_use : 0
     * coupon_type : 2
     * full_cost : 150
     */

    private String order_no;
    private String term_time;
    private String coupon_title;
    private int cut_cost;
    private int use_type;
    private String use_time;
    private String is_use;
    private int coupon_type;
    private int full_cost;


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTerm_time() {
        return term_time;
    }

    public void setTerm_time(String term_time) {
        this.term_time = term_time;
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public int getCut_cost() {
        return cut_cost;
    }

    public void setCut_cost(int cut_cost) {
        this.cut_cost = cut_cost;
    }

    public int getUse_type() {
        return use_type;
    }

    public void setUse_type(int use_type) {
        this.use_type = use_type;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    public int getFull_cost() {
        return full_cost;
    }

    public void setFull_cost(int full_cost) {
        this.full_cost = full_cost;
    }
}
