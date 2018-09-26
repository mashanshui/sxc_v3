package com.sxcapp.www.UserCenter;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.LotteryResultBean;
import com.sxcapp.www.UserCenter.Bean.LotteryRuleBean;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 免费用车抽奖活动
 * Created by wenleitao on 2018/2/3.
 */

public class LotteryActivity extends BaseActivity {
    @BindView(R.id.dial_iv)
    ImageView dial_iv;
    @BindView(R.id.begin_btn)
    Button begin_btn;
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    private int ran_degree = 0;
    private int type;
    private UserCenterService service;
    private boolean isFirst = true;
    private int roed_degree;
    private int temp_degree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        ButterKnife.bind(this);
        setTopBarTitle("新年活动", null);
        setTopbarLeftBackBtn();
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        getCouponRemark();
        begin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedData.getInstance().isLogin()) {
                    begin_btn.setClickable(false);
                    getResult();
                } else {
                    startActivityForResult(new Intent(LotteryActivity.this, LoginActivity.class), 11);
                }
            }
        });

    }

    /**
     * 获取优惠券使用规则列表
     */
    private void getCouponRemark() {
        showProgressDlg();
        Observable<Result<LotteryRuleBean>> ob = service.getLotteryRule();
        ob.compose(compose(this.<Result<LotteryRuleBean>>bindToLifecycle())).subscribe(new BaseObserver<LotteryRuleBean>(this) {
            @Override
            protected void onHandleSuccess(LotteryRuleBean lotteryRuleBean) {
                removeProgressDlg();
                TextView tv_tittle = new TextView(LotteryActivity.this);
                LinearLayout.LayoutParams layoutParams_tittle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams_tittle.gravity = Gravity.CENTER_HORIZONTAL;
                tv_tittle.setText("活动规则");
                tv_tittle.setLayoutParams(layoutParams_tittle);
                tv_tittle.setPadding(0, 5, 0, 5);
                tv_tittle.setTextColor(getResources().getColor(R.color.orRed));
                tv_tittle.setTextSize(20);
                rules_lin.addView(tv_tittle, 0, layoutParams_tittle);
                for (int i = 0; i < lotteryRuleBean.getRemark().size(); i++) {
                    TextView tv = new TextView(LotteryActivity.this);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    String str = (i + 1) + "." + lotteryRuleBean.getRemark().get(i);
                    tv.setText(str);
                    tv.setLayoutParams(layoutParams);
                    tv.setPadding(0, 5, 0, 5);
                    tv.setTextColor(getResources().getColor(R.color.orRed));
                    tv.setTextSize(15);
                    rules_lin.addView(tv, i + 1);
                }
//                为了留白
                TextView tv = new TextView(LotteryActivity.this);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setText("");
                tv.setLayoutParams(layoutParams);
                tv.setPadding(0, 5, 0, 5);
                tv.setTextColor(getResources().getColor(R.color.orRed));
                tv.setTextSize(15);
                rules_lin.addView(tv, lotteryRuleBean.getRemark().size() + 1);

            }
        });
    }

    /**
     * 获取抽奖结果
     */
    private void getResult() {
        showProgressDlg();
        Observable<Result<LotteryResultBean>> ob = service.getLotteryResult(SharedData.getInstance().getString(SharedData._user_id));
        ob.compose(compose(this.<Result<LotteryResultBean>>bindToLifecycle())).subscribe(new BaseObserver<LotteryResultBean>(this) {
            @Override
            protected void onHandleSuccess(LotteryResultBean lotteryResultBean) {
                removeProgressDlg();
                type = lotteryResultBean.getCoupon_id();
                begin_btn.setClickable(true);
                roed_degree = temp_degree;
                setDegree();
//                防止后台设置不止一天抽奖一次
                ran_degree = temp_degree;
                if (isFirst) {
                    ran_degree = 1800 + ran_degree;
                } else {
                    ran_degree = 1800 + (360 - roed_degree) + ran_degree;
                }

                isFirst = false;
//                开始转盘动画
                dial_iv.animate().rotationBy(ran_degree).setDuration(5000).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setType();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                begin_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                begin_btn.setClickable(true);
            }
        });
    }

    private void setDegree() {
        if (type == 1) {
            temp_degree = 0;
        } else if (type == 2) {
            temp_degree = 36;
        } else if (type == 3) {
            temp_degree = 72;
        } else if (type == 4) {
            temp_degree = 108;
        } else {
            temp_degree = 144;
        }
    }

    private void setType() {
        if (type == 1) {
            showToast("恭喜您抽中免费用7天券");
        } else if (type == 2) {
            showToast("恭喜您抽中免费用3天券");
        } else if (type == 3) {
            showToast("恭喜您抽中免费用1天券");
        } else if (type == 4) {
            showToast("恭喜您抽中50元用1天券");
        } else {
            showToast("谢谢参与");
        }
    }
}
