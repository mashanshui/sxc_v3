package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/1/13.
 */

public class LuxuryCarListBean {

    /**
     * shareStore : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"2d1aabc5378a4f109cbb8bebfd8a3f44","store_name":"合肥门店","address_prov":"a3cbfa00f8a011e69c07408d5c04d4aa","address_city":"a3cc3845f8a011e69c07408d5c04d4aa","address_district":"a3e1440ef8a011e69c07408d5c04d4aa","address_details":"徽商总部广场","start_time":"09:00","end_time":"17:00","belong_store":"2d1aabc5378a4f109cbb8bebfd8a3f44","phone":"18656135336","type":"1","manage":"龙爷","manage_phone":"18656135336","store_psw":null,"longitude":"117.332798,117.332798,117.332798,117.332798,117.332798","latitude":"31.786872,31.786872,31.786872,31.786872,31.786872","create_time":1501723890000,"provname":"安徽省","cityname":"合肥市","districtname":"包河区","usecarnum":null,"park_num":0}
     * list : [{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"b11f5271f7a611e7ac4900163e1ae70a","vehicle_brand_id":"19e7c22c4f2c400a9921e4454ace090e","vehicle_serise_id":"03ea7df8787545dd94eb5a34515b5d9d","vehicle_model_id":"05e48142c84f48b4acd90d60f7d06e48","vehicle_model_custom":"aeedb4c6402d11e78c5300163e000e21","displacement":"5935","number_seats":2,"number_doors":2,"fuel_type":"汽油","gearbox_type":"手自一体变速箱(AT)","fuel_label":"97号(京95号)","drive_mode":"无","engine_intake_form":"自然吸气","sky_light":"无","reversing_radar":"前标配/后标配","airbag":"主标配/副标配","dvd":"标配","gps":"标配","license_plate_number":"豪12324555","cable_gps_number":"117422300000709","wireless_gps_number":null,"daily_average_price":null,"base_premium":null,"state":"2","tank_capacity":"23.6kWh","loudspeaker_box":null,"chair":"仿皮","store_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","audit_time":"2018-01-12 22:41:36","shelves_time":"2018-01-12 22:41:46","lower_shelves_time":null,"estimate_time":null,"image_path":null,"charge_status":"0","dump_energy":"42","shelves_id":"2d1aabc5378a4f109cbb8bebfd8a3f44","offshelve_id":null,"brand_code":"阿斯顿·马丁","series_code":"V12 Vantage","model_code":"2015款 6.0L S","vehicle_model_name":"豪华型","store_name":"合肥门店","shelves_name":"合肥门店","offshelve_name":null,"shelves_admin":"91f7096423c2427294c16c50fa119d90","offshelve_admin":null,"bespeak":false,"show_image":"upload/shareimage/20180112/XzUEPPydDVtfHon5Aa5lAn5Ki77CI8OD224135103.jpg","life_km":0,"share_fee_setting_type":"玛莎拉蒂","share_fee_long_setting_type":"玛莎拉蒂","classno":"12345","engineno":"123456","city_code":"HEFEI","newSettingList":null,"newLongSettingList":null,"no_deductible":null,"maintain_type_id":null,"share_fee_more_setting_type":"玛莎拉蒂"}]
     * settingList : ["租金与不计免赔金额根据实际租赁天数计算，押金根据车型确定","确认后，费用直接扣除","提前还车，不存在费用返还","超过租赁天数，按分时收费","最终解释权归平台所有"]
     */

    private ShareStoreBean shareStore;
    private List<ListBean> list;
    private List<String> settingList;

    public ShareStoreBean getShareStore() {
        return shareStore;
    }

