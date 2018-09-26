package com.sxcapp.www.UserCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.CustomerView.CircleTransformt;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.UserInfo;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 信息修改
 * Created by wenleitao on 2017/8/2.
 */

public class UserProfilActivity extends BaseActivity {
    @BindView(R.id.avatar_iv)
    ImageView avatar_iv;
    @BindView(R.id.name_tv)
    EditText name_edit;
    private UserCenterService service;
    private int PROFILE_REQUEST = 11;
    private RequestOptions options = new RequestOptions();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
        setTopbarLeftBackBtn();
        setTopBarTitle("我的资料", null);
        setTopbarRightbtn(0, R.string.modify, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfilActivity.this, ProfileModifyActivity.class);
                startActivityForResult(intent, PROFILE_REQUEST);
            }
        });
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        options.transform(new CircleTransformt(UserProfilActivity.this));
        initViews();
        loadData();

    }

    private void initViews() {
        name_edit.setClickable(false);
        name_edit.setFocusable(false);
    }

    private void loadData() {
//        showProgressDlg();
//        Observable<Result<UserInfo>> ob = service.getUserInfo(SharedData.getInstance().getString(SharedData._user_id));
//        ob.compose(compose(UserProfilActivity.this.<Result<UserInfo>>bindToLifecycle())).subscribe(new BaseObserver<UserInfo>(UserProfilActivity.this) {
//            @Override
//            protected void onHandleSuccess(UserInfo userInfo) {
//                removeProgressDlg();
//                UserInfo.CustomerBean bean = userInfo.getCustomer();
//                if (bean.getHead_image() != null) {
//                    Glide.with(UserProfilActivity.this).load(Properties.baseImageUrl + bean.getHead_image()).apply(options).into(avatar_iv);
//                }
//                if (bean.getNick_name() != null && bean.getNick_name().length() > 0) {
//                    name_edit.setText(bean.getNick_name());
//                }
//            }
//
//            @Override
//            protected void onHandleError(String msg) {
//                super.onHandleError(msg);
//                removeProgressDlg();
//            }
//        });
        if (!TextUtils.isEmpty(SharedData.getInstance().getString(SharedData._user_name))) {
            name_edit.setText(SharedData.getInstance().getString(SharedData._user_name));
        }
        if (!TextUtils.isEmpty(SharedData.getInstance().getString(SharedData._avatar))) {
            Glide.with(this).load(SharedData.getInstance().getString(SharedData._avatar)).apply(options).into(avatar_iv);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                name_edit.setText(SharedData.getInstance().getString(SharedData._user_name));
                if (!TextUtils.isEmpty(SharedData.getInstance().getString(SharedData._avatar))) {
                    Glide.with(this).load(SharedData.getInstance().getString(SharedData._avatar)).apply(options).into(avatar_iv);
                }
            }
        }
    }
}
