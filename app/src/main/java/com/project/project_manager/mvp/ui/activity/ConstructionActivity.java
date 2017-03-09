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
import com.jaiky.imagespickers.utils.GlideLoader;
import com.jaiky.imagespickers.utils.Utils;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.NodeBean;
import com.project.project_manager.mvp.entity.PvoObjectBean;
import com.project.project_manager.mvp.entity.ResourceBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.TransformUtils;
import com.project.project_manager.utils.UT;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import rx.Subscriber;

import static com.project.project_manager.common.ApiConstants.IMG_HOST;
import static com.project.project_manager.mvp.entity.NodeBean.NODE_KEY;

/**
 * 施工方
 */
public class ConstructionActivity extends BaseActivity {
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    private static final int CAPTURE_MEDIA = 368;
    private ArrayList<String> pathList = new ArrayList<>();
    @BindView(R.id.btn_video)
    Button mBtnRecord;
    @BindView(R.id.btn_take_photo)
    Button mBtnPhoto;

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJCVideoPlayerStandard;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;

//    private ImageConfig imageConfig;

    private NodeBean nodeBean;
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
        initIntentDate();
        postDataFroUrl();
    }

    private void initIntentDate() {
        nodeBean = (NodeBean) getIntent().getSerializableExtra(NODE_KEY);
    }

    private void postDataFroUrl() {

        RetrofitManager.getInstance(1).getProjectPvoObservable(nodeBean.getId() + "")
                .compose(TransformUtils.<BaseRspObj<PvoObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<PvoObjectBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj<PvoObjectBean> pvoObjectBeanBaseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + pvoObjectBeanBaseRspObj.toString());
                        if (pvoObjectBeanBaseRspObj.getHead().getRspCode().equals("0")) {
                            List<ResourceBean> resources = pvoObjectBeanBaseRspObj.getBody().getPvo().getList();
                            if (resources.size() > 0) {
                                for (ResourceBean res : resources) {
                                    if (res.getResourceType().equals("0")) {
                                        pathList.add(IMG_HOST + res.getResourceUrl());
                                    }
                                }
                            }
                      //      containerAdapter.refreshData(pathList, new GlideLoader());
                        }
                    }
                });
    }

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
                    if (containerAdapter != null) {
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
