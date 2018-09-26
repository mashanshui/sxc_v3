package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/20.
 */

public class ElecShortPayInfoBeanV3 {

    /**
     * order_no : 18042015566668
     * gearbox_type : 固定齿比变速箱
     * total_cost : 33
     * use_time : 170
     * dump_energy : 61
     * image_flag : 1
     * car_color : 3E8AF8
     * exception_remark : ["2018/04/20,还车时电量低于70%,需支付20.00元"]
     * fetch_time : 2018-04-20 15:56:37
     * return_store_name : 合肥门店
     * license_plate_number : 测LAX104
     * pay_remark : ["订单已生成请及时支付，1小时之后系统自动扣费不能享用优惠券"]
     * use_km : 0.00
     * car_name : 奇瑞eQ
     * return_time : 2018-04-20 19:02:03
     * car_image : http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/16/efb5ee29865a4f8c896beb11ffcd45c1.jpg?Expires=1839220793&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=5Y8h%2BfZeAEsRxI%2FdIJV98xRMUjQ%3D
     * coupon_size : 0
     * number_seats : 4
     * exception_cost : 20.00
     * cost_remark : ["预约15分钟免费，超过15分钟，自动计费","首次使用前180分钟3.00元,前30公里免费","里程费（前30公里免费），使用了0公里，0.00元","不计免赔 10元"]
     * fetch_store_name : 合肥门店
     * source_id : 4028801362cd26390162cd2ef1ab000c
     * life_km : 0
     */

    private String order_no;
    private String gearbox_type;
    private String total_cost;
    private String use_time;
    private String dump_energy;
    private int image_flag;
    private String car_color;
    private String fetch_time;
    private String return_store_name;
    private String license_plate_number;
    private String use_km;
    private String car_name;
    private String return_time;
    private String car_image;
    private int coupon_size;
    private int number_seats;
    private String exception_cost;
    private String fetch_store_name;
    private String source_id;
    private String life_km;
    private List<String> exception_remark;
    private List<String> pay_remark;
    private List<String> cost_remark;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getGearbox_type() {
        return gearbox_type;
    }

    public void setGearbox_type(String gearbox_type) {
        this.gearbox_type = gearbox_type;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getDump_energy() {
        return dump_energy;
    }

    public void setDump_energy(String dump_energy) {
        this.dump_energy = dump_energy;
    }

    public int getImage_flag() {
        return image_flag;
    }

    public void setImage_flag(int image_flag) {
        this.image_flag = image_flag;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getFetch_time() {
        return fetch_time;
    }

    public void setFetch_time(String fetch_time) {
        this.fetch_time = fetch_time;
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

    public String getUse_km() {
        return use_km;
    }

    public void setUse_km(String use_km) {
        this.use_km = use_km;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public int getCoupon_size() {
        return coupon_size;
    }

    public void setCoupon_size(int coupon_size) {
        this.coupon_size = coupon_size;
    }

    public int getNumber_seats() {
        return number_seats;
    }

    public void setNumber_seats(int number_seats) {
        this.number_seats = number_seats;
    }

    public String getException_cost() {
        return exception_cost;
    }

    public void setException_cost(String exception_cost) {
        this.exception_cost = exception_cost;
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

    public String getLife_km() {
        return life_km;
    }

    public void setLife_km(String life_km) {
        this.life_km = life_km;
    }

    public List<String> getException_remark() {
        return exception_remark;
    }

    public void setException_remark(List<String> exception_remark) {
        this.exception_remark = exception_remark;
    }

    public List<String> getPay_remark() {
        return pay_remark;
    }

    public void setPay_remark(List<String> pay_remark) {
        this.pay_remark = pay_remark;
    }

    public List<String> getCost_remark() {
        return cost_remark;
    }

    public void setCost_remark(List<String> cost_remark) {
        this.cost_remark = cost_remark;
    }
}
