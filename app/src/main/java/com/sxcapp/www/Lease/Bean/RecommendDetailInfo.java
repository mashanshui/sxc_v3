package com.sxcapp.www.Lease.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/8/8.
 */

public class RecommendDetailInfo {

    /**
     * store_id : bb360ee7ebb4486f83aaa2e44713da1f
     * city_name : 合肥市
     * data : [{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"8105c12277ea11e7a43900163e1ae70a","vehicle_brand_id":"6c514d8b18904be39188f4b3aceaf915","vehicle_serise_id":"0cad55a7f9264837b9e7339bee197e18","vehicle_model_id":"0277367d030b470283a2ee1beeb630dd","vehicle_model_custom":"c29836fd402d11e78c5300163e000e21","displacement":"1997","number_seats":5,"number_doors":5,"fuel_type":"汽油","gearbox_type":"手自一体变速箱(AT)","fuel_label":"95号","drive_mode":"标配","engine_intake_form":"涡轮增压","sky_light":"标配","reversing_radar":"前标配/后标配","airbag":"主标配/副标配","dvd":null,"gps":"标配","license_plate_number":"皖A33333","cable_gps_number":"1","wireless_gps_number":"1","daily_average_price":0.01,"base_premium":0.01,"state":"2","brand_code":null,"series_code":null,"model_code":null,"vehicle_model_name":"商务型","tank_capacity":"60","loudspeaker_box":null,"chair":"真皮","store_id":"bb360ee7ebb4486f83aaa2e44713da1f","store_name":null,"audit_time":"2017-08-03 09:24:32","notAudit_time":null,"shelves_time":"2017-08-03 09:26:19","lower_shelves_time":null,"fetch_time":null,"return_time":null,"image_path":null,"estimate_time":1511971200000,"lease_car_price":1,"mName":"2013款 328i M运动型 旅行版","bName":"宝马","sName":"宝马3系(进口)"}]
     * imageList : ["upload/image/20170803/o1j9FKppQqrF47d9oJboSahkDMNpZW7W092557643.jpg","upload/image/20170803/ZRntOFFZhBcb69TaKjgSAcFpEBvZnVK3092432181.jpg"]
     * city_id : a3cc3845f8a011e69c07408d5c04d4aa
     */

    private String store_id;
    private String city_name;
    private String city_id;
    private List<LeaseCar> data;
    private List<String> imageList;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

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

    public List<LeaseCar> getData() {
        return data;
    }

    public void setData(List<LeaseCar> data) {
        this.data = data;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }


}
