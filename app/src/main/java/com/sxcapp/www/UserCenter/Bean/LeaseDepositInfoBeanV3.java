package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/10.
 */

public class LeaseDepositInfoBeanV3 {
    private String customer_sxc_deposit;
    private List<String> customer_sxc_deposit_remark;

    public String getCustomer_sxc_deposit() {
        return customer_sxc_deposit;
    }

    public void setCustomer_sxc_deposit(String customer_sxc_deposit) {
        this.customer_sxc_deposit = customer_sxc_deposit;
    }

    public List<String> getCustomer_sxc_deposit_remark() {
        return customer_sxc_deposit_remark;
    }

    public void setCustomer_sxc_deposit_remark(List<String> customer_sxc_deposit_remark) {
        this.customer_sxc_deposit_remark = customer_sxc_deposit_remark;
    }

    public LeaseDepositInfoBeanV3(String customer_sxc_deposit, List<String> customer_sxc_deposit_remark) {
        this.customer_sxc_deposit = customer_sxc_deposit;
        this.customer_sxc_deposit_remark = customer_sxc_deposit_remark;
    }
}
