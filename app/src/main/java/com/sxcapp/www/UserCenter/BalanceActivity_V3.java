package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.BalanceInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.WalletChangeEvent;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 余额界面
 * Created by wenleitao on 2018/4/4.
 */

public class BalanceActivity_V3 extends BaseActivity {
    @BindView(R.id.balance_tv)
    TextView balance_tv;
    @BindView(R.id.recharge_btn)
    Button recharge_btn;
    @BindView(R.id.withdraw_btn)
    Button withdraw_btn;
    @BindView(R.id.remind_lin)
    LinearLayout remind_lin;
    private UserCenterService service;
    private String user_id;
    private static final int REQUEST_CODE = 11;
    private List<String> remind_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("余额", null);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        remind_list = new ArrayList<>();
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        loadData();

    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<BalanceInfoBeanV3>> ob = service.getBalanceInfoV3(user_id);
        ob.compose(compose(this.<CodeResultV3<BalanceInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<BalanceInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final BalanceInfoBeanV3 balanceInfoBeanV3) {
                removeProgressDlg();
                balance_tv.setText(balanceInfoBeanV3.getCustomer_balance());
                EventBus.getDefault().post(new WalletChangeEvent("balance", balanceInfoBeanV3.getCustomer_balance()));
                if (balanceInfoBeanV3.getCustomer_balance_remark().size() > 0) {
                    remind_list = balanceInfoBeanV3.getCustomer_balance_remark();
                    for (int i = 0; i < balanceInfoBeanV3.getCustomer_balance_remark().size(); i++) {
                        String str = "*" + balanceInfoBeanV3.getCustomer_balance_remark().get(i);
                        SpannableStringBuilder builder = new SpannableStringBuilder(str);
                        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.top_bar_red));
                        builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        TextView textView = new TextView(BalanceActivity_V3.this);
                        textView.setTextColor(getResources().getColor(R.color.black_tv_26));
                        textView.setTextSize(11);
                        textView.setText(builder);
                        remind_lin.addView(textView);
                    }

                }
                withdraw_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Double.parseDouble(balanceInfoBeanV3.getCustomer_balance()) < 1) {
                            showToast("当前余额不足");
                        } else {
                            Intent intent_wi = new Intent(BalanceActivity_V3.this, WithDrawActivity.class);
                            intent_wi.putExtra("type", "balance");
                            intent_wi.putExtra("balance_count", Double.parseDouble(balanceInfoBeanV3.getCustomer_balance()));
                            startActivityForResult(intent_wi, REQUEST_CODE);
                        }
                    }
                });
            }
        });
        recharge_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalanceActivity_V3.this, RechargeActivity.class);
                intent.putStringArrayListExtra("msg", (ArrayList<String>) remind_list);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                loadData();
            }
        }
    }
}
