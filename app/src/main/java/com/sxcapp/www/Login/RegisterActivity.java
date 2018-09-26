package com.sxcapp.www.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.CodeObserver;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Login.Bean.UserId;
import com.sxcapp.www.Login.HttpService.LoginService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import io.reactivex.Observable;

/**
 * Created by wenleitao on 2017/7/12.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private TextView code_tv;
    private EditText phone_edit, code_edit, pass_word_edit, confirm_edit;
    private RelativeLayout phone_re, code_re, pass_word_re, confirm_re;
    private Button register_btn;
    private Handler handler = new Handler();
    private int count ;
    private String phone,code,pass_word,confirm_password;
    Runnable code_run = new Runnable() {
        @Override
        public void run() {
            count--;
            if(count<=0) {
                code_tv.setText("重新发送");
                code_tv.setClickable(true);
            }else {
                code_tv.setText("重新发送("+count+"s）");
                handler.postDelayed(code_run,1000);
            }
        }
    };
    private LoginService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTopbarLeftBackBtn();
        setTopBarTitle("会员注册", null);
        initView();
    }

    private void initView() {
        code_tv = (TextView) findViewById(R.id.code_tv);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        code_edit = (EditText) findViewById(R.id.code_edit);
        pass_word_edit = (EditText) findViewById(R.id.pass_word_edit);
        confirm_edit = (EditText) findViewById(R.id.confirm_edit);
        phone_re = (RelativeLayout) findViewById(R.id.phone_re);
        code_re = (RelativeLayout) findViewById(R.id.code_re);
        pass_word_re = (RelativeLayout) findViewById(R.id.pass_word_re);
        confirm_re = (RelativeLayout) findViewById(R.id.confirm_re);
        register_btn = (Button) findViewById(R.id.register_btn);
        code_tv.setOnClickListener(this);
        code_re.setOnClickListener(this);
        phone_re.setOnClickListener(this);
        pass_word_re.setOnClickListener(this);
        confirm_re.setOnClickListener(this);
        register_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phone_re:
                InputMethodManager m = (InputMethodManager) phone_re.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.code_re:
                InputMethodManager code_m = (InputMethodManager) code_re.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                code_m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.pass_word_re:
                InputMethodManager pass_m = (InputMethodManager) pass_word_re.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                pass_m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.confirm_re:
                InputMethodManager confirm_m = (InputMethodManager) phone_re.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                confirm_m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.code_tv:
                if(TextUtils.isEmpty((phone_edit.getText().toString().trim()))){
                    showToast("请输入手机号");
                }else {
                     String phone_number = phone_edit.getText().toString().trim();
                      phone = phone_number;
                    if(AndroidTool.isMobileNO(phone_number)){
                         service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(LoginService.class);
                        Observable<CodeResultV3<Object>>codeOb = service.sendCode(phone_number,1);
                        codeOb.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
                            @Override
                            protected void onHandleSuccess(Object o) {
                                count = 60;
                                code_tv.setClickable(false);
                                handler.post(code_run);
                            }
                        });
                    }else {
                        showToast("请输入正确的手机号");
                    }
                }
                break;
            case R.id.register_btn:
                if(phone.isEmpty()){
                    showToast("请输入手机号");
                }else {
                    if(TextUtils.isEmpty((code_edit.getText().toString().trim()))){
                        showToast("请获取验证码");
                    }else {
                        code = code_edit.getText().toString().trim();
                        if(TextUtils.isEmpty((pass_word_edit.getText().toString().trim()))){
                            showToast("请输入密码");
                        }else {
                            pass_word = pass_word_edit.getText().toString().trim();
                            if(TextUtils.isEmpty((confirm_edit.getText().toString().trim()))){
                                showToast("请输入确认密码");
                            }else {
                                if(pass_word.equals(confirm_edit.getText().toString().trim())){
                                    confirm_password = confirm_edit.getText().toString().trim();
                                    register();
                                }else {
                                    showToast("请检查密码与确认密码是否一致");
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    private void register() {
            Observable observable = service.register(phone,code,pass_word);
           observable.compose(compose(this.<Result<UserId>>bindToLifecycle())).subscribe(new BaseObserver<UserId>(this) {
               @Override
               protected void onHandleSuccess(UserId userId) {
                   Log.e("@!@!@!",userId.getCustomer_id());
                   SharedData.getInstance().set(SharedData._user_id,userId.getCustomer_id());
                   showToast("注册成功");
                   Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                   setResult(Activity.RESULT_OK,intent);
               }


           });

    }
}
