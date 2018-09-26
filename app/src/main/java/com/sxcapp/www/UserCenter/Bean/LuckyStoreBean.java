package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/12/14.
 */

public class LuckyStoreBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * city_name : 合肥市
         * store_list : [{"store_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","store_name":"合肥门店"}]
         * city_id : a3cc3845f8a011e69c07408d5c04d4aa
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
             * store_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
             * store_name : 合肥门店
             */

            private String store_id;
            private String store_name;

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
        }
    }
}
