package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/12/27.
 */

public class MapStoreListBean {

    /**
     * usecarnum : 14
     * latitude : 39.908689
     * cityname : 全国
     * list : [{"usecarnum":"13","latitude":"39.12 ","cityname":"天津市","list":[{"usecarnum":"13","latitude":"39.12 ","cityname":"天津市","list":[{"usecarnum":"13","latitude":"39.13 ","cityname":"南开区","list":[{"usecarnum":"0","latitude":"39.09859639","storeName":"王顶堤如家网点","longitude":"117.14875162"},{"usecarnum":"1","latitude":"39.101617","storeName":"天津市西青区侯台农贸市场停车场","longitude":"117.115728"},{"usecarnum":"12","latitude":"39.10771612","storeName":"天津南开区","longitude":"117.15601804"}],"longitude":"117.15 "}],"longitude":"117.20 "}],"longitude":"117.20 "},{"usecarnum":"1","latitude":"31.83 ","cityname":"安徽省","list":[{"usecarnum":"1","latitude":"31.83 ","cityname":"合肥市","list":[{"usecarnum":"1","latitude":"31.80 ","cityname":"包河区","list":[{"usecarnum":"0","latitude":"31.785636","storeName":"内蒙路网点","longitude":"117.337974"},{"usecarnum":"1","latitude":"31.786872","storeName":"合肥门店","longitude":"117.332798"},{"usecarnum":"0","latitude":"31.810549","storeName":"创客梦空间","longitude":"117.269903"},{"usecarnum":"0","latitude":"31.780055","storeName":"合肥质监局网点","longitude":"117.327599"},{"usecarnum":"0","latitude":"31.851132","storeName":"小乐子门店","longitude":"117.327595"},{"usecarnum":"0","latitude":"31.794509","storeName":"包河花园网点","longitude":"117.320372"},{"usecarnum":"0","latitude":"31.780096","storeName":"安徽青网电商园","longitude":"117.3547"},{"usecarnum":"0","latitude":"31.795161","storeName":"水丽坊网点","longitude":"117.327191"}],"longitude":"117.30 "}],"longitude":"117.25 "}],"longitude":"117.25 "}]
     * longitude : 116.397459
     */

    private String usecarnum;
    private String latitude;
    private String cityname;
    private String longitude;
    private List<ListBeanXXX> list;

    public String getUsecarnum() {
        return usecarnum;
    }

    public void setUsecarnum(String usecarnum) {
        this.usecarnum = usecarnum;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<ListBeanXXX> getList() {
        return list;
    }

    public void setList(List<ListBeanXXX> list) {
        this.list = list;
    }

    public static class ListBeanXXX {
        /**
         * usecarnum : 13
         * latitude : 39.12
         * cityname : 天津市
         * list : [{"usecarnum":"13","latitude":"39.12 ","cityname":"天津市","list":[{"usecarnum":"13","latitude":"39.13 ","cityname":"南开区","list":[{"usecarnum":"0","latitude":"39.09859639","storeName":"王顶堤如家网点","longitude":"117.14875162"},{"usecarnum":"1","latitude":"39.101617","storeName":"天津市西青区侯台农贸市场停车场","longitude":"117.115728"},{"usecarnum":"12","latitude":"39.10771612","storeName":"天津南开区","longitude":"117.15601804"}],"longitude":"117.15 "}],"longitude":"117.20 "}]
         * longitude : 117.20
         */

        private int usecarnum;
        private double latitude;
        private String cityname;
        private double longitude;
        private List<ListBeanXX> list;

        public int getUsecarnum() {
            return usecarnum;
        }

        public void setUsecarnum(int usecarnum) {
            this.usecarnum = usecarnum;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public List<ListBeanXX> getList() {
            return list;
        }

        public void setList(List<ListBeanXX> list) {
            this.list = list;
        }

        public static class ListBeanXX {
            /**
             * usecarnum : 13
             * latitude : 39.12
             * cityname : 天津市
             * list : [{"usecarnum":"13","latitude":"39.13 ","cityname":"南开区","list":[{"usecarnum":"0","latitude":"39.09859639","storeName":"王顶堤如家网点","longitude":"117.14875162"},{"usecarnum":"1","latitude":"39.101617","storeName":"天津市西青区侯台农贸市场停车场","longitude":"117.115728"},{"usecarnum":"12","latitude":"39.10771612","storeName":"天津南开区","longitude":"117.15601804"}],"longitude":"117.15 "}]
             * longitude : 117.20
             */

            private int usecarnum;
            private double latitude;
            private String cityname;
            private double longitude;
            private List<ListBeanX> list;

            public int getUsecarnum() {
                return usecarnum;
            }

            public void setUsecarnum(int usecarnum) {
                this.usecarnum = usecarnum;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX {
                /**
                 * usecarnum : 13
                 * latitude : 39.13
                 * cityname : 南开区
                 * list : [{"usecarnum":"0","latitude":"39.09859639","storeName":"王顶堤如家网点","longitude":"117.14875162"},{"usecarnum":"1","latitude":"39.101617","storeName":"天津市西青区侯台农贸市场停车场","longitude":"117.115728"},{"usecarnum":"12","latitude":"39.10771612","storeName":"天津南开区","longitude":"117.15601804"}]
                 * longitude : 117.15
                 */

                private int usecarnum;
                private double latitude;
                private String cityname;
                private double longitude;
                private List<ListBean> list;

                public int getUsecarnum() {
                    return usecarnum;
                }

                public void setUsecarnum(int usecarnum) {
                    this.usecarnum = usecarnum;
                }

                public double getLatitude() {
                    return latitude;
                }

                public void setLatitude(double latitude) {
                    this.latitude = latitude;
                }

                public String getCityname() {
                    return cityname;
                }

                public void setCityname(String cityname) {
                    this.cityname = cityname;
                }

                public double getLongitude() {
                    return longitude;
                }

                public void setLongitude(double longitude) {
                    this.longitude = longitude;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean {
                    /**
                     * usecarnum : 0
                     * latitude : 39.09859639
                     * storeName : 王顶堤如家网点
                     * longitude : 117.14875162
                     */
                    private String store_id;
                    private int usecarnum;
                    private double latitude;
                    private String storeName;
                    private double longitude;
                    private String address_details;

                    public String getAddress_details() {
                        return address_details;
                    }

                    public void setAddress_details(String address_details) {
                        this.address_details = address_details;
                    }

                    public String getStore_id() {
                        return store_id;
                    }

                    public void setStore_id(String store_id) {
                        this.store_id = store_id;
                    }

                    public int getUsecarnum() {
                        return usecarnum;
                    }

                    public void setUsecarnum(int usecarnum) {
                        this.usecarnum = usecarnum;
                    }

                    public double getLatitude() {
                        return latitude;
                    }

                    public void setLatitude(double latitude) {
                        this.latitude = latitude;
                    }

                    public String getStoreName() {
                        return storeName;
                    }

                    public void setStoreName(String storeName) {
                        this.storeName = storeName;
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
}
