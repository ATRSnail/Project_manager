package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.container.GridViewForScrollView;
import com.jaiky.imagespickers.container.SimpleImageAdapter;
import com.jaiky.imagespickers.utils.FileUtils;
import com.jaiky.imagespickers.utils.GlideLoader;
import com.jaiky.imagespickers.utils.Utils;
import com.project.project_manager.R;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.TaskDetailsBean;
import com.project.project_manager.mvp.entity.ResourceBean;
import com.project.project_manager.mvp.entity.TaskBean;
import com.project.project_manager.mvp.entity.TaskDetailsObjBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.event.ProjectRefEvent;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.ScreenUtils;
import com.project.project_manager.utils.TransformUtils;
import com.project.project_manager.utils.UT;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import rx.Subscriber;

import static com.project.project_manager.common.ApiConstants.IMG_HOST;
import static com.project.project_manager.common.ProStatusType.TYPE_ALLOCAD;
import static com.project.project_manager.common.ProStatusType.TYPE_APPLY;
import static com.project.project_manager.common.ProStatusType.TYPE_LOCKED;
import static com.project.project_manager.common.ProStatusType.TYPE_NO_COMPLETE;
import static com.project.project_manager.common.ProStatusType.TYPE_REJECT;
import static com.project.project_manager.common.ProStatusType.TYPE_UNAUDITED;
import static com.project.project_manager.mvp.entity.TaskBean.TASK_KEY;

/**
 * 审核详情页
 */
