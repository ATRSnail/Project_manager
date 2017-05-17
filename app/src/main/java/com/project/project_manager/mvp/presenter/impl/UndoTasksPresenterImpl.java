package com.project.project_manager.mvp.presenter.impl;

import com.project.project_manager.common.LoadNewsType;
import com.project.project_manager.mvp.entity.TaskBean;
import com.project.project_manager.mvp.entity.UndoneTaskObjBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.interactor.ProjectNodesInteractor;
import com.project.project_manager.mvp.interactor.impl.UnDoTasksInteractorImpl;
import com.project.project_manager.mvp.presenter.ProjectNodePresenter;
import com.project.project_manager.mvp.presenter.base.BasePresenterImpl;
import com.project.project_manager.mvp.ui.view.base.BaseListView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class UndoTasksPresenterImpl extends BasePresenterImpl<BaseListView<TaskBean>, BaseRspObj<UndoneTaskObjBean>>
        implements ProjectNodePresenter {

    private ProjectNodesInteractor mNewsInteractor;
    private int pageNum;
    private String taskCode;
    private String projectId;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public UndoTasksPresenterImpl(UnDoTasksInteractorImpl newsInteractor) {
        this.mNewsInteractor = newsInteractor;
    }

    @Override
    public void onCreate() {
        if (mView != null) {
            loadNewsData();
        }
    }

    @Override
    public void beforeRequest() {
        if (!misFirstLoad) {
            mView.showProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if (mView != null) {
            int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_ERROR : LoadNewsType.TYPE_LOAD_MORE_ERROR;
            mView.setList(null, loadType);
        }
    }

    @Override
    public void success(BaseRspObj<UndoneTaskObjBean> data) {
        misFirstLoad = true;
        KLog.a("ddd----success");

        if (mView == null)
            return;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;
        mView.hideProgress();
        if (data == null || data.getBody() == null) {
            mView.setList(null, loadType);
            return;
        }
        pageNum++;
        mView.setList((List) data.getBody().getUndoneTask(), loadType);


    }

    @Override
    public void setNewsTypeAndId(int pageNum, String projectId, String taskCode) {
        this.pageNum = pageNum;
        this.projectId = projectId;
        this.taskCode = taskCode;
    }

    @Override
    public void refreshData() {
        pageNum = 1;
        mIsRefresh = true;
        loadNewsData();
    }

    @Override
    public void loadMore() {
        mIsRefresh = false;
        loadNewsData();
    }

    private void loadNewsData() {
        mSubscription = mNewsInteractor.loadNews(this, pageNum, projectId, taskCode);
    }
}