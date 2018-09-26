package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/8/2.
 */

public class WalletInfo {

    /**
     * balance : 9710.00
     * sxcDeposit : 0.00
     * shareDeposit : 0.00
     */

    private double balance;
    private double sxcDeposit;
    private double shareDeposit;
    private int integral;
    private boolean balance_type;
    private String balance_message;
    private List<String>pointOut;
    /**
     * sxcDeposit : 8000.00
     * integral : 0
     * shareDeposit : 0.01
     * grade : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"3dd308c3fc8711e6b7741c872c63f5fc","grade_type":"1","grade_name":"银卡会员","cumulative_consumption_amount":1000,"disposable_recharge":800,"sort":2,"create_user_id":null,"create_time":null,"modify_user_id":"607dd46e481711e6b208408d5c04d4aa","modify_time":1500724025000,"discount":100}
     */

    private GradeBean grade;

    public boolean isBalance_type() {
        return balance_type;
    }

    public void setBalance_type(boolean balance_type) {
        this.balance_type = balance_type;
    }

    public String getBalance_message() {
        return balance_message;
    }

    public List<String> getPointOut() {
        return pointOut;
    }

    public void setPointOut(List<String> pointOut) {
        this.pointOut = pointOut;
    }

    public void setBalance_message(String balance_message) {
        this.balance_message = balance_message;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSxcDeposit() {
        return sxcDeposit;
    }

    public void setSxcDeposit(double sxcDeposit) {
        this.sxcDeposit = sxcDeposit;
    }

    public double getShareDeposit() {
        return shareDeposit;
    }

    public void setShareDeposit(double shareDeposit) {
        this.shareDeposit = shareDeposit;
    }

    public GradeBean getGrade() {
        return grade;
    }

    public void setGrade(GradeBean grade) {
        this.grade = grade;
    }

    public static class GradeBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : 3dd308c3fc8711e6b7741c872c63f5fc
         * grade_type : 1
         * grade_name : 银卡会员
         * cumulative_consumption_amount : 1000.0
         * disposable_recharge : 800.0
         * sort : 2
         * create_user_id : null
         * create_time : null
         * modify_user_id : 607dd46e481711e6b208408d5c04d4aa
         * modify_time : 1500724025000
         * discount : 100.0
         */

        private MapBean map;
        private String cnd_type;
        private String ob_type;
        private String  id;
        private int grade_type;
        private String grade_name;
        private double cumulative_consumption_amount;
        private double disposable_recharge;
        private int sort;
        private Object create_user_id;
        private Object create_time;
        private String modify_user_id;
        private long modify_time;
        private double discount;
        private List<?> relationModelList;
        private List<?> orderByModelList;

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
        }

        public String getCnd_type() {
            return cnd_type;
        }

        public void setCnd_type(String cnd_type) {
            this.cnd_type = cnd_type;
        }

        public String getOb_type() {
            return ob_type;
        }

        public void setOb_type(String ob_type) {
            this.ob_type = ob_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getGrade_type() {
            return grade_type;
        }

        public void setGrade_type(int grade_type) {
            this.grade_type = grade_type;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }

        public double getCumulative_consumption_amount() {
            return cumulative_consumption_amount;
        }

        public void setCumulative_consumption_amount(double cumulative_consumption_amount) {
            this.cumulative_consumption_amount = cumulative_consumption_amount;
        }

        public double getDisposable_recharge() {
            return disposable_recharge;
        }

        public void setDisposable_recharge(double disposable_recharge) {
            this.disposable_recharge = disposable_recharge;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public Object getCreate_user_id() {
            return create_user_id;
        }

        public void setCreate_user_id(Object create_user_id) {
            this.create_user_id = create_user_id;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getModify_user_id() {
            return modify_user_id;
        }

        public void setModify_user_id(String modify_user_id) {
            this.modify_user_id = modify_user_id;
        }

        public long getModify_time() {
            return modify_time;
        }

        public void setModify_time(long modify_time) {
            this.modify_time = modify_time;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public List<?> getRelationModelList() {
            return relationModelList;
        }

        public void setRelationModelList(List<?> relationModelList) {
            this.relationModelList = relationModelList;
        }

        public List<?> getOrderByModelList() {
            return orderByModelList;
        }

        public void setOrderByModelList(List<?> orderByModelList) {
            this.orderByModelList = orderByModelList;
        }

        public static class MapBean {
        }
    }
}
