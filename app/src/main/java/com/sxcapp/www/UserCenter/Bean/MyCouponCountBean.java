package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/12/14.
 */

public class MyCouponCountBean {

    /**
     * maxSurplusNum : 8
     * couponList : [{"term_time":"2017-12-31","coupon_title":"满15元减1元","cut_cost":1,"surplus_num":"2","coupon_id":"07539809df1911e7ac4900163e1ae70a","type":"1","full_cost":15,"max_num":"4"},{"term_time":"2017-12-31","coupon_title":"满25元减3元","cut_cost":3,"surplus_num":"3","coupon_id":"8aa519addf1a11e7ac4900163e1ae70a","type":"1","full_cost":25,"max_num":"3"},{"term_time":"2017-12-31","coupon_title":"满35元减5元","cut_cost":5,"surplus_num":"2","coupon_id":"bf8b4f69df1a11e7ac4900163e1ae70a","type":"1","full_cost":35,"max_num":"2"},{"term_time":"2017-12-31","coupon_title":"满45元减8元","cut_cost":8,"surplus_num":"1","coupon_id":"c58b77c7df1a11e7ac4900163e1ae70a","type":"1","full_cost":45,"max_num":"1"}]
     * surplusNum : 200
     */

    private int maxSurplusNum;
    private int surplusNum;
    private List<CouponListBean> couponList;

    public int getMaxSurplusNum() {
        return maxSurplusNum;
    }

    public void setMaxSurplusNum(int maxSurplusNum) {
        this.maxSurplusNum = maxSurplusNum;
    }

    public int getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(int surplusNum) {
        this.surplusNum = surplusNum;
    }

    public List<CouponListBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponListBean> couponList) {
        this.couponList = couponList;
    }

    public static class CouponListBean {
        /**
         * term_time : 2017-12-31
         * coupon_title : 满15元减1元
         * cut_cost : 1
         * surplus_num : 2
         * coupon_id : 07539809df1911e7ac4900163e1ae70a
         * type : 1
         * full_cost : 15
         * max_num : 4
         */

        private String term_time;
        private String coupon_title;
        private int cut_cost;
        private int surplus_num;
        private String coupon_id;
        private String type;
        private int full_cost;
        private int max_num;

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

        public int getSurplus_num() {
            return surplus_num;
        }

        public void setSurplus_num(int surplus_num) {
            this.surplus_num = surplus_num;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getFull_cost() {
            return full_cost;
        }

        public void setFull_cost(int full_cost) {
            this.full_cost = full_cost;
        }

        public int getMax_num() {
            return max_num;
        }

        public void setMax_num(int max_num) {
            this.max_num = max_num;
        }
    }
}
