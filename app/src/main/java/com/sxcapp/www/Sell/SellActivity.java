package com.sxcapp.www.Sell;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxcapp.www.Bean.BannerBean;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.CustomerView.InfinitePagerAdapter;
import com.sxcapp.www.CustomerView.InfiniteViewPager;
import com.sxcapp.www.CustomerView.XListView.Xcircleindicator;
import com.sxcapp.www.R;
import com.sxcapp.www.Sell.Bean.ConsultPhoneBean;
import com.sxcapp.www.Sell.HttpService.SellService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 卖车界面
 * Created by wenleitao on 2017/7/21.
 */

public class SellActivity extends BaseActivity implements View.OnClickListener {
    private SellService service;
    private String phone;
    private InfinitePagerAdapter infiAdapter;
    private Xcircleindicator mXcircleindicator;
    @BindView(R.id.know_more_tv)
    TextView know_more_tv;
    @BindView(R.id.know_more_btn)
    Button know_more_btn;
    @BindView(R.id.sell_btn)
    Button sell_btn;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.banner_vp)
    InfiniteViewPager banner_vp;
    @BindView(R.id.indicator_lin)
    LinearLayout indicator_lin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("我要卖车", null);
        setTopbarRightbtn(R.mipmap.phone_top, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callService();
            }
        });
        initViews();
        loadData();
    }

    private void initViews() {
        know_more_tv.setOnClickListener(this);
        sell_btn.setOnClickListener(this);
    }


    private void loadData() {
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(SellService.class);
        Observable<Result<ConsultPhoneBean>> reOb = service.getConsultPhone();
        reOb.compose(compose(this.<Result<ConsultPhoneBean>>bindToLifecycle())).subscribe(new BaseObserver<ConsultPhoneBean>(this) {
            @Override
            protected void onHandleSuccess(ConsultPhoneBean phoneBean) {
                phone = phoneBean.getOfficial_hotline();
                know_more_btn.setText("免费咨询:" + phone);
                know_more_btn.setOnClickListener(SellActivity.this);
            }
        });
        Observable<Result<List<BannerBean>>> ob = service.getAppBanner(4);
        ob.compose(compose(this.<Result<List<BannerBean>>>bindToLifecycle())).subscribe(new BaseObserver<List<BannerBean>>(this) {
            @Override
            protected void onHandleSuccess(final List<BannerBean> bannerBeenList) {
                if (bannerBeenList.size() > 0) {
                    SellBannerPageAdapter adapter = new SellBannerPageAdapter(SellActivity.this, bannerBeenList);
                    infiAdapter = new InfinitePagerAdapter(adapter);
                    if (bannerBeenList.size() > 1) {
                        banner_vp.setAdapter(infiAdapter);
                    } else {
                        banner_vp.setAdapter(adapter);
                    }
                    if (bannerBeenList.size() > 1) {
                        mXcircleindicator = new Xcircleindicator(SellActivity.this);
                        mXcircleindicator.setPageTotalCount(bannerBeenList.size());
                        mXcircleindicator.setFillColor(R.color.lightGray3);
                        mXcircleindicator.setStrokeColor(R.color.orRed);
                        mXcircleindicator.setCircleInterval(12);
                        mXcircleindicator.setRadius(12);
                        indicator_lin.addView(mXcircleindicator);
                        mXcircleindicator.setCurrentPage(0);
                        banner_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                mXcircleindicator.setCurrentPage(Math.abs(position - bannerBeenList.size() * 100000) % bannerBeenList.size());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    } else {
                        indicator_lin.setVisibility(View.GONE);
                    }

                }
            }
        });

    }

    private void callService() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.know_more_btn:
                callService();
                break;
            case R.id.know_more_tv:
                Intent in = new Intent(SellActivity.this, SellFlowActivity.class);
                startActivity(in);
                break;
            case R.id.sell_btn:
                String str = phone_edit.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    showToast("请先输入号码");
                } else {
                    if (AndroidTool.isMobileNO(str)) {
                        sellSubmit(str);

                    } else {
                        showToast("请输入正确的手机号码");
                    }
                }
                break;
            default:
                break;
        }

    }

    private void sellSubmit(String phone) {
        Observable<Result<Object>> ob = service.sellSubmit(phone);
        ob.compose(compose(this.<Result<Object>>bindToLifecycle())).subscribe(new BaseObserver<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SellActivity.this);
                builder.setIcon(R.mipmap.icon);
                builder.setMessage("客服人员尽快联系您，请保持手机通畅，耐心等待。");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showToast("预约卖车成功");
                    }
                });
                builder.create();
                builder.show();
            }
        });

    }
}
