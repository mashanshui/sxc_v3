package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/8/10.
 */

public class InviteInfo {

    /**
     * invite_url : http://www.sxzcar.com/sxc_admin/app/userApi/invite_friends?user_id=
     * invite_title : 邀请好友
     * rules : ["邀请用户消费在平台内消费赠送1000积分"]
     * invite_desc : 每成功邀请一个好友完成注册并在线支付，即赠送500积分，可以直接抵现哦！
     */

    private String invite_url;
    private String invite_title;
    private String invite_desc;
    private List<String> rules;

    public String getInvite_url() {
        return invite_url;
    }

    public void setInvite_url(String invite_url) {
        this.invite_url = invite_url;
    }

    public String getInvite_title() {
        return invite_title;
    }

    public void setInvite_title(String invite_title) {
        this.invite_title = invite_title;
    }

    public String getInvite_desc() {
        return invite_desc;
    }

    public void setInvite_desc(String invite_desc) {
        this.invite_desc = invite_desc;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }
}
