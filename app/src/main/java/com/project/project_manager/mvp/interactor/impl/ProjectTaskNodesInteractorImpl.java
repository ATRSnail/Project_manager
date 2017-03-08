package com.project.project_manager.mvp.interactor.impl;

import com.project.project_manager.listener.RequestCallBack;
import com.project.project_manager.mvp.entity.TaskObjectBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.interactor.ProjectTaskNodesInteractor;
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
public class ProjectTaskNodesInteractorImpl implements ProjectTaskNodesInteractor<BaseRspObj<TaskObjectBean>> {

    @Inject
    public ProjectTaskNodesInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<BaseRspObj<TaskObjectBean>> listener, int pageNum,String projectId, String userId, String userType) {

        return RetrofitManager.getInstance(1).getProjectTaskObservable(pageNum + "", projectId, userId,userType)
                .compose(TransformUtils.<BaseRspObj<TaskObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<TaskObjectBean>>() {
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
                    public void onNext(BaseRspObj<TaskObjectBean> newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }
}
