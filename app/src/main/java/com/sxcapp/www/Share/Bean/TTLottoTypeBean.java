package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/3/14.
 * 二月二龙抬头抽奖类别实体类
 */

public class TTLottoTypeBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * city_name : 赤峰市
         * store_list : [{"store_id":"c788a63f83ba4f2ea32cc7b5bb01f0c9","store_name":"内蒙古赤峰店","coupon_list":[{"type_name":"长租","type":"3"},{"type_name":"分时","type":"1"}]}]
         * city_id : a3cc14a2f8a011e69c07408d5c04d4aa
         */

        private String city_name;
        private String city_id;
        private List<StoreListBean> store_list;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public List<StoreListBean> getStore_list() {
            return store_list;
        }

        public void setStore_list(List<StoreListBean> store_list) {
            this.store_list = store_list;
        }

        public static class StoreListBean {
            /**
             * store_id : c788a63f83ba4f2ea32cc7b5bb01f0c9
             * store_name : 内蒙古赤峰店
             * coupon_list : [{"type_name":"长租","type":"3"},{"type_name":"分时","type":"1"}]
             */

            private String store_id;
            private String store_name;
            private List<CouponListBean> coupon_list;

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

            public List<CouponListBean> getCoupon_list() {
                return coupon_list;
            }

            public void setCoupon_list(List<CouponListBean> coupon_list) {
                this.coupon_list = coupon_list;
            }

            public static class CouponListBean {
                /**
                 * type_name : 长租
                 * type : 3
                 */

                private String type_name;
                private String type;

                public String getType_name() {
                    return type_name;
                }

                public void setType_name(String type_name) {
                    this.type_name = type_name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }
}
