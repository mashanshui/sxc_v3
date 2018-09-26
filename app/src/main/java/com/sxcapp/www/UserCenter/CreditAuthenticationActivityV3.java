package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Lease.LeaseSuccessActivity;
import com.sxcapp.www.Lease.PayDepositActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.CreditAuthenStateBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.MessageEvent;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/28.
 */

public class CreditAuthenticationActivityV3 extends BaseActivity {
    @BindView(R.id.credit_card_re)
    RelativeLayout credit_card_re;
    @BindView(R.id.credit_report_re)
    RelativeLayout credit_report_re;
    @BindView(R.id.card_card_state_tv)
    TextView credit_card_state_tv;
    @BindView(R.id.credit_report_state_tv)
    TextView credit_report_state_tv;
    private UserCenterService service;
    private String user_id;
    //    credit_card_flag(是否绑定信用卡：0.未绑定 1.已绑定)
//    credit_report_flag(是否上传征信报告：-1.未上传 0.待审核 1.审核通过 2.审核不通过)
    private static final int REPORT_QUEST = 11;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_authentication_v3);
        setStatusView(R.color.top_bar_red);
        setTopBarColor(R.color.top_bar_red);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("诚信审核", null);
        ButterKnife.bind(this);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        EventBus.getDefault().register(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        loadData();
    }

    private void loadData() {
        Observable<CodeResultV3<CreditAuthenStateBeanV3>> ob = service.getCreditAuthenStateV3(user_id);
        ob.compose(compose(this.<CodeResultV3<CreditAuthenStateBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<CreditAuthenStateBeanV3>(this) {
            @Override
            protected void onHandleSuccess(CreditAuthenStateBeanV3 beanV3) {
                if (beanV3.getCredit_card_flag() == 0) {
                    credit_card_state_tv.setText("未绑定");
                    credit_card_re.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(CreditAuthenticationActivityV3.this, UploadCreditCardActivityV3.class));
                        }
                    });
                } else {
                    credit_card_state_tv.setText("已绑定");
                }
                if (beanV3.getCredit_report_flag() == -1) {
                    credit_report_state_tv.setText("未认证");
                } else if (beanV3.getCredit_report_flag() == 0) {
                    credit_report_state_tv.setText("待审核");
                } else if (beanV3.getCredit_report_flag() == 1) {
                    credit_report_state_tv.setText("审核通过");
                } else if (beanV3.getCredit_report_flag() == 2) {
                    credit_report_state_tv.setText("审核不通过");
                }
                credit_report_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(CreditAuthenticationActivityV3.this, UploadCreditReportActivityV3.class), REPORT_QUEST);

                    }
                });
                if (beanV3.getCredit_report_flag() == -1 || beanV3.getCredit_report_flag() == 2) {
                    credit_report_re.setClickable(true);
                } else {
                    credit_report_re.setClickable(false);
                }

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("success".equals(messageEvent.getMessage())) {
            credit_card_state_tv.setText("已绑定");
            credit_card_re.setClickable(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
