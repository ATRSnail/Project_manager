package com.project.project_manager.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.BaseNewBean;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/18
 * 某个项目施工列表
 */

public class ProjectListAdapter extends BaseQuickAdapter<BaseNewBean>{
    public ProjectListAdapter(int layoutResId, List<BaseNewBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BaseNewBean baseNewBean) {
        baseViewHolder.setText(R.id.tv_cate,"施工方");
        baseViewHolder.setText(R.id.tv_title,baseNewBean.getTitle());
        baseViewHolder.setText(R.id.tv_content,baseNewBean.getTitle());
    }
}
