package com.sxcapp.www.Lease;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.CustomerView.SortListView.CharacterParser;
import com.sxcapp.www.CustomerView.SortListView.PinyinComparator;
import com.sxcapp.www.CustomerView.SortListView.SideBar;
import com.sxcapp.www.CustomerView.SortListView.SortAdapter;
import com.sxcapp.www.CustomerView.SortListView.SortModel;
import com.sxcapp.www.Lease.Bean.City;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 选择城市界面
 * Created by wenleitao on 2017/7/5.
 */

public class SelectCityActivity extends BaseActivity {
    private CharacterParser characterParser;
    private List<SortModel> sourceDateList;
    private PinyinComparator pinyinComparator;
    private SortAdapter adapter;
    private boolean isHas = false;
    @BindView(R.id.lv)
    ListView sortListView;
    @BindView(R.id.sidebar)
    SideBar sideBar;
    @BindView(R.id.index_tv)
    TextView dialog;
    @BindView(R.id.lo_city_tv)
    TextView lo_city_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcity);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("选择城市", null);
        initViews();
        getPermission();
        LeaseService cityService = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        Observable<Result<List<City>>> resultObservable = cityService.getCity();
        resultObservable.compose(compose(this.<Result<List<City>>>bindToLifecycle())).subscribe(new BaseObserver<List<City>>(this) {
            @Override
            protected void onHandleSuccess(List<City> list) {
                sourceDateList = filledData(list);
                Collections.sort(sourceDateList, pinyinComparator);
                adapter = new SortAdapter(SelectCityActivity.this, sourceDateList);
                sortListView.setAdapter(adapter);
                initSlideBar();

            }


        });
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sourceDateList = new ArrayList<>();
        sideBar.setmTextDialog(dialog);
    }

    private void initSlideBar() {
        sideBar.setOnTouchingLetterChanged(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(SelectCityActivity.this, LeaseActivity.class);
                intent.putExtra("id", sourceDateList.get(position).getId());
                intent.putExtra("name", sourceDateList.get(position).getName());
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });

    }

    /**
     * 获取SortModel
     *
     * @param list
     * @return
     */
    private List<SortModel> filledData(List<City> list) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(list.get(i).getName());
            sortModel.setId(list.get(i).getId());
///           汉字转换为拼音
            String pinyin = characterParser.getSelling(list.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            sortModel.setSortLetters(sortString.toUpperCase());
            mSortList.add(sortModel);
        }
        return mSortList;

    }

    private void LoCity() {

        AMapLocationClient mLocationClient = null;
//声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                        final String city = aMapLocation.getCity();
                        lo_city_tv.setText(city);
                        lo_city_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (sourceDateList != null) {
                                    for (int i = 0; i < sourceDateList.size(); i++) {
                                        if (sourceDateList.get(i).getName().equals(city)) {
                                            Intent intent = new Intent(SelectCityActivity.this, LeaseActivity.class);
                                            intent.putExtra("id", sourceDateList.get(i).getId());
                                            intent.putExtra("name", sourceDateList.get(i).getName());
                                            isHas = true;
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    }
                                    if (!isHas) {
                                        showToast("当前城市未开通门店");
                                    }
                                } else {
                                    showToast("当前城市未开通门店");
                                }
                            }
                        });
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }

            }
        };
//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationCacheEnable(false);
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    /**
     * 获取定位权限
     */
    public void getPermission() {
        PackageManager pkgManager = getPackageManager();
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
            PermissionGen.with(SelectCityActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    .request();
        } else {
            LoCity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        LoCity();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        lo_city_tv.setText("定位失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
