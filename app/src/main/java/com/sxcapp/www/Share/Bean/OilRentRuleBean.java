package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/1/12.
 */

public class OilRentRuleBean {


    /**
     * rent_type : 1
     * rule : ["奇瑞EQ合肥--分时租基本费用：0.50元/每分钟，0.50元/公里，此规则仅适用于奇瑞EQ合肥，不含优惠活动价格"]
     * title : 100公里以内，建议分时租
     */

    private String rent_type;
    private String title;
    private List<String> rule;

    public OilRentRuleBean(String rent_type, String title, List<String> rule) {
        this.rent_type = rent_type;
        this.title = title;
        this.rule = rule;
    }

    public String getRent_type() {
        return rent_type;
    }

    public void setRent_type(String rent_type) {
        this.rent_type = rent_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getRule() {
        return rule;
    }

    public void setRule(List<String> rule) {
        this.rule = rule;
    }
}
