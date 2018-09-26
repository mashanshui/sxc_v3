package com.sxcapp.www.Buy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Buy.Bean.OldCarListResult;
import com.sxcapp.www.Buy.HttpService.BuyService;
import com.sxcapp.www.Buy.entity.FilterUrl;
import com.sxcapp.www.CustomerView.XListView.XListView;
import com.sxcapp.www.CustomerView.filter.DropDownMenu;
import com.sxcapp.www.CustomerView.filter.interfaces.OnFilterDoneListener;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.MessageCenterActivity;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.activity.MainActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 二手车列表界面
 * Created by wenleitao on 2017/7/17.
 */

public class OldCarListActivity extends BaseActivity implements XListView.IXListViewListener, OnFilterDoneListener, View.OnClickListener {
    private HashMap<String, String> dataMap;
    private BuyService service;
    private int index = 1;
    private int total_page;
    private HashMap<String, String> map00 = new HashMap<>();
    private HashMap<String, String> map01 = new HashMap<>();
    private HashMap<String, String> map02 = new HashMap<>();
    private HashMap<String, String> map03 = new HashMap<>();
    private static final int LOADMOREABLE = 11;
    private static final int LOADMOREUNABLE = 12;
    private OldCarAdapter adapter;
    @BindView(R.id.mFilterContentView)
    XListView lv;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R.id.search_re)
    RelativeLayout search_re;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.user_iv)
    ImageView user_iv;
    @BindView(R.id.message_iv)
    ImageView message_iv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buycar_list);
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(BuyService.class);
//        LinearLayout header = (LinearLayout) getLayoutInflater().inflate(R.layout.buy_car_list_header, null);
//        lv.addHeaderView(header);
        lv.setPullRefreshEnable(true);
        lv.setXListViewListener(this);
        dataMap = new HashMap<>();
        dataMap.put("mileage", "");
        dataMap.put("displacement", "");
        dataMap.put("emission_standard", "");
        dataMap.put("gearbox_type", "");
        dataMap.put("seating_num", "");
        dataMap.put("fuel_type", "");
        dataMap.put("vehicle_brand_id", "");
        dataMap.put("desc", "");
        dataMap.put("price", "");
        dataMap.put("brand", "");
        dataMap.put("vehicle_model_id", "");
        dataMap.put("owner_quote", "");
        dataMap.put("order", "");
        dataMap.put("owner_city_id", "");
        if (getIntent().hasExtra("bannerId")) {
            dataMap.put("bannerId", getIntent().getStringExtra("bannerId"));
        }
        if (getIntent().hasExtra("priceId")) {
            dataMap.put("priceId", getIntent().getStringExtra("priceId"));
        }
        if (getIntent().hasExtra("modelId")) {
            dataMap.put("modelId", getIntent().getStringExtra("modelId"));
        }
