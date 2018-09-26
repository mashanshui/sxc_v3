package com.sxcapp.www.Buy;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.CodeObserver;
import com.sxcapp.www.Bean.CodeResult;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Buy.Bean.OldCarDetail;
import com.sxcapp.www.Buy.HttpService.BuyService;
import com.sxcapp.www.CustomerView.InfinitePagerAdapter;
import com.sxcapp.www.CustomerView.InfiniteViewPager;
import com.sxcapp.www.Login.HttpService.LoginService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 二手车买车界面
 * Created by wenleitao on 2017/7/18.
 */

public class OldCarDetailActivity extends BaseActivity implements View.OnClickListener {
    Runnable code_run = new Runnable() {
        @Override
        public void run() {
            count--;
            if (count <= 0) {
                code_tv.setText("重新发送");
                code_tv.setClickable(true);
            } else {
                code_tv.setText("重新发送(" + count + "s）");
                mHandler.postDelayed(code_run, 1000);
            }
        }
    };
    private Dialog dialog;
    private EditText phone_edit, code_edit;
    private int count;
    private BuyService service;

    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case 1:
                // 获取ViewPager当前正在展示的pager的索引
                int currentItem = car_vp.getCurrentItem() - car_vp.getOffsetAmount();
                car_vp.setCurrentItem(currentItem + 1);
                break;
            default:
                break;
        }
    }

    private Thread thread;
    private String car_id;
    private ImagePageAdapter imagePageAdapter;
    private InfinitePagerAdapter infiAdapter;
    private List<String> imageList;
    private TextView code_tv;
    private OldCarDetail.DetailBean detailBean;
    private Button dialog_subscribe_btn;
    private String service_phone, subscribe_phone;
    @BindView(R.id.car_vp)
    InfiniteViewPager car_vp;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.car_number)
    TextView car_number_tv;
    @BindView(R.id.image_index_tv)
    TextView image_index_tv;
    @BindView(R.id.image_count_tv)
    TextView image_count_tv;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.new_car_price_tv)
    TextView new_price_tv;
    @BindView(R.id.servicefee_tv)
    TextView servicefee_tv;
    @BindView(R.id.mileage_tv)
    TextView mileage_tv;
    @BindView(R.id.emission_standard_tv)
    TextView emission_standard_tv;
    @BindView(R.id.licensing_time)
    TextView licensing_time_tv;
    @BindView(R.id.displacement_tv)
    TextView displacement_tv;
    @BindView(R.id.oper_time_tv)
    TextView oper_time_tv;
    @BindView(R.id.oper_city_tv)
    TextView oper_city_tv;
    @BindView(R.id.more_config_tv)
    TextView more_config_tv;
    @BindView(R.id.owner_name_tv)
    TextView owner_name_tv;
    @BindView(R.id.owner_dict_name_tv)
    TextView owner_dict_name_tv;
    @BindView(R.id.look_car_tv)
    TextView look_car_tv;
    @BindView(R.id.instruction_tv)
    TextView instruction_tv;
    @BindView(R.id.annual_inspection_end_time_tv)
    TextView annual_inspection_end_time_tv;
    @BindView(R.id.tcl_end_time_tv)
    TextView tcl_end_time_tv;
    @BindView(R.id.vcl_end_time_tv)
    TextView vcl_end_time_tv;
    @BindView(R.id.transfer_count_tv)
    TextView transfer_count_tv;
    @BindView(R.id.auth_iv)
    ImageView ertifiedImage_iv;
    @BindView(R.id.appearance_iv)
    ImageView appearance_iv;
    @BindView(R.id.trim_iv)
    ImageView trim_iv;
    @BindView(R.id.engine_tv)
    ImageView engine_iv;
    @BindView(R.id.call_service_btn)
    Button call_service_btn;
    @BindView(R.id.subscribe_btn)
    Button subscribe_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldcar_detail);
        ButterKnife.bind(this);
        car_id = getIntent().getStringExtra("car_id");
        initViews();
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(BuyService.class);
        showProgressDlg();
        Observable<CodeResult<OldCarDetail>> resultOb = service.getOldCarDetail(car_id);
        resultOb.compose(compose(this.<CodeResult<OldCarDetail>>bindToLifecycle())).subscribe(new CodeObserver<OldCarDetail>(this) {
            @Override
            protected void onHandleSuccess(OldCarDetail oldCarDetail) {
                removeProgressDlg();
                addData(oldCarDetail);
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                removeProgressDlg();
            }
        });

    }

    private void addData(OldCarDetail oldCarDetail) {
        detailBean = oldCarDetail.getDetail();
        imageList = new ArrayList<>();
        for (int i = 0; i < oldCarDetail.getWgimageList().size(); i++) {
            imageList.add(oldCarDetail.getWgimageList().get(i).getImage_path());
        }
        for (int i = 0; i < oldCarDetail.getDpimageList().size(); i++) {
            imageList.add(oldCarDetail.getDpimageList().get(i).getImage_path());
        }
        for (int i = 0; i < oldCarDetail.getNsimageList().size(); i++) {
            imageList.add(oldCarDetail.getNsimageList().get(i).getImage_path());
        }
        for (int i = 0; i < oldCarDetail.getQximageList().size(); i++) {
            imageList.add(oldCarDetail.getQximageList().get(i).getImage_path());
        }
        imagePageAdapter = new ImagePageAdapter(this, imageList);
        infiAdapter = new InfinitePagerAdapter(imagePageAdapter);
        car_vp.setOffscreenPageLimit(4); //设置
        car_vp.setAdapter(infiAdapter);
        if (thread == null) {
            thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        // 系统时钟的睡眠方法.--->电量的消耗会很小.
                        SystemClock.sleep(4500);
                        mHandler.sendEmptyMessage(1);
                    }
                }
            };
            thread.start();
        }
        car_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = Math.abs(position - imageList.size() * 100000) % imageList.size() + 1;
                image_index_tv.setText(index + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        car_number_tv.setText("车辆编号:" + detailBean.getVehicle_source_number());
        image_index_tv.setText("1");
        image_count_tv.setText("/" + imageList.size());
        car_name_tv.setText(detailBean.getBName() + detailBean.getSName() + detailBean.getMName());
        DecimalFormat df = new DecimalFormat("######0.00");
        double save_money = (detailBean.getNew_vehicle_price() * 100 - detailBean.getOwner_quote() * 100) / 100;
        price_tv.setText(df.format(detailBean.getOwner_quote()));
        new_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        new_price_tv.setText("新车含税" + df.format(detailBean.getNew_vehicle_price()) + "万" + "(省" + df.format(save_money) + "万)");
        servicefee_tv.setText("(服务费:" + df.format(oldCarDetail.getServiceFee()) + "元," + "车价的" + oldCarDetail.getServiceFeeRatio() + "%)");
        mileage_tv.setText(df.format(detailBean.getMileage()) + "");
        licensing_time_tv.setText(TimeFormat.getStringFormMill(detailBean.getLicensing_time()));
        oper_time_tv.setText(TimeFormat.getStringFormMill(detailBean.getOper_time()));
        emission_standard_tv.setText(detailBean.getEmission_standard());
        DecimalFormat df_one = new DecimalFormat("######0.0");
        displacement_tv.setText(df_one.format(Double.parseDouble(detailBean.getDisplacement()) / 1000) + "升");
        oper_city_tv.setText(detailBean.getCity_Name());
        owner_name_tv.setText("车主" + detailBean.getOwner_name());
        owner_dict_name_tv.setText(detailBean.getOwner_dict_name());
        instruction_tv.setText(detailBean.getInstruction());
        annual_inspection_end_time_tv.setText(TimeFormat.getStringFormMill(detailBean.getAnnual_inspection_end_time()));
        tcl_end_time_tv.setText(TimeFormat.getStringFormMill(detailBean.getTcl_end_time()));
        vcl_end_time_tv.setText(TimeFormat.getStringFormMill(detailBean.getVcl_end_time()));
        transfer_count_tv.setText(detailBean.getTransfer_count());
        Glide.with(this).load(Properties.baseImageUrl + oldCarDetail.getWgimageList().get(0).getImage_path()).into(appearance_iv);
        Glide.with(this).load(Properties.baseImageUrl + oldCarDetail.getNsimageList().get(0).getImage_path()).into(trim_iv);
        Glide.with(this).load(Properties.baseImageUrl + oldCarDetail.getDpimageList().get(0).getImage_path()).into(engine_iv);
        Glide.with(this).load(Properties.baseImageUrl + oldCarDetail.getErtifiedImage()).into(ertifiedImage_iv);
        service_phone = oldCarDetail.getOfficial_hotline();
        call_service_btn.setOnClickListener(this);


    }

    private void initViews() {
        look_car_tv.setOnClickListener(this);
        more_config_tv.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        subscribe_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_service_btn:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + service_phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.subscribe_btn:
                showSubscribeDialog();
                break;
            case R.id.look_car_tv:
                showSubscribeDialog();
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_config_tv:
                openWebView("：http://106.14.135.110/SxcH5/sxcar/more_param.html?carId=" + car_id, "配置参数", true);
                break;
            default:
                break;
        }
    }

    public void showSubscribeDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.subscribe_dialog, null);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialogWindow.setWindowAnimations(R.style.dialog_anim_style);
        dialog.show();
        phone_edit = (EditText) view.findViewById(R.id.phone_edit);
        code_edit = (EditText) view.findViewById(R.id.code_edit);
        code_tv = (TextView) view.findViewById(R.id.code_tv);
        dialog_subscribe_btn = (Button) view.findViewById(R.id.dialog_subscribe_btn);
        code_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = phone_edit.getText().toString().trim();
                subscribe_phone = phone_number;
                if (AndroidTool.isMobileNO(phone_number)) {
                    getCode(phone_number);
                } else {
                    showToast("请输入正确的手机号");
                }
            }
        });
        dialog_subscribe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = phone_edit.getText().toString().trim();
                subscribe_phone = phone_number;
                checkCode(phone_number);
            }
        });

    }

    public void getCode(String phone_number) {
        final LoginService loginService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(LoginService.class);
        Observable<CodeResultV3<Object>> codeOb = loginService.sendCode(phone_number, 4);
        codeOb.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                count = 60;
                code_tv.setClickable(false);
                mHandler.post(code_run);
            }
        });
    }

    public void checkCode(final String number) {
        if (AndroidTool.isMobileNO(number)) {
            if (code_edit.getText().toString().trim().isEmpty()) {
                showToast("请输入验证码");
            } else {
                Observable<Result<Object>> codeOb = service.checkCode(subscribe_phone, code_edit.getText().toString().trim());
                codeOb.compose(compose(this.<Result<Object>>bindToLifecycle())).subscribe(new BaseObserver<Object>(this) {
                    @Override
                    protected void onHandleSuccess(Object o) {
                        oldCarAppointment(number);
                    }
                });
            }
        } else {
            showToast("请输入正确的手机号");
        }
    }

    public void oldCarAppointment(String number) {
        Observable<CodeResult<Object>> appointOb = service.appointment(SharedData.getInstance().getString(SharedData._user_id),
                car_id, number);
        appointOb.compose(compose(this.<CodeResult<Object>>bindToLifecycle())).subscribe(new CodeObserver<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                showToast("预约成功");
                dialog.dismiss();
            }
        });
    }
}
