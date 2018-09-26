package com.sxcapp.www.UserCenter.Bean;

/**
 * 充值或者提现后押金或者余额发生变化 EventBus发送消息更新MyWalletActivityV3  UI
 * Created by wenleitao on 2018/4/10.
 */

public class WalletChangeEvent {
    private String type;
    private String count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public WalletChangeEvent(String type, String count) {
        this.type = type;
        this.count = count;
    }
}