public class ConstructionTActivity extends BaseActivity implements View.OnClickListener, SimpleImageAdapter.DelImgListener {
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    private static final int CAPTURE_MEDIA = 368;
    private TaskBean taskBean;
    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJCVideoPlayerStandard;
    @Inject
    Activity mActivity;
    @BindView(R.id.tv_project_name)
    TextView tv_project_name;
    @BindView(R.id.tv_cate)
    TextView tv_cate;
    @BindView(R.id.tv_link)
    TextView tv_link;
    @BindView(R.id.tv_point)
    TextView tv_point;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.btn_video)
    Button btn_video;
    @BindView(R.id.btn_take_photo)
    Button btn_take_photo;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.tv_option)
    TextView tv_option;
    @BindView(R.id.ll_btn_contain)
    LinearLayout ll_btn_contain;
    @BindView(R.id.tv_demand)
    TextView tv_demand;
    @BindView(R.id.fl_video)
    FrameLayout fl_video;
    @BindView(R.id.img_delete)
    ImageView img_delete;
    @BindView(R.id.tv_position)
    TextView tv_position;
    @BindView(R.id.tv_reallyPosition)
    TextView tv_reallyPosition;

    private TaskDetailsBean taskDetails;
    private ArrayList<String> imgPathList = new ArrayList<>();
    private ArrayList<String> vidPathList = new ArrayList<>();
    private SimpleImageAdapter containerAdapter;
    private String videoFile;
    private File tempFile;
    private static final int REQUEST_CAMERA = 100;
    private SweetAlertDialog dialog;
    protected AMapLocation location;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();


    @Override
    public int getLayoutId() {
        return R.layout.activity_construction_t;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        dialog = initDialog("正在定位...");
        initIntentDate();
        setCustomTitle(taskBean.getNodeName());
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
        initLocation();
        initDate();
        setContainer(llContainer, 3, isCanDelete(taskBean.getStatus()) ? true : false);
        img_delete.setVisibility(isCanDelete(taskBean.getStatus()) ? View.VISIBLE : View.GONE);

        startLocation();
    }

    /**
     * 是否可以删除
     *
     * @param status
     * @return
     */
    private boolean isCanDelete(String status) {
        return status.equals(TYPE_LOCKED) || status.equals(TYPE_REJECT);
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

    protected void enableCamera() {
        btn_video.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        img_delete.setOnClickListener(this);
    }

    private void initIntentDate() {
        taskBean = (TaskBean) getIntent().getSerializableExtra(TASK_KEY);
    }

    private void initDate() {
        RetrofitManager.getInstance(1).getTaskDetails(taskBean.getProjectTaskId() + "", UsrMgr.getUseId(), taskBean.getStatus(), UsrMgr.getUseType())
                .compose(TransformUtils.<BaseRspObj<TaskDetailsObjBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<TaskDetailsObjBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj<TaskDetailsObjBean> baseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + baseRspObj.toString());
                        UT.show(baseRspObj.getHead().getRspMsg());
                        if (baseRspObj.getBody() == null) {
                            finish();
                        }
                        taskDetails = baseRspObj.getBody().getTaskDetails();
                        List<ResourceBean> resources = taskDetails.getList();
                        if (resources != null && resources.size() > 0) {
                            for (ResourceBean res : resources) {
                                if (res.getResourceType().equals("0")) {
                                    imgPathList.add(IMG_HOST + res.getResourceUrl());
                                } else if (res.getResourceType().equals("1")) {
                                    vidPathList.add(IMG_HOST + res.getResourceUrl());
                                }
                            }
                        }
                        initDateToView(taskDetails);
                    }
                });
    }

    /**
     * 获取数据成功，填充数据
     *
     * @param ta
     */
    private void initDateToView(TaskDetailsBean ta) {
        KLog.a("pvoObjectBeanBaseRspObj--->" + ta.toString());
        tv_project_name.setText(ta.getProjectName());
        tv_cate.setText(setCate(ta.getCompanyType()));
        tv_link.setText(ta.getTaskDictionary().getName());
        tv_point.setText(ta.getNodeName());
        tv_company_name.setText(ta.getCompanyName());
        tv_people.setText(ta.getSysUser().getNiceName());
        tv_option.setText("审核意见:" + ta.getOpinion());
        tv_demand.setText(setDemand(ta.getTaskType()));
        tv_position.setText(ta.getPosition());
        tv_reallyPosition.setText(ta.getReallyPosition());
        initImgsView(imgPathList);
        initVideoView(vidPathList);
        initStatus(taskBean.getStatus());
        setDemandVisible(ta.getTaskType());
    }

    /**
     * 初始化videoView
     *
     * @param pathList
     */
    private void initVideoView(List<String> pathList) {

        if (pathList.size() <= 0) {
            fl_video.setVisibility(View.GONE);
            return;
        }
        fl_video.setVisibility(View.VISIBLE);
        mJCVideoPlayerStandard.setUp(
                pathList.get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "aa");

        Glide.with(mActivity)
                .load("http://gb.cri.cn/mmsource/images/2010/07/19/ec100719005.jpg")
                .into(mJCVideoPlayerStandard.thumbImageView);
    }

    /**
     * 初始化imgs
     *
     * @param pathList
     */
    private void initImgsView(List<String> pathList) {
        containerAdapter.refreshData(pathList, new GlideLoader());
    }

    /**
     * 按钮显示状态
     *
     * @param status
     */
    private void initStatus(String status) {
        if (status.equals(TYPE_NO_COMPLETE)) {//未完成
            ll_btn_contain.setVisibility(View.VISIBLE);
        }
        if (status.equals(TYPE_ALLOCAD)) {//已分派
            ll_btn_contain.setVisibility(View.GONE);
        }
        if (status.equals(TYPE_LOCKED)) {//已锁定
            ll_btn_contain.setVisibility(View.VISIBLE);
        }
        if (status.equals(TYPE_UNAUDITED)) { //未审核
            ll_btn_contain.setVisibility(View.GONE);
        }
        if (status.equals(TYPE_REJECT)) {//驳回
            ll_btn_contain.setVisibility(View.VISIBLE);
            tv_option.setVisibility(View.VISIBLE);
        }
        if (status.equals(TYPE_APPLY)) {//审核通过
            ll_btn_contain.setVisibility(View.GONE);
            tv_option.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 要求button显隐
     *
     * @param taskType
     */
    private void setDemandVisible(String taskType) {
        if (taskType.equals("0")) {
            btn_video.setVisibility(View.GONE);
            btn_take_photo.setVisibility(View.VISIBLE);
        }
        if (taskType.equals("1")) {
            btn_video.setVisibility(View.VISIBLE);
            btn_take_photo.setVisibility(View.VISIBLE);
        }
        if (taskType.equals("2")) {
            btn_video.setVisibility(View.VISIBLE);
            btn_take_photo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_video:
                AnncaConfiguration.Builder videoLimited = new AnncaConfiguration.Builder(mActivity, CAPTURE_MEDIA);
                videoLimited.setMediaAction(AnncaConfiguration.MEDIA_ACTION_VIDEO);
                videoLimited.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_AUTO);
                videoLimited.setVideoFileSize(5 * 1024 * 1024);
                videoLimited.setMinimumVideoDuration(5 * 60 * 1000);
                new Annca(videoLimited.build()).launchCamera();
                break;
            case R.id.btn_take_photo:
                showCameraAction();
                break;
            case R.id.btn_save:
                uploadFile();
                break;
            case R.id.img_delete:
                if (!vidPathList.isEmpty()) {
                    delImg(vidPathList.get(0));
                } else {
                    videoFile = null;
                    fl_video.setVisibility(View.GONE);
                }
                break;
        }
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            videoFile = data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH);
            fl_video.setVisibility(View.VISIBLE);
            mJCVideoPlayerStandard.setUp(
                    "file://" + videoFile, JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    "");
            KLog.a("file_path---->" + data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH));
            UT.show("Media captured." + data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH));
        }

        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (tempFile != null) {
                    imgPathList.add(tempFile.getAbsolutePath());
                    //改变gridview的内容
                    if (containerAdapter != null) {
                        containerAdapter.refreshData(imgPathList, new GlideLoader());
                    }
                }
            } else {
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
    }

    private void uploadFile() {

        removeHttp(imgPathList);
        removeHttp(vidPathList);

        RetrofitManager.getInstance(1).uploadImage(taskDetails.getUid(), imgPathList, videoFile, taskBean.getProjectTaskId() + "", "")
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.a("e---" + e.toString());
                    }

                    @Override
                    public void onNext(BaseRspObj baseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + baseRspObj.toString());
                        UT.show(baseRspObj.getHead().getRspMsg());
                        taskBean.setStatus(TYPE_UNAUDITED);
                        EventBus.getDefault().post(new ProjectRefEvent());
                        ConstructionTActivity.this.finish();
                    }
                });
    }


    /**
     * 清除http地址
     *
     * @param list
     */
    private void removeHttp(ArrayList<String> list) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str.contains("http")) {
                iterator.remove();
            }
        }
    }

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
            containerAdapter = new SimpleImageAdapter(container, isDelete, rowImageCount, ScreenUtils.getScreenWidth(mActivity), this);
            gvView.setAdapter(containerAdapter);
            container.addView(gvView);
        } else {
            GridViewForScrollView gvView = (GridViewForScrollView) container.getChildAt(0);
            containerAdapter = (SimpleImageAdapter) gvView.getAdapter();
        }
    }

    @Override
    public void delImg(String data) {
        KLog.a("spil---->" + splitLast(data));
        delFile(splitLast(data), data);
    }

    private String splitLast(String str) {
        String[] temp = null;
        temp = str.split("/");
        return temp[temp.length - 1];
    }


    private void delFile(String resourceUrl, final String data) {
        RetrofitManager.getInstance(1).delImage(resourceUrl)
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.a("e---" + e.toString());
                    }

                    @Override
                    public void onNext(BaseRspObj baseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + baseRspObj.toString());
                        if (data.contains("image")) {
                            imgPathList.remove(data);
                            containerAdapter.refreshData(imgPathList);
                        }
                        if (data.contains("video")) {
                            videoFile = null;
                            vidPathList.clear();
                            fl_video.setVisibility(View.GONE);
                        }

                    }
                });
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }


    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation loc) {
            dialog.dismiss();
            if (null != loc) {
                location = loc;
                KLog.a("loc---->" + loc.toStr());
                UT.show("loc--->" + loc.getAddress());
                //解析定位结果
//                String result = Utils.getLocationStr(loc);
//                tvResult.setText(result);
            } else {
                UT.show("定位失败,请重新定位");
                //tvResult.setText("定位失败，loc is null");
            }
        }
    };

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        dialog.show();
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        //设置是否使用传感器
        locationOption.setSensorEnable(true);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        String strInterval = "2000";
        if (!TextUtils.isEmpty(strInterval)) {
            try {
                // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
                locationOption.setInterval(Long.valueOf(strInterval));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        String strTimeout = "5000";
        if (!TextUtils.isEmpty(strTimeout)) {
            try {
                // 设置网络请求超时时间
                locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
