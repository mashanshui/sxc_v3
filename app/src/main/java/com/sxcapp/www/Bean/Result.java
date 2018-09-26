package com.sxcapp.www.Bean;

/**
 * Created by wenleitao on 2016/8/31.
 */
public class Result<T> {
    /**
     * flag : true 成功
     * msg : 提示错误信息
     * data : null
     */

    private boolean flag;
    private String msg;
    private T obj;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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
        return "Result{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }
}
