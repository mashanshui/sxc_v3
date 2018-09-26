package com.sxcapp.www.Lease.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/13.
 */

public class LeaseResult {
    private int totalPage;
    private List<LeaseCar>leaseList;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<LeaseCar> getLeaseList() {
        return leaseList;
    }

    public void setLeaseList(List<LeaseCar> leaseList) {
        this.leaseList = leaseList;
    }


}