    public void setShareStore(ShareStoreBean shareStore) {
        this.shareStore = shareStore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<String> getSettingList() {
        return settingList;
    }

    public void setSettingList(List<String> settingList) {
        this.settingList = settingList;
    }

    public static class ShareStoreBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : 2d1aabc5378a4f109cbb8bebfd8a3f44
         * store_name : 合肥门店
         * address_prov : a3cbfa00f8a011e69c07408d5c04d4aa
         * address_city : a3cc3845f8a011e69c07408d5c04d4aa
         * address_district : a3e1440ef8a011e69c07408d5c04d4aa
         * address_details : 徽商总部广场
         * start_time : 09:00
         * end_time : 17:00
         * belong_store : 2d1aabc5378a4f109cbb8bebfd8a3f44
         * phone : 18656135336
         * type : 1
         * manage : 龙爷
         * manage_phone : 18656135336
         * store_psw : null
         * longitude : 117.332798,117.332798,117.332798,117.332798,117.332798
         * latitude : 31.786872,31.786872,31.786872,31.786872,31.786872
         * create_time : 1501723890000
         * provname : 安徽省
         * cityname : 合肥市
         * districtname : 包河区
         * usecarnum : null
         * park_num : 0
         */

        private MapBean map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String store_name;
        private String address_prov;
        private String address_city;
        private String address_district;
        private String address_details;
        private String start_time;
        private String end_time;
        private String belong_store;
        private String phone;
        private String type;
        private String manage;
        private String manage_phone;
        private Object store_psw;
        private String longitude;
        private String latitude;
        private long create_time;
        private String provname;
        private String cityname;
        private String districtname;
        private Object usecarnum;
        private int park_num;
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

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getAddress_prov() {
            return address_prov;
        }

        public void setAddress_prov(String address_prov) {
            this.address_prov = address_prov;
        }

        public String getAddress_city() {
            return address_city;
        }

        public void setAddress_city(String address_city) {
            this.address_city = address_city;
        }

        public String getAddress_district() {
            return address_district;
        }

        public void setAddress_district(String address_district) {
            this.address_district = address_district;
        }

        public String getAddress_details() {
            return address_details;
        }

        public void setAddress_details(String address_details) {
            this.address_details = address_details;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getBelong_store() {
            return belong_store;
        }

        public void setBelong_store(String belong_store) {
            this.belong_store = belong_store;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getManage() {
            return manage;
        }

        public void setManage(String manage) {
            this.manage = manage;
        }

        public String getManage_phone() {
            return manage_phone;
        }

        public void setManage_phone(String manage_phone) {
            this.manage_phone = manage_phone;
        }

        public Object getStore_psw() {
            return store_psw;
        }

        public void setStore_psw(Object store_psw) {
            this.store_psw = store_psw;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getProvname() {
            return provname;
        }

        public void setProvname(String provname) {
            this.provname = provname;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getDistrictname() {
            return districtname;
        }

        public void setDistrictname(String districtname) {
            this.districtname = districtname;
        }

        public Object getUsecarnum() {
            return usecarnum;
        }

        public void setUsecarnum(Object usecarnum) {
            this.usecarnum = usecarnum;
        }

        public int getPark_num() {
            return park_num;
        }

        public void setPark_num(int park_num) {
            this.park_num = park_num;
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

    public static class ListBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : b11f5271f7a611e7ac4900163e1ae70a
         * vehicle_brand_id : 19e7c22c4f2c400a9921e4454ace090e
         * vehicle_serise_id : 03ea7df8787545dd94eb5a34515b5d9d
         * vehicle_model_id : 05e48142c84f48b4acd90d60f7d06e48
         * vehicle_model_custom : aeedb4c6402d11e78c5300163e000e21
         * displacement : 5935
         * number_seats : 2
         * number_doors : 2
         * fuel_type : 汽油
         * gearbox_type : 手自一体变速箱(AT)
         * fuel_label : 97号(京95号)
         * drive_mode : 无
         * engine_intake_form : 自然吸气
         * sky_light : 无
         * reversing_radar : 前标配/后标配
         * airbag : 主标配/副标配
         * dvd : 标配
         * gps : 标配
         * license_plate_number : 豪12324555
         * cable_gps_number : 117422300000709
         * wireless_gps_number : null
         * daily_average_price : null
         * base_premium : null
         * state : 2
         * tank_capacity : 23.6kWh
         * loudspeaker_box : null
         * chair : 仿皮
         * store_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
         * audit_time : 2018-01-12 22:41:36
         * shelves_time : 2018-01-12 22:41:46
         * lower_shelves_time : null
         * estimate_time : null
         * image_path : null
         * charge_status : 0
         * dump_energy : 42
         * shelves_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
         * offshelve_id : null
         * brand_code : 阿斯顿·马丁
         * series_code : V12 Vantage
         * model_code : 2015款 6.0L S
         * vehicle_model_name : 豪华型
         * store_name : 合肥门店
         * shelves_name : 合肥门店
         * offshelve_name : null
         * shelves_admin : 91f7096423c2427294c16c50fa119d90
         * offshelve_admin : null
         * bespeak : false
         * show_image : upload/shareimage/20180112/XzUEPPydDVtfHon5Aa5lAn5Ki77CI8OD224135103.jpg
         * life_km : 0
         * share_fee_setting_type : 玛莎拉蒂
         * share_fee_long_setting_type : 玛莎拉蒂
         * classno : 12345
         * engineno : 123456
         * city_code : HEFEI
         * newSettingList : null
         * newLongSettingList : null
         * no_deductible : null
         * maintain_type_id : null
         * share_fee_more_setting_type : 玛莎拉蒂
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
        private Object wireless_gps_number;
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
        private String charge_status;
        private String dump_energy;
        private String shelves_id;
        private Object offshelve_id;
        private String brand_code;
        private String series_code;
        private String model_code;
        private String vehicle_model_name;
        private String store_name;
        private String shelves_name;
        private Object offshelve_name;
        private String shelves_admin;
        private Object offshelve_admin;
        private boolean bespeak;
        private String show_image;
        private int life_km;
        private String share_fee_setting_type;
        private String share_fee_long_setting_type;
        private String classno;
        private String engineno;
        private String city_code;
        private Object newSettingList;
        private Object newLongSettingList;
        private Object no_deductible;
        private Object maintain_type_id;
        private String share_fee_more_setting_type;
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

        public Object getWireless_gps_number() {
            return wireless_gps_number;
        }

        public void setWireless_gps_number(Object wireless_gps_number) {
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

        public String getCharge_status() {
            return charge_status;
        }

        public void setCharge_status(String charge_status) {
            this.charge_status = charge_status;
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

        public Object getOffshelve_name() {
            return offshelve_name;
        }

        public void setOffshelve_name(Object offshelve_name) {
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

        public int getLife_km() {
            return life_km;
        }

        public void setLife_km(int life_km) {
            this.life_km = life_km;
        }

        public String getShare_fee_setting_type() {
            return share_fee_setting_type;
        }

        public void setShare_fee_setting_type(String share_fee_setting_type) {
            this.share_fee_setting_type = share_fee_setting_type;
        }

        public String getShare_fee_long_setting_type() {
            return share_fee_long_setting_type;
        }

        public void setShare_fee_long_setting_type(String share_fee_long_setting_type) {
            this.share_fee_long_setting_type = share_fee_long_setting_type;
        }

        public String getClassno() {
            return classno;
        }

        public void setClassno(String classno) {
            this.classno = classno;
        }

        public String getEngineno() {
            return engineno;
        }

        public void setEngineno(String engineno) {
            this.engineno = engineno;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public Object getNewSettingList() {
            return newSettingList;
        }

        public void setNewSettingList(Object newSettingList) {
            this.newSettingList = newSettingList;
        }

        public Object getNewLongSettingList() {
            return newLongSettingList;
        }

        public void setNewLongSettingList(Object newLongSettingList) {
            this.newLongSettingList = newLongSettingList;
        }

        public Object getNo_deductible() {
            return no_deductible;
        }

        public void setNo_deductible(Object no_deductible) {
            this.no_deductible = no_deductible;
        }

        public Object getMaintain_type_id() {
            return maintain_type_id;
        }

        public void setMaintain_type_id(Object maintain_type_id) {
            this.maintain_type_id = maintain_type_id;
        }

        public String getShare_fee_more_setting_type() {
            return share_fee_more_setting_type;
        }

        public void setShare_fee_more_setting_type(String share_fee_more_setting_type) {
            this.share_fee_more_setting_type = share_fee_more_setting_type;
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
}
