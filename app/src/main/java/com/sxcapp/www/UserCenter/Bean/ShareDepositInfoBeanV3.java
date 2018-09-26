package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class ShareDepositInfoBeanV3 {

    /**
     * customer_share_deposit_remark : ["押金在最后一个订单交易成功的25日后可以提现","客户点击提现按钮后提交提现信息","经平台审核后1~3个工作日返还押金"]
     * customer_share_deposit : 5805.02
     */

    private String customer_share_deposit;
    private List<String> customer_share_deposit_remark;

    public String getCustomer_share_deposit() {
        return customer_share_deposit;
    }

    public void setCustomer_share_deposit(String customer_share_deposit) {
        this.customer_share_deposit = customer_share_deposit;
    }

    public List<String> getCustomer_share_deposit_remark() {
        return customer_share_deposit_remark;
    }

    public void setCustomer_share_deposit_remark(List<String> customer_share_deposit_remark) {
        this.customer_share_deposit_remark = customer_share_deposit_remark;
    }
}
