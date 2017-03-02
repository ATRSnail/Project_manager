package com.project.project_manager.mvp.interactor.impl;

import com.project.project_manager.listener.RequestCallBack;
import com.project.project_manager.mvp.entity.ProjectObjectBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.interactor.ProjectsInteractor;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.MyUtils;
import com.project.project_manager.utils.TransformUtils;
import com.socks.library.KLog;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class ProjectsInteractorImpl implements ProjectsInteractor<BaseRspObj<ProjectObjectBean>> {

    @Inject
    public ProjectsInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<BaseRspObj<ProjectObjectBean>> listener, int pageNum, String userType, String status, String userId) {

        return RetrofitManager.getInstance(1).getProjectListObservable(pageNum + "", userType, status, userId)
                .compose(TransformUtils.<BaseRspObj<ProjectObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<ProjectObjectBean>>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        listener.onError(MyUtils.analyzeNetworkError(e));
                    }

                    @Override
                    public void onNext(BaseRspObj<ProjectObjectBean> newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }
}
