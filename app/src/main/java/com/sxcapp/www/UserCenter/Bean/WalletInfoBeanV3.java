package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/3.
 */

public class WalletInfoBeanV3 {

    /**
     * customer_integral : 11
     * customer_integral_remark : ["积分商城_http://www.baidu.com","如何积分_http://www.qq.com"]
     * customer_sxc_deposit : 0.00
     * customer_sxc_deposit_remark : ["用于随心租车"]
     * customer_share_deposit_remark : ["用于共享电动、燃油、豪车租赁"]
     * customer_coupontotal : 0
     * customer_balance : 8759.75
     * customer_share_deposit : 5805.02
     * customer_cantuse_coupontotal : 0
     * customer_balance_remark : ["充100元送10元优惠券","多充多送，最多100元优惠券"]
     * customer_used_coupontotal : 0
     * customer_canuse_coupontotal : 0
     */

    private String customer_integral;
    private String customer_sxc_deposit;
    private String customer_coupontotal;
    private String customer_balance;
    private String customer_share_deposit;
    private String customer_cantuse_coupontotal;
    private String customer_used_coupontotal;
    private String customer_canuse_coupontotal;
    private List<String> customer_integral_remark;
    private List<String> customer_sxc_deposit_remark;
    private List<String> customer_share_deposit_remark;
    private List<String> customer_balance_remark;

    public String getCustomer_integral() {
        return customer_integral;
    }

    public void setCustomer_integral(String customer_integral) {
        this.customer_integral = customer_integral;
    }

    public String getCustomer_sxc_deposit() {
        return customer_sxc_deposit;
    }

    public void setCustomer_sxc_deposit(String customer_sxc_deposit) {
        this.customer_sxc_deposit = customer_sxc_deposit;
    }

    public String getCustomer_coupontotal() {
        return customer_coupontotal;
    }

    public void setCustomer_coupontotal(String customer_coupontotal) {
        this.customer_coupontotal = customer_coupontotal;
    }

    public String getCustomer_balance() {
        return customer_balance;
    }

    public void setCustomer_balance(String customer_balance) {
        this.customer_balance = customer_balance;
    }

    public String getCustomer_share_deposit() {
        return customer_share_deposit;
    }

    public void setCustomer_share_deposit(String customer_share_deposit) {
        this.customer_share_deposit = customer_share_deposit;
    }

    public String getCustomer_cantuse_coupontotal() {
        return customer_cantuse_coupontotal;
    }

    public void setCustomer_cantuse_coupontotal(String customer_cantuse_coupontotal) {
        this.customer_cantuse_coupontotal = customer_cantuse_coupontotal;
    }

    public String getCustomer_used_coupontotal() {
        return customer_used_coupontotal;
    }

    public void setCustomer_used_coupontotal(String customer_used_coupontotal) {
        this.customer_used_coupontotal = customer_used_coupontotal;
    }

    public String getCustomer_canuse_coupontotal() {
        return customer_canuse_coupontotal;
    }

    public void setCustomer_canuse_coupontotal(String customer_canuse_coupontotal) {
        this.customer_canuse_coupontotal = customer_canuse_coupontotal;
    }

    public List<String> getCustomer_integral_remark() {
        return customer_integral_remark;
    }

    public void setCustomer_integral_remark(List<String> customer_integral_remark) {
        this.customer_integral_remark = customer_integral_remark;
    }

    public List<String> getCustomer_sxc_deposit_remark() {
        return customer_sxc_deposit_remark;
    }

    public void setCustomer_sxc_deposit_remark(List<String> customer_sxc_deposit_remark) {
        this.customer_sxc_deposit_remark = customer_sxc_deposit_remark;
    }

    public List<String> getCustomer_share_deposit_remark() {
        return customer_share_deposit_remark;
    }

    public void setCustomer_share_deposit_remark(List<String> customer_share_deposit_remark) {
        this.customer_share_deposit_remark = customer_share_deposit_remark;
    }

    public List<String> getCustomer_balance_remark() {
        return customer_balance_remark;
    }

    public void setCustomer_balance_remark(List<String> customer_balance_remark) {
        this.customer_balance_remark = customer_balance_remark;
    }
}
