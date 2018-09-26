package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/9/9.
 */

public class ShareInDayOrderDetailBean {

    /**
     * exception : {}
     * management : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"9a4a002e402d11e78c5300163e000e21","model_type":"0","vehicle_model_name":"电动汽车型","model_image":"upload/image/20170524/thumbnails/jEeqZt2nbuJSV1csUL63kKgxFh5rDOnM110327679.png","sort":2,"create_user_id":"607dd46e481711e6b208408d5c04d4aa","create_time":1495595026000,"modify_user_id":null,"modify_time":null,"i":null}
     * storeFetch : 小乐子门店
     * series : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"00b4a7332b2e4d34a1ef2b50bae14002","brand_id":"d93196523b7042778a49087b184b1d40","code":"2324","code_desc":"奇瑞E5","sort":10185}
     * vehicleModel : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"0edb073636a34988977c8f69a456a968","series_id":"00b4a7332b2e4d34a1ef2b50bae14002","code":"16602","code_desc":"2014款 1.8L CVT优悦型","sort":103651}
     * shareCarLongOrder : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"d664993c94b511e7a8c7d43d7eb1d148","order_no":"17090900508886","order_time":1504889400000,"order_state":"0","customer_id":"31e5a83e798911e7a43900163e1ae70a","customer_phone":"18356085323","customer_name":"陶文磊","customer_idcard":"342426199301084639","fetch_time":null,"return_time":null,"order_pay_time":null,"use_time":null,"fetch_store":"a3b052484ce7480cbb5b36a9ff134107","return_store":"a3b052484ce7480cbb5b36a9ff134107","lease_remark":null,"brand":null,"car_series":null,"model":null,"car_type":null,"day_cost":3,"deposit_cost":0,"single_cost":2.3,"total_cost":2.3,"no_deductible":0.2,"pay_channel":"2","pay_status":"1","source_id":"a76f8b667cad11e7a43900163e1ae70a","is_exception":"0","exception_remark":null,"manage_fetch_time":null,"manage_return_time":null,"cost_remark":null,"fee_setting_id":"b34c4ed7924811e7a8c7d43d7eb1d148"}
     * brand : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"d93196523b7042778a49087b184b1d40","code":"26","code_desc":"奇瑞","sort":615,"first_letter":"Q","brand_image":"upload/image/20170519/6BreK8cQw5MnJRgr6sz81XkbfkIJn5lc122454667.jpg"}
     * settingList : ["租车单价2.30元,租3天,需押金0.00元","确认后，费用直接扣除","提前还车，不存在费用返还","超过3.00天时间，按分时收费","最终解释权归平台所有"]
     * shareInformation : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"a76f8b667cad11e7a43900163e1ae70a","vehicle_brand_id":"d93196523b7042778a49087b184b1d40","vehicle_serise_id":"00b4a7332b2e4d34a1ef2b50bae14002","vehicle_model_id":"0edb073636a34988977c8f69a456a968","vehicle_model_custom":"9a4a002e402d11e78c5300163e000e21","displacement":"1845","number_seats":5,"number_doors":4,"fuel_type":"汽油","gearbox_type":"无级变速箱(CVT)","fuel_label":"93号(京92号)","drive_mode":"无","engine_intake_form":"自然吸气","sky_light":"无","reversing_radar":"前无/后标配","airbag":"主标配/副标配","dvd":"有","gps":"无","license_plate_number":"皖 A 00001","cable_gps_number":"11211","wireless_gps_number":"11211","daily_average_price":null,"base_premium":null,"state":"4","tank_capacity":"22.3kWh","loudspeaker_box":null,"chair":"仿皮","store_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","audit_time":"2017-08-09 10:51:33","shelves_time":"2017-08-09 10:59:34","lower_shelves_time":"2017-08-25 14:31:47","estimate_time":null,"image_path":null,"dump_energy":"100%","shelves_id":"a3b052484ce7480cbb5b36a9ff134107","offshelve_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","brand_code":"奇瑞","series_code":"奇瑞E5","model_code":"2014款 1.8L CVT优悦型","vehicle_model_name":"电动汽车型","store_name":"龙爷门店","shelves_name":"小乐子门店","offshelve_name":"龙爷门店","shelves_admin":"91f7096423c2427294c16c50fa119d90","offshelve_admin":null,"bespeak":false,"show_image":"upload/image/20170809/NQuBdJHeSJwweEUiX7axEct4z2iwFrJa105126052.jpg"}
     * storeReturn : 小乐子门店
     */

