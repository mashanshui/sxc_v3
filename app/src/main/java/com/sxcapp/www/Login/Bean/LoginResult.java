package com.sxcapp.www.Login.Bean;

/**
 * Created by wenleitao on 2017/7/21.
 */

public class LoginResult<T>  {
    private String code;
    private String msg;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
