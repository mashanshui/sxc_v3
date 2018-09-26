package com.sxcapp.www.UserCenter.Bean;

/**
 * Created by wenleitao on 2017/8/2.
 */

public class ShareDepositInfo {

    /**
     * share_rental : 10.00
     * customer_rental : 0
     * car_rental : 10.00
     */

    private double share_rental;
    private double customer_rental;
    private double car_rental;

    public double getShare_rental() {
        return share_rental;
    }

    public void setShare_rental(double share_rental) {
        this.share_rental = share_rental;
    }

    public double getCustomer_rental() {
        return customer_rental;
    }

    public void setCustomer_rental(double customer_rental) {
        this.customer_rental = customer_rental;
    }

    public double getCar_rental() {
        return car_rental;
    }

    public void setCar_rental(double car_rental) {
        this.car_rental = car_rental;
    }
}
