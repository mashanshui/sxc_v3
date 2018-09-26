package com.sxcapp.www.Lease;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.Bean.Area;
import com.sxcapp.www.Lease.Bean.Store;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 选择门店界面
 * Created by wenleitao on 2017/7/10.
 */

public class SelectStoreActivity extends BaseActivity {
    private String city_id;
    private AreaAdapter areaAdapter;
    private TextView emptyView;
    private StoreAdapter storeAdapter;
    private LeaseService service;
    private List<Area> areaList = new ArrayList<>();
    private int areaIndex = 0;
    @BindView(R.id.area_lv)
    ListView area_lv;
    @BindView(R.id.store_lv)
    ListView store_lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectstore);
        ButterKnife.bind(this);
        setTopBarTitle("选择门店", null);
        setTopbarLeftBackBtn();
        city_id = getIntent().getStringExtra("city_id");
        initViews();
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
//获取区县列表
        Observable<Result<List<Area>>> areaOb = service.getArea(city_id);
        areaOb.compose(compose(this.<Result<List<Area>>>bindToLifecycle())).subscribe(new BaseObserver<List<Area>>(this) {
            @Override
            protected void onHandleSuccess(final List<Area> list) {
                areaList = list;
                chooseStore(0);
                areaAdapter = new AreaAdapter(SelectStoreActivity.this, list);
                area_lv.setAdapter(areaAdapter);
                area_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != areaIndex) {
                            areaIndex = i;
                            areaAdapter.setPosition(i);
                            chooseStore(i);
                        }
                    }
                });
            }
        });

    }

    private void initViews() {
        emptyView = new TextView(this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        emptyView.setPadding(12, 10, 0, 0);
        emptyView.setTextSize(16);
        emptyView.setTextColor(Color.parseColor("#969696"));
        emptyView.setText("暂无内容");
        ((ViewGroup) store_lv.getParent()).addView(emptyView);

    }

    /**
     * @param i index
     * 根据区县id获取门店列表
     */
    private void chooseStore(int i) {
        final Observable<Result<List<Store>>> storeOb = service.getStore(areaList.get(i).getId());
        storeOb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Store>>(SelectStoreActivity.this) {
                    @Override
                    protected void onHandleSuccess(final List<Store> stores) {
                        if (stores == null || stores.size() == 0) {
                            store_lv.setAdapter(null);
                            emptyView.setVisibility(View.VISIBLE);
                            store_lv.setEmptyView(emptyView);
                        } else {
                            storeAdapter = new StoreAdapter(SelectStoreActivity.this, stores);
                            store_lv.setAdapter(storeAdapter);
                            emptyView.setVisibility(View.GONE);
                            store_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    showToast(stores.get(i).getStore_name());
                                }
                            });
                            store_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(SelectStoreActivity.this, LeaseActivity.class);
                                    intent.putExtra("id", stores.get(position).getId());
                                    intent.putExtra("name", stores.get(position).getStore_name());
                                    intent.putExtra("start_time", stores.get(position).getStart_time());
                                    intent.putExtra("end_time", stores.get(position).getEnd_time());
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                }
                            });
                        }
                    }
                });
    }
}
