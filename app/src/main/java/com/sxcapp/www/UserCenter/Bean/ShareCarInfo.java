package com.sxcapp.www.UserCenter.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wenleitao on 2017/8/4.
 */

public class ShareCarInfo implements Parcelable {

    /**
     * pageCount : 1
     * data : [{"orderInfo":{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"af3fc26f78e411e7a43900163e1ae70a","order_no":"17080415156354","order_time":1501830900000,"order_state":"0","customer_id":"f4b1fcc778d911e7a43900163e1ae70a","customer_phone":"18356085323","customer_name":"陶渊明","customer_idcard":"342601199308165611","fetch_time":null,"return_time":null,"order_pay_time":null,"use_time":null,"fetch_store":"2d1aabc5378a4f109cbb8bebfd8a3f44","return_store":"2d1aabc5378a4f109cbb8bebfd8a3f44","lease_remark":null,"brand":null,"car_series":null,"model":null,"car_type":null,"total_cost":0,"no_deductible":10,"pay_channel":null,"pay_status":"0","source_id":"7220675777ec11e7a43900163e1ae70a","is_exception":"0","exception_remark":null,"manage_fetch_time":null,"manage_return_time":null,"fetch_store_name":"龙爷门店","return_store_name":"龙爷门店"},"shareCarInfo":{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"7220675777ec11e7a43900163e1ae70a","vehicle_brand_id":"6948ca261abc4f4d9e7b8e2b11629c0c","vehicle_serise_id":"1e584dc6c37848a9adfcf4b13f192056","vehicle_model_id":"500ba779f68f4a1abd1270850cd1e1a9","vehicle_model_custom":"9a4a002e402d11e78c5300163e000e21","displacement":"无","number_seats":5,"number_doors":5,"fuel_type":"纯电动","gearbox_type":"固定齿比变速箱","fuel_label":"无","drive_mode":"无","engine_intake_form":"无","sky_light":"无","reversing_radar":"前标配/后标配","airbag":"主标配/副标配","dvd":"无","gps":"标配","license_plate_number":"皖All335","cable_gps_number":"00","wireless_gps_number":"00","daily_average_price":null,"base_premium":null,"state":"4","tank_capacity":"22.3kWh","loudspeaker_box":null,"chair":"真皮","store_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","audit_time":"2017-08-03 09:38:26","shelves_time":"2017-08-03 09:38:52","lower_shelves_time":null,"estimate_time":null,"image_path":null,"dump_energy":"100","shelves_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","offshelve_id":null,"brand_code":"特斯拉","series_code":"MODEL X","model_code":"2017款 MODEL X 100D","vehicle_model_name":"电动汽车型","store_name":"龙爷门店","shelves_name":"龙爷门店","offshelve_name":null,"shelves_admin":"91f7096423c2427294c16c50fa119d90","offshelve_admin":null,"bespeak":false,"show_image":"upload/image/20170803/cygNtzOEtH5nVdK1ja1CkYnoL7n8nUp4093817751.jpg"}}]
     * pageIndex : 1
     * pageSize : 10
     */

    private int pageCount;
    private int pageIndex;
    private int pageSize;
    private List<DataBean> data;

    protected ShareCarInfo(Parcel in) {
        pageCount = in.readInt();
        pageIndex = in.readInt();
        pageSize = in.readInt();
    }