//        筛选选项对应id
        map00.put("智能排序", "0");
        map00.put("最新发布", "1");
        map00.put("价格最低", "2");
        map00.put("价格最高", "3");
        map00.put("车龄最短", "4");
        map00.put("里程最短", "5");
        map01.put("大众", "1015d031c1174d75840691bd8e9dc3f0");
        map01.put("江淮", "519391cce6474ba38146c8de3d8b4a89");
        map01.put("福特", "f13a12283bbc4f0fab1eefabe3c23739");
        map01.put("雪佛兰", "6595b9db823a4438a04dbeaf6b86ee8f");
        map01.put("别克", "5d0d7a733624425fb43f09ea83ac1226");
        map01.put("起亚", "c341bf58340f4866af5dea722a228b1f");
        map02.put("3万以下", "44dc08bb405e11e78c5300163e000e21");
        map02.put("3-5万", "563e039b405e11e78c5300163e000e21");
        map02.put("5-7万", "631f5c2d405e11e78c5300163e000e21");
        map02.put("7-9万", "6edf8e83405e11e78c5300163e000e21");
        map02.put("9-12万", "7d1ec027405e11e78c5300163e000e21");
        map02.put("12-15万", "861d33b4405e11e78c5300163e000e21");
        map02.put("15-17万", "925fff53405e11e78c5300163e000e21");
        map02.put("17万以上", "a8d222a4405e11e78c5300163e000e21");
        map03.put("跑车", "f01d1599406311e78c5300163e000e21");
        map03.put("MPV", "356551fa40f811e78c5300163e000e21");
        map03.put("轿车", "6a8ae6ae40f911e78c5300163e000e21");
        map03.put("SUV", "0ae6201a404711e78c5300163e000e21");
        loadData();
        initFilterDropDownView();
        search_re.setOnClickListener(this);
        message_iv.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        user_iv.setOnClickListener(this);
    }

    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case LOADMOREABLE:
                lv.setPullLoadEnable(true);
                break;
            case LOADMOREUNABLE:
                lv.setPullLoadEnable(false);
                break;
            default:
                break;
        }
    }

    private void initFilterDropDownView() {
        String[] titleList = new String[]{"智能排序", "品牌", "价格", "车型"};
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList, this));
    }

    private void loadData() {
        index = 1;
        dataMap.put("pageIndex", 1 + "");
        Observable<Result<OldCarListResult>> resultOb = service.getOldCarList(dataMap);
        resultOb.compose(compose(this.<Result<OldCarListResult>>bindToLifecycle())).subscribe(new BaseObserver<OldCarListResult>(this) {
            @Override
            protected void onHandleSuccess(final OldCarListResult o) {
                lv.stopRefresh(true);
                total_page = o.getTotalPageNum();
                if (total_page == 1) {
                    mHandler.sendEmptyMessage(LOADMOREUNABLE);
                } else {
                    mHandler.sendEmptyMessage(LOADMOREABLE);
                }

                adapter = new OldCarAdapter(OldCarListActivity.this, o.getOldcarlist());
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(OldCarListActivity.this, OldCarDetailActivity.class);
                        intent.putExtra("car_id", o.getOldcarlist().get(position - 1).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lv.stopRefresh(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMore() {
        index++;
        dataMap.put("pageIndex", index + "");
        Observable<Result<OldCarListResult>> resultOb = service.getOldCarList(dataMap);
        resultOb.compose(compose(this.<Result<OldCarListResult>>bindToLifecycle())).subscribe(new BaseObserver<OldCarListResult>(this) {
            @Override
            protected void onHandleSuccess(final OldCarListResult o) {
                lv.stopLoadMore();
                total_page = o.getTotalPageNum();
                if (total_page > index) {
                    mHandler.sendEmptyMessage(LOADMOREABLE);
                } else {
                    mHandler.sendEmptyMessage(LOADMOREUNABLE);
                }
                adapter.addData(o.getOldcarlist());
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lv.stopLoadMore();
                index--;
                showToast("加载失败");
            }
        });
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {

        dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        if (FilterUrl.instance().position == 0) {
            dataMap.put("sorting", map00.get(FilterUrl.instance().positionTitle));
            loadData();
        } else if (FilterUrl.instance().position == 1) {
            if ("不限".equals(FilterUrl.instance().positionTitle)) {
                dataMap.put("bannerId", "");
            } else {
                dataMap.put("bannerId", map01.get(FilterUrl.instance().positionTitle));
            }
            loadData();
        } else if (FilterUrl.instance().position == 2) {
            if ("不限".equals(FilterUrl.instance().positionTitle)) {
                dataMap.put("priceId", "");
            } else {
                dataMap.put("priceId", map02.get(FilterUrl.instance().positionTitle));
            }
            loadData();
        } else if (FilterUrl.instance().position == 3) {
            if ("不限".equals(FilterUrl.instance().positionTitle)) {
                dataMap.put("modelId", "");
            } else {
                dataMap.put("modelId", map03.get(FilterUrl.instance().positionTitle));
            }
            loadData();
        }
        dropDownMenu.close();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
}
