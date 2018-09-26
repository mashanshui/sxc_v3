package com.sxcapp.www.UserCenter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * 实名认证界面
 * Created by wenleitao on 2017/7/27.
 */

public class AutonymActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.idcard_front_re)
    RelativeLayout idcard_front_re;
    @BindView(R.id.idcard_front_iv)
    ImageView idcard_front_iv;
    @BindView(R.id.drive_card_front_iv)
    ImageView drive_card_front_iv;
    @BindView(R.id.drive_card_appendix_front_iv)
    ImageView drive_card_appendix_front_iv;
    @BindView(R.id.authentication_iv)
    ImageView authentication_iv;
    @BindView(R.id.authentication_tv)
    TextView authentication_tv;
    @BindView(R.id.authentication_lin)
    LinearLayout authentication_lin;
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.iv03)
    ImageView iv03;
    @BindView(R.id.iv04)
    ImageView iv04;
    @BindView(R.id.drive_card_front_re)
    RelativeLayout drive_card_front_re;
    @BindView(R.id.drive_card_appendix_front_re)
    RelativeLayout drive_card_appendix_front_re;
    @BindView(R.id.hand_idcard_re)
    RelativeLayout hand_idcard_re;
    @BindView(R.id.upload_btn)
    Button upload_btn;
    @BindView(R.id.go_authen_btn)
    Button go_authen_btn;
    @BindView(R.id.hand_idcard_iv)
    ImageView hand_idcard_iv;

    private Dialog dialog;
    private int ID_CARD_CODE = 11;
    private int DRIVE_CARD_CODE = 12;
    private int APPENDIX_CODE = 13;
    private int HAND_CARD_CODE = 14;
    private int REQUEST_CODE;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private String fileName;
    private FileUploadService service;
    private String imagePath01;
    private String imagePath02;
    private String imagePath03;
    private String imagePath04;
    private List<String> imageList = new ArrayList<>();
    private List<String> uploadImageList = new ArrayList<>();
    private List<String> uploadImageKeyList = new ArrayList<>();
    private int index = 0;
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private int phoneWidth;
    private int phoneHeight;
    private UserCenterService userCenterService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonym);
        setTopbarLeftWhiteBackBtn();
        ButterKnife.bind(this);
        setTopBarTitle("实名认证", null);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        userCenterService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        initViews();
        Observable<CodeResultV3<Object>> observer = userCenterService.doAuthenCheckV3(SharedData.getInstance().getString(SharedData._user_id));
        showProgressDlg();
        observer.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                authentication_tv.setVisibility(View.VISIBLE);
                authentication_iv.setBackgroundResource(R.mipmap.authentication_pass);
                authentication_tv.setText("恭喜，实名认证成功！");
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                removeProgressDlg();
                if ("701".equals(value.getCode())) {
                    authentication_lin.setVisibility(View.VISIBLE);
                } else if ("702".equals(value.getCode())) {
                    authentication_iv.setVisibility(View.VISIBLE);
                    authentication_tv.setVisibility(View.VISIBLE);
                    authentication_iv.setBackgroundResource(R.mipmap.authenticationing);
                    authentication_tv.setText("客服正在努力为您审核实名认证资料");
                } else if ("703".equals(value.getCode())) {
                    authentication_iv.setVisibility(View.VISIBLE);
                    authentication_tv.setVisibility(View.VISIBLE);
                    authentication_iv.setBackgroundResource(R.mipmap.authentication_fail);
                    authentication_tv.setText("实名认证失败，再试一试" + "\n" + "客服电话:400-007-7000");
                    go_authen_btn.setVisibility(View.VISIBLE);
                } else if ("704".equals(value.getCode())) {
                    authentication_iv.setVisibility(View.VISIBLE);
                    authentication_tv.setVisibility(View.VISIBLE);
                    authentication_iv.setBackgroundResource(R.mipmap.authentication_fail);
                    authentication_tv.setText("驾照已过期,请重新上传认证");
                    go_authen_btn.setVisibility(View.VISIBLE);
                } else {
                    showToast(msg);
                }
            }
        });


    }

    private void initViews() {
        upload_btn.setOnClickListener(this);
        go_authen_btn.setOnClickListener(this);
        idcard_front_re.setOnClickListener(this);
        drive_card_front_re.setOnClickListener(this);
        drive_card_appendix_front_re.setOnClickListener(this);
        hand_idcard_re.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idcard_front_re:
                REQUEST_CODE = ID_CARD_CODE;
                getPermission();
                break;
            case R.id.drive_card_front_re:
                REQUEST_CODE = DRIVE_CARD_CODE;
                getPermission();
                break;
            case R.id.drive_card_appendix_front_re:
                REQUEST_CODE = APPENDIX_CODE;
                getPermission();
                break;
            case R.id.hand_idcard_re:
                REQUEST_CODE = HAND_CARD_CODE;
                getPermission();
                break;
            case R.id.pop_camera_re:
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                if (Build.VERSION.SDK_INT > 23) {
                    FileUtils.startActionCapture(this, new File(path + fileName), REQUEST_CODE);
                    dialog.dismiss();
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
                    startActivityForResult(intent, REQUEST_CODE);
                    dialog.dismiss();
                }
                break;
            case R.id.pop_album_re:
                fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                goAlbums();
                dialog.dismiss();
                break;
            case R.id.pop_cancel_relative:
                dialog.dismiss();
                break;
            case R.id.upload_btn:
                if (TextUtils.isEmpty(imagePath01) || TextUtils.isEmpty(imagePath02) || TextUtils.isEmpty(imagePath03) || TextUtils.isEmpty(imagePath04)) {
                    showToast("请上传所有图片");
                } else {
                    imageList.add(imagePath01);
                    imageList.add(imagePath02);
                    imageList.add(imagePath03);
                    imageList.add(imagePath04);
                    upload_btn.setClickable(false);
                    uploadImage();
                }
                break;
            case R.id.go_authen_btn:
                authentication_iv.setVisibility(View.GONE);
                authentication_tv.setVisibility(View.GONE);
                go_authen_btn.setVisibility(View.GONE);
                authentication_lin.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }


    }


    /**
     * 调用相册
     */
    private void goAlbums() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);
        startActivityForResult(intent, PHOTOZOOM);
    }

    public void uploadImage() {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(imageList.get(index)));
        Observable<CodeResultV3<ImageBeanV3>> observable = service.uploadImageV3("customerAuthentication",
                requestFile);

        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBean) {
                deleteImageByPath(imageList.get(index));
                uploadImageList.add(imageBean.getSave_path());
                uploadImageKeyList.add(imageBean.getView_path());
                if (uploadImageList.size() < 4) {
                    index++;
                    uploadImage();
                } else {
                    index = 0;
                    submitInfo();
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<ImageBeanV3> value) {
                super.onHandleError(msg, value);
                index = 0;
                upload_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                upload_btn.setClickable(true);
            }
        });
    }

    public void submitInfo() {
        UserCenterService userCenterService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", SharedData.getInstance().getString(SharedData._user_id));
        map.put("face_card_image", uploadImageList.get(0));
        map.put("face_drive_image", uploadImageList.get(1));
        map.put("back_drive_image", uploadImageList.get(2));
        map.put("hand_card_image", uploadImageList.get(3));
        Observable<CodeResultV3<Object>> resultOb = userCenterService.doauthenticationV3(map);
        resultOb.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("上传成功");
                finish();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                upload_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                upload_btn.setClickable(true);
            }
        });


    }

    public void showTakePhotoDialog(int requestCode) {
        REQUEST_CODE = requestCode;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                        Luban.with(this)
                                .load(path+fileName)
                                .ignoreBy(100)
                                .setTargetDir(ImageTool.getPath())
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        String image_temp = file.getAbsolutePath();

                                        if (REQUEST_CODE == ID_CARD_CODE) {
                                            imagePath01 = image_temp;
                                            Glide.with(AutonymActivity.this).load(image_temp).into(idcard_front_iv);
                                            iv01.setVisibility(View.GONE);
                                        } else if (REQUEST_CODE == DRIVE_CARD_CODE) {
                                            imagePath02 = image_temp;
                                            Glide.with(AutonymActivity.this).load(image_temp).into(drive_card_front_iv);
                                            iv02.setVisibility(View.GONE);
                                        } else if (REQUEST_CODE == APPENDIX_CODE) {
                                            imagePath03 = image_temp;
                                            Glide.with(AutonymActivity.this).load(image_temp).into(drive_card_appendix_front_iv);
                                            iv03.setVisibility(View.GONE);
                                        } else if (REQUEST_CODE == HAND_CARD_CODE) {
                                            imagePath04 = image_temp;
                                            Glide.with(AutonymActivity.this).load(image_temp).into(hand_idcard_iv);
                                            iv04.setVisibility(View.GONE);
                                        }
                                        deleteImage();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                }).launch();


            }
        } else if (requestCode == PHOTOZOOM) {
            if (data != null) {

                Uri uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, proj, null, null, null);

                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);

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
                                String image_temp = file.getAbsolutePath();

                                if (REQUEST_CODE == ID_CARD_CODE) {
                                    imagePath01 = image_temp;
                                    Glide.with(AutonymActivity.this).load(image_temp).into(idcard_front_iv);
                                    iv01.setVisibility(View.GONE);
                                } else if (REQUEST_CODE == DRIVE_CARD_CODE) {
                                    imagePath02 = image_temp;
                                    Glide.with(AutonymActivity.this).load(image_temp).into(drive_card_front_iv);
                                    iv02.setVisibility(View.GONE);
                                } else if (REQUEST_CODE == APPENDIX_CODE) {
                                    imagePath03 = image_temp;
                                    Glide.with(AutonymActivity.this).load(image_temp).into(drive_card_appendix_front_iv);
                                    iv03.setVisibility(View.GONE);
                                } else if (REQUEST_CODE == HAND_CARD_CODE) {
                                    imagePath04 = image_temp;
                                    Glide.with(AutonymActivity.this).load(image_temp).into(hand_idcard_iv);
                                    iv04.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();

            }
        }

    }


    public void deleteImage() {
        File file = new File(path + fileName);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = AutonymActivity.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + fileName + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        AutonymActivity.this.sendBroadcast(intent);
    }

    public void deleteImageByPath(String imageUrl) {
        File file = new File(imageUrl);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = AutonymActivity.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + imageUrl + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        AutonymActivity.this.sendBroadcast(intent);
    }


    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri)) {
                return imageUri.getLastPathSegment();
            }
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void getPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(AutonymActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        } else {
            showTakePhotoDialog(REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        showTakePhotoDialog(REQUEST_CODE);
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        showAlertDlg("使用相册和拍照功能，请开启拍照和读取手机储存权限", R.string.set, new View.OnClickListener() {
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
