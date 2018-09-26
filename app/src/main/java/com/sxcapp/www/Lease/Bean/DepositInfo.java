package com.sxcapp.www.Lease.Bean;

/**
 * Created by wenleitao on 2017/7/26.
 */

public class DepositInfo {

    /**
     * illegal_rental : 0
     * total_cost : 8000.00
     * car_rental : 8000.00
     */

    private String illegal_rental;
    private double total_cost;
    private String car_rental;

    public String getIllegal_rental() {
        return illegal_rental;
    }

    public void setIllegal_rental(String illegal_rental) {
        this.illegal_rental = illegal_rental;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public String getCar_rental() {
        return car_rental;
    }

    public void setCar_rental(String car_rental) {
        this.car_rental = car_rental;
    }
}
