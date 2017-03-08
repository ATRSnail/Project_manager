package com.project.project_manager.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.TaskDictionaryBean;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/8
 */

public class PopuItemAdapter extends BaseQuickAdapter<TaskDictionaryBean>{
    public PopuItemAdapter(int layoutResId, List<TaskDictionaryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TaskDictionaryBean taskDictionaryBean) {
       baseViewHolder.setText(R.id.tv_name,taskDictionaryBean.getName());
    }
}
