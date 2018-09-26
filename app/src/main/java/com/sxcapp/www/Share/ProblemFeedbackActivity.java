package com.sxcapp.www.Share;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.UserCenter.Bean.ImageBean;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 问题反馈界面
 * Created by wenleitao on 2017/11/2.
 */

public class ProblemFeedbackActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.check_one_lin)
    LinearLayout check_one_lin;
    @BindView(R.id.check_two_lin)
    LinearLayout check_two_lin;
    @BindView(R.id.check_three_lin)
    LinearLayout check_three_lin;
    @BindView(R.id.check_four_lin)
    LinearLayout check_four_lin;
    @BindView(R.id.check_five_lin)
    LinearLayout check_five_lin;
    @BindView(R.id.check_six_lin)
    LinearLayout check_six_lin;
    @BindView(R.id.car_number_edit)
    EditText car_number_edit;
    @BindView(R.id.detail_edit)
    EditText detail_edit;
    @BindView(R.id.upload_pic_iv)
    ImageView upload_pic_iv;
    @BindView(R.id.commit_btn)
    Button commit_btn;
    @BindView(R.id.box_one)
    ImageView box_one;
    @BindView(R.id.box_two)
    ImageView box_two;
    @BindView(R.id.box_three)
    ImageView box_three;
    @BindView(R.id.box_four)
    ImageView box_four;
    @BindView(R.id.box_five)
    ImageView box_five;
    @BindView(R.id.box_six)
    ImageView box_six;
    private static final String check_one = "98bb487dbd4911e7b022ecf4bbbfe9f8";
    private static final String check_two = " a09cdd72bd4911e7b022ecf4bbbfe9f8";
    private static final String check_three = "a7fe09bbbd4911e7b022ecf4bbbfe9f8";
    private static final String check_four = "af643d1dbd4911e7b022ecf4bbbfe9f8";
    private static final String check_five = "b80b863ebd4911e7b022ecf4bbbfe9f8";
    private static final String check_six = "be76a72cbd4911e7b022ecf4bbbfe9f8";
    private int chose_what = 0;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private String fileName;
    private FileUploadService service;
    private ShareService shareService;
    private String problemImagePath = "", problemImagePath_key = "";
    private String user_id = SharedData.getInstance().getString(SharedData._user_id);
    private String type_id, from;
    private int resoure_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemfeedback);
        ButterKnife.bind(this);
        setTopbarLeftWhiteBackBtn();
        from = getIntent().getStringExtra("from");
        setTopBarTitle("问题反馈", null);
        if ("elec".equals(from)) {
            setStatusView(R.color.green);
            setTopBarColor(R.color.green);
            StatusBarUtil.StatusBarDarkMode(this);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_green);
            resoure_id = R.mipmap.green_checked_v3;
        } else if ("oil".equals(from)) {
            setStatusView(R.color.top_bar_red);
            setTopBarColor(R.color.top_bar_red);
            StatusBarUtil.StatusBarDarkMode(this);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_red);
            resoure_id = R.mipmap.red_checked_v3;
        } else if ("luxury".equals(from)) {
            setStatusView(R.color.luxury);
            setTopBarColor(R.color.luxury);
            StatusBarUtil.StatusBarDarkMode(this);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_luxury);
            resoure_id = R.mipmap.luxury_checked_v3;
        }
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        shareService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        initView();
    }

    private void initView() {
        check_one_lin.setOnClickListener(this);
        check_two_lin.setOnClickListener(this);
        check_three_lin.setOnClickListener(this);
        check_four_lin.setOnClickListener(this);
        check_five_lin.setOnClickListener(this);
        check_six_lin.setOnClickListener(this);
        upload_pic_iv.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
    }

    private void changeCheckBox(int index) {
        if (index != 1) {
            box_one.setBackgroundResource(R.mipmap.box_uncheck);
        }
        if (index != 2) {
            box_two.setBackgroundResource(R.mipmap.box_uncheck);
        }
        if (index != 3) {
            box_three.setBackgroundResource(R.mipmap.box_uncheck);
        }
        if (index != 4) {
            box_four.setBackgroundResource(R.mipmap.box_uncheck);
        }
        if (index != 5) {
            box_five.setBackgroundResource(R.mipmap.box_uncheck);
        }
        if (index != 6) {
            box_six.setBackgroundResource(R.mipmap.box_uncheck);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.check_one_lin:
                chose_what = 1;
                box_one.setBackgroundResource(resoure_id);
                type_id = check_one;
                changeCheckBox(1);
                break;
            case R.id.check_two_lin:
                chose_what = 2;
                type_id = check_two;
                box_two.setBackgroundResource(resoure_id);
                changeCheckBox(2);
                break;
            case R.id.check_three_lin:
                chose_what = 3;
                type_id = check_three;
                box_three.setBackgroundResource(resoure_id);
                changeCheckBox(3);
                break;
            case R.id.check_four_lin:
                chose_what = 4;
                type_id = check_four;
                box_four.setBackgroundResource(resoure_id);
                changeCheckBox(4);
                break;
            case R.id.check_five_lin:
                chose_what = 5;
                type_id = check_five;
                box_five.setBackgroundResource(resoure_id);
                changeCheckBox(5);
                break;
            case R.id.check_six_lin:
                chose_what = 6;
                type_id = check_six;
                box_six.setBackgroundResource(resoure_id);
                changeCheckBox(6);
                break;
            case R.id.upload_pic_iv:
                getPermission();
                break;
            case R.id.commit_btn:
                commit_btn.setClickable(false);
                commit();
                break;
            default:
                break;
        }
    }

    private void commit() {
        if (TextUtils.isEmpty(car_number_edit.getText().toString().trim())) {
            showToast("请输入车牌号");
            commit_btn.setClickable(true);
        } else if (chose_what == 0) {
            showToast("请选择问题类型");
            commit_btn.setClickable(true);
        } else if (TextUtils.isEmpty(detail_edit.getText().toString().trim())) {
            showToast("请填写问题描述");
            commit_btn.setClickable(true);
        } else {
            showProgressDlg();
            String licence = car_number_edit.getText().toString().trim();
            String problem_detail = detail_edit.getText().toString().trim();
            Observable<CodeResultV3<Object>> ob = shareService.problemFeedbackV3(user_id, licence, problem_detail, problemImagePath, problemImagePath_key, type_id);
            ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
                @Override
                protected void onHandleSuccess(Object o) {
                    removeProgressDlg();
                    showToast("反馈成功");
                    finish();
                }

                @Override
                protected void onHandleError(String msg, CodeResultV3<Object> value) {
                    super.onHandleError(msg, value);
                    commit_btn.setClickable(true);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    commit_btn.setClickable(true);
                }
            });
        }
    }


    private void goCamera() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        if (Build.VERSION.SDK_INT > 23) {
            FileUtils.startActionCapture(this, new File(path + fileName), 12);

        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
            startActivityForResult(intent, 12);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                Uri u = Uri.parse(MediaStore.Images.Media
                        .insertImage(getContentResolver(),
                                path + fileName, null, null));
                Bitmap bitmap = ImageTool.decodeUriAsBitmap(u,this);

                if (bitmap != null) {
                    String imgPath = ImageTool.amendRotatePhoto(path + fileName, bitmap, true);
                    Luban.with(this)
                            .load(imgPath)
                            .ignoreBy(100)
                            .setTargetDir(ImageTool.getPath())
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(File file) {
                                    uploadImage(file.getAbsolutePath());

                                }

                                @Override
                                public void onError(Throwable e) {
                                }
                            }).launch();
                    deleteImage();
                    bitmap.recycle();
                    System.gc();
                    Glide.with(this).load(imgPath).into(upload_pic_iv);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(String url) {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(url));
        Observable<CodeResultV3<ImageBeanV3>> observable = service.uploadImageV3("feedback", requestFile);
        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBean) {
                removeProgressDlg();
                problemImagePath = imageBean.getSave_path();
                problemImagePath_key = imageBean.getView_path();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<ImageBeanV3> value) {
                super.onHandleError(msg, value);

            }
        });
    }


    public void deleteImage() {
        File file = new File(path + fileName);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = ProblemFeedbackActivity.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + fileName + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        ProblemFeedbackActivity.this.sendBroadcast(intent);
    }




    public void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(ProblemFeedbackActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            android.Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .request();
        } else {
            goCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        goCamera();
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        showAlertDlg("使用拍照功能，请开启拍照和读取手机储存权限", R.string.set, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        }, R.string.refuse, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();
            }
        }, true);
    }
}
