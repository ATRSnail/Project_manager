package com.project.project_manager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.project_manager.R;
import com.project.project_manager.common.Constants;
import com.project.project_manager.common.LoadNewsType;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.entity.TaskBean;
import com.project.project_manager.mvp.event.ProjectRefEvent;
import com.project.project_manager.mvp.presenter.impl.UndoTasksPresenterImpl;
import com.project.project_manager.mvp.ui.activity.NeedToDoActivity;
import com.project.project_manager.mvp.ui.adapter.UndoTaskAdapter;
import com.project.project_manager.mvp.ui.fragment.base.BaseRefreshFragment;
import com.project.project_manager.mvp.ui.view.base.BaseListView;
import com.project.project_manager.utils.NetUtil;
import com.project.project_manager.utils.UT;
import com.project.project_manager.weight.RecyclerViewDivider;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/3
 */

public class HomeFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnRecyclerViewItemClickListener, View.OnClickListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseListView<TaskBean> {

    @Inject
    Activity mActivity;
    @Inject
    UndoTasksPresenterImpl mNewsPresenter;
    private UndoTaskAdapter mFileAdapter;
    private List<TaskBean> fileBeen;
    private TaskBean undoneTaskBean;
    private View mTitleHeader;
    private View mCategyView;

    @Override
    public void onRefreshView() {
        mNewsPresenter.refreshData();
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        EventBus.getDefault().register(this);
        initHeadView();
        clickEvent();
        initRecycleView();
        initPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    private void initPresenter() {
        mNewsPresenter.setNewsTypeAndId(pageNum, UsrMgr.getUseId(), UsrMgr.getUseInfo().getUserType());
        mNewsPresenter.attachView(this);
        mPresenter = mNewsPresenter;
        mPresenter.onCreate();
    }

    private void initHeadView() {
        mCategyView = View.inflate(mActivity, R.layout.item_head_categy, null);
        mTitleHeader = View.inflate(mActivity, R.layout.layout_head_text, null);
    }


    private void initRecycleView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fileBeen = new ArrayList<>();
        mFileAdapter = new UndoTaskAdapter(R.layout.item_un_do_task, fileBeen);
        mFileAdapter.addHeaderView(mCategyView);
        mFileAdapter.addHeaderView(mTitleHeader);
        mFileAdapter.setOnRecyclerViewItemClickListener(this);
        mFileAdapter.openLoadMore(Constants.numPerPage, true);
        mRecyclerView.setAdapter(mFileAdapter);
    }

    @Override
    public void onItemClick(View view, int i) {
        undoneTaskBean = mFileAdapter.getData().get(i);
        if (UsrMgr.getUseType().equals("0")) {
            undoneTaskBean.intentToNext(mActivity);
        } else {
            undoneTaskBean.intentToNextT(mActivity);
        }
    }

    public void clickEvent() {
        mCategyView.findViewById(R.id.ll_fill).setOnClickListener(this);
        mCategyView.findViewById(R.id.ll_meeting).setOnClickListener(this);
        mCategyView.findViewById(R.id.ll_talk).setOnClickListener(this);
        mCategyView.findViewById(R.id.ll_repate).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fill:
                startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
            case R.id.ll_meeting:
                UT.show("敬请期待");
            //    startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
            case R.id.ll_talk:
                UT.show("敬请期待");
             //   startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
            case R.id.ll_repate:
                UT.show("敬请期待");
              //  startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mNewsPresenter.loadMore();
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

    @Override
    public void setList(List<TaskBean> newsSummary, @LoadNewsType.checker int loadType) {

        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                if (newsSummary == null)
                    newsSummary = new ArrayList<>();
                KLog.a("%%%%%" + newsSummary.toString());
                mFileAdapter.setNewData(newsSummary);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:

                if (newsSummary == null)
                    return;

                if (newsSummary.size() == Constants.numPerPage) {
                    mFileAdapter.notifyDataChangedAfterLoadMore(newsSummary, true);
                } else {
                    mFileAdapter.notifyDataChangedAfterLoadMore(newsSummary, false);
                    Snackbar.make(mRecyclerView, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

    @Subscribe
    public void onEventMainThread(ProjectRefEvent event) {
        mNewsPresenter.refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
