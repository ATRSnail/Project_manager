package com.project.project_manager.mvp.interactor.impl;

import com.project.project_manager.listener.RequestCallBack;
import com.project.project_manager.mvp.entity.LinkObjectBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.interactor.NewsInteractor;
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
public class LinksInteractorImpl implements NewsInteractor<BaseRspObj<LinkObjectBean>> {

    @Inject
    public LinksInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<BaseRspObj<LinkObjectBean>> listener, int pageNum, String title) {

        return RetrofitManager.getInstance(1).getProjectLinkObservable(pageNum+"",title)
                .compose(TransformUtils.<BaseRspObj<LinkObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<LinkObjectBean>>() {
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
                    public void onNext(BaseRspObj<LinkObjectBean> newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }
}
