package com.sxcapp.www.Buy.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/18.
 */

public class OldCarListResult {
//    private  "totalNum" : 94,
//            "totalPageNum" : 10,
//            "oldcarlist"

    private int totalNum;
    private int totalPageNum;
    private List<OldVechileInformation> oldcarlist;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<OldVechileInformation> getOldcarlist() {
        return oldcarlist;
    }

    public void setOldcarlist(List<OldVechileInformation> oldcarlist) {
        this.oldcarlist = oldcarlist;
    }
}
