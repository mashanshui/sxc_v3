package com.sxcapp.www.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sxcapp.www.Util.MessageEvent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        int code = resp.errCode;

        switch (code) {
            case 0://支付成功后的界面
                EventBus.getDefault().post(new MessageEvent("success"));
                break;
            case -1:
                Toast.makeText(getApplicationContext(), "签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、您的微信账号异常等。", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MessageEvent("fail"));
                break;
            case -2://用户取消支付后的界面
                break;
            default:
                break;
        }
        finish();
        //微信支付后续操作，失败，成功，取消
    }

}