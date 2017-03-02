package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 审核详情页
 */
public class VerifyDetailActivity extends BaseActivity {

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJCVideoPlayerStandard;
    @Inject
    Activity mActivity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_detail;
    }

    @Override
    public void initInjector() {
    mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("施工方");
        mJCVideoPlayerStandard.setUp(
                "http://video.jiecao.fm/8/17/%E6%8A%AB%E8%90%A8.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "aa");

        Glide.with(mActivity)
                .load("http://gb.cri.cn/mmsource/images/2010/07/19/ec100719005.jpg")
                .into(mJCVideoPlayerStandard.thumbImageView);
    }
}
