package com.sxcapp.www.Base;

/**
 * Created by wenleitao on 2018/1/24.
 */

public class CodeResultV3<T> {
    private String code;
    private String msg;
    private T obj;

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

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "CodeResultV3{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", Obj=" + obj +
                '}';
    }
}
