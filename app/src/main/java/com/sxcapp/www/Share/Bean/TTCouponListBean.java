package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/3/14.
 */

public class TTCouponListBean {

    /**
     * store_id : ee8e536a06054cce8656bbc4a443c4ef
     * couponList : [{"coupon_title":"满10元减5元","coupon_id":"56945df4272b11e8a47800163e1ae70a","coupon":{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"56945df4272b11e8a47800163e1ae70a","store_id":"ee8e536a06054cce8656bbc4a443c4ef","full_cost":10,"cut_cost":5,"create_time":"2018-03-14 09:59:32","update_time":"2018-03-14 09:59:32","state":"1","remark":null,"term_time":"2018-11-15","max_num":5,"store_name":"合肥门店","type":"1"}},{"coupon_title":"满10元减2元","coupon_id":"4d693cab272b11e8a47800163e1ae70a","coupon":{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"4d693cab272b11e8a47800163e1ae70a","store_id":"ee8e536a06054cce8656bbc4a443c4ef","full_cost":10,"cut_cost":2,"create_time":"2018-03-14 09:59:17","update_time":"2018-03-14 09:59:17","state":"1","remark":null,"term_time":"2018-03-20","max_num":5,"store_name":"合肥门店","type":"1"}},{"coupon_title":"满150元减150元","coupon_id":"27f31b55268b11e8a47800163e1ae70a","coupon":{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"27f31b55268b11e8a47800163e1ae70a","store_id":"ee8e536a06054cce8656bbc4a443c4ef","full_cost":150,"cut_cost":150,"create_time":"2018-03-13 14:52:54","update_time":"2018-03-14 15:08:27","state":"1","remark":null,"term_time":"2018-07-12","max_num":1,"store_name":"合肥门店","type":"1"}},{"coupon_title":"满10元减1元","coupon_id":"42a964f8227911e8a47800163e1ae70a","coupon":{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"42a964f8227911e8a47800163e1ae70a","store_id":"ee8e536a06054cce8656bbc4a443c4ef","full_cost":10,"cut_cost":1,"create_time":"2018-03-08 10:36:29","update_time":"2018-03-14 09:57:58","state":"1","remark":null,"term_time":"2018-12-05","max_num":10,"store_name":"合肥门店","type":"1"}}]
     * store_name : 合肥门店
     * remark : ["每名用户每天2次抽奖机会","合肥市地区优惠券活动","活动周期：2018年3月18日至2018年5月1日,抽奖券不可转让、不可返现、过期作废。","春游每日抽奖活动不与首单限免活动冲突，可同时参与。","最终解释权归本平台所有"]
     */

    private String store_id;
    private String store_name;
    private List<CouponListBean> couponList;
    private List<String> remark;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public List<CouponListBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponListBean> couponList) {
        this.couponList = couponList;
    }

    public List<String> getRemark() {
        return remark;
    }

    public void setRemark(List<String> remark) {
        this.remark = remark;
    }

    public static class CouponListBean {
        /**
         * coupon_title : 满10元减5元
         * coupon_id : 56945df4272b11e8a47800163e1ae70a
         * coupon : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"56945df4272b11e8a47800163e1ae70a","store_id":"ee8e536a06054cce8656bbc4a443c4ef","full_cost":10,"cut_cost":5,"create_time":"2018-03-14 09:59:32","update_time":"2018-03-14 09:59:32","state":"1","remark":null,"term_time":"2018-11-15","max_num":5,"store_name":"合肥门店","type":"1"}
         */

        private String coupon_title;
        private String coupon_id;
        private CouponBean coupon;

        public String getCoupon_title() {
            return coupon_title;
        }

        public void setCoupon_title(String coupon_title) {
            this.coupon_title = coupon_title;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public static class CouponBean {
            /**
             * map : {}
             * relationModelList : []
             * orderByModelList : []
             * cnd_type : default
             * ob_type : default
             * id : 56945df4272b11e8a47800163e1ae70a
             * store_id : ee8e536a06054cce8656bbc4a443c4ef
             * full_cost : 10
             * cut_cost : 5
             * create_time : 2018-03-14 09:59:32
             * update_time : 2018-03-14 09:59:32
             * state : 1
             * remark : null
             * term_time : 2018-11-15
             * max_num : 5
             * store_name : 合肥门店
             * type : 1
             */

            private MapBean map;
            private String cnd_type;
            private String ob_type;
            private String id;
            private String store_id;
            private int full_cost;
            private int cut_cost;
            private String create_time;
            private String update_time;
            private String state;
            private Object remark;
            private String term_time;
            private int max_num;
            private String store_name;
            private String type;
            private List<?> relationModelList;
            private List<?> orderByModelList;

            public MapBean getMap() {
                return map;
            }

            public void setMap(MapBean map) {
                this.map = map;
            }

            public String getCnd_type() {
                return cnd_type;
            }

            public void setCnd_type(String cnd_type) {
                this.cnd_type = cnd_type;
            }

            public String getOb_type() {
                return ob_type;
            }

            public void setOb_type(String ob_type) {
                this.ob_type = ob_type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public int getFull_cost() {
                return full_cost;
            }

            public void setFull_cost(int full_cost) {
                this.full_cost = full_cost;
            }

            public int getCut_cost() {
                return cut_cost;
            }

            public void setCut_cost(int cut_cost) {
                this.cut_cost = cut_cost;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getTerm_time() {
                return term_time;
            }

            public void setTerm_time(String term_time) {
                this.term_time = term_time;
            }

            public int getMax_num() {
                return max_num;
            }

            public void setMax_num(int max_num) {
                this.max_num = max_num;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<?> getRelationModelList() {
                return relationModelList;
            }

            public void setRelationModelList(List<?> relationModelList) {
                this.relationModelList = relationModelList;
            }

            public List<?> getOrderByModelList() {
                return orderByModelList;
            }

            public void setOrderByModelList(List<?> orderByModelList) {
                this.orderByModelList = orderByModelList;
            }

            public static class MapBean {
            }
        }
    }
}
