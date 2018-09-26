package com.sxcapp.www.Lease.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/24.
 */

public class LeaseCostBean {

    private List<Double> premium;
    private List<Double> noCount;
    private List<Double> lease;
    private List<Double> handlingCharge;

    public List<Double> getPremium() {
        return premium;
    }

    public void setPremium(List<Double> premium) {
        this.premium = premium;
    }

    public List<Double> getNoCount() {
        return noCount;
    }

    public void setNoCount(List<Double> noCount) {
        this.noCount = noCount;
    }

    public List<Double> getLease() {
        return lease;
    }

    public void setLease(List<Double> lease) {
        this.lease = lease;
    }

    public List<Double> getHandlingCharge() {
        return handlingCharge;
    }

    public void setHandlingCharge(List<Double> handlingCharge) {
        this.handlingCharge = handlingCharge;
    }
}
