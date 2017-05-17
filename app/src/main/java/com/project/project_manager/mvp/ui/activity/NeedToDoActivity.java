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
import com.project.project_manager.mvp.presenter.impl.ProjectsPresenterImpl;
import com.project.project_manager.mvp.ui.activity.base.BaseRefreshActivity;
import com.project.project_manager.mvp.ui.adapter.NeedToDoAdapter;
import com.project.project_manager.mvp.ui.view.base.BaseListView;
import com.project.project_manager.utils.NetUtil;
import com.project.project_manager.weight.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 待办
 */
public class NeedToDoActivity extends BaseRefreshActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseListView<ProjectBean> {


    private List<ProjectBean> been;
    private NeedToDoAdapter needToDoAdapter;

    @Inject
    Activity mActivity;
    @Inject
    ProjectsPresenterImpl mNewsPresenter;

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
        setCustomTitle("待办");
        initRecyclerView();
        initPresenter();
    }

    private void initPresenter() {
        mNewsPresenter.setNewsTypeAndId(pageNum, UsrMgr.getUseType(),"1", UsrMgr.getUseId());
        mNewsPresenter.attachView(this);
        mPresenter = mNewsPresenter;
        mPresenter.onCreate();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        been = new ArrayList<>();
        needToDoAdapter = new NeedToDoAdapter(R.layout.item_line_text, been);
        needToDoAdapter.setOnLoadMoreListener(this);
        needToDoAdapter.setOnRecyclerViewItemClickListener(this);
        needToDoAdapter.openLoadMore(Constants.numPerPage, true);
        mRecyclerView.setAdapter(needToDoAdapter);
    }

    @Override
    public void onItemClick(View view, int i) {
        needToDoAdapter.getItem(i).intentToTask(mActivity);
    }

    @Override
    public void onLoadMoreRequested() {
        mNewsPresenter.loadMore();
    }

    @Override
    public void setList(List<ProjectBean> newsSummary, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                if (newsSummary == null) return;
                needToDoAdapter.setNewData(newsSummary);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                if (newsSummary == null)
                    return;

                if (newsSummary.size() == Constants.numPerPage){
                    needToDoAdapter.notifyDataChangedAfterLoadMore(newsSummary, true);
                }else {
                    needToDoAdapter.notifyDataChangedAfterLoadMore(newsSummary, false);
                    Snackbar.make(mRecyclerView, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                }
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

}
