package com.project.project_manager.mvp.presenter;

import com.project.project_manager.mvp.presenter.base.BaseListPresenter;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public interface ProjectsPresenter extends BaseListPresenter {
    void setNewsTypeAndId(int pageNum, String userType, String status, String userId);
}
