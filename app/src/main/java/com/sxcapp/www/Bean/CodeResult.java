package com.sxcapp.www.Bean;

/**
 * Created by wenleitao on 2017/7/13.
 * code类型的泛型类
 */

public class CodeResult<T> {
    private String result;
    private String msg;
    private String time;
    private T dataT;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public T getDataT() {
        return dataT;
    }

    public void setDataT(T dataT) {
        this.dataT = dataT;
    }

    @Override
    public String toString() {
        return "CodeResult{" +
                "result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", dataT=" + dataT +
                '}';
    }
}
