package com.sxcapp.www.Login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Login.Bean.LoginObserver;
import com.sxcapp.www.Login.Bean.LoginResult;
import com.sxcapp.www.Login.Bean.UserId;
import com.sxcapp.www.Login.HttpService.LoginService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 登录界面
 * Created by wenleitao on 2017/7/12.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private int REGISTER_REQUEST = 14;
    private Handler handler = new Handler();
    private int count;
    private String phone, code;
    Runnable code_run = new Runnable() {
        @Override
        public void run() {
            count--;
            if (count <= 0) {
                code_tv.setText("重新发送");
                code_tv.setClickable(true);
            } else {
                code_tv.setText("重新发送(" + count + "s）");
                handler.postDelayed(code_run, 1000);
            }
        }
    };
    private LoginService service;
    @BindView(R.id.code_tv)
    TextView code_tv;
    @BindView(R.id.agreement_tv)
    TextView agreement_tv;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.code_edit)
    EditText code_edit;
    @BindView(R.id.phone_lin)
    LinearLayout phone_lin;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.code_lin)
    LinearLayout code_lin;
    @BindView(R.id.back_iv)
    ImageView back_iv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(LoginService.class);
        initViews();

    }

    private void initViews() {
        login_btn.setOnClickListener(this);
        agreement_tv.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        code_lin.setOnClickListener(this);
        phone_lin.setOnClickListener(this);
        code_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.code_tv:
                if (TextUtils.isEmpty((phone_edit.getText().toString().trim()))) {
                    showToast("请输入手机号");
                } else {
                    String phone_number = phone_edit.getText().toString().trim();
                    phone = phone_number;
                    if (AndroidTool.isMobileNO(phone_number)) {
                        getCode(phone_number);
                    } else {
                        showToast("请输入正确的手机号");
                    }
                }
                break;
            case R.id.phone_lin:
                InputMethodManager m = (InputMethodManager) phone_lin.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                phone_edit.requestFocus();
                break;
            case R.id.code_lin:
                InputMethodManager m_code = (InputMethodManager) code_lin.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m_code.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                code_edit.requestFocus();
                break;
            case R.id.login_btn:
                String phone_number = phone_edit.getText().toString().trim();
                phone = phone_number;
                if (phone.isEmpty()) {
                    showToast("请输入手机号");
                } else {
                    if (AndroidTool.isMobileNO(phone_number)) {
                        if (TextUtils.isEmpty((code_edit.getText().toString().trim()))) {
                            showToast("请获取验证码");
                        } else {
                            Login(phone, code_edit.getText().toString().trim());
                        }
                    } else {
                        showToast("请输入正确的手机号");
                    }

                }
                break;
            case R.id.back_iv:
                setBack();
                break;
            case R.id.agreement_tv:
                openWebView("http://106.14.135.110:80/SxcH5/agreement.html", "用户协议", true);
                break;
            default:
                break;
        }
    }

    private void getCode(String phone_number) {
        Observable<CodeResultV3<Object>> codeOb = service.sendCode(phone_number, 2);
        codeOb.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                count = 60;
                code_tv.setClickable(false);
                handler.post(code_run);
            }
        });
    }

    private void Login(String phone_number, String code_str) {
        Log.e("Phone", phone_number);
        Log.e("code_str", code_str);
        Observable<CodeResultV3<UserId>> resultObservable = service.login(phone_number, code_str);
        resultObservable.compose(compose(this.<CodeResultV3<UserId>>bindToLifecycle())).subscribe(new CodeObserverV3<UserId>(this) {
            @Override
            protected void onHandleSuccess(UserId userId) {
                SharedData sharedData = SharedData.getInstance();
                sharedData.set(SharedData._user_id, userId.getCustomer_id());
                sharedData.set(SharedData._user_phone, phone);
                showToast("登录成功");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
