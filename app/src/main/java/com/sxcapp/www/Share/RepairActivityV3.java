package com.sxcapp.www.Share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.AliPay.PayResult;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Lease.Bean.PrepayIdBean;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.RepairRemarkInfoBeanV3;
import com.sxcapp.www.Share.ElectricShortShare.ElecShortOffOrderDetailActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.UserCenter.Bean.AliSignV3;
import com.sxcapp.www.UserCenter.Bean.OrderNoBean;
import com.sxcapp.www.UserCenter.Bean.WXPrepayInfoBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.UserCenter.ShareDepositActivity_V3;
import com.sxcapp.www.UserCenter.ShareDepositRechargeActivity_V3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MessageEvent;
import com.sxcapp.www.Util.PayUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;
import com.sxcapp.www.wxapi.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 补款充值界面
 * Created by wenleitao on 2018/5/18.
 */

public class RepairActivityV3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.repair_price_tv)
    TextView repair_price_tv;
    @BindView(R.id.ali_re)
    RelativeLayout ali_re;
    @BindView(R.id.wx_re)
    RelativeLayout wx_re;
    @BindView(R.id.ali_check_iv)
    ImageView ali_check_iv;
    @BindView(R.id.wx_check_iv)
    ImageView wx_check_iv;
    @BindView(R.id.repair_btn)
    Button repair_btn;
    @BindView(R.id.msg_lin)
    LinearLayout msg_lin;
    private ShareService service;
    private String user_id, order_no, add_cost, repair_order_no;
    private int add_type;
    private boolean ali_check = true, wx_check = false;
    private UserCenterService userCenterService;
    private int payType;
    private PayUtil payUtil;
    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_v3);
        setStatusView(R.color.top_bar_red);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("补款充值", null);
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        userCenterService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        add_type = getIntent().getIntExtra("add_type", 0);
        order_no = getIntent().getStringExtra("order_no");
        add_cost = getIntent().getStringExtra("add_cost");
        payUtil = new PayUtil();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        EventBus.getDefault().register(this);
        loadData();
    }

    private void loadData() {
        Observable<CodeResultV3<RepairRemarkInfoBeanV3>> ob = service.getRepairRemarkV3(user_id, order_no, add_type, add_cost);
        ob.compose(compose(this.<CodeResultV3<RepairRemarkInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<RepairRemarkInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(RepairRemarkInfoBeanV3 beanV3) {
                repair_price_tv.setText(add_cost);
                for (int i = 0; i < beanV3.getRecharge_remark().size(); i++) {
                    String str = "*" + beanV3.getRecharge_remark().get(i);
                    TextView textView = new TextView(RepairActivityV3.this);
                    textView.setTextColor(getResources().getColor(R.color.top_bar_red));
                    textView.setTextSize(16);
                    textView.setText(str);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setPadding(0, AndroidTool.dip2px(RepairActivityV3.this, 12), 0, 0);
                    msg_lin.addView(textView);

                }
                repair_btn.setOnClickListener(RepairActivityV3.this);
                wx_re.setOnClickListener(RepairActivityV3.this);
                ali_re.setOnClickListener(RepairActivityV3.this);
            }
        });
    }

    private void recharge() {
        if (ali_check) {
            payType = 0;
        } else {
            payType = 1;
        }
        repair_btn.setClickable(false);
        showProgressDlg();
        Observable<CodeResultV3<OrderNoBean>> reOb = service.getRepairOrderNoV3(user_id, order_no, add_type, add_cost, payType);
        reOb.compose(compose(RepairActivityV3.this.<CodeResultV3<OrderNoBean>>bindToLifecycle())).subscribe(new CodeObserverV3<OrderNoBean>(RepairActivityV3.this) {
            @Override
            protected void onHandleSuccess(OrderNoBean orderNoBean) {
                repair_order_no = orderNoBean.getOrder_no();
                if (ali_check) {
                    doALiPay();
                } else if (wx_check) {
                    removeProgressDlg();
                    repair_btn.setClickable(true);
                    wxPay();
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<OrderNoBean> value) {
                super.onHandleError(msg, value);
                repair_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                repair_btn.setClickable(true);
            }
        });
    }

    private void doALiPay() {
        Observable<CodeResultV3<AliSignV3>> ob = userCenterService.getALiSignV3(repair_order_no, add_cost, "补款订单", "addrecharge");
        ob.compose(compose(this.<CodeResultV3<AliSignV3>>bindToLifecycle())).subscribe(new CodeObserverV3<AliSignV3>(this) {
            @Override
            protected void onHandleSuccess(AliSignV3 aliSign) {
                removeProgressDlg();
                repair_btn.setClickable(true);
                payUtil.doWebPay(RepairActivityV3.this, mHandler, aliSign.getPay_info());
            }


            @Override
            protected void onHandleError(String msg, CodeResultV3<AliSignV3> value) {
                super.onHandleError(msg, value);
                repair_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                repair_btn.setClickable(true);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        repair_btn.setClickable(true);
        if ("success".equals(messageEvent.getMessage())) {
            showToast("充值成功");
            finish();
        } else {
            showToast("充值失败");
        }
    }

    private void wxPay() {
        Observable<CodeResultV3<WXPrepayInfoBeanV3>> ob = userCenterService.getWXPrepayIdV3(repair_order_no, add_cost, "补款订单", "addrecharge");
        ob.compose(compose(this.<CodeResultV3<WXPrepayInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<WXPrepayInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(WXPrepayInfoBeanV3 wxPrepayInfoBeanV3) {
                PrepayIdBean prepayIdBean = wxPrepayInfoBeanV3.getPrepay_info();
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId = prepayIdBean.getAppid();
                req.partnerId = prepayIdBean.getPartnerid();
                req.prepayId = prepayIdBean.getPrepayid();
                req.nonceStr = prepayIdBean.getNoncestr();
                req.timeStamp = prepayIdBean.getTimestamp();
                req.packageValue = "Sign=WXPay";
                req.sign = prepayIdBean.getSign();
//                        req.extData			= "app data"; // optional
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            }
        });
    }

    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                repair_btn.setClickable(true);
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */

                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                Logger.e("支付宝返回信息", resultStatus);
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    showToast("支付成功");

                    finish();
                } else {

                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    showToast("支付失败");
                }
                break;
            }

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ali_re:
                if (!ali_check) {
                    ali_check_iv.setBackgroundResource(R.mipmap.check);
                    wx_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    ali_check = true;
                    wx_check = false;
                }
                break;
            case R.id.wx_re:
                if (!wx_check) {
                    ali_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    wx_check_iv.setBackgroundResource(R.mipmap.check);
                    ali_check = false;
                    wx_check = true;
                }
                break;
            case R.id.repair_btn:
                recharge();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
