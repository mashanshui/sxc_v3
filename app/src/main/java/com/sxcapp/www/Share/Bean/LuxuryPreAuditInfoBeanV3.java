package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/28.
 */

public class LuxuryPreAuditInfoBeanV3 {
    //    order_no（订单号），appoint_time（限时多少分钟免费时间），fetch_store_name（取车门店），return_store_name（还车门店）
// ，license_plate_number（车牌号），car_name（车名），rent_cost（日租金），basic_premium（基础保险费），no_deductible（不计免赔），contract_orderno（合同编号），car_image（车图），
//    surp_appoint_time（剩余预约时间，超过预约返回0），settings（规则）
//    order_time（下单时间），contract_flag（合同 0.未上传 1.已上传），guarantor_flag（担保人 0.未上传 1.已上传），idcard_flag（身份证识别照片：0.未上传 1.已上传）

    private String order_no;
    private String appoint_time;
    private String fetch_store_name;
    private String return_store_name;
    private String license_plate_number;
    private String car_name;
    private String rent_cost;
    private String basic_premium;
    private String no_deductible;
    private String contract_orderno;
    private List<String> car_image;
    private String surp_appoint_time;
    private List<String> settings;
    private String order_time;
    private int contract_flag;
    private int guarantor_flag;
    private int idcard_flag;

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getContract_flag() {
        return contract_flag;
    }

    public void setContract_flag(int contract_flag) {
        this.contract_flag = contract_flag;
    }

    public int getGuarantor_flag() {
        return guarantor_flag;
    }

    public void setGuarantor_flag(int guarantor_flag) {
        this.guarantor_flag = guarantor_flag;
    }

    public int getIdcard_flag() {
        return idcard_flag;
    }

    public void setIdcard_flag(int idcard_flag) {
        this.idcard_flag = idcard_flag;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAppoint_time() {
        return appoint_time;
    }

    public void setAppoint_time(String appoint_time) {
        this.appoint_time = appoint_time;
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

    public String getRent_cost() {
        return rent_cost;
    }

    public void setRent_cost(String rent_cost) {
        this.rent_cost = rent_cost;
    }

    public String getBasic_premium() {
        return basic_premium;
    }

    public void setBasic_premium(String basic_premium) {
        this.basic_premium = basic_premium;
    }

    public String getNo_deductible() {
        return no_deductible;
    }

    public void setNo_deductible(String no_deductible) {
        this.no_deductible = no_deductible;
    }

    public String getContract_orderno() {
        return contract_orderno;
    }

    public void setContract_orderno(String contract_orderno) {
        this.contract_orderno = contract_orderno;
    }

    public List<String> getCar_image() {
        return car_image;
    }

    public void setCar_image(List<String> car_image) {
        this.car_image = car_image;
    }

    public String getSurp_appoint_time() {
        return surp_appoint_time;
    }

    public void setSurp_appoint_time(String surp_appoint_time) {
        this.surp_appoint_time = surp_appoint_time;
    }

    public List<String> getSettings() {
        return settings;
    }

    public void setSettings(List<String> settings) {
        this.settings = settings;
    }
}
