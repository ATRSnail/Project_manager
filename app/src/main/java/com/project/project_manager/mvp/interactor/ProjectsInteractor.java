package com.project.project_manager.mvp.interactor;

import com.project.project_manager.listener.RequestCallBack;

import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface ProjectsInteractor<T> {
    Subscription loadNews(RequestCallBack<T> listener, int pageNum, String userType, String status, String userId);
}
