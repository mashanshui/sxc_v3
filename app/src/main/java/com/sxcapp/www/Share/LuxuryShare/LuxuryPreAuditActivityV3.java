package com.sxcapp.www.Share.LuxuryShare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.LuxuryAppointCarImagePageAdapterV3;
import com.sxcapp.www.Share.Bean.LuxuryPreAuditInfoBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 *
 * @author wenleitao
 * @date 2018/4/28
 */

public class LuxuryPreAuditActivityV3 extends BaseActivity {

    @BindView(R.id.car_vp)
    ViewPager car_vp;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.license_num_tv)
    TextView license_num_tv;
    @BindView(R.id.fetch_store_name_tv)
    TextView fetch_store_name_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.audit_btn)
    Button audit_btn;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mXcircleindicator;
    @BindView(R.id.limit_time_tv)
    TextView limit_time_tv;
    @BindView(R.id.contract_up_iv)
    ImageView contract_up_iv;
    @BindView(R.id.bondsman_up_iv)
    ImageView bondsman_up_iv;
    @BindView(R.id.idcard_discern_up_iv)
    ImageView idcard_discern_up_iv;
    @BindView(R.id.contract_up_lin)
    LinearLayout contract_up_lin;
    @BindView(R.id.bondsman_up_lin)
    LinearLayout bondsman_up_lin;
    @BindView(R.id.idcard_discern_up_lin)
    LinearLayout idcard_discern_up_lin;
    private String order_no, user_id, constract_num;
    private ShareService service;
    private LuxuryAppointCarImagePageAdapterV3 adapterV3;
    private int time_limit;
    private long netNowDate, order_date;
    private boolean COUNT_DOWN_GOING = true;
    private static final int CHANGE_COUNT_DOWN = 11, CONSTRACT_REQUEST = 101, BONDSMAN_REQUEST = 102, IDCARD_REQUEST = 103;
    private Thread thread_time = new Thread(new Runnable() {
        @Override
        public void run() {
            netNowDate = TimeFormat.getNetNowDate();
            mHandler.sendEmptyMessage(CHANGE_COUNT_DOWN);
        }
    });
    private int constract_flag, bondsman_flag, idcard_flag;

    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case CHANGE_COUNT_DOWN:
                if (COUNT_DOWN_GOING) {
                    if (getCountDown(netNowDate, order_date) == null) {
                        limit_time_tv.setText("已超出预约时间");
                    } else {
                        limit_time_tv.setText(getCountDown(netNowDate, order_date) + "秒后自动计费");
                        netNowDate = netNowDate + 1000;
                        mHandler.sendEmptyMessageDelayed(CHANGE_COUNT_DOWN, 1000);
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_predudit_v4);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        setStatusView(R.color.luxury);
        setTopBarColor(R.color.luxury);
        setTopbarLeftWhiteToMainBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("豪车去审核", null);
        order_no = getIntent().getStringExtra("order_no");
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        ButterKnife.bind(this);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<LuxuryPreAuditInfoBeanV3>> ob = service.getLuxuryPreAuditInfoV3(order_no);
        ob.compose(compose(this.<CodeResultV3<LuxuryPreAuditInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LuxuryPreAuditInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final LuxuryPreAuditInfoBeanV3 beanV3) {
                removeProgressDlg();
                adapterV3 = new LuxuryAppointCarImagePageAdapterV3(LuxuryPreAuditActivityV3.this, beanV3.getCar_image());
                car_vp.setAdapter(adapterV3);
                mXcircleindicator.setCount(beanV3.getCar_image().size());
                mXcircleindicator.setSelection(0);
                mXcircleindicator.setRadius(5);
                car_name_tv.setText(beanV3.getCar_name());
                license_num_tv.setText(beanV3.getLicense_plate_number());
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                time_limit = Integer.parseInt(beanV3.getAppoint_time());
                order_date = TimeFormat.getTimeStamp(beanV3.getOrder_time());
                constract_num = beanV3.getContract_orderno();
                thread_time.start();
                if (beanV3.getContract_flag() == 0) {
                    contract_up_iv.setBackgroundResource(R.mipmap.contract_up_icon_v3);
                    constract_flag = 0;
                    contract_up_lin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_cons = new Intent(LuxuryPreAuditActivityV3.this, UploadConstractActivityV3.class);
                            intent_cons.putExtra("order_no", order_no);
                            intent_cons.putExtra("constract_num", constract_num);
                            startActivityForResult(intent_cons, CONSTRACT_REQUEST);
                        }
                    });
                } else if (beanV3.getContract_flag() == 1) {
                    constract_flag = 1;
                    contract_up_iv.setBackgroundResource(R.mipmap.constract_pass_icon_v3);

                }
                if (beanV3.getGuarantor_flag() == 0) {
                    bondsman_flag = 0;
                    bondsman_up_iv.setBackgroundResource(R.mipmap.bondsman_up_icon_v3);
                    bondsman_up_lin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_bond = new Intent(LuxuryPreAuditActivityV3.this, UploadBondsManActivityV3.class);
                            intent_bond.putExtra("order_no", order_no);
                            startActivityForResult(intent_bond, BONDSMAN_REQUEST);
                        }
                    });

                } else if (beanV3.getGuarantor_flag() == 1) {
                    bondsman_flag = 1;
                    bondsman_up_iv.setBackgroundResource(R.mipmap.bondsman_pass_icon_v3);
                }
                if (beanV3.getIdcard_flag() == 0) {
                    idcard_flag = 0;
                    idcard_discern_up_iv.setBackgroundResource(R.mipmap.idcard_discern_up_icon_v3);
                    idcard_discern_up_lin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_id = new Intent(LuxuryPreAuditActivityV3.this, UploadIDCardActivityV3.class);
                            intent_id.putExtra("order_no", order_no);
                            startActivityForResult(intent_id, IDCARD_REQUEST);
                        }
                    });
                } else if (beanV3.getIdcard_flag() == 1) {
                    idcard_flag = 1;
                    idcard_discern_up_iv.setBackgroundResource(R.mipmap.idcard_discern_pass_icon_v3);
                }
                car_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        mXcircleindicator.setSelection(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                audit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPreAuditInfo();
                    }
                });
            }
        });
    }

    private void getPreAuditInfo() {
        if (constract_flag == 0) {

            showLuxuryAlertDlg("合同未上传,去上传？", R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAlertDlg();
                    Intent intent_cons = new Intent(LuxuryPreAuditActivityV3.this, UploadConstractActivityV3.class);
                    intent_cons.putExtra("order_no", order_no);
                    intent_cons.putExtra("constract_num", constract_num);
                    startActivityForResult(intent_cons, CONSTRACT_REQUEST);
                }
            }, R.string.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAlertDlg();
                }
            }, true);
            return;
        }
        if (bondsman_flag == 0) {
            showLuxuryAlertDlg("担保人信息未上传,去上传？", R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAlertDlg();
                    Intent intent03 = new Intent(LuxuryPreAuditActivityV3.this, UploadBondsManActivityV3.class);
                    intent03.putExtra("order_no", order_no);
                    startActivityForResult(intent03, BONDSMAN_REQUEST);
                }
            }, R.string.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAlertDlg();
                }
            }, true);
            return;
        }
        if (idcard_flag == 0) {
            showLuxuryAlertDlg("身份证识别图未上传,去上传？", R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAlertDlg();
                    Intent intent01 = new Intent(LuxuryPreAuditActivityV3.this, UploadIDCardActivityV3.class);
                    intent01.putExtra("order_no", order_no);
                    startActivityForResult(intent01, IDCARD_REQUEST);
                }
            }, R.string.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAlertDlg();
                }
            }, true);
            return;
        }
        if (constract_flag == 1 && bondsman_flag == 1 && idcard_flag == 1) {
            showProgressDlg();
            Observable<CodeResultV3<Object>> ob = service.getPriAuditInfoV3(user_id, order_no);
            ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
                @Override
                protected void onHandleSuccess(Object o) {
                    removeProgressDlg();
                    Intent intent02 = new Intent(LuxuryPreAuditActivityV3.this, LuxuryConfirmDayTypeActivityV3.class);
                    intent02.putExtra("order_no", order_no);
                    startActivity(intent02);

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CONSTRACT_REQUEST) {
                contract_up_iv.setBackgroundResource(R.mipmap.constract_pass_icon_v3);
                constract_flag = 1;
                contract_up_lin.setClickable(false);
            } else if (requestCode == BONDSMAN_REQUEST) {
                bondsman_up_iv.setBackgroundResource(R.mipmap.bondsman_pass_icon_v3);
                bondsman_flag = 1;
                bondsman_up_lin.setClickable(false);
            } else if (requestCode == IDCARD_REQUEST) {
                idcard_discern_up_iv.setBackgroundResource(R.mipmap.idcard_discern_pass_icon_v3);
                idcard_flag = 1;
                idcard_discern_up_lin.setClickable(false);
            }
        }
    }

    /**
     * @param nowDate
     * @param order_time
     * @return 倒计时
     */
    public String getCountDown(long nowDate, long order_time) {
        long diff = (nowDate - order_time) / 1000;
        long residue = ((order_time + time_limit * 60 * 1000) - nowDate) / 1000;
        if (((diff / (time_limit * 60) > 1) || (diff / (time_limit * 60) == 1))) {
            COUNT_DOWN_GOING = false;
            return null;
        } else if (residue / 60 < 10) {
            if ((residue % 60) < 10) {
                return "0" + residue / 60 + ":" + "0" + residue % 60;
            } else {
                return "0" + residue / 60 + ":" + residue % 60;
            }
        } else {
            if ((residue % 60) < 10) {
                return residue / 60 + ":" + "0" + residue % 60;
            } else {
                return residue / 60 + ":" + residue % 60;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            MyApplication.getInstance().gotoMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
