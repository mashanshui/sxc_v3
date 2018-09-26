package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/5/4.
 */

public class LuxuryOffOrderDetailBeanV3 {

    /**
     * order_no : 18042621205939
     * car_name : 奇瑞eQ
     * fetch_store : 合肥门店
     * car_image : http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/27/9cef790c750446828206d9fc96f7ddfd.png?Expires=1840151259&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=o1LggKOTzKlEu9Bbf2UAGqUJ%2FsI%3D
     * number_seats : 4
     * cost_remark : ["预约15分钟免费，超过15分钟，自动计费","时长费（0分钟*0.15元/分钟） 0.00元","里程费（0公里*0.25元/公里） 0.00元","不计免赔  10元","各优惠不叠加，最终解释权归本平台所有"]
     * return_store : 合肥门店
     * license_plate_number : 津LAX101
     */

    private String order_no;
    private String car_name;
    private String fetch_store;
    private List<String> car_image;
    private int number_seats;
    private String return_store;
    private String license_plate_number;
    private List<String> cost_remark;
    private List<String> exceptions;
    private String total_cost;

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
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

    public String getFetch_store() {
        return fetch_store;
    }

    public void setFetch_store(String fetch_store) {
        this.fetch_store = fetch_store;
    }

    public List<String> getCar_image() {
        return car_image;
    }

    public void setCar_image(List<String> car_image) {
        this.car_image = car_image;
    }

    public int getNumber_seats() {
        return number_seats;
    }

    public void setNumber_seats(int number_seats) {
        this.number_seats = number_seats;
    }

    public String getReturn_store() {
        return return_store;
    }

    public void setReturn_store(String return_store) {
        this.return_store = return_store;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }

    public List<String> getCost_remark() {
        return cost_remark;
    }

    public void setCost_remark(List<String> cost_remark) {
        this.cost_remark = cost_remark;
    }
}
