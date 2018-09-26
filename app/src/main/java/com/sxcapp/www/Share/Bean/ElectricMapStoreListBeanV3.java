package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/14.
 */

public class ElectricMapStoreListBeanV3 {

    private List<ListBeanXX> list;

    public List<ListBeanXX> getList() {
        return list;
    }

    public void setList(List<ListBeanXX> list) {
        this.list = list;
    }

    public static class ListBeanXX {
        /**
         * city_name : 天津市
         * latitude : 39.12
         * list : [{"city_name":"南开区","latitude":"39.13 ","list":[{"store_id":"1653f18ce8ab4d75ad94bef4573297a2","latitude":"39.065112","store_name":"凌奥渔夫码头网点","car_num":"2","longitude":"117.168311"},{"store_id":"ba1b758a352047f0afe53f1e142a9790","latitude":"39.07438727","store_name":"南翠屏公园网点","car_num":"3","longitude":"117.15428223"},{"store_id":"d5995958477e4555b13c371329a2046b","latitude":"39.10771612","store_name":"天津南开店","car_num":"3","longitude":"117.15601804"},{"store_id":"f6945a7b8c974087a6657b67c714b009","latitude":"39.09859639","store_name":"王顶堤如家网点","car_num":"1","longitude":"117.14875162"}],"longitude":"117.15 ","city_id":"a3d2959cf8a011e69c07408d5c04d4aa"},{"city_name":"西青区","latitude":"39.13 ","list":[{"store_id":"6f0b9a79443649089841b766ae646b60","latitude":"39.101617","store_name":"天津市西青区侯台农贸市场停车场","car_num":"2","longitude":"117.115728"}],"longitude":"117.00 ","city_id":"a3d2a69df8a011e69c07408d5c04d4aa"}]
         * longitude : 117.20
         * city_id : a3cc08c2f8a011e69c07408d5c04d4aa
         */

        private String city_name;
        private double latitude;
        private double longitude;
        private String city_id;
        private List<ListBeanX> list;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class ListBeanX {
            /**
             * city_name : 南开区
             * latitude : 39.13
             * list : [{"store_id":"1653f18ce8ab4d75ad94bef4573297a2","latitude":"39.065112","store_name":"凌奥渔夫码头网点","car_num":"2","longitude":"117.168311"},{"store_id":"ba1b758a352047f0afe53f1e142a9790","latitude":"39.07438727","store_name":"南翠屏公园网点","car_num":"3","longitude":"117.15428223"},{"store_id":"d5995958477e4555b13c371329a2046b","latitude":"39.10771612","store_name":"天津南开店","car_num":"3","longitude":"117.15601804"},{"store_id":"f6945a7b8c974087a6657b67c714b009","latitude":"39.09859639","store_name":"王顶堤如家网点","car_num":"1","longitude":"117.14875162"}]
             * longitude : 117.15
             * city_id : a3d2959cf8a011e69c07408d5c04d4aa
             */

            private String city_name;
            private double latitude;
            private double longitude;
            private String city_id;
            private List<ListBean> list;

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * store_id : 1653f18ce8ab4d75ad94bef4573297a2
                 * latitude : 39.065112
                 * store_name : 凌奥渔夫码头网点
                 * car_num : 2
                 * longitude : 117.168311
                 */

                private String store_id;
                private double latitude;
                private String store_name;
                private int  car_num;
                private double longitude;

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

                public int getCar_num() {
                    return car_num;
                }

                public void setCar_num(int car_num) {
                    this.car_num = car_num;
                }

                public double getLatitude() {
                    return latitude;
                }

                public void setLatitude(double latitude) {
                    this.latitude = latitude;
                }

                public double getLongitude() {
                    return longitude;
                }

                public void setLongitude(double longitude) {
                    this.longitude = longitude;
                }
            }
        }
    }
}
