package com.project.project_manager.listener;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public interface RequestCallBack<T> {

    void beforeRequest();

    void success(T data);

    void onError(String errorMsg);
}
