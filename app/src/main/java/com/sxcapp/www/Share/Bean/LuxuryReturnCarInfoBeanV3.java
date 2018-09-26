package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/5/3.
 */

public class LuxuryReturnCarInfoBeanV3 {

    /**
     * order_no : 18050314274734
     * car_name : 奥迪A5
     * settings : ["预约需先付费100.00元，预约10分钟免费","超过10分钟，自动计费，每分钟0.20元且预约费不予退还","租金（100.00元/天 * 30天） 3000.00元","基础保险费（10.00元/天 * 30天） 300.00元","不计免赔（10.00元/天 * 30天） 300.00元","手续费10.00元"]
     * total_cost : 3610.00
     * car_image : ["http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/17/a5ec05699cf948e9a6d5703752872727.png?Expires=1839312131&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=46l9HoLfrsWbpbpMvwBxVgnp%2BDE%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/860c0937dec94519b66b9ec525c8c397.png?Expires=1839933079&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=ISww2p%2Bv6%2FSfy6B9t48TuMI7kpw%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/ec3aaf6426e3401b98e56c4347aa3ec5.png?Expires=1839933081&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=T1fgI8Vv%2FDrIqNPeiB7Puptd9vA%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/e5140b037b5d4770a4a63215e460c450.png?Expires=1839933083&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=yas0WrJi5%2FQ6LxOHgxd%2BlSS6yDY%3D"]
     * fetch_store_name : 合肥门店
     * source_id : 4028801362d27bc50162d2a95b1e001e
     * license_plate_number : 豪LAX101
     * return_store_name : 合肥门店
     */

    private String order_no;
    private String car_name;
    private String total_cost;
    private String fetch_store_name;
    private String source_id;
    private String license_plate_number;
    private String return_store_name;
    private List<String> settings;
    private List<String> car_image;
    private String fetch_store_id;
    private String return_store_id;

    public String getFetch_store_id() {
        return fetch_store_id;
    }

    public void setFetch_store_id(String fetch_store_id) {
        this.fetch_store_id = fetch_store_id;
    }

    public String getReturn_store_id() {
        return return_store_id;
    }

    public void setReturn_store_id(String return_store_id) {
        this.return_store_id = return_store_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
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

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }

    public String getReturn_store_name() {
        return return_store_name;
    }

    public void setReturn_store_name(String return_store_name) {
        this.return_store_name = return_store_name;
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
