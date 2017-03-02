package com.project.project_manager.mvp.ui.view.base;

import com.project.project_manager.common.LoadNewsType;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/18
 */

public interface BaseListView<T> extends BaseView{
    void setList(List<T> newsSummary, @LoadNewsType.checker int loadType);
}
