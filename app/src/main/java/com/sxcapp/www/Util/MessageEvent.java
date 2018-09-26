package com.sxcapp.www.Util;

/**
 * Created by wenleitao on 2017/8/5.
 */

public class MessageEvent {
    private String message;
    public MessageEvent(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}