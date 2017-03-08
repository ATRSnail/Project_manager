package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.project_manager.R;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.TaskDetailsBean;
import com.project.project_manager.mvp.entity.TaskBean;
import com.project.project_manager.mvp.entity.TaskDetailsObjBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.TransformUtils;
import com.project.project_manager.utils.UT;
import com.socks.library.KLog;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Subscriber;

import static com.project.project_manager.mvp.entity.TaskBean.TASK_KEY;

/**
 * 审核详情页
 */
public class VerifyDetailActivity extends BaseActivity {

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
        initIntentDate();
        mJCVideoPlayerStandard.setUp(
                "http://video.jiecao.fm/8/17/%E6%8A%AB%E8%90%A8.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "aa");

        Glide.with(mActivity)
                .load("http://gb.cri.cn/mmsource/images/2010/07/19/ec100719005.jpg")
                .into(mJCVideoPlayerStandard.thumbImageView);
        initDate();
    }

    private void initIntentDate() {
        taskBean = (TaskBean) getIntent().getSerializableExtra(TASK_KEY);
    }

    private void initDate() {
        RetrofitManager.getInstance(1).getTaskDetails(taskBean.getProjectTaskId() + "", UsrMgr.getUseId(), taskBean.getStatus(), UsrMgr.getUseInfo().getUserType())
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
                        initDateToView(baseRspObj.getBody().getTaskDetails());
                    }
                });
    }

    private void initDateToView(TaskDetailsBean ta){

        tv_project_name.setText(ta.getProjectName());
        tv_cate.setText(ta.getTaskDictionary().getName());
        tv_link.setText(ta.getTaskDictionary().getName());
        tv_point.setText(ta.getTaskDictionary().getName());
        tv_company_name.setText(ta.getCompanyName());
        tv_people.setText(ta.getSysUser().getNiceName());
    }
}
