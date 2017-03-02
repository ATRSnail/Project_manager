package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.jaiky.imagespickers.container.GridViewForScrollView;
import com.jaiky.imagespickers.container.SimpleImageAdapter;
import com.jaiky.imagespickers.utils.FileUtils;
import com.jaiky.imagespickers.utils.Utils;
import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.utils.GlideLoader;
import com.project.project_manager.utils.UT;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;

/**
 * 施工方
 */
public class ConstructionActivity extends BaseActivity {
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    private static final int CAPTURE_MEDIA = 368;
    private ArrayList<String> path = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<String>();
    public static final int REQUEST_CODE = 123;
    @BindView(R.id.btn_video)
    Button mBtnRecord;
    @BindView(R.id.btn_take_photo)
    Button mBtnPhoto;

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJCVideoPlayerStandard;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;

//    private ImageConfig imageConfig;


    @Inject
    Activity mActivity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_construction;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("施工方");

        Uri videoUri = Uri.parse(Environment.getExternalStorageDirectory()
                .getPath() + "/cn.itguy.recordvideodemo/Media/VID_1484816999264.mp4");
        mJCVideoPlayerStandard.setUp(
                "file://" + videoUri + "", JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "aa");

        if (Build.VERSION.SDK_INT > 15) {
            askForPermissions(new String[]{
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.RECORD_AUDIO,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSIONS);
        } else {
            enableCamera();
        }
    }

//    @OnClick(R.id.btn_video)
//    public void onClick(View view){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            /**
//             * 请求权限是一个异步任务  不是立即请求就能得到结果 在结果回调中返回
//             */
//            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.RECORD_AUDIO}, CAMERA_OK);
//        } else {
//            startActivity(new Intent(this,RecordActivity.class));
//        }
//    }

    protected void enableCamera() {
        mBtnRecord.setOnClickListener(onClickListener);
        mBtnPhoto.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_video:
                    AnncaConfiguration.Builder videoLimited = new AnncaConfiguration.Builder(mActivity, CAPTURE_MEDIA);
                    videoLimited.setMediaAction(AnncaConfiguration.MEDIA_ACTION_VIDEO);
                    videoLimited.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_AUTO);
                    videoLimited.setVideoFileSize(5 * 1024 * 1024);
                    videoLimited.setMinimumVideoDuration(5 * 60 * 1000);
                    new Annca(videoLimited.build()).launchCamera();
                    break;
                case R.id.btn_take_photo:
                    setContainer(llContainer, 3, true);
//                    AnncaConfiguration.Builder photo = new AnncaConfiguration.Builder(mActivity, CAPTURE_MEDIA);
//                    photo.setMediaAction(AnncaConfiguration.MEDIA_ACTION_PHOTO);
//                    photo.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_LOW);
//                    new Annca(photo.build()).launchCamera();
//                    imageConfig = new ImageConfig.Builder(
//                            new GlideLoader())
//                            .steepToolBarColor(getResources().getColor(R.color.titleBlue))
//                            .titleBgColor(getResources().getColor(R.color.titleBlue))
//                            .titleSubmitTextColor(getResources().getColor(R.color.white))
//                            .titleTextColor(getResources().getColor(R.color.white))
//                            // 开启多选   （默认为多选）
//                            .mutiSelect()
//                            // 多选时的最大数量   （默认 9 张）
//                            .mutiSelectMaxSize(9)
//                            //设置图片显示容器，参数：、（容器，每行显示数量，是否可删除）
//                            .setContainer(llContainer, 4, true)
//                            // 已选择的图片路径
//                            .pathList(path)
//                            // 拍照后存放的图片路径（默认 /temp/picture）
//                            .filePath("/temp")
//                            // 开启拍照功能 （默认关闭）
//                            .showCamera()
//                            .requestCode(REQUEST_CODE)
//                            .build();
//                    ImageSelector.open(ConstructionActivity.this, imageConfig);
                    showCameraAction();
                    break;
            }
        }
    };
    private File tempFile;
    private static final int REQUEST_CAMERA = 100;

    private void showCameraAction() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            tempFile = FileUtils.createTmpFile(mActivity, "/temp");
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            UT.show("没有系统相机");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) return;
        enableCamera();
    }

    protected final void askForPermissions(String[] permissions, int requestCode) {
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), requestCode);
        } else enableCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            UT.show("Media captured.");
        }

        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (tempFile != null) {
                    pathList.add(tempFile.getAbsolutePath());
                    //改变gridview的内容
                    if (containerAdapter!= null) {
                        containerAdapter.refreshData(pathList, new GlideLoader());
                    }
                }
            } else {
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
    }
    private SimpleImageAdapter containerAdapter;
    public void setContainer(ViewGroup container, int rowImageCount, boolean isDelete) {
        if (container.getChildCount() == 0) {
            //新建一个GridView
            GridViewForScrollView gvView = new GridViewForScrollView(container.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            gvView.setLayoutParams(layoutParams);

            if (isDelete) {
                //间距3dp
                gvView.setHorizontalSpacing(Utils.dip2px(container.getContext(), 3));
                gvView.setVerticalSpacing(Utils.dip2px(container.getContext(), 3));
            } else {
                //间距10dp
                gvView.setHorizontalSpacing(Utils.dip2px(container.getContext(), 10));
                gvView.setVerticalSpacing(Utils.dip2px(container.getContext(), 10));
            }
            gvView.setNumColumns(rowImageCount);
            gvView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

            //设置适配器，暂时数据为控
            containerAdapter = new SimpleImageAdapter(container, isDelete, rowImageCount);
            gvView.setAdapter(containerAdapter);
            container.addView(gvView);
        }
        else {
            GridViewForScrollView gvView = (GridViewForScrollView) container.getChildAt(0);
            containerAdapter = (SimpleImageAdapter) gvView.getAdapter();
        }
    }
}
