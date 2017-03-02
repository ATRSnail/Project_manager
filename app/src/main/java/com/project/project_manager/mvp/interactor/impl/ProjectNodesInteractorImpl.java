package com.project.project_manager.mvp.interactor.impl;

import com.project.project_manager.listener.RequestCallBack;
import com.project.project_manager.mvp.entity.NodeObjectBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.interactor.ProjectNodesInteractor;
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
public class ProjectNodesInteractorImpl implements ProjectNodesInteractor<BaseRspObj<NodeObjectBean>> {

    @Inject
    public ProjectNodesInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<BaseRspObj<NodeObjectBean>> listener, int pageNum, String userType, String status) {

        return RetrofitManager.getInstance(1).getProjectNodeObservable(pageNum + "", userType, status)
                .compose(TransformUtils.<BaseRspObj<NodeObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<NodeObjectBean>>() {
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
                    public void onNext(BaseRspObj<NodeObjectBean> newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }
}
