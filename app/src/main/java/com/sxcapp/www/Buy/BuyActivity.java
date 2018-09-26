package com.sxcapp.www.Buy;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Bean.BannerBean;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Buy.HttpService.BuyService;
import com.sxcapp.www.CustomerView.InfinitePagerAdapter;
import com.sxcapp.www.CustomerView.InfiniteViewPager;
import com.sxcapp.www.CustomerView.XListView.Xcircleindicator;
import com.sxcapp.www.Lease.BannerPageAdapter;
import com.sxcapp.www.Lease.Bean.Recommend;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.MessageCenterActivity;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.activity.MainActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 买车界面
 * Created by wenleitao on 2017/7/11.
 */

public class BuyActivity extends BaseActivity implements View.OnClickListener {
    private BuyService service;
    private Recommend recommend01, recommend02;
    private String recom_id01, recom_id02;
    private Xcircleindicator mXcircleindicator;
    @BindView(R.id.all_btn)
    Button all_btn;
    @BindView(R.id.banner_vp)
    InfiniteViewPager banner_vp;
    @BindView(R.id.recommend_iv01)
    ImageView recommend_iv01;
    @BindView(R.id.recommend_iv02)
    ImageView recommend_iv02;
    @BindView(R.id.name_tv)
    TextView re_name_tv01;
    @BindView(R.id.name_tv02)
    TextView re_name_tv02;
    @BindView(R.id.time_tv)
    TextView re_time_tv01;
    @BindView(R.id.time_tv02)
    TextView re_time_tv02;
    @BindView(R.id.distance_tv)
    TextView dis_tv01;
    @BindView(R.id.distance_tv02)
    TextView dis_tv02;
    @BindView(R.id.price_tv)
    TextView price_tv01;
    @BindView(R.id.price_tv02)
    TextView price_tv02;
    @BindView(R.id.old_price_tv)
    TextView oldPrice_tv01;
    @BindView(R.id.old_price_tv02)
    TextView oldPrice_tv02;
    @BindView(R.id.recommend_re01)
    RelativeLayout recommend_re01;
    @BindView(R.id.recommend_re02)
    RelativeLayout recommend_re02;
    @BindView(R.id.indicator_lin)
    LinearLayout indicator_lin;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.user_iv)
    ImageView user_iv;
    @BindView(R.id.message_iv)
    ImageView message_iv;
    @BindView(R.id.search_re)
    RelativeLayout search_re;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(BuyService.class);
        initViews();
        loadData();
    }

    private void loadData() {
//        获取banner
        Observable<Result<List<BannerBean>>> ob = service.getAppBanner(3);
        ob.compose(compose(this.<Result<List<BannerBean>>>bindToLifecycle())).subscribe(new BaseObserver<List<BannerBean>>(this) {
            @Override
            protected void onHandleSuccess(final List<BannerBean> bannerBeenList) {
                if (bannerBeenList.size() > 0) {
                    BannerPageAdapter adapter = new BannerPageAdapter(BuyActivity.this, bannerBeenList);
                    InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(adapter);
                    if (bannerBeenList.size() > 1) {
                        banner_vp.setAdapter(infinitePagerAdapter);
                    } else {
                        banner_vp.setAdapter(adapter);
                    }
                    if (bannerBeenList.size() > 1) {
//                    翻页白点
                        mXcircleindicator = new Xcircleindicator(BuyActivity.this);
                        mXcircleindicator.setPageTotalCount(bannerBeenList.size());
                        mXcircleindicator.setFillColor(R.color.lightGray3);
                        mXcircleindicator.setStrokeColor(R.color.lightGray2);
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

        Observable<Result<List<Recommend>>> resultOb = service.getLeaseRecommend(1);
        resultOb.compose(compose(this.<Result<List<Recommend>>>bindToLifecycle())).subscribe(new BaseObserver<List<Recommend>>(this) {
            @Override
            protected void onHandleSuccess(List<Recommend> list) {
                List<Recommend> newList = new ArrayList<>();
                for (Recommend recommend : list) {
                    if (recommend.getVehicle_source_type() == 1) {
                        newList.add(recommend);
                    }
                }
                if (newList.size() > 0) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    recommend01 = newList.get(0);
                    recommend02 = newList.get(1);
                    Glide.with(BuyActivity.this).load(Properties.baseImageUrl + recommend01.getOldImage()).into(recommend_iv01);
                    Glide.with(BuyActivity.this).load(Properties.baseImageUrl + recommend02.getOldImage()).into(recommend_iv02);
                    re_name_tv01.setText(recommend01.getBrand() + recommend01.getSeries() + recommend01.getModel());
                    re_name_tv02.setText(recommend02.getBrand() + recommend02.getSeries() + recommend02.getModel());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
                    String date01 = format.format(recommend01.getLicensingTime());
                    String date02 = format.format(recommend02.getLicensingTime());
                    re_time_tv01.setText(date01 + "上牌");
                    re_time_tv02.setText(date02 + "上牌");
                    dis_tv01.setText("|行驶" + recommend01.getMileage() + "公里");
                    dis_tv02.setText("|行驶" + recommend02.getMileage() + "公里");
                    price_tv01.setText(df.format(recommend01.getOwnerQuote()) + "万");
                    price_tv02.setText(df.format(recommend02.getOwnerQuote()) + "万");
                    oldPrice_tv01.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    oldPrice_tv02.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    oldPrice_tv01.setText("新车价:" + df.format(recommend01.getNewVehiclePrice()) + "万");
                    oldPrice_tv02.setText("新车价:" + df.format(recommend02.getNewVehiclePrice()) + "万");
                    recom_id01 = recommend01.getLicense_plate_number();
                    recom_id02 = recommend02.getLicense_plate_number();
                    recommend_re01.setOnClickListener(BuyActivity.this);
                    recommend_re02.setOnClickListener(BuyActivity.this);
                }
            }
        });
    }

    private void initViews() {
        search_re.setOnClickListener(this);
        all_btn.setOnClickListener(this);
        message_iv.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        user_iv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_btn:
                Intent intent = new Intent(this, OldCarListActivity.class);
                startActivity(intent);
                break;
            case R.id.recommend_re01:
                Intent intent_re01 = new Intent(BuyActivity.this, OldCarDetailActivity.class);
                intent_re01.putExtra("car_id", recom_id01);
                startActivity(intent_re01);
                break;
            case R.id.recommend_re02:
                Intent intent_re02 = new Intent(BuyActivity.this, OldCarDetailActivity.class);
                intent_re02.putExtra("car_id", recom_id02);
                startActivity(intent_re02);
                break;
            case R.id.back_iv:
                setBack();
                break;
            case R.id.user_iv:
                isToLogin();
                break;
            case R.id.message_iv:
                if (SharedData.getInstance().isLogin()) {
                    Intent intent_message = new Intent(this, MessageCenterActivity.class);
                    startActivity(intent_message);
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST);
                }
                break;
            case R.id.search_re:
                Intent intent1 = new Intent(this, SearchListActivity.class);
                startActivity(intent1);
                break;
            case R.id.dazong_re:
                startWithvehicle_brand_id("1015d031c1174d75840691bd8e9dc3f0");
                break;
            case R.id.jac_re:
                startWithvehicle_brand_id("519391cce6474ba38146c8de3d8b4a89");
                break;
            case R.id.ford_re:
                startWithvehicle_brand_id("f13a12283bbc4f0fab1eefabe3c23739");
                break;
            case R.id.xuefulan_re:
                startWithvehicle_brand_id("6595b9db823a4438a04dbeaf6b86ee8f");
                break;
            case R.id.bike_re:
                startWithvehicle_brand_id("5d0d7a733624425fb43f09ea83ac1226");
                break;
            case R.id.kia_re:
                startWithvehicle_brand_id("c341bf58340f4866af5dea722a228b1f");
                break;
            case R.id.less_three:
                startWithPriceId("44dc08bb405e11e78c5300163e000e21");
                break;
            case R.id.three_to_five:
                startWithPriceId("563e039b405e11e78c5300163e000e21");
                break;
            case R.id.five_to_seven:
                startWithPriceId("631f5c2d405e11e78c5300163e000e21");
                break;
            case R.id.seven_to_nine:
                startWithPriceId("6edf8e83405e11e78c5300163e000e21");
                break;
            case R.id.nine_to_twelve:
                startWithPriceId("7d1ec027405e11e78c5300163e000e21");
                break;
            case R.id.twelve_to_fifteen:
                startWithPriceId("861d33b4405e11e78c5300163e000e21");
                break;
            case R.id.fifteen_to_seventeen:
                startWithPriceId("925fff53405e11e78c5300163e000e21");
                break;
            case R.id.high_seventeen:
                startWithPriceId("a8d222a4405e11e78c5300163e000e21");
                break;
            case R.id.paoche_re:
                startWithModelId("f01d1599406311e78c5300163e000e21");
                break;
            case R.id.mpv_re:
                startWithModelId("356551fa40f811e78c5300163e000e21");
                break;
            case R.id.jiaoche_re:
                startWithModelId("6a8ae6ae40f911e78c5300163e000e21");
                break;
            case R.id.suv_re:
                startWithModelId("0ae6201a404711e78c5300163e000e21");
                break;
            default:
                break;

        }

    }

    @Override
    public void handleLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("from", "user");
        startActivity(intent);
    }

    /**
     * @param id 根据车品牌跳转
     */
    public void startWithvehicle_brand_id(String id) {
        Intent intent = new Intent(this, OldCarListActivity.class);
        intent.putExtra("bannerId", id);
        startActivity(intent);
    }

    /**
     * @param id 根据车价格区间跳转
     */
    public void startWithPriceId(String id) {
        Intent intent = new Intent(this, OldCarListActivity.class);
        intent.putExtra("priceId", id);
        startActivity(intent);
    }

    /**
     * @param id 根据车类型跳转
     */
    public void startWithModelId(String id) {
        Intent intent = new Intent(this, OldCarListActivity.class);
        intent.putExtra("modelId", id);
        startActivity(intent);
    }

}
