package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/1/20.
 */

public class OilSelectTypeBean {

    /**
     * ruleSettings : {"settings":["1、不在本平台指定网点停车有可能产生其他费用","2、非本网点，在有车位的免费停车场，加收25元拖车费","3、非本网点，在付费停车场加25元拖车费及实际产生的停车场费用","4、恶意在非车位任意停车，罚款100元，产生的违章罚款和扣分客户自负","5、离开赤峰市区（三个区）罚款1000元（根据后期网点分布情况，再往下面旗县扩展)"]}
     * ruleTime : {"rent_type":"1","rule":["奇瑞EQ合肥--分时租基本费用：0.50元/每分钟，0.50元/公里，此规则仅适用于奇瑞EQ合肥，不含优惠活动价格"],"title":"100公里以内，建议分时租"}
     * ruleMore : {"rent_type":"2","rule":[],"title":"100公里以外，建议长租"}
     */

    private RuleSettingsBean ruleSettings;
    private RuleTimeBean ruleTime;
    private RuleMoreBean ruleMore;

    public RuleSettingsBean getRuleSettings() {
        return ruleSettings;
    }

    public void setRuleSettings(RuleSettingsBean ruleSettings) {
        this.ruleSettings = ruleSettings;
    }

    public RuleTimeBean getRuleTime() {
        return ruleTime;
    }

    public void setRuleTime(RuleTimeBean ruleTime) {
        this.ruleTime = ruleTime;
    }

    public RuleMoreBean getRuleMore() {
        return ruleMore;
    }

    public void setRuleMore(RuleMoreBean ruleMore) {
        this.ruleMore = ruleMore;
    }

    public static class RuleSettingsBean {
        private List<String> settings;

        public List<String> getSettings() {
            return settings;
        }

        public void setSettings(List<String> settings) {
            this.settings = settings;
        }
    }

    public static class RuleTimeBean {
        /**
         * rent_type : 1
         * rule : ["奇瑞EQ合肥--分时租基本费用：0.50元/每分钟，0.50元/公里，此规则仅适用于奇瑞EQ合肥，不含优惠活动价格"]
         * title : 100公里以内，建议分时租
         */

        private String rent_type;
        private String title;
        private List<String> rule;

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

    public static class RuleMoreBean {
        /**
         * rent_type : 2
         * rule : []
         * title : 100公里以外，建议长租
         */

        private String rent_type;
        private String title;
        private List<String> rule;

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
}
