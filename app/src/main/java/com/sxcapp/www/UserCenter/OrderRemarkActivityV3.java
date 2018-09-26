package com.sxcapp.www.UserCenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.CircleTransformt;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.RemarkLabelV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 订单评价界面
 * Created by wenleitao on 2018/7/3.
 */

public class OrderRemarkActivityV3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.car_remark_star01)
    ImageView carRemarkStar01;
    @BindView(R.id.car_remark_star02)
    ImageView carRemarkStar02;
    @BindView(R.id.car_remark_star03)
    ImageView carRemarkStar03;
    @BindView(R.id.car_remark_star04)
    ImageView carRemarkStar04;
    @BindView(R.id.car_remark_star05)
    ImageView carRemarkStar05;
    @BindView(R.id.car_remark_lin)
    LinearLayout carRemarkLin;
    @BindView(R.id.service_remark_star01)
    ImageView serviceRemarkStar01;
    @BindView(R.id.service_remark_star02)
    ImageView serviceRemarkStar02;
    @BindView(R.id.service_remark_star03)
    ImageView serviceRemarkStar03;
    @BindView(R.id.service_remark_star04)
    ImageView serviceRemarkStar04;
    @BindView(R.id.service_remark_star05)
    ImageView serviceRemarkStar05;
    @BindView(R.id.remark_re)
    RelativeLayout remarkRe;
    @BindView(R.id.remark_label_lin)
    LinearLayout remarkLabelLin;
    @BindView(R.id.commit_btn)
    Button commit_btn;
    @BindView(R.id.car_iv)
    ImageView car_iv;
    @BindView(R.id.remark_edit)
    EditText remark_edit;
    @BindView(R.id.complaint_edit)
    EditText complaint_edit;
    @BindView(R.id.complaint_re)
    RelativeLayout complaint_re;
    private String order_no, remark_string = "", complaint_string = "", labelString = "", from;
    private int order_type;
    //    1.电分 2.电长 3.油分 4.油长 5.豪车
    private UserCenterService service;
    private int car_remark_score = 0;
    private int service_remark_score = 0;
    private List<String> labelList;
    private HashMap<Integer, String> choseLabelMap;
    private StringBuffer buffer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_order_v3);
        ButterKnife.bind(this);
        order_no = getIntent().getStringExtra("order_no");
        order_type = getIntent().getIntExtra("order_type", 1);
        from = getIntent().getStringExtra("from");
        if ("order".equals(from)) {
            setTopbarRightbtn(0, R.string.close, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.getInstance().gotoMainActivity();
                }
            });
        } else {
            setTopbarLeftWhiteBackBtn();
        }
        StatusBarUtil.StatusBarDarkMode(this);
        labelList = new ArrayList<>();
        choseLabelMap = new HashMap<>();
        buffer = new StringBuffer();
        if (order_type == 1 || order_type == 2) {
            setStatusView(R.color.green);
            setTopBarColor(R.color.green);
            setStatusView(R.color.green);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_green);
            if (order_type == 1) {
                setTopBarTitle("电车分时订单评价", null);
            } else {
                setTopBarTitle("电车长租订单评价", null);
            }
        } else if (order_type == 3 || order_type == 4) {
            setStatusView(R.color.top_bar_red);
            setTopBarColor(R.color.top_bar_red);
            setStatusView(R.color.top_bar_red);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_red);
            if (order_type == 3) {
                setTopBarTitle("油车分时订单评价", null);
            } else {
                setTopBarTitle("油车长租订单评价", null);
            }
        } else if (order_type == 5) {
            setStatusView(R.color.luxury);
            setTopBarColor(R.color.luxury);
            setTopBarColor(R.color.luxury);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_luxury);
            setTopBarTitle("豪车租赁订单评价", null);
        }
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        loadData();
    }

    private void loadData() {
        Observable<CodeResultV3<RemarkLabelV3>> ob = service.getRemarkLabelV3(order_no, order_type);
        ob.compose(compose(this.<CodeResultV3<RemarkLabelV3>>bindToLifecycle())).subscribe(new CodeObserverV3<RemarkLabelV3>(this) {
            @Override
            protected void onHandleSuccess(RemarkLabelV3 remarkLabelV3) {
                Glide.with(OrderRemarkActivityV3.this).load(
                        remarkLabelV3.getCar_image()).into(car_iv);
                if (remarkLabelV3.getLabels().size() > 0) {
                    int groupCount = remarkLabelV3.getLabels().size() / 2;
                    labelList = remarkLabelV3.getLabels();
                    for (int i = 0; i < groupCount; i++) {
                        View view = LayoutInflater.from(OrderRemarkActivityV3.this).inflate(R.layout.remark_label_item_left_v3, null);
                        remarkLabelLin.addView(view);
                        TextView left_tv = view.findViewById(R.id.left_tv);
                        final ImageButton imageButton = view.findViewById(R.id.left_box);
                        LinearLayout left_lin = view.findViewById(R.id.left_lin);
                        RelativeLayout right_re = view.findViewById(R.id.right_re);

                        final int index = i;
                        left_lin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (imageButton.isSelected()) {
                                    imageButton.setSelected(false);
                                    choseLabelMap.remove(index);
                                } else {
                                    imageButton.setSelected(true);
                                    choseLabelMap.put(index, labelList.get(index * 2));
                                }

                            }
                        });
                        final ImageButton imageButton_right = view.findViewById(R.id.right_box);
                        right_re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (imageButton_right.isSelected()) {
                                    imageButton_right.setSelected(false);
                                    choseLabelMap.remove(index);
                                } else {
                                    imageButton_right.setSelected(true);
                                    choseLabelMap.put(index, labelList.get(index * 2 + 1));
                                }

                            }
                        });
                        TextView right_tv = view.findViewById(R.id.right_tv);
                        left_tv.setText(labelList.get(i * 2));
                        right_tv.setText(labelList.get(i * 2 + 1));
                    }
                    int residueCount = remarkLabelV3.getLabels().size() % 2;
                    if (residueCount == 1) {
                        View view = LayoutInflater.from(OrderRemarkActivityV3.this).inflate(R.layout.remark_label_item_left_v3, null);
                        RelativeLayout right_re = view.findViewById(R.id.right_re);
                        right_re.setVisibility(View.GONE);
                        remarkLabelLin.addView(view);
                        final ImageButton imageButton = view.findViewById(R.id.left_box);
                        final int index = labelList.size() - 1;
                        LinearLayout left_lin = view.findViewById(R.id.left_lin);
                        left_lin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (imageButton.isSelected()) {
                                    imageButton.setSelected(false);
                                    choseLabelMap.remove(index);
                                } else {
                                    imageButton.setSelected(true);
                                    choseLabelMap.put(index, labelList.get(labelList.size() - 1));
                                }

                            }
                        });
                    }
                }
                carRemarkStar01.setOnClickListener(OrderRemarkActivityV3.this);
                carRemarkStar02.setOnClickListener(OrderRemarkActivityV3.this);
                carRemarkStar03.setOnClickListener(OrderRemarkActivityV3.this);
                carRemarkStar04.setOnClickListener(OrderRemarkActivityV3.this);
                carRemarkStar05.setOnClickListener(OrderRemarkActivityV3.this);
                serviceRemarkStar01.setOnClickListener(OrderRemarkActivityV3.this);
                serviceRemarkStar02.setOnClickListener(OrderRemarkActivityV3.this);
                serviceRemarkStar03.setOnClickListener(OrderRemarkActivityV3.this);
                serviceRemarkStar04.setOnClickListener(OrderRemarkActivityV3.this);
                serviceRemarkStar05.setOnClickListener(OrderRemarkActivityV3.this);
                commit_btn.setOnClickListener(OrderRemarkActivityV3.this);
                remarkRe.setOnClickListener(OrderRemarkActivityV3.this);
                complaint_re.setOnClickListener(OrderRemarkActivityV3.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_remark_star01:
                carRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar02.setBackgroundResource(R.mipmap.star_gray);
                carRemarkStar03.setBackgroundResource(R.mipmap.star_gray);
                carRemarkStar04.setBackgroundResource(R.mipmap.star_gray);
                carRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                car_remark_score = 1;
                break;
            case R.id.car_remark_star02:
                carRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar03.setBackgroundResource(R.mipmap.star_gray);
                carRemarkStar04.setBackgroundResource(R.mipmap.star_gray);
                carRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                car_remark_score = 2;
                break;
            case R.id.car_remark_star03:
                carRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar03.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar04.setBackgroundResource(R.mipmap.star_gray);
                carRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                car_remark_score = 3;
                break;
            case R.id.car_remark_star04:
                carRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar03.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar04.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                car_remark_score = 4;
                break;
            case R.id.car_remark_star05:
                carRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar03.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar04.setBackgroundResource(R.mipmap.star_gold);
                carRemarkStar05.setBackgroundResource(R.mipmap.star_gold);
                car_remark_score = 5;
                break;
            case R.id.service_remark_star01:
                serviceRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar02.setBackgroundResource(R.mipmap.star_gray);
                serviceRemarkStar03.setBackgroundResource(R.mipmap.star_gray);
                serviceRemarkStar04.setBackgroundResource(R.mipmap.star_gray);
                serviceRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                service_remark_score = 1;
                break;
            case R.id.service_remark_star02:
                serviceRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar03.setBackgroundResource(R.mipmap.star_gray);
                serviceRemarkStar04.setBackgroundResource(R.mipmap.star_gray);
                serviceRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                service_remark_score = 2;
                break;
            case R.id.service_remark_star03:
                serviceRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar03.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar04.setBackgroundResource(R.mipmap.star_gray);
                serviceRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                service_remark_score = 3;
                break;
            case R.id.service_remark_star04:
                serviceRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar03.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar04.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar05.setBackgroundResource(R.mipmap.star_gray);
                service_remark_score = 4;
                break;
            case R.id.service_remark_star05:
                serviceRemarkStar01.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar02.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar03.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar04.setBackgroundResource(R.mipmap.star_gold);
                serviceRemarkStar05.setBackgroundResource(R.mipmap.star_gold);
                service_remark_score = 5;
                break;
            case R.id.remark_re:
                remark_edit.requestFocus();
                InputMethodManager imm01 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm01.showSoftInput(remark_edit, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.complaint_re:
                complaint_edit.requestFocus();
                InputMethodManager imm02 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm02.showSoftInput(complaint_edit, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.commit_btn:
                if (car_remark_score == 0) {
                    showToast("请给车辆评价打分");
                } else if (service_remark_score == 0) {
                    showToast("请给服务评价打分");
                } else {
                    if (!TextUtils.isEmpty(remark_edit.getText().toString())) {
                        remark_string = remark_edit.getText().toString();
                    }
                    if (!TextUtils.isEmpty(complaint_edit.getText().toString())) {
                        complaint_string = complaint_edit.getText().toString();
                    }
                    for (Integer key : choseLabelMap.keySet()) {
                        buffer.append(choseLabelMap.get(key) + ",");
                    }
                    if (buffer.length() > 0) {
                        buffer.deleteCharAt(buffer.length() - 1);
                        labelString = buffer.toString();
                    }
                    Observable<CodeResultV3<Object>> ob = service.remarkOrderV3(order_no, order_type, car_remark_score,
                            service_remark_score, labelString, remark_string, complaint_string, SharedData.getInstance().getString(SharedData._user_id));
                    ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
                        @Override
                        protected void onHandleSuccess(Object o) {
                            showToast("评价成功");
                            if ("order".equals(from)) {
                                MyApplication.getInstance().gotoMainActivity();
                            } else {
                                finish();
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }


        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&&"order".equals(from)) {
                MyApplication.getInstance().gotoMainActivity();
                return false;
            }
            return super.onKeyDown(keyCode, event);

    }
}
