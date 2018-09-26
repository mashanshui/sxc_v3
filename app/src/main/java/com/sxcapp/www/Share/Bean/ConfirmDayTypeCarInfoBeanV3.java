package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/5/2.
 */
//order_no（订单号），fetch_store_name（取车门店），return_store_name（还车门店），
// license_plate_number（车牌号），car_name（车名），rent_days（租车天数，没有返回0），
// rent_days_id（租车天数规则id），rent_days_settings（租车时长规则），car_image（车图），
// settings（规则），surp_audit_timesurp_audit_time（等待审核时间:分钟），
// audit_state（审核状态：0.待审 1.审核通过 2.合同审核不通过3.身份证证件不一致4.担保人信息不通过），
// audit_msg（审核信息），audit_time（审核时间：分钟），guarantor_time（担保信息提交时间）

public class ConfirmDayTypeCarInfoBeanV3 {

    /**
     * order_no : 18050222171285
     * settings : ["豪车租每笔订单需收取10.00手续费"]
     * rent_days : 0.00
     * audit_state : 0
     * license_plate_number : 豪LAX101
     * return_store_name : 合肥门店
     * surp_audit_time : -649
     * car_name : 奥迪A5
     * car_image : ["http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/17/a5ec05699cf948e9a6d5703752872727.png?Expires=1839312131&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=46l9HoLfrsWbpbpMvwBxVgnp%2BDE%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/860c0937dec94519b66b9ec525c8c397.png?Expires=1839933079&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=ISww2p%2Bv6%2FSfy6B9t48TuMI7kpw%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/ec3aaf6426e3401b98e56c4347aa3ec5.png?Expires=1839933081&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=T1fgI8Vv%2FDrIqNPeiB7Puptd9vA%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/24/e5140b037b5d4770a4a63215e460c450.png?Expires=1839933083&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=yas0WrJi5%2FQ6LxOHgxd%2BlSS6yDY%3D"]
     * rent_days_id :
     * guarantor_time : 2018-05-02 22:22:39
     * fetch_store_name : 合肥门店
     * rent_days_settings : [{"rent_days_id":"4028801362c1d58f0162c1de4bf20000","rent_days_title":"1天:租金100元,不计免赔10元,保险费10元"},{"rent_days_id":"4028801362c1e1cb0162c1e930c80002","rent_days_title":"2天:租金200元,不计免赔20元,保险费20元"}]
     * audit_msg : 信息审核中
     */

    private String order_no;
    private int rent_days;
    private String audit_state;
    private String license_plate_number;
    private String return_store_name;
    private int surp_audit_time;
    private String car_name;
    private String rent_days_id;
    private String guarantor_time;
    private String fetch_store_name;
    private String audit_msg;
    private List<String> settings;
    private List<String> car_image;
    private List<RentDaysSettingsBean> rent_days_settings;
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

    public int getRent_days() {
        return rent_days;
    }

    public void setRent_days(int rent_days) {
        this.rent_days = rent_days;
    }

    public String getAudit_state() {
        return audit_state;
    }

    public void setAudit_state(String audit_state) {
        this.audit_state = audit_state;
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

    public int getSurp_audit_time() {
        return surp_audit_time;
    }

    public void setSurp_audit_time(int surp_audit_time) {
        this.surp_audit_time = surp_audit_time;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getRent_days_id() {
        return rent_days_id;
    }

    public void setRent_days_id(String rent_days_id) {
        this.rent_days_id = rent_days_id;
    }

    public String getGuarantor_time() {
        return guarantor_time;
    }

    public void setGuarantor_time(String guarantor_time) {
        this.guarantor_time = guarantor_time;
    }

    public String getFetch_store_name() {
        return fetch_store_name;
    }

    public void setFetch_store_name(String fetch_store_name) {
        this.fetch_store_name = fetch_store_name;
    }

    public String getAudit_msg() {
        return audit_msg;
    }

    public void setAudit_msg(String audit_msg) {
        this.audit_msg = audit_msg;
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

    public List<RentDaysSettingsBean> getRent_days_settings() {
        return rent_days_settings;
    }

    public void setRent_days_settings(List<RentDaysSettingsBean> rent_days_settings) {
        this.rent_days_settings = rent_days_settings;
    }

    public static class RentDaysSettingsBean {
        /**
         * rent_days_id : 4028801362c1d58f0162c1de4bf20000
         * rent_days_title : 1天:租金100元,不计免赔10元,保险费10元
         */

        private String rent_days_id;
        private String rent_days_title;

        public String getRent_days_id() {
            return rent_days_id;
        }

        public void setRent_days_id(String rent_days_id) {
            this.rent_days_id = rent_days_id;
        }

        public String getRent_days_title() {
            return rent_days_title;
        }

        public void setRent_days_title(String rent_days_title) {
            this.rent_days_title = rent_days_title;
        }
    }
}

