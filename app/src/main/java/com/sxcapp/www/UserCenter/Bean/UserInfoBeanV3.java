package com.sxcapp.www.UserCenter.Bean;

/**
 * Created by wenleitao on 2018/5/1.
 */

public class UserInfoBeanV3 {

    /**
     * is_credit : 0
     * head_image : null
     * help_tips : 计费规则详解
     * is_authen : 1
     * balance : 5848.80
     * activity_tips : 海量福利优惠券
     * integral : 1011
     * nick_name : 陶文磊
     * rentals : 25
     * member_level : VIP会员
     */
//    balance(账户余额), integral（积分）， rentals（租车次数），head_image(头像)
//    nick_name(昵称), member_level(会员级别), is_authen(实名认证：0.未认证 1.已认证)，activity_tips(活动提示信息)，help_tips(帮助提示信息)，is_credit(信用认证：0.未认证 1.已认证)


    private int is_credit;
    private String head_image;
    private String help_tips;
    private int is_authen;
    private String balance;
    private String activity_tips;
    private String integral;
    private String nick_name;
    private String rentals;
    private String member_level;

    public int getIs_credit() {
        return is_credit;
    }

    public void setIs_credit(int is_credit) {
        this.is_credit = is_credit;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getHelp_tips() {
        return help_tips;
    }

    public void setHelp_tips(String help_tips) {
        this.help_tips = help_tips;
    }

    public int getIs_authen() {
        return is_authen;
    }

    public void setIs_authen(int is_authen) {
        this.is_authen = is_authen;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getActivity_tips() {
        return activity_tips;
    }

    public void setActivity_tips(String activity_tips) {
        this.activity_tips = activity_tips;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getRentals() {
        return rentals;
    }

    public void setRentals(String rentals) {
        this.rentals = rentals;
    }

    public String getMember_level() {
        return member_level;
    }

    public void setMember_level(String member_level) {
        this.member_level = member_level;
    }
}
