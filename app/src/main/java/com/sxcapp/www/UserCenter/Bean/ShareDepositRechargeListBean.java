package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/8.
 */

public class ShareDepositRechargeListBean {

    private List<DepositRemarkBean> deposit_remark;

    public List<DepositRemarkBean> getDeposit_remark() {
        return deposit_remark;
    }

    public void setDeposit_remark(List<DepositRemarkBean> deposit_remark) {
        this.deposit_remark = deposit_remark;
    }

    public static class DepositRemarkBean {
        /**
         * deposit_cost : 799
         * deposit_content : 799 电车押金
         */

        private String deposit_cost;
        private String deposit_content;

        public String getDeposit_cost() {
            return deposit_cost;
        }

        public void setDeposit_cost(String deposit_cost) {
            this.deposit_cost = deposit_cost;
        }

        public String getDeposit_content() {
            return deposit_content;
        }

        public void setDeposit_content(String deposit_content) {
            this.deposit_content = deposit_content;
        }
    }
}
