package com.sxcapp.www.UserCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 提现界面
 * Created by wenleitao on 2017/8/1.
 */

public class WithDrawActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.user_name_edit)
    EditText user_name_edit;
    @BindView(R.id.card_edit)
    EditText card_edit;
    @BindView(R.id.address_edit)
    EditText address_edit;
    @BindView(R.id.count_edit)
    EditText count_edit;
    @BindView(R.id.withdraw_btn)
    Button withdraw_btn;
    @BindView(R.id.remind_tv)
    TextView remind_tv;
    private String phone, name, card, address, count;
    private UserCenterService service;
    private String type;
    private double money, balance_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_withdraw);
        setStatusView(R.color.top_bar_red);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if ("balance".equals(type)) {
            setTopBarTitle("余额提现", null);
            balance_count = getIntent().getDoubleExtra("balance_count", 0);
        } else if ("lease".equals(type)) {
            setTopBarTitle("租车押金提现", null);
            money = getIntent().getDoubleExtra("money", 0);
        } else if ("share".equals(type)) {
            setTopBarTitle("共享汽车押金提现", null);
            money = getIntent().getDoubleExtra("money", 0);
        }
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        initViews();
    }

    private void initViews() {
        if ("balance".equals(type)) {
            remind_tv.setVisibility(View.VISIBLE);
        } else {
            remind_tv.setVisibility(View.GONE);
            count_edit.setClickable(false);
            count_edit.setFocusable(false);
            DecimalFormat df = new DecimalFormat("#0.00");
            count_edit.setText(df.format(money));
        }
        withdraw_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withdraw_btn:
                if (phone_edit.getText().toString().trim().isEmpty()) {
                    showToast("请输入手机号");
                } else {
                    if (!AndroidTool.isMobileNO(phone_edit.getText().toString().trim())) {
                        showToast("请输入正确的手机号");
                    } else {
                        phone = phone_edit.getText().toString().trim();
                        if (user_name_edit.getText().toString().trim().isEmpty()) {
                            showToast("请输入用户名");
                        } else {
                            name = user_name_edit.getText().toString().trim();
                            if (card_edit.getText().toString().trim().isEmpty()) {
                                showToast("请输入银行卡号");
                            } else {
                                if (!AndroidTool.checkBankCard(card_edit.getText().toString().trim())) {
                                    showToast("请输入正确的银行卡号");
                                } else {
                                    card = card_edit.getText().toString().trim();
                                    if (address_edit.getText().toString().trim().isEmpty()) {
                                        showToast("请输入开户行地址");
                                    } else {
                                        address = address_edit.getText().toString().trim();
                                        if ("balance".equals(type)) {
                                            if (count_edit.getText().toString().trim().isEmpty()) {
                                                showToast("请输入提现金额");
                                            } else {

                                                if (count_edit.getText().toString().trim().startsWith("0")) {
                                                    showToast("请输入正确的提现金额");
                                                } else {
                                                    if (((int) (balance_count)) < Integer.parseInt(count_edit.getText().toString().trim())) {
                                                        showToast("提现金额超限");
                                                    } else {
                                                        count = count_edit.getText().toString().trim();
                                                        withDraw();
                                                    }
                                                }

                                            }

                                        } else {
                                            count = money + "";
                                            withDraw();
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void withDraw() {
        showProgressDlg();
        withdraw_btn.setClickable(false);
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", SharedData.getInstance().getString(SharedData._user_id));
        map.put("customer_name", name);
        map.put("customer_bank_no", card);
        map.put("customer_bank_address", address);
        map.put("customer_phone", phone);
        if ("balance".equals(type)) {
            map.put("amount", count);
        }
        Observable<CodeResultV3<Object>> observable = null;
        if ("balance".equals(type)) {
            observable = service.balanceWithdrawV3(map);
        } else if ("lease".equals(type)) {
            observable = service.leaseWithDrawV3(map);
        } else if ("share".equals(type)) {
            observable = service.shareWithDrawV3(map);
        }
         if(observable !=null) {
             observable.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
                 @Override
                 protected void onHandleSuccess(Object o) {
                     removeProgressDlg();
                     withdraw_btn.setClickable(true);
                     showToast("资料提交成功,客服会在1~3个工作日内处理");
                     setResult(Activity.RESULT_OK);
                     finish();
                 }

                 @Override
                 protected void onHandleError(String msg, CodeResultV3<Object> value) {
                     super.onHandleError(msg, value);
                     withdraw_btn.setClickable(true);
                 }

                 @Override
                 public void onError(Throwable e) {
                     super.onError(e);
                     withdraw_btn.setClickable(true);
                 }
             });
         }
    }
}
