package com.project.project_manager.mvp.presenter.impl;

import com.project.project_manager.common.LoadNewsType;
import com.project.project_manager.mvp.entity.BaseNewBean;
import com.project.project_manager.mvp.entity.response.RspNewsBean;
import com.project.project_manager.mvp.interactor.NewsInteractor;
import com.project.project_manager.mvp.interactor.impl.NewsInteractorImpl;
import com.project.project_manager.mvp.presenter.NewsPresenter;
import com.project.project_manager.mvp.presenter.base.BasePresenterImpl;
import com.project.project_manager.mvp.ui.view.base.BaseListView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class NewsPresenterImpl extends BasePresenterImpl<BaseListView<List<BaseNewBean>>, RspNewsBean>
        implements NewsPresenter {

    private NewsInteractor mNewsInteractor;
    private int pageNum;
    private String title;
    private String typeId;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public NewsPresenterImpl(NewsInteractorImpl newsInteractor) {
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
    public void success(RspNewsBean data) {
        misFirstLoad = true;
        KLog.a("ddd----success");
        if (data == null || data.getBody() == null) return;
        pageNum++;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;

        if (mView != null) {
            mView.setList((List)data.getBody().getNewsList(), loadType);
            mView.hideProgress();
        }
    }

    @Override
    public void setNewsTypeAndId(int pageNum,String title) {
        this.pageNum = pageNum;
        this.title = title;
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
        mSubscription = mNewsInteractor.loadNews(this, pageNum,title);
    }
}
