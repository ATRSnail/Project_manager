package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.project_manager.R;
import com.project.project_manager.common.Constants;
import com.project.project_manager.common.LoadNewsType;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.entity.ProjectBean;
import com.project.project_manager.mvp.entity.TaskBean;
import com.project.project_manager.mvp.event.ProjectRefEvent;
import com.project.project_manager.mvp.presenter.impl.ProjectTaskNodesPresenterImpl;
import com.project.project_manager.mvp.ui.activity.base.BaseRefreshActivity;
import com.project.project_manager.mvp.ui.adapter.ProjectListAdapter;
import com.project.project_manager.mvp.ui.view.base.BaseListView;
import com.project.project_manager.utils.MyUtils;
import com.project.project_manager.utils.NetUtil;
import com.project.project_manager.utils.UT;
import com.project.project_manager.weight.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.project.project_manager.common.ProStatusType.TYPE_NO_COMPLETE;
import static com.project.project_manager.mvp.entity.ProjectBean.PROJECT_KEY;

public class ProjectItemsActivity extends BaseRefreshActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseListView<TaskBean> {

    private List<TaskBean> been;
    private ProjectListAdapter mProjectListAdapter;

    private ProjectBean projectBean;
    private TaskBean taskBean;
    @Inject
    Activity mActivity;
    @Inject
    ProjectTaskNodesPresenterImpl mNewsPresenter;

    @Override
    public void onRefreshView() {
        mNewsPresenter.refreshData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_need_to_do;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        EventBus.getDefault().register(this);
        if (UsrMgr.getUseType().equals("0"))
            setRightBtnClick(new addNeedBtnClick());
        initIntentDate();
        setCustomTitle(projectBean.getProjectName());
        initRecyclerView();
        initPresenter();

    }

    private void initIntentDate() {
        projectBean = (ProjectBean) getIntent().getSerializableExtra(PROJECT_KEY);
    }

    private void initPresenter() {
        mNewsPresenter.setNewsTypeAndId(pageNum, projectBean.getId() + "", UsrMgr.getUseId(), UsrMgr.getUseInfo().getUserType());
        mNewsPresenter.attachView(this);
        mPresenter = mNewsPresenter;
        mPresenter.onCreate();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, MyUtils.dp2px(mActivity, 15), getResources().getColor(R.color.red)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        been = new ArrayList<>();
        mProjectListAdapter = new ProjectListAdapter(R.layout.item_project_item, been);
        mProjectListAdapter.setOnLoadMoreListener(this);
        mProjectListAdapter.setOnRecyclerViewItemClickListener(this);
        mProjectListAdapter.openLoadMore(Constants.numPerPage, true);
        mRecyclerView.setAdapter(mProjectListAdapter);
    }

    @Override
    public void onItemClick(View view, int i) {
        taskBean = mProjectListAdapter.getItem(i);
        if (UsrMgr.getUseType().equals("1") && taskBean.getStatus().equals(TYPE_NO_COMPLETE)) {
            UT.show("请先锁定");
            return;
        }

        if (UsrMgr.getUseType().equals("0")) {
            taskBean.intentToNext(mActivity);
        } else {
            taskBean.intentToNextT(mActivity);
        }

    }

    @Subscribe
    public void onEventMainThread(ProjectRefEvent event) {
        mNewsPresenter.refreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        mNewsPresenter.loadMore();
    }

    @Override
    public void setList(List<TaskBean> newsSummary, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                if (newsSummary == null) return;
                mProjectListAdapter.setNewData(newsSummary);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:

                if (newsSummary == null)
                    return;

                if (newsSummary.size() == Constants.numPerPage) {
                    mProjectListAdapter.notifyDataChangedAfterLoadMore(newsSummary, true);
                } else {
                    mProjectListAdapter.notifyDataChangedAfterLoadMore(newsSummary, false);
                    Snackbar.make(mRecyclerView, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMsg(String msg) {
        mProgressBar.setVisibility(View.GONE);
        // 网络不可用状态在此之前已经显示了提示信息
        if (NetUtil.isNetworkAvailable()) {
            Snackbar.make(mRecyclerView, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    public static final String PROJECT_ID = "project_id";

    private class addNeedBtnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            projectBean.intentToAdd(mActivity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
