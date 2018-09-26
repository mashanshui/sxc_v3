package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/5/3.
 */

public class LuxuryRentCarInfoBeanV3 {

    /**
     * order_no : 18050314274734
     * settings : ["豪车租每笔订单需收取10.00元手续费","超过订单时长,将收取超时分钟费用","租金不包含油费"]
     * rent_days : 30
     * no_deductible : 10.00
     * poundage : 10.00
     * rent_cost : 3000.00
     * return_store_name : 合肥门店
     * license_plate_number : 豪LAX101
     * car_name : 奥迪A5
     * car_image : ["http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/17/a5ec05699cf948e9a6d5703752872727.png?Expires=1839312131&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=46l9HoLfrsWbpbpMvwBxVgnp%2BDE%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/860c0937dec94519b66b9ec525c8c397.png?Expires=1839933079&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=ISww2p%2Bv6%2FSfy6B9t48TuMI7kpw%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/ec3aaf6426e3401b98e56c4347aa3ec5.png?Expires=1839933081&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=T1fgI8Vv%2FDrIqNPeiB7Puptd9vA%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/e5140b037b5d4770a4a63215e460c450.png?Expires=1839933083&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=yas0WrJi5%2FQ6LxOHgxd%2BlSS6yDY%3D"]
     * coupon_size : 1
     * basic_premium : 300.00
     * fetch_store_name : 合肥门店
     * source_id : 4028801362d27bc50162d2a95b1e001e
     */

    private String order_no;
    private String rent_days;
    private String no_deductible;
    private String poundage;
    private String rent_cost;
    private String return_store_name;
    private String license_plate_number;
    private String car_name;
    private int coupon_size;
    private String basic_premium;
    private String fetch_store_name;
    private String source_id;
    private List<String> settings;
    private List<String> car_image;
    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRent_days() {
        return rent_days;
    }

    public void setRent_days(String rent_days) {
        this.rent_days = rent_days;
    }

    public String getNo_deductible() {
        return no_deductible;
    }

    public void setNo_deductible(String no_deductible) {
        this.no_deductible = no_deductible;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public String getRent_cost() {
        return rent_cost;
    }

    public void setRent_cost(String rent_cost) {
        this.rent_cost = rent_cost;
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

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public int getCoupon_size() {
        return coupon_size;
    }

    public void setCoupon_size(int coupon_size) {
        this.coupon_size = coupon_size;
    }

    public String getBasic_premium() {
        return basic_premium;
    }

    public void setBasic_premium(String basic_premium) {
        this.basic_premium = basic_premium;
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