    private ExceptionBean exception;
    private ManagementBean management;
    private String storeFetch;
    private SeriesBean series;
    private VehicleModelBean vehicleModel;
    private ShareCarLongOrderBean shareCarLongOrder;
    private BrandBean brand;
    private ShareInformationBean shareInformation;
    private String storeReturn;
    private List<String> settingList;
    private List<String> exception_remark;
    private String image_flag;
    private String coupon;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public List<String> getException_remark() {
        return exception_remark;
    }

    public void setException_remark(List<String> exception_remark) {
        this.exception_remark = exception_remark;
    }

    public String getImage_flag() {
        return image_flag;
    }

    public void setImage_flag(String image_flag) {
        this.image_flag = image_flag;
    }

    public ExceptionBean getException() {
        return exception;
    }

    public void setException(ExceptionBean exception) {
        this.exception = exception;
    }

    public ManagementBean getManagement() {
        return management;
    }

    public void setManagement(ManagementBean management) {
        this.management = management;
    }

    public String getStoreFetch() {
        return storeFetch;
    }

    public void setStoreFetch(String storeFetch) {
        this.storeFetch = storeFetch;
    }

    public SeriesBean getSeries() {
        return series;
    }

    public void setSeries(SeriesBean series) {
        this.series = series;
    }

