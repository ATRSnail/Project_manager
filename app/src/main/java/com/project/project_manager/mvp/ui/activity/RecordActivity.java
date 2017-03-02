package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;

import javax.inject.Inject;

//import com.project.project_manager.weight.RecordFragmentHolder;
@Deprecated
public class RecordActivity extends BaseActivity {

//    @BindView(R.id.view_camera_preview)
//    CameraPreviewView mViewCameraPreview;
//    @BindView(R.id.btn_record)
//    CircleBackgroundTextView mBtnRecord;
//    private RecordFragmentHolder mRecordFragmentHolder;

    private int screenWidth = 480;
    private int screenHeight = 800;

    @Inject
    Activity mActivity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    public void initInjector() {
       mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //Find screen dimensions
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

//        mRecordFragmentHolder = new RecordFragmentHolder(mActivity, new RecordFragmentHolder.OnRecordListener() {
//            @Override
//            public void onEnd(String videoPath) {
//                UT.show("视频存放在：" + videoPath);
//                //  IntentUtil.openFile(mActivity, new File(videoPath));
//            }
//        });
//        mRecordFragmentHolder.setOutputWidthAndHeight(screenWidth,screenHeight);
//        if (!mRecordFragmentHolder.init(mViewCameraPreview, mBtnRecord, null, 15000)){
//            mActivity.finish();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mRecordFragmentHolder.onPause();
        mActivity.finish();
    }
}
