package com.project.project_manager.mvp.presenter.impl;

import com.project.project_manager.common.LoadNewsType;
import com.project.project_manager.mvp.entity.ProjectBean;
import com.project.project_manager.mvp.entity.ProjectObjectBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.interactor.ProjectsInteractor;
import com.project.project_manager.mvp.interactor.impl.ProjectsInteractorImpl;
import com.project.project_manager.mvp.presenter.ProjectsPresenter;
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

public class ProjectsPresenterImpl extends BasePresenterImpl<BaseListView<ProjectBean>, BaseRspObj<ProjectObjectBean>>
        implements ProjectsPresenter {

    private ProjectsInteractor mNewsInteractor;
    private int pageNum;
    private String userId;
    private String userType;
    private String status;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public ProjectsPresenterImpl(ProjectsInteractorImpl newsInteractor) {
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
    public void success(BaseRspObj<ProjectObjectBean> data) {
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
        mView.setList((List) data.getBody().getProjectList(), loadType);


    }

    @Override
    public void setNewsTypeAndId(int pageNum, String userType, String status, String userId) {
        this.pageNum = pageNum;
        this.userType = userType;
        this.status = status;
        this.userId = userId;
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
        mSubscription = mNewsInteractor.loadNews(this, pageNum, userType, status, userId);
    }
}