package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class BalanceInfoBeanV3 {

    /**
     * customer_balance : 8759.75
     * customer_balance_remark : ["充值余额，若提现需扣除10%手续费"]
     */

    private String customer_balance;
    private List<String> customer_balance_remark;

    public String getCustomer_balance() {
        return customer_balance;
    }

    public void setCustomer_balance(String customer_balance) {
        this.customer_balance = customer_balance;
    }

    public List<String> getCustomer_balance_remark() {
        return customer_balance_remark;
    }

    public void setCustomer_balance_remark(List<String> customer_balance_remark) {
        this.customer_balance_remark = customer_balance_remark;
    }
}
