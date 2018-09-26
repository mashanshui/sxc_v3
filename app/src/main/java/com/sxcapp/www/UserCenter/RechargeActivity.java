package com.sxcapp.www.UserCenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.AliPay.PayResult;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Lease.Bean.PrepayIdBean;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.AliSignV3;
import com.sxcapp.www.UserCenter.Bean.OrderNoBean;
import com.sxcapp.www.UserCenter.Bean.WXPrepayInfoBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by wenleitao on 2017/7/28.
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    private int money = 200;
    private Button th_btn, fh_btn, ot_btn, tt_btn, ft_btn, et_btn, recharge_btn;
    private LinearLayout other_money_lin, msg_lin;
    private EditText other_money_edit;
    private RelativeLayout ali_re, wx_re;
    private ImageView ali_check_iv, wx_check_iv;
    private boolean ali_check = true, wx_check = false;
    private static final int SDK_PAY_FLAG = 1;
    private UserCenterService service;
    private IWXAPI api;
    private String order_no;
    private int payType = 0;
    private PayUtil payUtil = new PayUtil();
    private List<String> msg_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("账户充值", null);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        EventBus.getDefault().register(this);
        initViews();
    }

    private void initViews() {
        th_btn = (Button) findViewById(R.id.th_btn);
        fh_btn = (Button) findViewById(R.id.fh_btn);
        ot_btn = (Button) findViewById(R.id.ot_btn);
        tt_btn = (Button) findViewById(R.id.tt_btn);
        ft_btn = (Button) findViewById(R.id.ft_btn);
        et_btn = (Button) findViewById(R.id.et_btn);
        msg_lin = (LinearLayout) findViewById(R.id.msg_lin);
        msg_list = getIntent().getStringArrayListExtra("msg");
        for (int i = 0; i < msg_list.size(); i++) {
            String str = "*" + msg_list.get(i);
            TextView textView = new TextView(this);
            textView.setTextColor(getResources().getColor(R.color.top_bar_red));
            textView.setTextSize(16);
            textView.setText(str);
            msg_lin.addView(textView);

        }
        recharge_btn = (Button) findViewById(R.id.recharge_btn);
        ali_re = (RelativeLayout) findViewById(R.id.ali_re);
        wx_re = (RelativeLayout) findViewById(R.id.wx_re);
        ali_check_iv = (ImageView) findViewById(R.id.ali_check_iv);
        wx_check_iv = (ImageView) findViewById(R.id.wx_check_iv);
        ali_re.setOnClickListener(this);
        wx_re.setOnClickListener(this);
        other_money_lin = (LinearLayout) findViewById(R.id.other_money_lin);
        other_money_edit = (EditText) findViewById(R.id.other_money_edit);
        other_money_lin.setOnClickListener(this);
        th_btn.setOnClickListener(this);
        fh_btn.setOnClickListener(this);
        ot_btn.setOnClickListener(this);
        tt_btn.setOnClickListener(this);
        ft_btn.setOnClickListener(this);
        et_btn.setOnClickListener(this);
        th_btn.setSelected(true);
        other_money_edit.setOnClickListener(this);
        recharge_btn.setOnClickListener(this);
    }


    private void recharge() {
        if (ali_check) {
            payType = 0;
        } else {
            payType = 1;
        }
        recharge_btn.setClickable(false);
        showProgressDlg();
        Observable<CodeResultV3<OrderNoBean>> reOb = service.getBalanceRechargeOrderNoV3(SharedData.getInstance().getString(SharedData._user_id), money + "", payType);
        reOb.compose(compose(RechargeActivity.this.<CodeResultV3<OrderNoBean>>bindToLifecycle())).subscribe(new CodeObserverV3<OrderNoBean>(RechargeActivity.this) {
            @Override
            protected void onHandleSuccess(OrderNoBean orderNoBean) {

                order_no = orderNoBean.getOrder_no();
                if (ali_check) {
                    doALiPay();
                } else if (wx_check) {
                    removeProgressDlg();
                    recharge_btn.setClickable(true);
                    wxPay();
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<OrderNoBean> value) {
                super.onHandleError(msg, value);
                recharge_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                recharge_btn.setClickable(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.th_btn:
                money = 200;
                break;
            case R.id.fh_btn:
                money = 400;
                break;
            case R.id.ot_btn:
                money = 1000;
                break;
            case R.id.tt_btn:
                money = 2000;
                break;
            case R.id.ft_btn:
                money = 4000;
                break;
            case R.id.et_btn:
                money = 8000;
                break;
            case R.id.other_money_lin:
                InputMethodManager m = (InputMethodManager) other_money_lin.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                money = 0;
                break;
            case R.id.other_money_edit:
                money = 0;
                break;
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
            case R.id.recharge_btn:
                if (money == 0) {
                    if (TextUtils.isEmpty(other_money_edit.getText().toString().trim())) {
                        showToast("请输入正确的充值金额");
                    } else {
                        if (Integer.parseInt(other_money_edit.getText().toString()) > 0) {
                            if (other_money_edit.getText().toString().startsWith("0")) {
                                showToast("请输入正确的充值金额");
                            } else {
                                money = Integer.parseInt(other_money_edit.getText().toString());
                                recharge();
                            }
                        } else {
                            showToast("请先选择充值金额");
                        }
                    }

                } else {
                    recharge();
                }
                break;
            default:
                break;

        }
        th_btn.setSelected(money == 200);
        fh_btn.setSelected(money == 400);
        ot_btn.setSelected(money == 1000);
        tt_btn.setSelected(money == 2000);
        ft_btn.setSelected(money == 4000);
        et_btn.setSelected(money == 8000);
    }

    private void wxPay() {
        Observable<CodeResultV3<WXPrepayInfoBeanV3>> ob = service.getWXPrepayIdV3(order_no, money + "", "账户充值", "recharge");
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


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    recharge_btn.setClickable(true);
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast("支付成功");
                        Intent intent = new Intent(RechargeActivity.this, BalanceActivity_V3.class);
                        setResult(Activity.RESULT_OK, intent);
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
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        recharge_btn.setClickable(true);
        if ("success".equals(messageEvent.getMessage())) {
            showToast("充值成功");
            Intent intent = new Intent(RechargeActivity.this, BalanceActivity_V3.class);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            showToast("充值失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void doALiPay() {
        Observable<CodeResultV3<AliSignV3>> ob = service.getALiSignV3(order_no, money + "", "账户充值", "recharge");
        ob.compose(compose(this.<CodeResultV3<AliSignV3>>bindToLifecycle())).subscribe(new CodeObserverV3<AliSignV3>(this) {
            @Override
            protected void onHandleSuccess(AliSignV3 aliSign) {
                removeProgressDlg();
                recharge_btn.setClickable(true);
                payUtil.doWebPay(RechargeActivity.this, mHandler, aliSign.getPay_info());
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<AliSignV3> value) {
                super.onHandleError(msg, value);
                recharge_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                recharge_btn.setClickable(true);
            }
        });

    }

}
