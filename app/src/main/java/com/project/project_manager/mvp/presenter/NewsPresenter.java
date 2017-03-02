package com.project.project_manager.mvp.presenter;

import com.project.project_manager.mvp.presenter.base.BaseListPresenter;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsPresenter extends BaseListPresenter {

    void setNewsTypeAndId(int pageNum, String title);


}
