package com.sxcapp.www.Util;

/**
 * Created by wenleitao on 2017/8/19.
 */

public class UserInfoMessageEvent {
    private String avatar;
    private String nick_name;

    public UserInfoMessageEvent(String avatar, String nick_name) {
        this.avatar = avatar;
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
