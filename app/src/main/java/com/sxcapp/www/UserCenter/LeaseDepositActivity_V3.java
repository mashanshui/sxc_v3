package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.LeaseDepositInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.ShareDepositWithdrawInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.WalletChangeEvent;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 共享押金界面
 * Created by wenleitao on 2018/4/4.
 */

public class LeaseDepositActivity_V3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.lease_deposit_tv)
    TextView lease_deposit_tv;
    @BindView(R.id.lease_deposit_withdraw_btn)
    Button lease_deposit_withdraw_btn;
    @BindView(R.id.remind_lin)
    LinearLayout remind_lin;
    private String user_id;
    private List<String> remind_list = new ArrayList<>();
    private UserCenterService service;
    private String share_deposit;
    private final static int REQUEST_CODE = 11;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_deposit_v3);
        setTopbarLeftWhiteBackBtn();
        ButterKnife.bind(this);
        setTopBarTitle("随心车押金", null);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        loadData();
        bindOnClick();
    }

    private void loadData() {
        Observable<CodeResultV3<LeaseDepositInfoBeanV3>> ob = service.getLeaseDepositInfoV3(user_id);
        ob.compose(compose(this.<CodeResultV3<LeaseDepositInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LeaseDepositInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(LeaseDepositInfoBeanV3 leaseDepositInfoBeanV3) {
                lease_deposit_tv.setText(leaseDepositInfoBeanV3.getCustomer_sxc_deposit());
                EventBus.getDefault().post(new WalletChangeEvent("lease_deposit", leaseDepositInfoBeanV3.getCustomer_sxc_deposit()));
                if (leaseDepositInfoBeanV3.getCustomer_sxc_deposit_remark().size() > 0) {
                    remind_list = leaseDepositInfoBeanV3.getCustomer_sxc_deposit_remark();
                    for (int i = 0; i < remind_list.size(); i++) {
                        String str = (i + 1) + "、" + remind_list.get(i);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 40, 0, 0);
                        TextView textView = new TextView(LeaseDepositActivity_V3.this);
                        textView.setTextColor(getResources().getColor(R.color.black_tv_26));
                        textView.setTextSize(11);
                        textView.setText(str);
                        textView.setLayoutParams(layoutParams);
                        remind_lin.addView(textView);
                    }
                }
            }
        });
    }

    private void bindOnClick() {
        lease_deposit_withdraw_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lease_deposit_withdraw_btn:
                checkCanWithdraw();
                break;
            default:
                break;
        }
    }


    private void checkCanWithdraw() {
        showProgressDlg();
        Observable<CodeResultV3<ShareDepositWithdrawInfoBeanV3>> ob = service.getLeaseDepositWithdrawInfoV3(user_id);
        ob.compose(compose(this.<CodeResultV3<ShareDepositWithdrawInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ShareDepositWithdrawInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ShareDepositWithdrawInfoBeanV3 shareDepositWithdrawInfoBeanV3) {
                removeProgressDlg();
                share_deposit = shareDepositWithdrawInfoBeanV3.getDeposit_cost();
                Intent intent_share = new Intent(LeaseDepositActivity_V3.this, WithDrawActivity.class);
                intent_share.putExtra("type", "lease");
                intent_share.putExtra("money", Double.parseDouble(share_deposit));
                startActivityForResult(intent_share, REQUEST_CODE);
            }

            @Override
            protected void onHandleError(String msg,CodeResultV3<ShareDepositWithdrawInfoBeanV3> value) {
                super.onHandleError(msg,value);
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
