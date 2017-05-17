package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.container.GridViewForScrollView;
import com.jaiky.imagespickers.container.SimpleImageAdapter;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
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
public class VerifyDetailActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.btn_pass)
    Button btn_pass;
    @BindView(R.id.btn_rebut)
    Button btn_rebut;
    @BindView(R.id.et_option)
    EditText et_option;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    private int pos;

    private TaskDetailsBean taskDetails;
    private ArrayList<String> imgPathList = new ArrayList<>();
    private ArrayList<String> vidPathList = new ArrayList<>();
    private SimpleImageAdapter containerAdapter;

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

        initIntentDate();
        setCustomTitle(taskBean.getNodeName());
        initClick();

        initDate();
        setContainer(llContainer, 3, false);
    }

    private void initClick() {
        btn_pass.setOnClickListener(this);
        btn_rebut.setOnClickListener(this);
    }

    private void initIntentDate() {
        taskBean = (TaskBean) getIntent().getSerializableExtra(TASK_KEY);
        pos = getIntent().getIntExtra("pos",-1);
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
                        taskDetails = baseRspObj.getBody().getTaskDetails();
                        List<ResourceBean> resources = taskDetails.getList();
                        if (resources.size() > 0) {
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
     * @param ta
     */
    private void initDateToView(TaskDetailsBean ta) {
        tv_project_name.setText(ta.getProjectName());
        tv_cate.setText(setCate(ta.getCompanyType()));
        tv_link.setText(ta.getTaskDictionary().getName());
        tv_point.setText(ta.getNodeName());
        tv_company_name.setText(ta.getCompanyName());
        tv_people.setText(ta.getSysUser().getNiceName());
        et_option.setText(ta.getOpinion());
        initImgsView(imgPathList);
        initVideoView(vidPathList);
        initStatus(taskBean.getStatus());
    }

    /**
     * 初始化videoView
     *
     * @param pathList
     */
    private void initVideoView(List<String> pathList) {
        KLog.a("vidPathList---->"+vidPathList.toString());
        if (pathList.size() <= 0) {
            mJCVideoPlayerStandard.setVisibility(View.GONE);
            return;
        }
        mJCVideoPlayerStandard.setUp(
                pathList.get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "");

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
     * @param status
     */
    private void initStatus(String status){
        if (status.equals(TYPE_NO_COMPLETE)){//未完成
            btn_rebut.setVisibility(View.VISIBLE);
            btn_pass.setVisibility(View.VISIBLE);
            et_option.setEnabled(true);
        }
        if (status.equals(TYPE_ALLOCAD)){//已分派
            btn_rebut.setVisibility(View.GONE);
            btn_pass.setVisibility(View.GONE);
            et_option.setEnabled(false);
        }
        if (status.equals(TYPE_LOCKED)){//已锁定
            btn_rebut.setVisibility(View.GONE);
            btn_pass.setVisibility(View.GONE);
            et_option.setEnabled(false);
        }
        if (status.equals(TYPE_UNAUDITED)){ //未审核
            btn_rebut.setVisibility(View.VISIBLE);
            btn_pass.setVisibility(View.VISIBLE);
            et_option.setEnabled(true);
        }
        if (status.equals(TYPE_REJECT)){//驳回
            btn_rebut.setVisibility(View.GONE);
            btn_pass.setVisibility(View.GONE);
            et_option.setEnabled(false);
        }
        if (status.equals(TYPE_APPLY)){//审核通过
            btn_rebut.setVisibility(View.GONE);
            btn_pass.setVisibility(View.GONE);
            et_option.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pass:
                getAuditStatus("5");
                break;
            case R.id.btn_rebut:
                getAuditStatus("4");
                break;
        }
    }

    //人物任务状态(字典)-0未完成,1已分派,2已锁定,3未审核,4驳回,5审核通过
    private void getAuditStatus(String status) {
        RetrofitManager.getInstance(1).getAuditStatus(taskDetails.getTaskUserId() + "", status, et_option.getText().toString().trim(), taskBean.getProjectTaskId() + "")
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj baseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + baseRspObj.toString());
                        UT.show(baseRspObj.getHead().getRspMsg());
                        EventBus.getDefault().post(new ProjectRefEvent());
                        finish();
                    }
                });
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
            containerAdapter = new SimpleImageAdapter(container, isDelete, rowImageCount, ScreenUtils.getScreenWidth(mActivity));
            gvView.setAdapter(containerAdapter);
            container.addView(gvView);
        } else {
            GridViewForScrollView gvView = (GridViewForScrollView) container.getChildAt(0);
            containerAdapter = (SimpleImageAdapter) gvView.getAdapter();
        }
    }
}
