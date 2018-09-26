package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/28.
 */

public class LuxuryAppointInfoBeanV3 {

    /**
     * car_name : 奇瑞E5
     * settings : ["豪车租预约收费10.00元","10.00分钟内取消订单可返还预约费用","超过10.00分钟将按0.12元/每分钟计算费用","预约前押金必须已交，余额不少于10.00元","如有疑问请联系客服，最终解释权归本平台所有"]
     * fetch_store : ee8e536a06054cce8656bbc4a443c4ef
     * car_image : ["http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/27/fe00d88f0a1f4b12b1e8eaa9f4d67978.png?Expires=1840194412&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=VQRSkDBKnwrK%2F%2BJjkoTkxEFbyqY%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/27/d6ed9888f6bc482ca3f3beb345fba3cf.png?Expires=1840194415&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=DkGMSB7xoUUrV2Gz8Pr%2FSa%2Bl5xk%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/27/a0b187678e6641a3907f2eb61acb4e52.png?Expires=1840194417&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=TnFFWVQlo2mIgtU8eWpKWFPuwAY%3D"]
     * fetch_store_name : 合肥门店
     * source_id : 4028801162d1ad110162d2c793050002
     * return_store : ee8e536a06054cce8656bbc4a443c4ef
     * return_store_name : 合肥门店
     * license_plate_number : 111111
     */

    private String car_name;
    private String fetch_store;
    private String fetch_store_name;
    private String source_id;
    private String return_store;
    private String return_store_name;
    private String license_plate_number;
    private List<String> settings;
    private List<String> car_image;

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getFetch_store() {
        return fetch_store;
    }

    public void setFetch_store(String fetch_store) {
        this.fetch_store = fetch_store;
    }

    public String getFetch_store_name() {
        return fetch_store_name;
    }

    public void setFetch_store_name(String fetch_store_name) {
        this.fetch_store_name = fetch_store_name;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getReturn_store() {
        return return_store;
    }

    public void setReturn_store(String return_store) {
        this.return_store = return_store;
    }

    public String getReturn_store_name() {
        return return_store_name;
    }

    public void setReturn_store_name(String return_store_name) {
        this.return_store_name = return_store_name;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }

    public List<String> getSettings() {
        return settings;
    }

    public void setSettings(List<String> settings) {
        this.settings = settings;
    }

    public List<String> getCar_image() {
        return car_image;
    }

    public void setCar_image(List<String> car_image) {
        this.car_image = car_image;
    }
}
