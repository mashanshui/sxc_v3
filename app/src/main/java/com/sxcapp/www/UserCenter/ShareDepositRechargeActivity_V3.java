package com.sxcapp.www.UserCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.AliPay.PayResult;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Lease.Bean.PrepayIdBean;
import com.sxcapp.www.Lease.Bean.PrepayObserver;
import com.sxcapp.www.Lease.Bean.PrepayResult;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.AliSignV3;
import com.sxcapp.www.UserCenter.Bean.OrderNoBean;
import com.sxcapp.www.UserCenter.Bean.ShareDepositRechargeListBean;
import com.sxcapp.www.UserCenter.Bean.WXPrepayInfoBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MessageEvent;
import com.sxcapp.www.Util.PayUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;
import com.sxcapp.www.wxapi.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/8.
 */

public class ShareDepositRechargeActivity_V3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.count_list_lin)
    LinearLayout count_list_lin;
    @BindView(R.id.other_money_lin)
    LinearLayout other_money_lin;
    @BindView(R.id.other_money_edit)
    EditText other_money_edit;
    @BindView(R.id.ali_re)
    RelativeLayout ali_re;
    @BindView(R.id.wx_re)
    RelativeLayout wx_re;
    @BindView(R.id.ali_check_iv)
    ImageView ali_check_iv;
    @BindView(R.id.wx_check_iv)
    ImageView wx_check_iv;
    @BindView(R.id.recharge_btn)
    Button recharge_btn;
    private UserCenterService service;
    private boolean ali_check = true, wx_check = false;
    private String user_id;
    private ShareDepositRechargeListBean listBean;
    private int btn_height, btn_width;
    private int screen_width, screen_height;
    private List<List<ShareDepositRechargeListBean.DepositRemarkBean>> groupList;
    private ColorStateList colorStateList;
    private List<Button> buttonList = new ArrayList<>();
    private List<String> costList = new ArrayList<>();
    private int checkIndex = 0;
    private String checkRechargePrice = "0";
    private String order_no;
    private String money;
    private PayUtil payUtil = new PayUtil();
    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;
    private int payType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedeposit_recharge_v3);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("共享押金充值", null);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        EventBus.getDefault().register(this);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        screen_width = dm.widthPixels;
        screen_height = dm.heightPixels;
        btn_height = AndroidTool.dip2px(this, 40);
        int btn_interval = AndroidTool.dip2px(this, 13);
        btn_width = (screen_width - btn_interval - AndroidTool.dip2px(this, 24)) / 2;
        colorStateList = getResources().getColorStateList(R.color.selector_btn_click_text_color);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        Observable<CodeResultV3<ShareDepositRechargeListBean>> ob = service.getShareDepositRechargeCountListV3(user_id);
        ob.compose(compose(this.<CodeResultV3<ShareDepositRechargeListBean>>bindToLifecycle())).subscribe(new CodeObserverV3<ShareDepositRechargeListBean>(this) {
            @Override
            protected void onHandleSuccess(ShareDepositRechargeListBean shareDepositRechargeListBean) {
                removeProgressDlg();
                listBean = shareDepositRechargeListBean;
                initButtons();
            }
        });

    }

    private void initButtons() {
        groupList = groupListByQuantity(listBean.getDeposit_remark(), 2);
        for (int i = 0; i < groupList.size(); i++) {
            RelativeLayout re = new RelativeLayout(this);
            re.setPadding(0, 30, 0, 0);
            for (int index = 0; index < groupList.get(i).size(); index++) {
                Button button = new Button(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(btn_width, btn_height);
                if (index == 0) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                } else {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }
                button.setText(groupList.get(i).get(index).getDeposit_content());
                button.setGravity(Gravity.CENTER);
                button.setTextSize(16);
                button.setBackgroundResource(R.drawable.selector_btn_click_bg);
                button.setTextColor(colorStateList);
                button.setLayoutParams(layoutParams);
                if (i == 0) {
                    button.setTag(index);
                } else {
                    if (index == 0) {
                        button.setTag(i * 2);
                    } else {
                        button.setTag(i * 2 + 1);
                    }
                }
                buttonList.add(button);
                costList.add(groupList.get(i).get(index).getDeposit_cost());
                re.addView(button);
            }
            count_list_lin.addView(re);

        }
        for (int i = 0; i < buttonList.size(); i++) {

            buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkIndex = (int) v.getTag();
                    setButtonCheck(checkIndex);
                }
            });
        }
        setButtonCheck(0);
        other_money_lin.setOnClickListener(this);
        other_money_edit.setOnClickListener(this);
        recharge_btn.setOnClickListener(this);
        ali_re.setOnClickListener(this);
        wx_re.setOnClickListener(this);

    }

    private void setButtonCheck(int position) {
        if (position != -1) {
            checkRechargePrice = costList.get(position);
        }
        for (int i = 0; i < buttonList.size(); i++) {
            if (position == i) {
                buttonList.get(i).setSelected(true);
            } else {
                buttonList.get(i).setSelected(false);
            }
        }
    }


    public static List groupListByQuantity(List list, int quantity) {
        if (list == null || list.size() == 0) {
            return list;
        }
        if (quantity <= 0) {
            new IllegalArgumentException("Wrong quantity.");
        }

        List wrapList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity));
            ;
            count += quantity;
        }

        return wrapList;
    }

    private void wxPay() {
        Observable<CodeResultV3<WXPrepayInfoBeanV3>> ob = service.getWXPrepayIdV3(order_no, checkRechargePrice, "账户充值", "sharedeposit");
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
                recharge_btn.setClickable(true);
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
                    Intent intent = new Intent(ShareDepositRechargeActivity_V3.this, ShareDepositActivity_V3.class);
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

    private void recharge() {
        if (ali_check) {
            payType = 0;
        } else {
            payType = 1;
        }
        recharge_btn.setClickable(false);
        showProgressDlg();
        Observable<CodeResultV3<OrderNoBean>> reOb = service.getShareRechargeOrderNoV3(SharedData.getInstance().getString(SharedData._user_id), checkRechargePrice, payType);
        reOb.compose(compose(ShareDepositRechargeActivity_V3.this.<CodeResultV3<OrderNoBean>>bindToLifecycle())).subscribe(new CodeObserverV3<OrderNoBean>(ShareDepositRechargeActivity_V3.this) {
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

    private void doALiPay() {
        Observable<CodeResultV3<AliSignV3>> ob = service.getALiSignV3(order_no, checkRechargePrice + "", "账户充值", "sharedeposit");
        ob.compose(compose(this.<CodeResultV3<AliSignV3>>bindToLifecycle())).subscribe(new CodeObserverV3<AliSignV3>(this) {
            @Override
            protected void onHandleSuccess(AliSignV3 aliSign) {
                removeProgressDlg();
                recharge_btn.setClickable(true);
                payUtil.doWebPay(ShareDepositRechargeActivity_V3.this, mHandler, aliSign.getPay_info());
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

//        payUtil.doWebPay(ShareDepositRechargeActivity_V3.this, mHandler, "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018031302362252&biz_content=%7B%22body%22%3A%22%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A%2266665633333%22%2C%22passback_params%22%3A%22sharedeposit%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.94.200.54%3A8080%2Fsxc_web%2Fpay%2Fhandle%2FalipayNotifyUrl&sign=p5fP10z8u%2BqWpf28ioOML2JUhn91hQ98mPY%2FjPvDSpPBJBki5Uv7dGR3r95RKXjSbKO1DPq0rCK2XNfXY1fcTHBhAL8PqhEWSXDL7g2D%2BlGYAsUol%2BROHfwbmLQ3iuy%2BbKvq4uK8msE%2FfoHBJf1L%2BPgoN4ucaWs6hGM7x7YIZIXlfvrHnrXGeTsL1UoOSLSkJDWM9nFRh%2By7YZy4Zr3rVgumfeEURixH6Q1An2x3n2G1cNdO0oY9jDUnlSTQI6Bq%2BBddBwH78PCCt%2FzvI4wiTb4VYIouNqMJrM74RDun7F9EKKNIQMdD7FSPLL8g6e8%2FdAFboo8lzFTbWbQXqtC1Aw%3D%3D&sign_type=RSA2&timestamp=2018-04-13+11%3A14%3A56&version=1.0");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        recharge_btn.setClickable(true);
        if ("success".equals(messageEvent.getMessage())) {
            showToast("充值成功");
            Intent intent = new Intent(ShareDepositRechargeActivity_V3.this, ShareDepositActivity_V3.class);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            showToast("充值失败");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.other_money_lin:
                checkIndex = -1;
                setButtonCheck(-1);
                InputMethodManager m = (InputMethodManager) other_money_lin.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                checkRechargePrice = "0";
                break;
            case R.id.other_money_edit:
                checkIndex = -1;
                setButtonCheck(-1);
                checkRechargePrice = "0";
                break;
            case R.id.recharge_btn:
                if ("0".equals(checkRechargePrice)) {
                    if (TextUtils.isEmpty(other_money_edit.getText().toString().trim())) {
                        showToast("请先选择充值金额");
                    } else {
                        if (Integer.parseInt(other_money_edit.getText().toString()) > 0) {
                            checkRechargePrice = other_money_edit.getText().toString();
                            recharge();
                        } else {
                            showToast("请先选择充值金额");
                        }
                    }
//                    recharge();

                } else {
                    recharge();
                }
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

