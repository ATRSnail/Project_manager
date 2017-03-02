package com.project.project_manager.mvp.interactor.impl;

import com.project.project_manager.listener.RequestCallBack;
import com.project.project_manager.mvp.entity.response.RspNewsBean;
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
public class NewsInteractorImpl implements NewsInteractor<RspNewsBean> {

    @Inject
    public NewsInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<RspNewsBean> listener, int pageNum, String title) {

        return RetrofitManager.getInstance(1).getNewsListObservable(pageNum,title)
                .compose(TransformUtils.<RspNewsBean>defaultSchedulers())
                .subscribe(new Subscriber<RspNewsBean>() {
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
                    public void onNext(RspNewsBean newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }
}