    public VehicleModelBean getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(VehicleModelBean vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public ShareCarLongOrderBean getShareCarLongOrder() {
        return shareCarLongOrder;
    }

    public void setShareCarLongOrder(ShareCarLongOrderBean shareCarLongOrder) {
        this.shareCarLongOrder = shareCarLongOrder;
    }

    public BrandBean getBrand() {
        return brand;
    }

    public void setBrand(BrandBean brand) {
        this.brand = brand;
    }

    public ShareInformationBean getShareInformation() {
        return shareInformation;
    }

    public void setShareInformation(ShareInformationBean shareInformation) {
        this.shareInformation = shareInformation;
    }

    public String getStoreReturn() {
        return storeReturn;
    }

    public void setStoreReturn(String storeReturn) {
        this.storeReturn = storeReturn;
    }

    public List<String> getSettingList() {
        return settingList;
    }

    public void setSettingList(List<String> settingList) {
        this.settingList = settingList;
    }

    public static class ExceptionBean {
    }

    public static class ManagementBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : 9a4a002e402d11e78c5300163e000e21
         * model_type : 0
         * vehicle_model_name : 电动汽车型
         * model_image : upload/image/20170524/thumbnails/jEeqZt2nbuJSV1csUL63kKgxFh5rDOnM110327679.png
         * sort : 2
         * create_user_id : 607dd46e481711e6b208408d5c04d4aa
         * create_time : 1495595026000
         * modify_user_id : null
         * modify_time : null
         * i : null
         */

        private MapBean map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String model_type;
        private String vehicle_model_name;
        private String model_image;
        private int sort;
        private String create_user_id;
        private long create_time;
        private Object modify_user_id;
        private Object modify_time;
        private Object i;
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

        public String getModel_type() {
            return model_type;
        }

        public void setModel_type(String model_type) {
            this.model_type = model_type;
        }

        public String getVehicle_model_name() {
            return vehicle_model_name;
        }

        public void setVehicle_model_name(String vehicle_model_name) {
            this.vehicle_model_name = vehicle_model_name;
        }

        public String getModel_image() {
            return model_image;
        }

        public void setModel_image(String model_image) {
            this.model_image = model_image;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCreate_user_id() {
            return create_user_id;
        }

        public void setCreate_user_id(String create_user_id) {
            this.create_user_id = create_user_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public Object getModify_user_id() {
            return modify_user_id;
        }

        public void setModify_user_id(Object modify_user_id) {
            this.modify_user_id = modify_user_id;
        }

        public Object getModify_time() {
            return modify_time;
        }

        public void setModify_time(Object modify_time) {
            this.modify_time = modify_time;
        }

        public Object getI() {
            return i;
        }

        public void setI(Object i) {
            this.i = i;
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

    public static class SeriesBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : 00b4a7332b2e4d34a1ef2b50bae14002
         * brand_id : d93196523b7042778a49087b184b1d40
         * code : 2324
         * code_desc : 奇瑞E5
         * sort : 10185
         */

        private MapBeanX map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String brand_id;
        private String code;
        private String code_desc;
        private int sort;
        private List<?> relationModelList;
        private List<?> orderByModelList;

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

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode_desc() {
            return code_desc;
        }

        public void setCode_desc(String code_desc) {
            this.code_desc = code_desc;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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

        public static class MapBeanX {
        }
    }

    public static class VehicleModelBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : 0edb073636a34988977c8f69a456a968
         * series_id : 00b4a7332b2e4d34a1ef2b50bae14002
         * code : 16602
         * code_desc : 2014款 1.8L CVT优悦型
         * sort : 103651
         */

        private MapBeanXX map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String series_id;
        private String code;
        private String code_desc;
        private int sort;
        private List<?> relationModelList;
        private List<?> orderByModelList;

        public MapBeanXX getMap() {
            return map;
        }

        public void setMap(MapBeanXX map) {
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

        public String getSeries_id() {
            return series_id;
        }

        public void setSeries_id(String series_id) {
            this.series_id = series_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode_desc() {
            return code_desc;
        }

        public void setCode_desc(String code_desc) {
            this.code_desc = code_desc;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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

        public static class MapBeanXX {
        }
    }

    public static class ShareCarLongOrderBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : d664993c94b511e7a8c7d43d7eb1d148
         * order_no : 17090900508886
         * order_time : 1504889400000
         * order_state : 0
         * customer_id : 31e5a83e798911e7a43900163e1ae70a
         * customer_phone : 18356085323
         * customer_name : 陶文磊
         * customer_idcard : 342426199301084639
         * fetch_time : null
         * return_time : null
         * order_pay_time : null
         * use_time : null
         * fetch_store : a3b052484ce7480cbb5b36a9ff134107
         * return_store : a3b052484ce7480cbb5b36a9ff134107
         * lease_remark : null
         * brand : null
         * car_series : null
         * model : null
         * car_type : null
         * day_cost : 3
         * deposit_cost : 0
         * single_cost : 2.3
         * total_cost : 2.3
         * no_deductible : 0.2
         * pay_channel : 2
         * pay_status : 1
         * source_id : a76f8b667cad11e7a43900163e1ae70a
         * is_exception : 0
         * exception_remark : null
         * manage_fetch_time : null
         * manage_return_time : null
         * cost_remark : null
         * fee_setting_id : b34c4ed7924811e7a8c7d43d7eb1d148
         * lock_state
         */

        private MapBeanXXX map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String order_no;
        private long order_time;
        private String order_state;
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
        private int day_cost;
        private double deposit_cost;
        private double single_cost;
        private double total_cost;
        private double no_deductible;
        private double add_cost;
        private String pay_channel;
        private String pay_status;
        private String source_id;
        private String is_exception;
        private String exception_remark;
        private Object manage_fetch_time;
        private Object manage_return_time;
        private Object cost_remark;
        private String fee_setting_id;
        private List<?> relationModelList;
        private List<?> orderByModelList;
        private String add_usetime;
        private String lock_state;

        public String getLock_state() {
            return lock_state;
        }

        public void setLock_state(String lock_state) {
            this.lock_state = lock_state;
        }

        public String getAdd_usetime() {
            return add_usetime;
        }

        public void setAdd_usetime(String add_usetime) {
            this.add_usetime = add_usetime;
        }

        public double getAdd_cost() {
            return add_cost;
        }

        public void setAdd_cost(double add_cost) {
            this.add_cost = add_cost;
        }

        public MapBeanXXX getMap() {
            return map;
        }

        public void setMap(MapBeanXXX map) {
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

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
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

        public int getDay_cost() {
            return day_cost;
        }

        public void setDay_cost(int day_cost) {
            this.day_cost = day_cost;
        }

        public double getDeposit_cost() {
            return deposit_cost;
        }

        public void setDeposit_cost(double deposit_cost) {
            this.deposit_cost = deposit_cost;
        }

        public double getSingle_cost() {
            return single_cost;
        }

        public void setSingle_cost(double single_cost) {
            this.single_cost = single_cost;
        }

        public double getTotal_cost() {
            return total_cost;
        }

        public void setTotal_cost(double total_cost) {
            this.total_cost = total_cost;
        }

        public double getNo_deductible() {
            return no_deductible;
        }

        public void setNo_deductible(double no_deductible) {
            this.no_deductible = no_deductible;
        }

        public String getPay_channel() {
            return pay_channel;
        }

        public void setPay_channel(String pay_channel) {
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

        public String getException_remark() {
            return exception_remark;
        }

        public void setException_remark(String exception_remark) {
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

        public Object getCost_remark() {
            return cost_remark;
        }

        public void setCost_remark(Object cost_remark) {
            this.cost_remark = cost_remark;
        }

        public String getFee_setting_id() {
            return fee_setting_id;
        }

        public void setFee_setting_id(String fee_setting_id) {
            this.fee_setting_id = fee_setting_id;
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

        public static class MapBeanXXX {
        }
    }

    public static class BrandBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : d93196523b7042778a49087b184b1d40
         * code : 26
         * code_desc : 奇瑞
         * sort : 615
         * first_letter : Q
         * brand_image : upload/image/20170519/6BreK8cQw5MnJRgr6sz81XkbfkIJn5lc122454667.jpg
         */

        private MapBeanXXXX map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String code;
        private String code_desc;
        private int sort;
        private String first_letter;
        private String brand_image;
        private List<?> relationModelList;
        private List<?> orderByModelList;

        public MapBeanXXXX getMap() {
            return map;
        }

        public void setMap(MapBeanXXXX map) {
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode_desc() {
            return code_desc;
        }

        public void setCode_desc(String code_desc) {
            this.code_desc = code_desc;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getFirst_letter() {
            return first_letter;
        }

        public void setFirst_letter(String first_letter) {
            this.first_letter = first_letter;
        }

        public String getBrand_image() {
            return brand_image;
        }

        public void setBrand_image(String brand_image) {
            this.brand_image = brand_image;
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

        public static class MapBeanXXXX {
        }
    }

    public static class ShareInformationBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : a76f8b667cad11e7a43900163e1ae70a
         * vehicle_brand_id : d93196523b7042778a49087b184b1d40
         * vehicle_serise_id : 00b4a7332b2e4d34a1ef2b50bae14002
         * vehicle_model_id : 0edb073636a34988977c8f69a456a968
         * vehicle_model_custom : 9a4a002e402d11e78c5300163e000e21
         * displacement : 1845
         * number_seats : 5
         * number_doors : 4
         * fuel_type : 汽油
         * gearbox_type : 无级变速箱(CVT)
         * fuel_label : 93号(京92号)
         * drive_mode : 无
         * engine_intake_form : 自然吸气
         * sky_light : 无
         * reversing_radar : 前无/后标配
         * airbag : 主标配/副标配
         * dvd : 有
         * gps : 无
         * license_plate_number : 皖 A 00001
         * cable_gps_number : 11211
         * wireless_gps_number : 11211
         * daily_average_price : null
         * base_premium : null
         * state : 4
         * tank_capacity : 22.3kWh
         * loudspeaker_box : null
         * chair : 仿皮
         * store_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
         * audit_time : 2017-08-09 10:51:33
         * shelves_time : 2017-08-09 10:59:34
         * lower_shelves_time : 2017-08-25 14:31:47
         * estimate_time : null
         * image_path : null
         * dump_energy : 100%
         * shelves_id : a3b052484ce7480cbb5b36a9ff134107
         * offshelve_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
         * brand_code : 奇瑞
         * series_code : 奇瑞E5
         * model_code : 2014款 1.8L CVT优悦型
         * vehicle_model_name : 电动汽车型
         * store_name : 龙爷门店
         * shelves_name : 小乐子门店
         * offshelve_name : 龙爷门店
         * shelves_admin : 91f7096423c2427294c16c50fa119d90
         * offshelve_admin : null
         * bespeak : false
         * show_image : upload/image/20170809/NQuBdJHeSJwweEUiX7axEct4z2iwFrJa105126052.jpg
         */

        private MapBeanXXXXX map;
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
        private String lower_shelves_time;
        private Object estimate_time;
        private Object image_path;
        private String dump_energy;
        private String shelves_id;
        private String offshelve_id;
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
        private String life_km;

        public String getLife_km() {
            return life_km;
        }

        public void setLife_km(String life_km) {
            this.life_km = life_km;
        }

        public MapBeanXXXXX getMap() {
            return map;
        }

        public void setMap(MapBeanXXXXX map) {
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

        public String getLower_shelves_time() {
            return lower_shelves_time;
        }

        public void setLower_shelves_time(String lower_shelves_time) {
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

        public String getOffshelve_id() {
            return offshelve_id;
        }

        public void setOffshelve_id(String offshelve_id) {
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

        public static class MapBeanXXXXX {
        }
    }
}
