package com.sxcapp.www.Lease.Bean;

/**
 * Created by wenleitao on 2017/7/21.
 */

public class PrepayResult<T>  {
    private String resultCode;
    private String msg;

    private T data;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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
