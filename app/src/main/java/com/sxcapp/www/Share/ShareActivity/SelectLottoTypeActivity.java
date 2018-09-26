package com.sxcapp.www.Share.ShareActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.TTLottoTypeBean;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2017/11/18.
 */

public class SelectLottoTypeActivity extends BaseActivity {
    @BindView(R.id.city_lv)
    ListView city_lv;
    @BindView(R.id.store_lv)
    ListView store_lv;
    @BindView(R.id.type_lv)
    ListView type_lv;
    private ShareService service;
    private CityAdapter cityAdapter;
    private StoreAdapter storeAdapter;
    private TypeAdapter typeAdapter;
    private List<String> cityList;
    private String store_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_type);
        setTopbarLeftBackBtn();
        setTopBarTitle("选择类型", null);
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        cityList = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        Observable<Result<TTLottoTypeBean>> ob_city = service.getTTLottoType();
        ob_city.compose(compose(this.<Result<TTLottoTypeBean>>bindToLifecycle())).subscribe(new BaseObserver<TTLottoTypeBean>(this) {
            @Override
            protected void onHandleSuccess(final TTLottoTypeBean listBean) {
                if (listBean.getList().size() > 0) {
                    for (int i = 0; i < listBean.getList().size(); i++) {
                        cityList.add(listBean.getList().get(i).getCity_name());
                    }
                    cityAdapter = new CityAdapter(SelectLottoTypeActivity.this, cityList);
                    city_lv.setAdapter(cityAdapter);
                    city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            store_lv.setVisibility(View.VISIBLE);
                            type_lv.setVisibility(View.GONE);
                            final List<TTLottoTypeBean.ListBean.StoreListBean> store_list = listBean.getList().get(position).getStore_list();
                            storeAdapter = new StoreAdapter(SelectLottoTypeActivity.this, store_list);
                            store_lv.setAdapter(storeAdapter);
                            store_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    type_lv.setVisibility(View.VISIBLE);
                                    store_id = store_list.get(position).getStore_id();
                                    final List<TTLottoTypeBean.ListBean.StoreListBean.CouponListBean> couponList = store_list.get(position).getCoupon_list();
                                    typeAdapter = new TypeAdapter(SelectLottoTypeActivity.this, couponList);
                                    type_lv.setAdapter(typeAdapter);
                                    type_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(SelectLottoTypeActivity.this, TTLuckyActivity.class);
                                            intent.putExtra("store_id", store_id);
                                            intent.putExtra("type_id", couponList.get(position).getType());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }


}