    public static final Creator<ShareCarInfo> CREATOR = new Creator<ShareCarInfo>() {
        @Override
        public ShareCarInfo createFromParcel(Parcel in) {
            return new ShareCarInfo(in);
        }

        @Override
        public ShareCarInfo[] newArray(int size) {
            return new ShareCarInfo[size];
        }
    };

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pageCount);
        dest.writeInt(pageIndex);
        dest.writeInt(pageSize);
    }

    public static class DataBean implements Parcelable {
        /**
         * orderInfo : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"af3fc26f78e411e7a43900163e1ae70a","order_no":"17080415156354","order_time":1501830900000,"order_state":"0","customer_id":"f4b1fcc778d911e7a43900163e1ae70a","customer_phone":"18356085323","customer_name":"陶渊明","customer_idcard":"342601199308165611","fetch_time":null,"return_time":null,"order_pay_time":null,"use_time":null,"fetch_store":"2d1aabc5378a4f109cbb8bebfd8a3f44","return_store":"2d1aabc5378a4f109cbb8bebfd8a3f44","lease_remark":null,"brand":null,"car_series":null,"model":null,"car_type":null,"total_cost":0,"no_deductible":10,"pay_channel":null,"pay_status":"0","source_id":"7220675777ec11e7a43900163e1ae70a","is_exception":"0","exception_remark":null,"manage_fetch_time":null,"manage_return_time":null,"fetch_store_name":"龙爷门店","return_store_name":"龙爷门店"}
         * shareCarInfo : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"7220675777ec11e7a43900163e1ae70a","vehicle_brand_id":"6948ca261abc4f4d9e7b8e2b11629c0c","vehicle_serise_id":"1e584dc6c37848a9adfcf4b13f192056","vehicle_model_id":"500ba779f68f4a1abd1270850cd1e1a9","vehicle_model_custom":"9a4a002e402d11e78c5300163e000e21","displacement":"无","number_seats":5,"number_doors":5,"fuel_type":"纯电动","gearbox_type":"固定齿比变速箱","fuel_label":"无","drive_mode":"无","engine_intake_form":"无","sky_light":"无","reversing_radar":"前标配/后标配","airbag":"主标配/副标配","dvd":"无","gps":"标配","license_plate_number":"皖All335","cable_gps_number":"00","wireless_gps_number":"00","daily_average_price":null,"base_premium":null,"state":"4","tank_capacity":"22.3kWh","loudspeaker_box":null,"chair":"真皮","store_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","audit_time":"2017-08-03 09:38:26","shelves_time":"2017-08-03 09:38:52","lower_shelves_time":null,"estimate_time":null,"image_path":null,"dump_energy":"100","shelves_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","offshelve_id":null,"brand_code":"特斯拉","series_code":"MODEL X","model_code":"2017款 MODEL X 100D","vehicle_model_name":"电动汽车型","store_name":"龙爷门店","shelves_name":"龙爷门店","offshelve_name":null,"shelves_admin":"91f7096423c2427294c16c50fa119d90","offshelve_admin":null,"bespeak":false,"show_image":"upload/image/20170803/cygNtzOEtH5nVdK1ja1CkYnoL7n8nUp4093817751.jpg"}
         */

        private OrderInfoBean orderInfo;
        private ShareCarInfoBean shareCarInfo;

        protected DataBean(Parcel in) {
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public OrderInfoBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public ShareCarInfoBean getShareCarInfo() {
            return shareCarInfo;
        }

        public void setShareCarInfo(ShareCarInfoBean shareCarInfo) {
            this.shareCarInfo = shareCarInfo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static class OrderInfoBean implements Parcelable {
            /**
             * map : {}
             * relationModelList : []
             * orderByModelList : []
             * cnd_type : default
             * ob_type : default
             * id : af3fc26f78e411e7a43900163e1ae70a
             * order_no : 17080415156354
             * order_time : 1501830900000
             * order_state : 0
             * customer_id : f4b1fcc778d911e7a43900163e1ae70a
             * customer_phone : 18356085323
             * customer_name : 陶渊明
             * customer_idcard : 342601199308165611
             * fetch_time : null
             * return_time : null
             * order_pay_time : null
             * use_time : null
             * fetch_store : 2d1aabc5378a4f109cbb8bebfd8a3f44
             * return_store : 2d1aabc5378a4f109cbb8bebfd8a3f44
             * lease_remark : null
             * brand : null
             * car_series : null
             * model : null
             * car_type : null
             * total_cost : 0
             * no_deductible : 10
             * pay_channel : null
             * pay_status : 0
             * source_id : 7220675777ec11e7a43900163e1ae70a
             * is_exception : 0
             * exception_remark : null
             * manage_fetch_time : null
             * manage_return_time : null
             * fetch_store_name : 龙爷门店
             * return_store_name : 龙爷门店
             */

            private MapBean map;
            private String cnd_type;
            private String ob_type;
            private String id;
            private String order_no;
            private long order_time;
            private int order_state;
            private String customer_id;
            private String customer_phone;
            private String customer_name;
            private String customer_idcard;
            private Object fetch_time;
            private Object return_time;
            private Object order_pay_time;
            private Object use_time;
            private String fetch_store;
            private String return_store;
            private Object lease_remark;
            private Object brand;
            private Object car_series;
            private Object model;
            private Object car_type;
            private double total_cost;
            private int no_deductible;
            private Object pay_channel;
            private String pay_status;
            private String source_id;
            private String is_exception;
            private Object exception_remark;
            private Object manage_fetch_time;
            private Object manage_return_time;
            private String fetch_store_name;
            private String return_store_name;
            private List<?> relationModelList;
            private List<?> orderByModelList;

            protected OrderInfoBean(Parcel in) {
                cnd_type = in.readString();
                ob_type = in.readString();
                id = in.readString();
                order_no = in.readString();
                order_time = in.readLong();
                order_state = in.readInt();
                customer_id = in.readString();
                customer_phone = in.readString();
                customer_name = in.readString();
                customer_idcard = in.readString();
                fetch_store = in.readString();
                return_store = in.readString();
                total_cost = in.readDouble();
                no_deductible = in.readInt();
                pay_status = in.readString();
                source_id = in.readString();
                is_exception = in.readString();
                fetch_store_name = in.readString();
                return_store_name = in.readString();
            }

            public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
                @Override
                public OrderInfoBean createFromParcel(Parcel in) {
                    return new OrderInfoBean(in);
                }

                @Override
                public OrderInfoBean[] newArray(int size) {
                    return new OrderInfoBean[size];
                }
            };

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

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public long getOrder_time() {
                return order_time;
            }

            public void setOrder_time(long order_time) {
                this.order_time = order_time;
            }

            public int getOrder_state() {
                return order_state;
            }

            public void setOrder_state(int order_state) {
                this.order_state = order_state;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getCustomer_phone() {
                return customer_phone;
            }

            public void setCustomer_phone(String customer_phone) {
                this.customer_phone = customer_phone;
            }

            public String getCustomer_name() {
                return customer_name;
            }

            public void setCustomer_name(String customer_name) {
                this.customer_name = customer_name;
            }

            public String getCustomer_idcard() {
                return customer_idcard;
            }

            public void setCustomer_idcard(String customer_idcard) {
                this.customer_idcard = customer_idcard;
            }

            public Object getFetch_time() {
                return fetch_time;
            }

            public void setFetch_time(Object fetch_time) {
                this.fetch_time = fetch_time;
            }

            public Object getReturn_time() {
                return return_time;
            }

            public void setReturn_time(Object return_time) {
                this.return_time = return_time;
            }

            public Object getOrder_pay_time() {
                return order_pay_time;
            }

            public void setOrder_pay_time(Object order_pay_time) {
                this.order_pay_time = order_pay_time;
            }

            public Object getUse_time() {
                return use_time;
            }

            public void setUse_time(Object use_time) {
                this.use_time = use_time;
            }

            public String getFetch_store() {
                return fetch_store;
            }

            public void setFetch_store(String fetch_store) {
                this.fetch_store = fetch_store;
            }

            public String getReturn_store() {
                return return_store;
            }

            public void setReturn_store(String return_store) {
                this.return_store = return_store;
            }

            public Object getLease_remark() {
                return lease_remark;
            }

            public void setLease_remark(Object lease_remark) {
                this.lease_remark = lease_remark;
            }

            public Object getBrand() {
                return brand;
            }

            public void setBrand(Object brand) {
                this.brand = brand;
            }

            public Object getCar_series() {
                return car_series;
            }

            public void setCar_series(Object car_series) {
                this.car_series = car_series;
            }

            public Object getModel() {
                return model;
            }

            public void setModel(Object model) {
                this.model = model;
            }

            public Object getCar_type() {
                return car_type;
            }

            public void setCar_type(Object car_type) {
                this.car_type = car_type;
            }

            public double getTotal_cost() {
                return total_cost;
            }

            public void setTotal_cost(double total_cost) {
                this.total_cost = total_cost;
            }

            public int getNo_deductible() {
                return no_deductible;
            }

            public void setNo_deductible(int no_deductible) {
                this.no_deductible = no_deductible;
            }

            public Object getPay_channel() {
                return pay_channel;
            }

            public void setPay_channel(Object pay_channel) {
                this.pay_channel = pay_channel;
            }

            public String getPay_status() {
                return pay_status;
            }

            public void setPay_status(String pay_status) {
                this.pay_status = pay_status;
            }

            public String getSource_id() {
                return source_id;
            }

            public void setSource_id(String source_id) {
                this.source_id = source_id;
            }

            public String getIs_exception() {
                return is_exception;
            }

            public void setIs_exception(String is_exception) {
                this.is_exception = is_exception;
            }

            public Object getException_remark() {
                return exception_remark;
            }

            public void setException_remark(Object exception_remark) {
                this.exception_remark = exception_remark;
            }

            public Object getManage_fetch_time() {
                return manage_fetch_time;
            }

            public void setManage_fetch_time(Object manage_fetch_time) {
                this.manage_fetch_time = manage_fetch_time;
            }

            public Object getManage_return_time() {
                return manage_return_time;
            }

            public void setManage_return_time(Object manage_return_time) {
                this.manage_return_time = manage_return_time;
            }

            public String getFetch_store_name() {
                return fetch_store_name;
            }

            public void setFetch_store_name(String fetch_store_name) {
                this.fetch_store_name = fetch_store_name;
            }

            public String getReturn_store_name() {
                return return_store_name;
            }

            public void setReturn_store_name(String return_store_name) {
                this.return_store_name = return_store_name;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(cnd_type);
                dest.writeString(ob_type);
                dest.writeString(id);
                dest.writeString(order_no);
                dest.writeLong(order_time);
                dest.writeInt(order_state);
                dest.writeString(customer_id);
                dest.writeString(customer_phone);
                dest.writeString(customer_name);
                dest.writeString(customer_idcard);
                dest.writeString(fetch_store);
                dest.writeString(return_store);
                dest.writeDouble(total_cost);
                dest.writeInt(no_deductible);
                dest.writeString(pay_status);
                dest.writeString(source_id);
                dest.writeString(is_exception);
                dest.writeString(fetch_store_name);
                dest.writeString(return_store_name);
            }

            public static class MapBean {
            }
        }

        public static class ShareCarInfoBean implements Parcelable {
            /**
             * map : {}
             * relationModelList : []
             * orderByModelList : []
             * cnd_type : default
             * ob_type : default
             * id : 7220675777ec11e7a43900163e1ae70a
             * vehicle_brand_id : 6948ca261abc4f4d9e7b8e2b11629c0c
             * vehicle_serise_id : 1e584dc6c37848a9adfcf4b13f192056
             * vehicle_model_id : 500ba779f68f4a1abd1270850cd1e1a9
             * vehicle_model_custom : 9a4a002e402d11e78c5300163e000e21
             * displacement : 无
             * number_seats : 5
             * number_doors : 5
             * fuel_type : 纯电动
             * gearbox_type : 固定齿比变速箱
             * fuel_label : 无
             * drive_mode : 无
             * engine_intake_form : 无
             * sky_light : 无
             * reversing_radar : 前标配/后标配
             * airbag : 主标配/副标配
             * dvd : 无
             * gps : 标配
             * license_plate_number : 皖All335
             * cable_gps_number : 00
             * wireless_gps_number : 00
             * daily_average_price : null
             * base_premium : null
             * state : 4
             * tank_capacity : 22.3kWh
             * loudspeaker_box : null
             * chair : 真皮
             * store_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
             * audit_time : 2017-08-03 09:38:26
             * shelves_time : 2017-08-03 09:38:52
             * lower_shelves_time : null
             * estimate_time : null
             * image_path : null
             * dump_energy : 100
             * shelves_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
             * offshelve_id : null
             * brand_code : 特斯拉
             * series_code : MODEL X
             * model_code : 2017款 MODEL X 100D
             * vehicle_model_name : 电动汽车型
             * store_name : 龙爷门店
             * shelves_name : 龙爷门店
             * offshelve_name : null
             * shelves_admin : 91f7096423c2427294c16c50fa119d90
             * offshelve_admin : null
             * bespeak : false
             * show_image : upload/image/20170803/cygNtzOEtH5nVdK1ja1CkYnoL7n8nUp4093817751.jpg
             */

            private MapBeanX map;
            private String cnd_type;
            private String ob_type;
            private String id;
            private String vehicle_brand_id;
            private String vehicle_serise_id;
            private String vehicle_model_id;
            private String vehicle_model_custom;
            private String displacement;
            private int number_seats;
            private int number_doors;
            private String fuel_type;
            private String gearbox_type;
            private String fuel_label;
            private String drive_mode;
            private String engine_intake_form;
            private String sky_light;
            private String reversing_radar;
            private String airbag;
            private String dvd;
            private String gps;
            private String license_plate_number;
            private String cable_gps_number;
            private String wireless_gps_number;
            private Object daily_average_price;
            private Object base_premium;
            private String state;
            private String tank_capacity;
            private Object loudspeaker_box;
            private String chair;
            private String store_id;
            private String audit_time;
            private String shelves_time;
            private Object lower_shelves_time;
            private Object estimate_time;
            private Object image_path;
            private String dump_energy;
            private String shelves_id;
            private Object offshelve_id;
            private String brand_code;
            private String series_code;
            private String model_code;
            private String vehicle_model_name;
            private String store_name;
            private String shelves_name;
            private String offshelve_name;
            private String shelves_admin;
            private Object offshelve_admin;
            private boolean bespeak;
            private String show_image;
            private List<?> relationModelList;
            private List<?> orderByModelList;

            protected ShareCarInfoBean(Parcel in) {
                cnd_type = in.readString();
                ob_type = in.readString();
                id = in.readString();
                vehicle_brand_id = in.readString();
                vehicle_serise_id = in.readString();
                vehicle_model_id = in.readString();
                vehicle_model_custom = in.readString();
                displacement = in.readString();
                number_seats = in.readInt();
                number_doors = in.readInt();
                fuel_type = in.readString();
                gearbox_type = in.readString();
                fuel_label = in.readString();
                drive_mode = in.readString();
                engine_intake_form = in.readString();
                sky_light = in.readString();
                reversing_radar = in.readString();
                airbag = in.readString();
                dvd = in.readString();
                gps = in.readString();
                license_plate_number = in.readString();
                cable_gps_number = in.readString();
                wireless_gps_number = in.readString();
                state = in.readString();
                tank_capacity = in.readString();
                chair = in.readString();
                store_id = in.readString();
                audit_time = in.readString();
                shelves_time = in.readString();
                dump_energy = in.readString();
                shelves_id = in.readString();
                brand_code = in.readString();
                series_code = in.readString();
                model_code = in.readString();
                vehicle_model_name = in.readString();
                store_name = in.readString();
                shelves_name = in.readString();
                offshelve_name = in.readString();
                shelves_admin = in.readString();
                bespeak = in.readByte() != 0;
                show_image = in.readString();
            }

            public static final Creator<ShareCarInfoBean> CREATOR = new Creator<ShareCarInfoBean>() {
                @Override
                public ShareCarInfoBean createFromParcel(Parcel in) {
                    return new ShareCarInfoBean(in);
                }

                @Override
                public ShareCarInfoBean[] newArray(int size) {
                    return new ShareCarInfoBean[size];
                }
            };

            public MapBeanX getMap() {
                return map;
            }

            public void setMap(MapBeanX map) {
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

            public String getVehicle_brand_id() {
                return vehicle_brand_id;
            }

            public void setVehicle_brand_id(String vehicle_brand_id) {
                this.vehicle_brand_id = vehicle_brand_id;
            }

            public String getVehicle_serise_id() {
                return vehicle_serise_id;
            }

            public void setVehicle_serise_id(String vehicle_serise_id) {
                this.vehicle_serise_id = vehicle_serise_id;
            }

            public String getVehicle_model_id() {
                return vehicle_model_id;
            }

            public void setVehicle_model_id(String vehicle_model_id) {
                this.vehicle_model_id = vehicle_model_id;
            }

            public String getVehicle_model_custom() {
                return vehicle_model_custom;
            }

            public void setVehicle_model_custom(String vehicle_model_custom) {
                this.vehicle_model_custom = vehicle_model_custom;
            }

            public String getDisplacement() {
                return displacement;
            }

            public void setDisplacement(String displacement) {
                this.displacement = displacement;
            }

            public int getNumber_seats() {
                return number_seats;
            }

            public void setNumber_seats(int number_seats) {
                this.number_seats = number_seats;
            }

            public int getNumber_doors() {
                return number_doors;
            }

            public void setNumber_doors(int number_doors) {
                this.number_doors = number_doors;
            }

            public String getFuel_type() {
                return fuel_type;
            }

            public void setFuel_type(String fuel_type) {
                this.fuel_type = fuel_type;
            }

            public String getGearbox_type() {
                return gearbox_type;
            }

            public void setGearbox_type(String gearbox_type) {
                this.gearbox_type = gearbox_type;
            }

            public String getFuel_label() {
                return fuel_label;
            }

            public void setFuel_label(String fuel_label) {
                this.fuel_label = fuel_label;
            }

            public String getDrive_mode() {
                return drive_mode;
            }

            public void setDrive_mode(String drive_mode) {
                this.drive_mode = drive_mode;
            }

            public String getEngine_intake_form() {
                return engine_intake_form;
            }

            public void setEngine_intake_form(String engine_intake_form) {
                this.engine_intake_form = engine_intake_form;
            }

            public String getSky_light() {
                return sky_light;
            }

            public void setSky_light(String sky_light) {
                this.sky_light = sky_light;
            }

            public String getReversing_radar() {
                return reversing_radar;
            }

            public void setReversing_radar(String reversing_radar) {
                this.reversing_radar = reversing_radar;
            }

            public String getAirbag() {
                return airbag;
            }

            public void setAirbag(String airbag) {
                this.airbag = airbag;
            }

            public String getDvd() {
                return dvd;
            }

            public void setDvd(String dvd) {
                this.dvd = dvd;
            }

            public String getGps() {
                return gps;
            }

            public void setGps(String gps) {
                this.gps = gps;
            }

            public String getLicense_plate_number() {
                return license_plate_number;
            }

            public void setLicense_plate_number(String license_plate_number) {
                this.license_plate_number = license_plate_number;
            }

            public String getCable_gps_number() {
                return cable_gps_number;
            }

            public void setCable_gps_number(String cable_gps_number) {
                this.cable_gps_number = cable_gps_number;
            }

            public String getWireless_gps_number() {
                return wireless_gps_number;
            }

            public void setWireless_gps_number(String wireless_gps_number) {
                this.wireless_gps_number = wireless_gps_number;
            }

            public Object getDaily_average_price() {
                return daily_average_price;
            }

            public void setDaily_average_price(Object daily_average_price) {
                this.daily_average_price = daily_average_price;
            }

            public Object getBase_premium() {
                return base_premium;
            }

            public void setBase_premium(Object base_premium) {
                this.base_premium = base_premium;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTank_capacity() {
                return tank_capacity;
            }

            public void setTank_capacity(String tank_capacity) {
                this.tank_capacity = tank_capacity;
            }

            public Object getLoudspeaker_box() {
                return loudspeaker_box;
            }

            public void setLoudspeaker_box(Object loudspeaker_box) {
                this.loudspeaker_box = loudspeaker_box;
            }

            public String getChair() {
                return chair;
            }

            public void setChair(String chair) {
                this.chair = chair;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getAudit_time() {
                return audit_time;
            }

            public void setAudit_time(String audit_time) {
                this.audit_time = audit_time;
            }

            public String getShelves_time() {
                return shelves_time;
            }

            public void setShelves_time(String shelves_time) {
                this.shelves_time = shelves_time;
            }

            public Object getLower_shelves_time() {
                return lower_shelves_time;
            }

            public void setLower_shelves_time(Object lower_shelves_time) {
                this.lower_shelves_time = lower_shelves_time;
            }

            public Object getEstimate_time() {
                return estimate_time;
            }

            public void setEstimate_time(Object estimate_time) {
                this.estimate_time = estimate_time;
            }

            public Object getImage_path() {
                return image_path;
            }

            public void setImage_path(Object image_path) {
                this.image_path = image_path;
            }

            public String getDump_energy() {
                return dump_energy;
            }

            public void setDump_energy(String dump_energy) {
                this.dump_energy = dump_energy;
            }

            public String getShelves_id() {
                return shelves_id;
            }

            public void setShelves_id(String shelves_id) {
                this.shelves_id = shelves_id;
            }

            public Object getOffshelve_id() {
                return offshelve_id;
            }

            public void setOffshelve_id(Object offshelve_id) {
                this.offshelve_id = offshelve_id;
            }

            public String getBrand_code() {
                return brand_code;
            }

            public void setBrand_code(String brand_code) {
                this.brand_code = brand_code;
            }

            public String getSeries_code() {
                return series_code;
            }

            public void setSeries_code(String series_code) {
                this.series_code = series_code;
            }

            public String getModel_code() {
                return model_code;
            }

            public void setModel_code(String model_code) {
                this.model_code = model_code;
            }

            public String getVehicle_model_name() {
                return vehicle_model_name;
            }

            public void setVehicle_model_name(String vehicle_model_name) {
                this.vehicle_model_name = vehicle_model_name;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getShelves_name() {
                return shelves_name;
            }

            public void setShelves_name(String shelves_name) {
                this.shelves_name = shelves_name;
            }

            public String getOffshelve_name() {
                return offshelve_name;
            }

            public void setOffshelve_name(String offshelve_name) {
                this.offshelve_name = offshelve_name;
            }

            public String getShelves_admin() {
                return shelves_admin;
            }

            public void setShelves_admin(String shelves_admin) {
                this.shelves_admin = shelves_admin;
            }

            public Object getOffshelve_admin() {
                return offshelve_admin;
            }

            public void setOffshelve_admin(Object offshelve_admin) {
                this.offshelve_admin = offshelve_admin;
            }

            public boolean isBespeak() {
                return bespeak;
            }

            public void setBespeak(boolean bespeak) {
                this.bespeak = bespeak;
            }

            public String getShow_image() {
                return show_image;
            }

            public void setShow_image(String show_image) {
                this.show_image = show_image;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(cnd_type);
                dest.writeString(ob_type);
                dest.writeString(id);
                dest.writeString(vehicle_brand_id);
                dest.writeString(vehicle_serise_id);
                dest.writeString(vehicle_model_id);
                dest.writeString(vehicle_model_custom);
                dest.writeString(displacement);
                dest.writeInt(number_seats);
                dest.writeInt(number_doors);
                dest.writeString(fuel_type);
                dest.writeString(gearbox_type);
                dest.writeString(fuel_label);
                dest.writeString(drive_mode);
                dest.writeString(engine_intake_form);
                dest.writeString(sky_light);
                dest.writeString(reversing_radar);
                dest.writeString(airbag);
                dest.writeString(dvd);
                dest.writeString(gps);
                dest.writeString(license_plate_number);
                dest.writeString(cable_gps_number);
                dest.writeString(wireless_gps_number);
                dest.writeString(state);
                dest.writeString(tank_capacity);
                dest.writeString(chair);
                dest.writeString(store_id);
                dest.writeString(audit_time);
                dest.writeString(shelves_time);
                dest.writeString(dump_energy);
                dest.writeString(shelves_id);
                dest.writeString(brand_code);
                dest.writeString(series_code);
                dest.writeString(model_code);
                dest.writeString(vehicle_model_name);
                dest.writeString(store_name);
                dest.writeString(shelves_name);
                dest.writeString(offshelve_name);
                dest.writeString(shelves_admin);
                dest.writeByte((byte) (bespeak ? 1 : 0));
                dest.writeString(show_image);
            }

            public static class MapBeanX {
            }
        }
    }
}
