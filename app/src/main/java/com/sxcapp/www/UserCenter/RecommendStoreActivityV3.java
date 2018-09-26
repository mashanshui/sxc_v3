package com.sxcapp.www.UserCenter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.CircleTransformt;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.Share.LuxuryShare.UploadConstractActivityV3;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 * Created by wenleitao on 2018/5/29.
 */

public class RecommendStoreActivityV3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.to_map_re)
    RelativeLayout to_map_re;
    @BindView(R.id.input_phone_re)
    RelativeLayout input_phone_re;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.content_re)
    RelativeLayout content_re;
    @BindView(R.id.content_edit)
    EditText content_edit;
    @BindView(R.id.take_photo_iv01)
    ImageView take_photo_iv01;
    @BindView(R.id.take_photo_iv02)
    ImageView take_photo_iv02;
    @BindView(R.id.take_photo_iv03)
    ImageView take_photo_iv03;
    @BindView(R.id.delete_iv01)
    ImageView delete_iv01;
    @BindView(R.id.delete_iv02)
    ImageView delete_iv02;
    @BindView(R.id.delete_iv03)
    ImageView delete_iv03;
    @BindView(R.id.address_tv)
    TextView address_tv;
    @BindView(R.id.commit_btn)
    Button commit_btn;
    @BindView(R.id.count_tv)
    TextView count_tv;
    private static final int TO_MAP_CODE = 23;
    private static final int PIC01_REQUEST = 15, PIC02_REQUEST = 16, PIC03_REQUEST = 17;
    private int CAMERA_REQUEST;
    private Dialog dialog;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private String fileName;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int FROM_ALBUM = 55;
    private List<String> imageList, net_image_list, net_image_key_list;
    private String imgPath, address, phone, content_str, img_str01, img_str02, img_str03, img_str01_key, img_str02_key, img_str03_key;
    private double lat, lng;
    private FileUploadService service;
    private UserCenterService userCenterService;
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommand_store_v3);
        ButterKnife.bind(this);
        setStatusView(R.color.top_bar_red);
        setTopBarTitle("推荐建点", null);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        imageList = new ArrayList<>();
        net_image_key_list = new ArrayList<>();
        net_image_list = new ArrayList<>();
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        userCenterService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        bindClick();
    }

    private void bindClick() {
        to_map_re.setOnClickListener(this);
        input_phone_re.setOnClickListener(this);
        content_re.setOnClickListener(this);
        take_photo_iv01.setOnClickListener(this);
        take_photo_iv02.setOnClickListener(this);
        take_photo_iv03.setOnClickListener(this);
        delete_iv01.setOnClickListener(this);
        delete_iv02.setOnClickListener(this);
        delete_iv03.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
        content_re.setOnClickListener(this);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                count_tv.setText(s.length() + "/160");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        content_edit.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_map_re:
                startActivityForResult(new Intent(this, RecommendStoreMapActivityV3.class), TO_MAP_CODE);
                break;
            case R.id.input_phone_re:
                phone_edit.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(phone_edit, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.content_re:
                content_edit.requestFocus();
                InputMethodManager imm01 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm01.showSoftInput(content_edit, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.take_photo_iv01:
                CAMERA_REQUEST = PIC01_REQUEST;
                getPermission();
                break;
            case R.id.take_photo_iv02:
                CAMERA_REQUEST = PIC02_REQUEST;
                getPermission();
                break;
            case R.id.take_photo_iv03:
                CAMERA_REQUEST = PIC03_REQUEST;
                getPermission();
                break;
            case R.id.pop_camera_re:
                goCamera();
                dialog.dismiss();
                break;
            case R.id.pop_album_re:
                goAlbums();
                dialog.dismiss();
                break;
            case R.id.pop_cancel_relative:
                dialog.dismiss();
                break;
            case R.id.delete_iv01:
                imageList.remove(0);
                setImageShow();
                break;
            case R.id.delete_iv02:
                imageList.remove(1);
                setImageShow();
                break;
            case R.id.delete_iv03:
                imageList.remove(2);
                setImageShow();
                break;
            case R.id.commit_btn:
                if (TextUtils.isEmpty(address)) {
                    showToast("请选择推荐位置");
                } else {
                    if (TextUtils.isEmpty(phone_edit.getText().toString().trim())) {
                        showToast("请输入电话号码");
                    } else {
                        if (AndroidTool.isMobileNO(phone_edit.getText().toString().trim())) {
                            phone = phone_edit.getText().toString().trim();
                            if (TextUtils.isEmpty(content_edit.getText().toString().trim())) {
                                showToast("请输入描述内容");
                            } else {
                                content_str = content_edit.getText().toString().trim();
                                if (imageList.size() == 0) {
                                    showToast("请拍摄至少一张照片");
                                } else {
                                    commit_btn.setClickable(false);
                                    uploadImage();
                                }
                            }
                        } else {
                            showToast("请输入正确的电话号码");
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public void uploadImage() {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(imageList.get(index)));
        Observable<CodeResultV3<ImageBeanV3>> observable = service.uploadImageV3("recommend", requestFile);
        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBean) {

                net_image_list.add(imageBean.getSave_path());
                net_image_key_list.add(imageBean.getView_path());
                index++;
                if (index == imageList.size()) {
                    uploadAddress();
                } else {
                    uploadImage();
                }

            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<ImageBeanV3> value) {
                super.onHandleError(msg, value);
                commit_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                removeProgressDlg();
                super.onError(e);
                commit_btn.setClickable(true);
                showToast("上传失败");
            }
        });
    }

    private void uploadAddress() {

        StringBuffer imaBuffer = new StringBuffer();
        for (int i = 0; i < net_image_list.size(); i++) {
            if (i == net_image_list.size() - 1) {
                imaBuffer.append(net_image_list.get(i));
            } else {
                imaBuffer.append(net_image_list.get(i) + ",");
            }
        }
        StringBuffer imaBuffer_key = new StringBuffer();
        for (int i = 0; i < net_image_key_list.size(); i++) {
            if (i == net_image_key_list.size() - 1) {
                imaBuffer_key.append(net_image_key_list.get(i));
            } else {
                imaBuffer_key.append(net_image_key_list.get(i) + ",");
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("customer_id", SharedData.getInstance().getString(SharedData._user_id));
        map.put("latitude", lat + "");
        map.put("longitude", lng + "");
        map.put("scene_image", imaBuffer.toString());
        map.put("scene_image_view", imaBuffer_key.toString());
        map.put("nearby_area", address);
        map.put("remark", content_str);
        Observable<CodeResultV3<Object>> ob = userCenterService.uploadRecommendInfoV3(map);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                showToast("推荐成功");
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


    private void setImageShow() {
        switch (imageList.size()) {
            case 0:
                take_photo_iv01.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.mipmap.reco_takephoto_icon_v3).into(take_photo_iv01);
                delete_iv01.setVisibility(View.GONE);
                take_photo_iv02.setVisibility(View.GONE);
                delete_iv02.setVisibility(View.GONE);
                take_photo_iv03.setVisibility(View.GONE);
                delete_iv03.setVisibility(View.GONE);
                break;
            case 1:
                take_photo_iv01.setVisibility(View.VISIBLE);
                delete_iv01.setVisibility(View.VISIBLE);
                take_photo_iv02.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageList.get(0)).apply(new RequestOptions().centerCrop()).into(take_photo_iv01);
                Glide.with(this).load(R.mipmap.reco_takephoto_icon_v3).into(take_photo_iv02);
                delete_iv02.setVisibility(View.GONE);
                take_photo_iv03.setVisibility(View.GONE);
                delete_iv03.setVisibility(View.GONE);
                break;
            case 2:
                take_photo_iv01.setVisibility(View.VISIBLE);
                delete_iv01.setVisibility(View.VISIBLE);
                take_photo_iv02.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageList.get(0)).apply(new RequestOptions().centerCrop()).into(take_photo_iv01);
                Glide.with(this).load(imageList.get(1)).apply(new RequestOptions().centerCrop()).into(take_photo_iv02);
                Glide.with(this).load(R.mipmap.reco_takephoto_icon_v3).into(take_photo_iv03);
                delete_iv02.setVisibility(View.VISIBLE);
                take_photo_iv03.setVisibility(View.VISIBLE);
                delete_iv03.setVisibility(View.GONE);
                break;
            case 3:
                take_photo_iv01.setVisibility(View.VISIBLE);
                delete_iv01.setVisibility(View.VISIBLE);
                take_photo_iv02.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageList.get(0)).apply(new RequestOptions().centerCrop()).into(take_photo_iv01);
                Glide.with(this).load(imageList.get(1)).apply(new RequestOptions().centerCrop()).into(take_photo_iv02);
                Glide.with(this).load(imageList.get(2)).apply(new RequestOptions().centerCrop()).into(take_photo_iv03);
                delete_iv02.setVisibility(View.VISIBLE);
                take_photo_iv03.setVisibility(View.VISIBLE);
                delete_iv03.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }


    public void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(RecommendStoreActivityV3.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .request();
        } else {
            showTakePhotoDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        showTakePhotoDialog();
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

    public void showTakePhotoDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_photo, null);
        RelativeLayout pop_camera_re = (RelativeLayout) view.findViewById(R.id.pop_camera_re);
        RelativeLayout pop_album_re = (RelativeLayout) view.findViewById(R.id.pop_album_re);
        RelativeLayout pop_cancel_re = (RelativeLayout) view.findViewById(R.id.pop_cancel_relative);
        pop_camera_re.setOnClickListener(this);
        pop_album_re.setOnClickListener(this);
        pop_cancel_re.setOnClickListener(this);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.mypopwindow_anim_style);
        dialog.show();
    }

    private void goCamera() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        if (Build.VERSION.SDK_INT > 23) {
            FileUtils.startActionCapture(this, new File(path + fileName), CAMERA_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    /**
     * 调用相册
     */
    private void goAlbums() {
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);
        startActivityForResult(intent, FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Luban.with(this)
                        .load(path + fileName)
                        .ignoreBy(100)
                        .setTargetDir(ImageTool.getPath())
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                imgPath = file.getAbsolutePath();
                                imageList.add(imgPath);
                                setImageShow();
                                ImageTool.deleteImage(path + fileName, RecommendStoreActivityV3.this);

                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();

            }
        } else if (requestCode == FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);

                    // 按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    final String path = cursor.getString(column_index);
                    Luban.with(this)
                            .load(path)
                            .ignoreBy(100)
                            .setTargetDir(ImageTool.getPath())
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(File file) {
                                    imgPath = file.getAbsolutePath();
                                    imageList.add(imgPath);
                                    setImageShow();
                                    ImageTool.deleteImage(path + fileName, RecommendStoreActivityV3.this);

                                }

                                @Override
                                public void onError(Throwable e) {
                                }
                            }).launch();

                }
            }
        } else if (requestCode == TO_MAP_CODE) {
            if (data != null) {
                address = data.getStringExtra("address");
                address_tv.setText(address);
                lat = data.getDoubleExtra("lat", 0);
                lng = data.getDoubleExtra("lng", 0);
            }
        }
    }

}
